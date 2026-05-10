package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.ButterflyLootModifier;
import com.bokmcdok.butterflies.common.loot.OakLeavesLootModifier;
import com.bokmcdok.butterflies.common.loot.TrailRuinsRareLootModifier;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers any loot table modifiers, used to modify vanilla loot tables.
 */
public class LootModifierRegistry {

    // The deferred register.
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> REGISTER;

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ButterfliesMod.MOD_ID);
        REGISTER.register("butterfly_loot", new ButterflyLootModifier(new LootItemCondition[]{}).getCodec());
        REGISTER.register("oak_leaves_loot", new OakLeavesLootModifier(new LootItemCondition[]{}).getCodec());
        REGISTER.register("trail_ruins_rare_loot", new TrailRuinsRareLootModifier(new LootItemCondition[]{}).getCodec());
    }

    // Prevent construction.
    private LootModifierRegistry() {}
}
