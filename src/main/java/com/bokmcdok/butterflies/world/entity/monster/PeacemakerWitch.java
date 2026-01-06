package com.bokmcdok.butterflies.world.entity.monster;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerWitch extends Witch {

    // Constants for Peacemaker Illusioner attributes.
    private static final double PEACEMAKER_WITCH_HEALTH = 39.0d;
    private static final double PEACEMAKER_WITCH_SPEED = 0.325;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_WITCH_SPEED)
                .add(Attributes.MAX_HEALTH, PEACEMAKER_WITCH_HEALTH);
    }

    /**
     * Create a peacemaker witch.
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerWitch(EntityType<? extends PeacemakerWitch> type,
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
