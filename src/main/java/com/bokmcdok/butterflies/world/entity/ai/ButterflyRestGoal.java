package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

/**
 * Goal used when a butterfly is inactive and wants to rest.
 */
public class ButterflyRestGoal extends MoveToBlockGoal {

    //  The butterfly using this goal.
    private final Butterfly butterfly;

    /**
     * Construction
     * @param mob The instance of the butterfly.
     * @param speedModifier The speed modifier applied when this goal is in progress.
     * @param searchRange The range to search for blocks.
     * @param verticalSearchRange The vertical range to search for blocks.
     */
    public ButterflyRestGoal(Butterfly mob,
                               double speedModifier,
                               int searchRange,
                               int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        this.butterfly = mob;
    }

    /**
     * Stop using if time of day changes to an active time.
     * @return Whether the goal can continue being active.
     */
    @Override
    public boolean canContinueToUse() {
        return !this.butterfly.getIsActive() && this.isValidTarget(this.mob.level(), this.blockPos);
    }

    /**
     * Butterflies can only rest when inactive.
     * @return TRUE if the butterfly can pollinate right now.
     */
    @Override
    public boolean canUse() {
        if (!this.butterfly.getIsActive()) {
            nextStartTick = 0;
            return super.canUse();
        }

        return false;
    }

    /**
     * Start using the goal - ensure the butterfly is not landed.
     */
    @Override
    public void start() {
        super.start();
    }

    /**
     * Ensure the butterfly isn't in the landed state when the goal ends.
     */
    @Override
    public void stop() {
        this.butterfly.setLanded(false);
        super.stop();
    }

    /**
     * Update the butterfly after it has landed.
     */
    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            this.butterfly.setLanded(true);
        } else {
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                moveMobToBlock();
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
        return "Rest / Target = [" + this.getMoveToTarget() +
                "] / Position = [" + butterfly.blockPosition() +
                "] / Reached = [" + this.isReachedTarget() +
                "] / Landed = [" + butterfly.getIsLanded() +
                "]";
    }

    /**
     * Overrides to return TRUE if any valid block is below the butterfly.
     * @return TRUE if the butterfly can land.
     */
    protected boolean isReachedTarget() {
        Level level = this.butterfly.level();
        BlockPos position = this.butterfly.blockPosition();
        return this.butterfly.isValidLandingBlock(level.getBlockState(position.below()));
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

        return blockPos.getY() < this.butterfly.getBlockY() &&
                levelReader.isEmptyBlock(blockPos) &&
                this.butterfly.isValidLandingBlock(levelReader.getBlockState(blockPos.below()));
    }

    /**
     * Override so the butterfly moves to the specified target block, not the
     * block above it.
     */
    protected void moveMobToBlock() {
        this.mob.getNavigation().moveTo(
                this.blockPos.getX() + 0.5,
                this.blockPos.getY(),
                this.blockPos.getZ() + 0.5,
                this.speedModifier);
    }
}
