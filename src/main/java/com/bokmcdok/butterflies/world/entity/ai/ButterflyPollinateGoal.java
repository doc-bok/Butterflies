package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Goal that enables butterflies to pollinate flowers.
 */
public class ButterflyPollinateGoal extends MoveToBlockGoal {

    private static final int POST_POLLINATION_LINGER_TICKS = 10;

    private static final Map<Block, Supplier<BlockState>> FLOWER_BUD_STATES = Map.ofEntries(
            Map.entry(Blocks.ALLIUM, () -> BlockRegistry.ALLIUM_BUD.get().defaultBlockState()),
            Map.entry(Blocks.AZURE_BLUET, () -> BlockRegistry.AZURE_BLUET_BUD.get().defaultBlockState()),
            Map.entry(Blocks.BLUE_ORCHID, () -> BlockRegistry.BLUE_ORCHID_BUD.get().defaultBlockState()),
            Map.entry(Blocks.CORNFLOWER, () -> BlockRegistry.CORNFLOWER_BUD.get().defaultBlockState()),
            Map.entry(Blocks.DANDELION, () -> BlockRegistry.DANDELION_BUD.get().defaultBlockState()),
            Map.entry(Blocks.LILY_OF_THE_VALLEY, () -> BlockRegistry.LILY_OF_THE_VALLEY_BUD.get().defaultBlockState()),
            Map.entry(Blocks.ORANGE_TULIP, () -> BlockRegistry.ORANGE_TULIP_BUD.get().defaultBlockState()),
            Map.entry(Blocks.OXEYE_DAISY, () -> BlockRegistry.OXEYE_DAISY_BUD.get().defaultBlockState()),
            Map.entry(Blocks.PINK_TULIP, () -> BlockRegistry.PINK_TULIP_BUD.get().defaultBlockState()),
            Map.entry(Blocks.POPPY, () -> BlockRegistry.POPPY_BUD.get().defaultBlockState()),
            Map.entry(Blocks.RED_TULIP, () -> BlockRegistry.RED_TULIP_BUD.get().defaultBlockState()),
            Map.entry(Blocks.WHITE_TULIP, () -> BlockRegistry.WHITE_TULIP_BUD.get().defaultBlockState()),
            Map.entry(Blocks.WITHER_ROSE, () -> BlockRegistry.WITHER_ROSE_BUD.get().defaultBlockState()),
            Map.entry(Blocks.SWEET_BERRY_BUSH, Blocks.SWEET_BERRY_BUSH::defaultBlockState)
    );

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private final Block preferredFlower;

    // The RNG.
    private final Random random;

    // Has pollination been attempted yet?
    private boolean hasPollinatedAtTarget;
    private int postPollinationTicks;

    private boolean allowNonPreferredFlowers;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    public ButterflyPollinateGoal(Butterfly mob,
                                  double speedModifier,
                                  int searchRange,
                                  int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        butterfly = mob;

        ButterflyData data = ButterflyRegistry.getEntry(butterfly.getButterflyIndex());
        if (data != null) {
            preferredFlower = ForgeRegistries.BLOCKS.getValue(data.foodBlock());
        } else {
            preferredFlower = null;
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
        if (!canPollinateNow()) {
            return false;
        }

        if (hasPollinatedAtTarget) {
            return postPollinationTicks > 0;
        }

        return super.canContinueToUse();
    }

    /**
     * Butterflies can only pollinate when active.
     * @return TRUE if the butterfly can pollinate right now.
     */
    @Override
    public boolean canUse() {
        return preferredFlower != null
                && canPollinateNow()
                && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        allowNonPreferredFlowers = random.nextBoolean();
        hasPollinatedAtTarget = false;
        postPollinationTicks = 0;
        super.start();
    }

    /**
     * Reset the state when the goal stops.
     */
    @Override
    public void stop() {
        hasPollinatedAtTarget = false;
        postPollinationTicks = 0;
        super.stop();
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

        if (!hasPollinatedAtTarget) {
            hasPollinatedAtTarget = true;
            postPollinationTicks = POST_POLLINATION_LINGER_TICKS;

            tryPollinateFlower();
        } else if (postPollinationTicks > 0) {
            --postPollinationTicks;
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
                "] / Attempted to Pollinate = [" + hasPollinatedAtTarget +
                "] / Num Eggs = [" + butterfly.getNumEggs() +
                "]";
    }

    /**
     * Tells the base goal which blocks are valid targets.
     * @param levelReader Gives access to the level.
     * @param flowerPos The block position to check.
     * @return TRUE if the block is a valid target.
     */
    @Override
    protected boolean isValidTarget(@NotNull LevelReader levelReader,
                                    @NotNull BlockPos flowerPos) {
        if (preferredFlower == null) {
            return false;
        }

        if (! levelReader.getBlockState(flowerPos.above()).isAir()) {
            return false;
        }

        // If this is the butterfly's preferred flower it is always valid.
        BlockState blockState = levelReader.getBlockState(flowerPos);
        if (blockState.is(preferredFlower)) {
            return true;
        }

        // Butterflies will only fly to other flowers half the time.
        return allowNonPreferredFlowers && blockState.is(BlockTags.SMALL_FLOWERS);
    }

    /**
     * Find a good position for a flower to grow.
     * @return A suitable position for a flower to grow, if any.
     */
    private BlockPos findNearestFlowerSpot(LevelReader level,
                                           Block flowerBlock) {
        BlockPos.MutableBlockPos candidate = new BlockPos.MutableBlockPos();

        for (int yOffset = verticalSearchStart;
             yOffset <= 2;
             yOffset = yOffset > 0 ? -yOffset : 1 - yOffset) {

            for (int range = 0; range < 2; ++range) {
                for (int xOffset = 0; xOffset <= range;
                     xOffset = xOffset > 0 ? -xOffset : 1 - xOffset) {

                    for (int zOffset = xOffset < range && xOffset > -range
                            ? range
                            : 0;
                         zOffset <= range;
                         zOffset = zOffset > 0 ? -zOffset : 1 - zOffset) {

                        candidate.setWithOffset(
                                blockPos,
                                xOffset,
                                yOffset - 1,
                                zOffset
                        );

                        BlockState budState = getBudState(flowerBlock);
                        if (budState != null &&
                                budState.canSurvive(level, candidate)) {
                            return candidate.immutable();
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Attempt to pollinate a flower.
     */
    private void tryPollinateFlower() {
        Level level = butterfly.level;

        if (!canPollinateNow()) {
            return;
        }
        BlockState flowerState = level.getBlockState(blockPos);
        Supplier<BlockState> budSupplier = FLOWER_BUD_STATES.get(flowerState.getBlock());

        if (budSupplier == null || random.nextInt(5) != 0) {
            return;
        }


        BlockState blockState = level.getBlockState(blockPos);
        Block flowerBlock = blockState.getBlock();
        BlockPos spawnPos = findNearestFlowerSpot(level, flowerBlock);
        if (spawnPos == null) {
            return;
        }

        BlockState budState = budSupplier.get();
        if (!budState.canSurvive(level, spawnPos)) {
            return;
        }

        level.setBlock(spawnPos, budState, 3);
    }

    /**
     * Check if the butterfly can pollinate.
     * @return True if the butterfly can pollinate.
     */
    private boolean canPollinateNow() {
        return butterfly.getIsActive();
    }

    /**
     * Helper to get the bud's default block state.
     * @param flowerBlock The base flower block.
     * @return The block state for the related flower bud.
     */
    private BlockState getBudState(Block flowerBlock) {
        return FLOWER_BUD_STATES.get(flowerBlock).get();
    }
}
