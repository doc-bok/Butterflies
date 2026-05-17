package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.bokmcdok.butterflies.common.loot.TrailRuinsRareLootModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS;

    static {
        LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        LOOT_MODIFIERS.register("butterfly_loot", ButterflyLootModifier.CODEC);
        LOOT_MODIFIERS.register("oak_leaves_loot", OakLeavesLootModifier.CODEC);
        LOOT_MODIFIERS.register("trail_ruins_rare_loot", TrailRuinsRareLootModifier.CODEC);
    }

    /**
     * Prevent construction.
     */
    private LootModifierRegistry() {}
}
