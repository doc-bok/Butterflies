package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import org.jetbrains.annotations.NotNull;

/**
 * Wander goal to determine the position a butterfly will move to.
 */
public class ButterflyWanderGoal extends WaterAvoidingRandomFlyingGoal {

    /**
     * Construction - set this to a movement goal.
     * @param butterfly The entity this goal belongs to.
     * @param speedModifier The speed modifier to apply to this goal.
     */
    public ButterflyWanderGoal(Butterfly butterfly, double speedModifier) {
        super(butterfly, speedModifier);
        this.setInterval(1);
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Wander (Butterfly)";
    }
}
