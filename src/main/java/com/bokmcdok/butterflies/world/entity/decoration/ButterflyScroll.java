package com.bokmcdok.butterflies.world.entity.decoration;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ButterflyScroll extends HangingEntity {

    protected ButterflyScroll(EntityType<? extends HangingEntity> p_31703_, Level p_31704_) {
        super(p_31703_, p_31704_);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void dropItem(@Nullable Entity p_31717_) {

    }

    @Override
    public void playPlacementSound() {

    }
}
