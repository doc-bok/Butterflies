package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * Wander goal to determine the position a butterfly will move to.
 */
public class ButterflyWanderGoal extends Goal {
    private final Butterfly butterfly;

    /**
     * Construction - set this to a movement goal.
     * @param butterfly The entity this goal belongs to.
     */
    public ButterflyWanderGoal(Butterfly butterfly) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.butterfly = butterfly;
    }

    /**
     * Returns TRUE if we can keep using this goal.
     * @return Whether we can continue to use the goal.
     */
    @Override
    public boolean canContinueToUse() {
        return this.butterfly.getNavigation().isInProgress();
    }


    /**
     * Returns TRUE if we can use this goal.
     * @return Whether we can use the goal.
     */
    @Override
    public boolean canUse() {
        return this.butterfly.getNavigation().isDone();
    }

    /**
     * Start using the goal - sets a target for our navigation.
     */
    @Override
    public void start() {
        Vec3 vec3 = this.findPos();
        this.butterfly.getNavigation().moveTo(this.butterfly.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0);

    }

    /**
     * Gets a random position for our butterfly to fly to.
     * @return A random position.
     */
    @NotNull
    private Vec3 findPos() {
        Vec3 position = butterfly.position();
        return position.add(butterfly.getRandom().nextInt(8) - 4,
                            butterfly.getRandom().nextInt(6) - 2,
                            butterfly.getRandom().nextInt(8) - 4);
    }
}
