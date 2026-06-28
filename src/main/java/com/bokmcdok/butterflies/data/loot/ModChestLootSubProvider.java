package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

/**
 * Adds chest loot tables.
 */
public class ModChestLootSubProvider implements LootTableSubProvider {

    /**
     * Entry point. Generates chest loot tables.
     * @param register The register for storing loot tables.
     */
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> register) {
        register.accept(new ResourceLocation(ButterfliesMod.MOD_ID, "chests/peacemaker_lair"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0.0f, 1.0f))
                                .add(LootItem.lootTableItem(Items.CROSSBOW)))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0f, 3.0f))
                                .add(weightedMultiItem(Items.WHEAT, 7, 3.0f, 5.0f))
                                .add(weightedMultiItem(Items.POTATO, 5, 2.0f, 5.0f))
                                .add(weightedMultiItem(Items.CARROT, 5, 3.0f, 5.0f)))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0f, 3.0f))
                                .add(LootItem.lootTableItem(Items.DARK_OAK_LOG)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0f, 3.0f))
                                .add(weightedItem(Items.EXPERIENCE_BOTTLE, 7))
                                .add(weightedMultiItem(Items.STRING, 4, 1.0f, 6.0f))
                                .add(weightedMultiItem(Items.ARROW, 4, 2.0f, 7.0f))
                                .add(weightedMultiItem(Items.TRIPWIRE_HOOK, 3, 1.0f, 3.0f))
                                .add(weightedMultiItem(Items.IRON_INGOT, 3, 1.0f, 3.0f))
                                .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment())))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0.0f, 1.0f))
                                .add(LootItem.lootTableItem(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(weightedItem(ItemRegistry.ZHUANGZI_BOOK.get(), 3))));
    }

    /**
     * Creates a weighted item.
     * @param item The item to add.
     * @param weight The weight of the item.
     * @return A weighted item Builder.
     */
    private LootPoolEntryContainer.Builder<?> weightedItem(ItemLike item,
                                                                int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    /**
     * Creates a weighted item that comes in groups.
     * @param item The item to add.
     * @param weight The weight of the item.
     * @param minCount The minimum number of items.
     * @param maxCount The maximum number of items.
     * @return An item Builder.
     */
    private LootPoolEntryContainer.Builder<?> weightedMultiItem(ItemLike item,
                                                                int weight,
                                                                float minCount,
                                                                float maxCount) {
        return LootItem.lootTableItem(item)
                .setWeight(weight)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));
    }
}
