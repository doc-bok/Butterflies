package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;

/**
 * A goal for butterflies that want to mate.
 */
public class ButterflyMatingGoal extends MoveTowardsTargetGoal {

    // The squared distance at which butterflies can mate.
    private static final double MATING_DISTANCE_SQUARED = 2.0 * 2.0;

    // The butterfly that owns the goal.
    private final Butterfly butterfly;

    /**
     * Construction
     * @param pathfinderMob The mob that owns this goal.
     * @param speedModifier The speed modifier applied when using this goal.
     * @param within The distance within which to search for a target.
     */
    public ButterflyMatingGoal(Butterfly pathfinderMob, double speedModifier, float within) {
        super(pathfinderMob, speedModifier, within);
        this.butterfly = pathfinderMob;
    }

    /**
     * Can only use the goal if butterflies have eggs that need fertilising.
     * @return TRUE if the butterfly can mate.
     */
    @Override
    public boolean canUse() {
        return this.butterfly.getNumEggs() > 0 &&
               !this.butterfly.getIsFertile() &&
               super.canUse();
    }

    /**
     * Do the actual mating if the butterflies get close enough to each other.
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.butterfly.getIsFertile()) {
            LivingEntity target = this.butterfly.getTarget();
            if (target != null && this.butterfly.distanceToSqr(target) < MATING_DISTANCE_SQUARED) {
                this.butterfly.setIsFertile(true);
                this.butterfly.setInLove(null);
            }
        }
    }
}
