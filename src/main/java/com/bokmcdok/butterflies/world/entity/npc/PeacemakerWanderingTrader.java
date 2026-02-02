package com.bokmcdok.butterflies.world.entity.npc;

import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerWanderingTrader extends WanderingTrader {

    // Constants for Peacemaker Illusioner attributes.
    private static final double PEACEMAKER_WANDERING_TRADER_FOLLOW_RANGE = 48.0d;
    private static final double PEACEMAKER_WANDERING_TRADER_SPEED = 0.7d;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_WANDERING_TRADER_SPEED)
                .add(Attributes.FOLLOW_RANGE, PEACEMAKER_WANDERING_TRADER_FOLLOW_RANGE);
    }

    /**
     * Create a peacemaker wandering trader.
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerWanderingTrader(EntityType<? extends PeacemakerWanderingTrader> type,
                                     Level level) {
        super(type, level);
    }

    /**
     * Spawn a peacemaker butterfly after death
     * @param damageSource The source of the damage that killed the villager
     */
    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        PeacemakerButterfly.spawn(this);
    }
}
