package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Goal that enables butterflies to eat crops.
 */
public class ButterflyEatCropGoal extends MoveToBlockGoal {

    // The butterfly using this goal.
    private final Butterfly butterfly;

    // The flower this butterfly prefers.
    private CropBlock foodSource = null;

    // The RNG.
    private final RandomSource random;

    // Has pollination been attempted yet?
    private boolean hasEaten;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    @SuppressWarnings("deprecation")
    public ButterflyEatCropGoal(Butterfly mob,
                                double speedModifier,
                                int searchRange,
                                int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        this.butterfly = mob;

        ButterflyData data = ButterflyData.getEntry(this.butterfly.getButterflyIndex());
        if (data != null) {
            Block potentialFoodSource = BuiltInRegistries.BLOCK.get(data.preferredFlower());
            if (potentialFoodSource instanceof CropBlock cropBlock) {
                this.foodSource = cropBlock;
            }
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
        hasEaten = false;
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

            if (!hasEaten) {
                hasEaten = true;

                BlockState blockState = this.mob.level().getBlockState(this.blockPos);
                if (blockState.is(this.foodSource)) {
                    int age = blockState.getValue(CropBlock.AGE);
                    if (age > 0) {
                        blockState.setValue(CropBlock.AGE, age -1);
                        this.mob.level().setBlockAndUpdate(this.blockPos, blockState);
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
        return "Eat Crop";
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
        if (!levelReader.getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        BlockState blockState = levelReader.getBlockState(blockPos);
        return blockState.is(this.foodSource);
    }
}
