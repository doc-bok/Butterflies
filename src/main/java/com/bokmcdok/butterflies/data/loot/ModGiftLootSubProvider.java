package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

/**
 * Adds Gift Loot Tables.
 */
public class ModGiftLootSubProvider implements LootTableSubProvider {

    /**
     * Construction.
     */
    public ModGiftLootSubProvider(HolderLookup.Provider lookupProvider) {
        super();
    }

    /**
     * Entry point. Generates chest loot tables.
     * @param register The register for storing loot tables.
     */
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> register) {
        register.accept(ModLootTableProvider.registerKey("gameplay/hero_of_the_village/lepidopterist"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(ItemRegistry.ZHUANGZI_BOOK.get()))
                                .add(LootItem.lootTableItem(ItemRegistry.SILK.get()))
                                .add(LootItem.lootTableItem(ItemRegistry.BURNT_BUTTERFLY_NET.get()))
                                .add(LootItem.lootTableItem(ItemRegistry.BUTTERFLY_BANNER_PATTERN.get()))
                                .add(LootItem.lootTableItem(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get()))));
    }
}
