package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

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
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
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
        this.butterfly.setLanded(false);

        //  Try to find a target.
        BlockPos targetPosition = this.findPos();
        if (targetPosition != null) {
            Path path = this.butterfly.getNavigation().createPath(targetPosition, 1);
            this.butterfly.getNavigation().moveTo(path, 1.0);
        }
    }

    /**
     * Gets a random position for our butterfly to fly to.
     * @return A random position.
     */
    private BlockPos findPos() {
        BlockPos position = butterfly.blockPosition();
        BlockPos targetPosition =  position.offset(butterfly.getRandom().nextInt(8) - 4,
                                                   butterfly.getRandom().nextInt(6) - 2,
                                                   butterfly.getRandom().nextInt(8) - 4);

        //  Make sure this is an air block.
        if (butterfly.level().getBlockState(targetPosition).isAir()) {
            return targetPosition;
        }

        return null;
    }
}
