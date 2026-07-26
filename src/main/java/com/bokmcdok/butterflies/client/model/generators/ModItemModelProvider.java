package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.data.models.model.ButterflyModelTemplates;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Generates Item Models for the mod.
 */
public class ModItemModelProvider extends ModelSubProvider {
    private final static List<Pair<DeferredHolder<Item, Item>, String>> PEACEMAKER_SPAWN_EGGS = List.of(
            new Pair<>(SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG, "peacemaker"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG, "peacemaker_cow"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_EVOKER_SPAWN_EGG, "peacemaker_evoker"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_ILLUSIONER_SPAWN_EGG, "peacemaker_illusioner"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_PILLAGER_SPAWN_EGG, "peacemaker_pillager"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_VILLAGER_SPAWN_EGG, "peacemaker_villager"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_VINDICATOR_SPAWN_EGG, "peacemaker_vindicator"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_WANDERING_TRADER_SPAWN_EGG, "peacemaker_wandering_trader"),
            new Pair<>(SpawnEggRegistry.PEACEMAKER_WITCH_SPAWN_EGG, "peacemaker_witch")
            );

    private final static List<Pair< String, List<DeferredHolder<Item, Item>>>> BUTTERFLY_STAGES = List.of(
            new Pair<>("butterfly", SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS),
            new Pair<>("chrysalis", SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS),
            new Pair<>("caterpillar", SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS),
            new Pair<>("egg", SpawnEggRegistry.EGG_SPAWN_EGGS)
    );

    /**
     * Construction
     */
    public ModItemModelProvider(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        super(blockModels, itemModels);
    }

    /**
     * Entry point.
     */
    @Override
    protected void register() {
        registerButterflyItems();
        registerSpecialNets();
        registerGeneralItems();
        registerSpawnEggs();
    }

    /**
     * Registers all our butterfly items
     */
    private void registerButterflyItems() {
        for(int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            registerBottledItems(i);
            registerButterflyEgg(i);
            registerFullButterflyNet(i);
            registerFireproofButterflyNet(i);
            registerButterflyScroll(i);
            registerCaterpillar(i);
        }
    }

    /**
     * Registers the special nets.
     */
    private void registerSpecialNets() {
        registerSpecialButterflyNet(ItemRegistry.EMPTY_BUTTERFLY_NET);
        registerSpecialButterflyNet(ItemRegistry.BURNT_BUTTERFLY_NET);
        registerSpecialButterflyNet(ItemRegistry.FIREPROOF_BUTTERFLY_NET);
    }

    /**
     * Register all general items.
     */
    private void registerGeneralItems() {
        basicItem(ItemRegistry.BUTTERFLY_BOOK.get());
        basicItem(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());
        basicItem(ItemRegistry.INFESTED_APPLE.get());
        basicItem(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get());
        basicItem(ItemRegistry.SILK.get());
        basicItem(ItemRegistry.ZHUANGZI_BOOK.get());
        singleTextureItem(
                ItemRegistry.BUTTERFLY_BANNER_PATTERN.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM,
                "item/butterfly_banner_pattern");
    }

    /**
     * Register all spawn eggs.
     */
    private void registerSpawnEggs() {
        for(int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            registerButterflySpawnEggs(i);
        }

        singleTextureItem(
                SpawnEggRegistry.BUTTERFLY_GOLEM_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM,
                "item/spawn_egg/golem/butterfly");

        registerPeacemakerSpawnEggs();
    }

    /**
     * Registers the bottled butterflies.
     */
    private void registerBottledItems(int index) {
        bottledButterflyItem(ItemRegistry.BOTTLED_BUTTERFLIES.get(index));
        bottledCaterpillarItem(ItemRegistry.BOTTLED_CATERPILLARS.get(index));
    }

    /**
     * Registers a butterfly egg.
     * @param index The butterfly index.
     */
    private void registerButterflyEgg(int index) {
        DeferredHolder<Item, Item> butterflyEgg = ItemRegistry.BUTTERFLY_EGGS.get(index);
        String path = getPath(butterflyEgg);
        singleTextureItem(butterflyEgg.get(), ButterflyModelTemplates.TEMPLATE_BUTTERFLY_EGG, "item/butterfly_egg/" + path);
    }

