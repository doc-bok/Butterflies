package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> INSTANCE =
            DeferredRegister.create(ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MODID);

    // Butterfly loot modifier.
    public static final RegistryObject<Codec<ButterflyLootModifier>> BUTTERFLY_LOOT =
            LootModifierRegistry.INSTANCE.register("butterfly_loot", ButterflyLootModifier.CODEC);

    // Modifier for oak leaves.
    public static final RegistryObject<Codec<OakLeavesLootModifier>> OAK_LEAVES =
            LootModifierRegistry.INSTANCE.register("oak_leaves_loot", OakLeavesLootModifier.CODEC);
}
