package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ParticleButterfly extends Butterfly {

    private final SimpleParticleType particleType;

    /**
     * The default constructor.
     * @param entityType The type of the entity.
     * @param level      The level where the entity exists.
     */
    public ParticleButterfly(BlockRegistry blockRegistry,
                             EntityType<? extends Butterfly> entityType,
                             Level level,
                             SimpleParticleType particleType) {
        super(blockRegistry, entityType, level);
        this.particleType = particleType;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.random.nextInt(16) == 1) {
            this.level().addParticle(this.particleType,
                    this.getRandomX(0.6),
                    this.getRandomY(),
                    this.getRandomZ(0.6),
                    0.0,
                    0.0,
                    0.0);
        }
    }
}