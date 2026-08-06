package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Goal that enables butterflies to pollinate flowers.
 * TODO: This should probably be two separate goals.
 */
public class ButterflyFeedAndPollinateGoal extends MoveToBlockGoal {

    private static final int POST_EAT_LINGER_TICKS = 10;

    private static final Map<Block, Supplier<Block>> FLOWER_BUDS = Map.ofEntries(
            Map.entry(Blocks.ALLIUM, BlockRegistry.ALLIUM_BUD),
            Map.entry(Blocks.AZURE_BLUET, BlockRegistry.AZURE_BLUET_BUD),
            Map.entry(Blocks.BLUE_ORCHID, BlockRegistry.BLUE_ORCHID_BUD),
            Map.entry(Blocks.CORNFLOWER, BlockRegistry.CORNFLOWER_BUD),
            Map.entry(Blocks.DANDELION, BlockRegistry.DANDELION_BUD),
            Map.entry(Blocks.LILY_OF_THE_VALLEY, BlockRegistry.LILY_OF_THE_VALLEY_BUD),
            Map.entry(Blocks.ORANGE_TULIP, BlockRegistry.ORANGE_TULIP_BUD),
            Map.entry(Blocks.OXEYE_DAISY, BlockRegistry.OXEYE_DAISY_BUD),
            Map.entry(Blocks.PINK_TULIP, BlockRegistry.PINK_TULIP_BUD),
            Map.entry(Blocks.POPPY, BlockRegistry.POPPY_BUD),
            Map.entry(Blocks.RED_TULIP, BlockRegistry.RED_TULIP_BUD),
            Map.entry(Blocks.TORCHFLOWER, () -> Blocks.TORCHFLOWER_CROP),
            Map.entry(Blocks.WHITE_TULIP, BlockRegistry.WHITE_TULIP_BUD),
            Map.entry(Blocks.WITHER_ROSE, BlockRegistry.WITHER_ROSE_BUD),
            Map.entry(Blocks.SWEET_BERRY_BUSH, () -> Blocks.SWEET_BERRY_BUSH)
    );

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private final Block foodSourceBlock;
    private final Item foodSourceItem;

    // The RNG.
    public final RandomSource random;

    // Has pollination been attempted yet?
    public boolean hasTried;
    private int postEatTicks;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    @SuppressWarnings("deprecation")
    public ButterflyFeedAndPollinateGoal(Butterfly mob,
                                         double speedModifier,
                                         int searchRange,
                                         int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        butterfly = mob;

        ButterflyData data = ButterflyRegistry.getEntry(butterfly.getButterflyIndex());
        if (data != null) {
            foodSourceBlock = BuiltInRegistries.BLOCK.get(data.foodBlock());
            foodSourceItem = BuiltInRegistries.ITEM.get(data.foodItem());
        } else {
            foodSourceBlock = null;
            foodSourceItem = null;
        }

        random = butterfly.getRandom();
    }

    /**
     * Increase the accepted distance.
     * @return A distance of 2 blocks.
     */
    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    /**
     * Stop using if time of day changes to inactive.
     * @return Whether the goal can continue being active.
     */
    @Override
    public boolean canContinueToUse() {
        if (!butterfly.getIsActive()) {
            return false;
        }

        if (hasTried) {
            return postEatTicks > 0;
        }

        return super.canContinueToUse();
    }

