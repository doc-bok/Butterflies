package com.bokmcdok.butterflies.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Class for butterfly path navigation. Contains some improvements over the
 * default navigation.
 */
public class ButterflyFlyingPathNavigation extends FlyingPathNavigation {

    /**
     * Construction
     * @param mob The mob this navigation belongs to.
     * @param level The level the mob has spawned within.
     */
    public ButterflyFlyingPathNavigation(Mob mob,
                                         Level level) {
        super(mob, level);

        setCanOpenDoors(false);
        setCanFloat(false);
    }

    /**
     * All destinations are stable for butterflies.
     * @param blockPos The block position of the destination.
     * @return Always TRUE.
     */
    @Override
    public boolean isStableDestination(@NotNull BlockPos blockPos) {
        return true;
    }
}
