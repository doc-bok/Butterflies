package com.bokmcdok.butterflies.world.entity.monster;

import com.bokmcdok.butterflies.registries.TagRegistry;
import com.bokmcdok.butterflies.world.entity.ai.PeacemakerGoals;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PeacemakerVindicator extends Vindicator {

    // Constants for Peacemaker Illusioner attributes.
    private static final double PEACEMAKER_VINDICATOR_ATTACK_DAMAGE = 7.5d;
    private static final double PEACEMAKER_VINDICATOR_FOLLOW_RANGE = 12.0d;
    private static final double PEACEMAKER_VINDICATOR_HEALTH = 32.0d;
    private static final double PEACEMAKER_VINDICATOR_SPEED = 0.52;

    // The goals for shared code.
    private PeacemakerGoals peacemakerGoals;

    /**
     * Butterflies make their hosts faster, stronger, and tougher
     * @return Attributes for the entity
     */
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, PEACEMAKER_VINDICATOR_ATTACK_DAMAGE)
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_VINDICATOR_SPEED)
                .add(Attributes.FOLLOW_RANGE, PEACEMAKER_VINDICATOR_FOLLOW_RANGE)
                .add(Attributes.MAX_HEALTH, PEACEMAKER_VINDICATOR_HEALTH);
    }

    /**
     * Create a peacemaker vindicator.
     * @param type The entity type
     * @param level The current level
     */
    public PeacemakerVindicator(TagRegistry tagRegistry,
                                EntityType<? extends PeacemakerVindicator> type,
                                Level level) {
        super(type, level);

        if (!this.level().isClientSide()) {
            this.peacemakerGoals.setTagRegistry(tagRegistry);
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

    /**
     * Override the target goals to ignore peacemaker mobs
     */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.peacemakerGoals = new PeacemakerGoals();
        this.peacemakerGoals.registerGoals(this);
    }
}
