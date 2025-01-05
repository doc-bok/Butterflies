package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers pottery patterns.
 */
public class DecoratedPotPatternsRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<String> deferredRegister;

    // The butterfly pot pattern.
    private RegistryObject<String> butterflyPotPattern;

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
    public RegistryObject<String> getButterflyPotPattern() {
        return butterflyPotPattern;
    }
}
