package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.*;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS;

    static {
        LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        LOOT_MODIFIERS.register("add_item_loot", AddItemLootModifier.CODEC);
        LOOT_MODIFIERS.register("replace_item_loot", ReplaceItemLootModifier.CODEC);
    }

    /**
     * Prevent construction.
     */
    private LootModifierRegistry() {}
}
