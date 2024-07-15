package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
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
        return !this.butterfly.getIsActive() && super.canContinueToUse();
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
        this.butterfly.setLanded(false);
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
        super.tick();

        if (this.isReachedTarget()) {
            Vec3 deltaMovement = this.butterfly.getDeltaMovement();
            this.butterfly.setLanded(true);
            this.butterfly.setDeltaMovement(0.0, deltaMovement.y, 0.0);
        }
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

        if (levelReader.isEmptyBlock(blockPos.above()) &&
                levelReader.getBlockState(blockPos).is(BlockTags.LEAVES)) {
            return blockPos.getY() < this.butterfly.getBlockY();
        }

        return false;
    }

    /**
     * Increase the accepted distance.
     * @return A distance of 2 blocks.
     */
    @Override
    public double acceptedDistance() {
        return 0.5;
    }
}
