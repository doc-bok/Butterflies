package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


public class LootModifierRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> INSTANCE =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);

    // The loot modifier
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<ButterflyLootModifier>> BUTTERFLY_LOOT =
            LootModifierRegistry.INSTANCE.register("butterfly_loot", ButterflyLootModifier.CODEC);
}
