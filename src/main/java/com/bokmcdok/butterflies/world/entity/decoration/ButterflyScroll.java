package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An entity representing a hanging butterfly scroll.
 */
public class ButterflyScroll extends HangingEntity {

    public static final String NAME = "butterfly_scroll";

    @NotNull
    public static ButterflyScroll create(EntityType<? extends ButterflyScroll> entityType, Level level) {
        return new ButterflyScroll(entityType, level);
    }

    public ButterflyScroll(EntityType<? extends ButterflyScroll> entityType, Level level) {
        super(entityType, level);
    }

    public ButterflyScroll(Level level, BlockPos blockPos, Direction direction) {
        this(EntityTypeRegistry.BUTTERFLY_SCROLL.get(), level);
        this.pos = blockPos;
        this.setDirection(direction);
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 21;
    }

    @Override
    public void dropItem(@Nullable Entity p_31717_) {

    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.ITEM_FRAME_PLACE, 1.0F, 1.0F);
    }
}
