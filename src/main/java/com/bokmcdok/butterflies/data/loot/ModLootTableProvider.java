package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates all loot tables.
 */
public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output,
                                           CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModChestLootSubProvider::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ModEntityLootSubProvider::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(x -> new ModGiftLootSubProvider(), LootContextParamSets.GIFT)
        ), lookupProvider);
    }

    public static ResourceKey<LootTable> registerKey(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, name));
    }
}
