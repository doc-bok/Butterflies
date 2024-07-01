package com.bokmcdok.butterflies.world.entity.animal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class IceButterfly extends Butterfly {

    /**
     * The default constructor.
     * @param entityType The type of the entity.
     * @param level      The level where the entity exists.
     */
    public IceButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                this.getRandomX(0.6),
                this.getRandomY(),
                this.getRandomZ(0.6),
                0.0,
                0.0,
                0.0);
    }
}