    /**
     * Butterflies can only pollinate when active.
     * @return TRUE if the butterfly can pollinate right now.
     */
    @Override
    public boolean canUse() {
        return foodSourceBlock != null
                && foodSourceItem != null
                && butterfly.getIsActive()
                && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        hasTried = false;
        postEatTicks = 0;
        super.start();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        super.tick();

        if (!isReachedTarget()) {
            return;
        }

        // Don't stay in the landed state for too long.
        Vec3 deltaMovement = butterfly.getDeltaMovement();
        butterfly.setDeltaMovement(0.0, deltaMovement.y, 0.0);

        if (!hasTried) {
            hasTried = true;
            postEatTicks = POST_EAT_LINGER_TICKS;

            tryEatFromFeeder();
            tryPollinateFlower();
        } else if (postEatTicks > 0) {
            --postEatTicks;
        }
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Pollinate Flower / Target = [" + getMoveToTarget() +
                "] / Reached Target = [" + isReachedTarget() +
                "] / Attempted to Pollinate = [" + hasTried +
                "] / Num Eggs = [" + butterfly.getNumEggs() +
                "]";
    }

    /**
     * Tells the base goal which blocks are valid targets.
     * @param levelReader Gives access to the level.
     * @param blockPos The block position to check.
     * @return TRUE if the block is a valid target.
     */
    @Override
    protected boolean isValidTarget(@NotNull LevelReader levelReader,
                                    @NotNull BlockPos blockPos) {
        if (foodSourceBlock == null
                || foodSourceItem == null) {
            return false;
        }

        if (! levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        // Butterflies will look for feeders.
        if (butterfly.getNumEggs() == 0
                && levelReader.getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder) {
            if (feeder.getItem(0).is(foodSourceItem)) {
                return true;
            }
        }


        // If this is the butterfly's preferred flower it is always valid.
        BlockState blockState = levelReader.getBlockState(blockPos);
        if (blockState.is(foodSourceBlock)) {
            return true;
        }

        // Butterflies will only fly to other flowers half the time.
        if (blockState.is(BlockTags.SMALL_FLOWERS)) {
            return (random.nextBoolean());
        }

        return false;
    }

    /**
     * Find a good position for a flower to grow.
     * @return A suitable position for a flower to grow, if any.
     */
    private BlockPos findNearestFlowerSpot() {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int yOffset = verticalSearchStart; yOffset <= 2; yOffset = yOffset > 0 ? -yOffset : 1 - yOffset) {
            for(int range = 0; range < 2; ++range) {
                for(int xOffset = 0; xOffset <= range; xOffset = xOffset > 0 ? -xOffset : 1 - xOffset) {
                    for(int zOffset = xOffset < range && xOffset > -range ? range : 0; zOffset <= range; zOffset = zOffset > 0 ? -zOffset : 1 - zOffset) {
                        mutableBlockPos.setWithOffset(blockPos, xOffset, yOffset - 1, zOffset);

                        // Torchflowers require farmland.
                        Block requiredBlock = Blocks.GRASS_BLOCK;
                        Level level = mob.level();
                        if (level.getBlockState(blockPos).is(Blocks.TORCHFLOWER)) {
                            requiredBlock = Blocks.FARMLAND;
                        }

                        if (level.getBlockState(mutableBlockPos).isAir() &&
                            level.getBlockState(mutableBlockPos.below()).is(requiredBlock)) {

                            return mutableBlockPos;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Attempt to eat from a butterfly feeder.
     */
    private void tryEatFromFeeder() {
        Level level = butterfly.level();
        if (!(level.getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder)) {
            return;
        }

        if (!feeder.getItem(0).is(foodSourceItem)) {
            return;
        }

        butterfly.setNumEggs(1);
        feeder.removeItem(0, 1);
    }

    /**
     * Attempt to pollinate a flower.
     */
    private void tryPollinateFlower() {
        Level level = butterfly.level();
        BlockState blockState = level.getBlockState(blockPos);
        Block flowerBlock = blockState.getBlock();
        if (!FLOWER_BUDS.containsKey(flowerBlock)) {
            return;
        }

        if (random.nextInt(5) != 0) {
            return;
        }

        BlockPos spawnPos = findNearestFlowerSpot();
        if (spawnPos == null) {
            return;
        }

        Block budBlock = FLOWER_BUDS.get(flowerBlock).get();
        level.setBlockAndUpdate(spawnPos, budBlock.defaultBlockState());
    }
}
