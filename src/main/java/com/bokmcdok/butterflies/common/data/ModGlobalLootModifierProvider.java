package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.common.loot.AddItemLootModifier;
import com.bokmcdok.butterflies.common.loot.ReplaceItemLootModifier;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

/**
 * Registers all the loot modifiers.
 */
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    /**
     * Construction.
     * @param output The pack to output to.
     */
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, ButterfliesMod.MOD_ID);
    }

    /**
     * Entry point. Registers the loot modifiers.
     */
    @Override
    protected void start() {

        add("butterfly_loot", new AddItemLootModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.03125f).build() },
                ItemRegistry.ZHUANGZI_BOOK.get()));

        add("oak_leaves_loot", new AddItemLootModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("blocks/oak_leaves")).build(),
                LootItemRandomChanceCondition.randomChance(0.00025f).build() },
                ItemRegistry.INFESTED_APPLE.get()));

        add("trail_runs_rare_loot", new ReplaceItemLootModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("archeology/trail_ruins_rare")).build(),
                LootItemRandomChanceCondition.randomChance(0.077f).build() },
                ItemRegistry.BUTTERFLY_POTTERY_SHERD.get()));
    }
}
