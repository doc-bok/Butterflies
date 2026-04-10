package com.bokmcdok.butterflies.world.entity.monster;

import com.bokmcdok.butterflies.world.entity.ai.PeacemakerGoalRegistrar;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerIllusioner extends Illusioner {

    // Constants for Peacemaker Illusioner attributes.
    private static final double PEACEMAKER_ILLUSIONER_FOLLOW_RANGE = 18.0d;
    private static final double PEACEMAKER_ILLUSIONER_HEALTH = 48.0d;
    private static final double PEACEMAKER_ILLUSIONER_SPEED = 0.75d;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_ILLUSIONER_SPEED)
                .add(Attributes.FOLLOW_RANGE, PEACEMAKER_ILLUSIONER_FOLLOW_RANGE)
                .add(Attributes.MAX_HEALTH, PEACEMAKER_ILLUSIONER_HEALTH);
    }

    /**
     * Create a peacemaker illusioner.
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerIllusioner(@NotNull PeacemakerGoalRegistrar peacemakerGoalRegistrar,
                                EntityType<? extends PeacemakerIllusioner> type,
                                Level level) {
        super(type, level);

        // Register Peacemaker-specific goals.
        if (!this.level().isClientSide()) {
            peacemakerGoalRegistrar.registerGoals(this);
        }
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
