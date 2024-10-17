package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.bokmcdok.butterflies.common.loot.TrailRuinsRareLootModifier;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    private final DeferredRegister<Codec<? extends IGlobalLootModifier>> deferredRegister;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LootModifierRegistry(IEventBus modEventBus) {
        // An instance of a deferred registry we use to register items.
        deferredRegister = DeferredRegister.create(ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        deferredRegister.register(modEventBus);
    }

    /**
     * Register the loot modifiers.
     * @param itemRegistry The item registry.
     */
    public void initialise(ItemRegistry itemRegistry) {
        deferredRegister.register("butterfly_loot", new ButterflyLootModifier(itemRegistry, new LootItemCondition[]{}).getCodec());
        deferredRegister.register("oak_leaves_loot", new OakLeavesLootModifier(itemRegistry, new LootItemCondition[]{}).getCodec());
        deferredRegister.register("trail_ruins_rare_loot", new TrailRuinsRareLootModifier(itemRegistry, new LootItemCondition[]{}).getCodec());
    }
}
