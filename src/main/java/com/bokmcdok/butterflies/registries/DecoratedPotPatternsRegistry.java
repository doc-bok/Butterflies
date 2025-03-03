package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
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
    private final ResourceKey<DecoratedPotPattern> butterflyPotPatternKey;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public DecoratedPotPatternsRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.DECORATED_POT_PATTERN, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);

        this.butterflyPotPatternKey = ResourceKey.create(
                Registries.DECORATED_POT_PATTERN,
                ResourceLocation.fromNamespaceAndPath(
                        ButterfliesMod.MOD_ID,
                        "butterfly_pottery_pattern"));    }

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

    /**
     * Add the new butterfly pottery pattern to the list of valid patterns.
     * @param itemRegistry The item registry.
     */
    public void expandVanillaPatterns(ItemRegistry itemRegistry) {
        ImmutableMap.Builder<Item, ResourceKey<DecoratedPotPattern>> itemsToPot = new ImmutableMap.Builder<>();
        itemsToPot.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        itemsToPot.put(itemRegistry.getButterflyPotterySherd().get(), butterflyPotPatternKey);
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemsToPot.build();
    }
}
