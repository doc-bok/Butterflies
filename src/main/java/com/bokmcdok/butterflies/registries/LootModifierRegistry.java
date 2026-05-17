package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.bokmcdok.butterflies.common.loot.TrailRuinsRareLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS;

    static {
        LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        LOOT_MODIFIERS.register("butterfly_loot", ButterflyLootModifier.Serializer::new);
        LOOT_MODIFIERS.register("oak_leaves_loot", OakLeavesLootModifier.Serializer::new);
        LOOT_MODIFIERS.register("trail_ruins_rare_loot", TrailRuinsRareLootModifier.Serializer::new);
    }

    private LootModifierRegistry() {}
}
