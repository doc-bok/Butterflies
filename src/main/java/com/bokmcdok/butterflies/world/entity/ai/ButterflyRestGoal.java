package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import org.jetbrains.annotations.NotNull;

/**
 * Goal used when a butterfly is inactive and wants to rest.
 */
public class ButterflyRestGoal extends ButterflyLandOnBlockGoal {

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
}
