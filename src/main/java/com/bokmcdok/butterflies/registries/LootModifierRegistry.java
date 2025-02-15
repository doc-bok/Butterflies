package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.bokmcdok.butterflies.common.loot.TrailRuinsRareLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    private final DeferredRegister<GlobalLootModifierSerializer<?>> deferredRegister;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LootModifierRegistry(IEventBus modEventBus) {
        // An instance of a deferred registry we use to register items.
        deferredRegister = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        deferredRegister.register(modEventBus);
    }

    /**
     * Register the loot modifiers.
     * @param itemRegistry The item registry.
     */
    public void initialise(ItemRegistry itemRegistry) {
        deferredRegister.register("butterfly_loot", () -> new ButterflyLootModifier.Serializer(itemRegistry));
        deferredRegister.register("oak_leaves_loot", () -> new OakLeavesLootModifier.Serializer(itemRegistry));
        deferredRegister.register("trail_ruins_rare_loot", () -> new TrailRuinsRareLootModifier.Serializer(itemRegistry));
    }
}
