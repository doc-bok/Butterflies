package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Goal that enables butterflies to pollinate flowers.
 */
public class ButterflyPollinateFlowerGoal extends MoveToBlockGoal {

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private final Block preferredFlowerBlock;
    private final Item preferredFlowerItem;

    // The RNG.
    public final RandomSource random;

    // Has pollination been attempted yet?
    public boolean attemptedToPollinate;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    @SuppressWarnings("deprecation")
    public ButterflyPollinateFlowerGoal(Butterfly mob,
                                        double speedModifier,
                                        int searchRange,
                                        int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        this.butterfly = mob;

        ButterflyData data = ButterflyData.getEntry(this.butterfly.getButterflyIndex());
        if (data != null) {
            this.preferredFlowerBlock = BuiltInRegistries.BLOCK.get(data.preferredFlower());
            this.preferredFlowerItem = BuiltInRegistries.ITEM.get(data.preferredFlower());
        } else {
            this.preferredFlowerBlock = null;
            this.preferredFlowerItem = null;
        }

        this.random = this.butterfly.getRandom();
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
        return this.butterfly.getIsActive() && super.canContinueToUse();
    }

    /**
     * Butterflies can only pollinate when active.
     * @return TRUE if the butterfly can pollinate right now.
     */
    @Override
    public boolean canUse() {
        return this.butterfly.getIsActive() && super.canUse();
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        attemptedToPollinate = false;
        super.start();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget()) {

            // Don't stay in the landed state for too long.
            this.tryTicks -= 11;
            Vec3 deltaMovement = this.butterfly.getDeltaMovement();
            this.butterfly.setDeltaMovement(0.0, deltaMovement.y, 0.0);

            if (!attemptedToPollinate) {
                attemptedToPollinate = true;

                if (butterfly.level().getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder) {
                    if (feeder.getItem(0).is(preferredFlowerItem)) {
                        butterfly.setNumEggs(1);
                        feeder.removeItem(0, 1);
                    }
                } else {
                    if (this.random.nextInt() % 5 == 0) {
                        BlockPos spawnPos = findNearestFlowerSpot();
                        if (spawnPos != null) {
                            BlockState blockState = this.mob.level().getBlockState(this.blockPos);
                            Block budBlock = getFlowerBud(blockState.getBlock());
                            if (budBlock != null) {
                                this.mob.level().setBlockAndUpdate(spawnPos, budBlock.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Pollinate Flower / Target = [" + this.getMoveToTarget() +
                "] / Reached Target = [" + this.isReachedTarget() +
                "] / Attempted to Pollinate = [" + this.attemptedToPollinate +
                "] / Num Eggs = [" + this.butterfly.getNumEggs() +
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
        if (! levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        // Butterflies will look for feeders.
        if (butterfly.getNumEggs() == 0 &&
                levelReader.getBlockEntity(blockPos) instanceof ButterflyFeederEntity feeder) {
            if (feeder.getItem(0).is(preferredFlowerItem)) {
                return true;
            }
        }

        BlockState blockState = levelReader.getBlockState(blockPos);

        // If this is the butterfly's preferred flower it is always valid.
        if (blockState.is(this.preferredFlowerBlock)) {
            return true;
        }

        if (blockState.is(BlockTags.SMALL_FLOWERS)) {

            // Butterflies will only fly to other flowers 50% of the time.
            return (this.random.nextInt() % 2 == 0);
        }

        return false;
    }

    /**
     * Find a good position for a flower to grow.
     * @return A suitable position for a flower to grow, if any.
     */
    private BlockPos findNearestFlowerSpot() {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int yOffset = this.verticalSearchStart; yOffset <= 2; yOffset = yOffset > 0 ? -yOffset : 1 - yOffset) {
            for(int range = 0; range < 2; ++range) {
                for(int xOffset = 0; xOffset <= range; xOffset = xOffset > 0 ? -xOffset : 1 - xOffset) {
                    for(int zOffset = xOffset < range && xOffset > -range ? range : 0; zOffset <= range; zOffset = zOffset > 0 ? -zOffset : 1 - zOffset) {
                        mutableBlockPos.setWithOffset(this.blockPos, xOffset, yOffset - 1, zOffset);

                        // Torchflowers require farmland.
                        Block requiredBlock = Blocks.GRASS_BLOCK;
                        if (this.mob.level().getBlockState(this.blockPos).is(Blocks.TORCHFLOWER)) {
                            requiredBlock = Blocks.FARMLAND;
                        }

                        if (this.mob.level().getBlockState(mutableBlockPos).isAir() &&
                            this.mob.level().getBlockState(mutableBlockPos.below()).is(requiredBlock)) {

                            return mutableBlockPos;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the flower bud for the specified flower block.
     * @param flowerBlock The flower we are trying to seed.
     * @return The bud block for the specified flower, if any.
     */
    private Block getFlowerBud(Block flowerBlock) {
        if (flowerBlock == Blocks.ALLIUM) {
            return butterfly.getBlockRegistry().getAlliumBud().get();
        }

        if (flowerBlock == Blocks.AZURE_BLUET) {
            return butterfly.getBlockRegistry().getAzureBluetBud().get();
        }

        if (flowerBlock == Blocks.BLUE_ORCHID) {
            return butterfly.getBlockRegistry().getBlueOrchidBud().get();
        }

        if (flowerBlock == Blocks.CORNFLOWER) {
            return butterfly.getBlockRegistry().getCornflowerBud().get();
        }

        if (flowerBlock == Blocks.DANDELION) {
            return butterfly.getBlockRegistry().getDandelionBud().get();
        }

        if (flowerBlock == Blocks.LILY_OF_THE_VALLEY) {
            return butterfly.getBlockRegistry().getLilyOfTheValleyBud().get();
        }

        if (flowerBlock == Blocks.ORANGE_TULIP) {
            return butterfly.getBlockRegistry().getOrangeTulipBud().get();
        }

        if (flowerBlock == Blocks.OXEYE_DAISY) {
            return butterfly.getBlockRegistry().getOxeyeDaisyBud().get();
        }

        if (flowerBlock == Blocks.PINK_TULIP) {
            return butterfly.getBlockRegistry().getPinkTulipBud().get();
        }

        if (flowerBlock == Blocks.POPPY) {
            return butterfly.getBlockRegistry().getPoppyBud().get();
        }

        if (flowerBlock == Blocks.RED_TULIP) {
            return butterfly.getBlockRegistry().getRedTulipBud().get();
        }

        if (flowerBlock == Blocks.TORCHFLOWER) {
            return Blocks.TORCHFLOWER_CROP;
        }

        if (flowerBlock == Blocks.WHITE_TULIP) {
            return butterfly.getBlockRegistry().getWhiteTulipBud().get();
        }

        if (flowerBlock == Blocks.WITHER_ROSE) {
            return butterfly.getBlockRegistry().getWitherRoseBud().get();
        }

        if (flowerBlock == Blocks.SWEET_BERRY_BUSH) {
            return Blocks.SWEET_BERRY_BUSH;
        }

        return null;
    }
}
