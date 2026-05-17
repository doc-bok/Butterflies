package com.bokmcdok.butterflies.world.entity.monster;

import com.bokmcdok.butterflies.world.entity.PeacemakerEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerEvoker extends Evoker implements PeacemakerEntity {

    // Constants for Peacemaker Evoker attributes.
    private static final double PEACEMAKER_EVOKER_FOLLOW_RANGE = 12.0d;
    private static final double PEACEMAKER_EVOKER_HEALTH = 36.0d;
    private static final double PEACEMAKER_EVOKER_SPEED = 0.75d;
    private static final double PEACEMAKER_EVOKER_TEMPT_RANGE = 10.0d;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_EVOKER_SPEED)
                .add(Attributes.FOLLOW_RANGE, PEACEMAKER_EVOKER_FOLLOW_RANGE)
                .add(Attributes.MAX_HEALTH, PEACEMAKER_EVOKER_HEALTH)
                .add(Attributes.TEMPT_RANGE, PEACEMAKER_EVOKER_TEMPT_RANGE);
    }

    /**
     * Create a peacemaker evoker
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerEvoker(EntityType<? extends PeacemakerEvoker> type,
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

    /**
     * Register Peacemaker-specific goals.
     */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        registerPathfinderGoals(this);
    }
}
