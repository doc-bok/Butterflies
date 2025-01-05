package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * The lava moth, found in the Nether.
 */
public class LavaMoth extends Butterfly {

    /**
     * The default constructor.
     * @param entityType The type of the entity.
     * @param level      The level where the entity exists.
     */
    public LavaMoth(BlockRegistry blockRegistry,
                    EntityType<? extends Butterfly> entityType,
                    Level level) {
        super(blockRegistry, entityType, level);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.getLevel().addParticle(ParticleTypes.DRIPPING_DRIPSTONE_LAVA,
                this.getRandomX(0.6),
                this.getRandomY(),
                this.getRandomZ(0.6),
                0.0,
                0.0,
                0.0);
    }
}
