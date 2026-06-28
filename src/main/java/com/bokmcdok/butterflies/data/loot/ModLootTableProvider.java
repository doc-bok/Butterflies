package com.bokmcdok.butterflies.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

/**
 * Generates all loot tables.
 */
public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModChestLootSubProvider::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(ModEntityLootSubProvider::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ModGiftLootSubProvider::new, LootContextParamSets.GIFT)
        ));
    }
}
