package com.bokmcdok.butterflies.data.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Generates all loot tables.
 */
public class ModLootTableProvider extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>>
            loot_tables = ImmutableList.of(
                Pair.of(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                Pair.of(ModChestLootSubProvider::new, LootContextParamSets.CHEST),
                Pair.of(ModEntityLootSubProvider::new, LootContextParamSets.ENTITY),
                Pair.of(ModGiftLootSubProvider::new, LootContextParamSets.GIFT));

    public ModLootTableProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @NotNull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return loot_tables;
    }

    @Override
    protected void validate(Map<ResourceLocation,
                            LootTable> map,
                            @NotNull ValidationContext validationtracker) {
        map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
    }
}
