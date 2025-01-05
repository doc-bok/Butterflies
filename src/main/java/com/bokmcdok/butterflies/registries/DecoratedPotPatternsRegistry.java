package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers pottery patterns.
 */
public class DecoratedPotPatternsRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<String> deferredRegister;

    // The butterfly pot pattern.
    private DeferredHolder<String, String> butterflyPotPattern;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public DecoratedPotPatternsRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the items.
     */
    public void initialise() {
        butterflyPotPattern = deferredRegister.register("butterfly_pottery_pattern", () -> "butterfly_pottery_pattern");
    }

    /**
     * Accessor for butterfly pot pattern.
     * @return The butterfly pot pattern.
     */
    public DeferredHolder<String, String> getButterflyPotPattern() {
        return butterflyPotPattern;
    }
}
