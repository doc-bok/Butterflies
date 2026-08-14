package com.bokmcdok.butterflies.butterfly_data;

/**
 * Holds the lifecycle data for a butterfly species.
 * @param eggLifespan The lifespan of the egg.
 * @param caterpillarLifespan The lifespan of the caterpillar.
 * @param chrysalisLifespan The lifespan of the chrysalis.
 * @param butterflyLifespan The lifespan of the butterfly.
 */
public record LifecycleData(int eggLifespan,
                            int caterpillarLifespan,
                            int chrysalisLifespan,
                            int butterflyLifespan) {

    /**
     * Compact Construction: Validate the entire life cycle.
     */
    public LifecycleData {
        ButterflyData.validateLifeCycle(eggLifespan, caterpillarLifespan, chrysalisLifespan, butterflyLifespan);
    }

    /**
     * Get the overall lifespan.
     * @return The overall lifespan for the species.
     */
    public ButterflyLifespan overallLifespan() {
        if (butterflyLifespan == ButterflyData.IMMORTAL_LIFESPAN) {
            return ButterflyLifespan.IMMORTAL;
        }
        long totalTicks = (long) eggLifespan + caterpillarLifespan + chrysalisLifespan + butterflyLifespan;
        long days = totalTicks / net.minecraft.world.level.Level.TICKS_PER_DAY;
        if (days < 18) return ButterflyLifespan.SHORT;
        if (days < 30) return ButterflyLifespan.MEDIUM;
        return ButterflyLifespan.LONG;
    }
}
