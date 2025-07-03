package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        return !this.butterfly.getIsActive() && this.isValidTarget(this.mob.getLevel(), this.blockPos);
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
        if (!butterfly.getIsLanded()) {
            if (this.isReachedTarget()) {
                --this.tryTicks;
                this.mob.getNavigation().stop();
                this.butterfly.setLanded(true);
            } else {
                ++this.tryTicks;
                if (this.shouldRecalculatePath()) {
                    moveMobToBlock();
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
        return "Rest / Target = [" + this.getMoveToTarget() +
                "] / Position = [" + butterfly.blockPosition() +
                "] / Reached = [" + this.isReachedTarget() +
                "] / Landed = [" + butterfly.getIsLanded() +
                "] / Direction = [" + butterfly.getLandedDirection() +
                "]";
    }

    /**
     * Overrides to return TRUE if any valid block is below the butterfly.
     * @return TRUE if the butterfly can land.
     */
    protected boolean isReachedTarget() {
        Level level = this.butterfly.getLevel();
        BlockPos position = this.butterfly.blockPosition();

        //  Land on top of a block.
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.below()))) {
            this.blockPos = position.below();
            this.butterfly.setLandedDirection(Direction.DOWN);
            return true;
        }

        //  Land underneath a block.
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.above()))) {
            this.blockPos = position.above();
            this.butterfly.setLandedDirection(Direction.UP);
            return true;
        }

        // Land north of a block
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.north()))) {
            this.blockPos = position.north();
            this.butterfly.setLandedDirection(Direction.NORTH);
            return true;
        }

        // Land south of a block
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.south()))) {
            this.blockPos = position.south();
            this.butterfly.setLandedDirection(Direction.SOUTH);
            return true;
        }

        // Land east of a block
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.east()))) {
            this.blockPos = position.east();
            this.butterfly.setLandedDirection(Direction.EAST);
            return true;
        }

        // Land south of a block
        if (this.butterfly.isValidLandingBlock(level.getBlockState(position.west()))) {
            this.blockPos = position.west();
            this.butterfly.setLandedDirection(Direction.WEST);
            return true;
        }

        return false;
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
        return this.butterfly.isValidLandingBlock(levelReader.getBlockState(blockPos));
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