    /**
     * Registers a butterfly scroll.
     * @param index The butterfly index.
     */
    private void registerButterflyScroll(int index) {
        DeferredHolder<Item, Item> butterflyScroll = ItemRegistry.BUTTERFLY_SCROLLS.get(index);
        String path = getPath(butterflyScroll);
        singleTextureItem(butterflyScroll.get(), ModelTemplates.FLAT_ITEM, "item/butterfly_scroll/" + path);
    }

    /**
     * Registers a caterpillar.
     * @param index The butterfly index.
     */
    private void registerCaterpillar(int index) {
        DeferredHolder<Item, Item> caterpillar = ItemRegistry.CATERPILLARS.get(index);
        String path = getPath(caterpillar);
        singleTextureItem(caterpillar.get(), ButterflyModelTemplates.TEMPLATE_CATERPILLAR, "item/caterpillar/" + path);
    }

    /**
     * Register all buttery-related spawn eggs.
     * @param index The butterfly index.
     */
    private void registerButterflySpawnEggs(int index) {
        String species = Objects.requireNonNull(ButterflyData.getEntry(index)).entityId();
        for (var stage : BUTTERFLY_STAGES) {
            singleTextureItem(stage.getSecond().get(index).get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/spawn_egg/" + stage.getFirst() + "/" + species);
        }
    }

    /**
     * Registers all the Peacemaker entity spawn eggs.
     */
    public void registerPeacemakerSpawnEggs() {
        for (var spawnEggs : PEACEMAKER_SPAWN_EGGS) {
            singleTextureItem(
                    spawnEggs.getFirst().get(),
                    ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                    "item/spawn_egg/peacemaker/" + spawnEggs.getSecond());
        }
    }

    /**
     * Registers a full butterfly net.
     * @param index The butterfly index.
     */
    private void registerFullButterflyNet(int index) {
        DeferredHolder<Item, Item> butterflyNet = ItemRegistry.BUTTERFLY_NETS.get(index);
        singleTextureItem(butterflyNet.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/butterfly_net/butterfly_net_full");
    }

    /**
     * Registers a fireproof butterfly net.
     * @param index The butterfly index.
     */
    private void registerFireproofButterflyNet(int index) {
        String path = getPath(ItemRegistry.FIREPROOF_BUTTERFLY_NETS.get(index));
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/butterfly_net/fireproof_butterfly_net_full"));
    }

    /**
     * Registers a special butterfly net (e.g. burnt or empty).
     * @param item The item to register.
     */
    private void registerSpecialButterflyNet(DeferredHolder<Item, Item> item) {
        String path = getPath(item);
        singleTextureItem(item.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/butterfly_net/" + path);
    }

    /**
     * Registers a bottled butterfly item.
     * @param item The item to register.
     */
    private void bottledButterflyItem(DeferredHolder<Item, Item> item)
    {
        bottledItem(item, "bottled_butterfly", "bottled");
    }

    /**
     * Registers a bottled caterpillar item.
     * @param item The item to register.
     */
    private void bottledCaterpillarItem(DeferredHolder<Item, Item> item)
    {
        bottledItem(item, "bottled_caterpillar", "bottled_caterpillar");
    }

    /**
     * Registers a bottled  item.
     * @param item The item to register.
     * @param textureLocation The location of the texture.
     * @param texturePrefix The prefix for the texture name.
     */
    private void bottledItem(DeferredHolder<Item, Item> item,
                             String textureLocation,
                             String texturePrefix)
    {
        ResourceLocation itemKey = Objects.requireNonNull(item.getKey()).location();
        Optional<String> species = ButterflyData.getSpeciesString(itemKey.getPath());
        species.ifPresent(s -> singleTextureItem(item.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/" + textureLocation + "/" + texturePrefix + "_" + s));
    }

    private void basicItem(Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void singleTextureItem(Item item,
                                   ModelTemplate parent,
                                   String textureLocation) {

        ResourceLocation flatItemModel = parent.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.layer0(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, textureLocation)),
                itemModels.modelOutput);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(flatItemModel));
    }

    /**
     * Helper to get an item's path.
     * @param item The item.
     * @return The path of the item.
     */
    private String getPath(DeferredHolder<Item, Item> item) {
        return Objects.requireNonNull(item.getId()).getPath();
    }
}
