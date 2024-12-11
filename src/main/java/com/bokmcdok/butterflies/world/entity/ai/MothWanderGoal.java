package com.bokmcdok.butterflies.world.entity.ai;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MothWanderGoal extends ButterflyWanderGoal {

    /**
     * Construction - set this to a movement goal.
     * @param butterfly     The entity this goal belongs to.
     * @param speedModifier The speed modifier to apply to this goal.
     */
    public MothWanderGoal(Butterfly butterfly, double speedModifier) {
        super(butterfly, speedModifier);
    }

    /**
     * Used for debug information.
     * @return The name of the goal.
     */
    @NotNull
    @Override
    public String toString() {
        return "Wander (Moth)";
    }

    /**
     * Moths will tend toward brighter areas.
     * @return The target position, if a valid position is found.
     */
    @Nullable
    @Override
    protected Vec3 getPosition() {
        Vec3 pos = super.getPosition();

        if (pos != null) {
            int targetBrightness = this.mob.level().getRawBrightness(new BlockPos((int)pos.x(), (int)pos.y(), (int)pos.z()), 0);
            int localBrightness = this.mob.level().getRawBrightness(this.mob.blockPosition(), 0);

            if (targetBrightness < localBrightness) {
                pos = super.getPosition();
            }
        }

        return pos;
    }
}
