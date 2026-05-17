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

    // An instance of a deferred registry we use to register patterns.
    public static final DeferredRegister<String> DECORATED_POT_PATTERNS;

    // The butterfly pot pattern.
    public static final DeferredHolder<String, String> BUTTERFLY_POT_PATTERN;

    static {
        DECORATED_POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, ButterfliesMod.MOD_ID);
        BUTTERFLY_POT_PATTERN = DECORATED_POT_PATTERNS.register("butterfly_pottery_pattern", () -> "butterfly_pottery_pattern");
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

    /*
     * Prevent construction.
     */
    private DecoratedPotPatternsRegistry() {}
}
