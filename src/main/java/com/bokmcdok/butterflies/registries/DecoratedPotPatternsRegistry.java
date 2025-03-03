package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers pottery patterns.
 */
public class DecoratedPotPatternsRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<DecoratedPotPattern> deferredRegister;

    // The butterfly pot pattern.
    private DeferredHolder<DecoratedPotPattern, DecoratedPotPattern> butterflyPotPattern;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public DecoratedPotPatternsRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.DECORATED_POT_PATTERN, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the items.
     */
    public void initialise() {
        butterflyPotPattern = deferredRegister.register(
                "butterfly_pottery_pattern",
                () -> new DecoratedPotPattern(ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        "butterfly_pottery_pattern")));
    }

    /**
     * Accessor for butterfly pot pattern.
     * @return The butterfly pot pattern.
     */
    public DeferredHolder<DecoratedPotPattern, DecoratedPotPattern> getButterflyPotPattern() {
        return butterflyPotPattern;
    }
}
