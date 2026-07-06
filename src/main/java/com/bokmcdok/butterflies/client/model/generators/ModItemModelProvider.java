package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * Generates Item Models for the mod.
 */
public class ModItemModelProvider extends ModelSubProvider {

    private final static ModelTemplate TEMPLATE_BUTTERFLY_EGG = ModelTemplates.createItem(ButterfliesMod.MOD_ID + ":template_butterfly_egg", TextureSlot.LAYER0);
    private final static ModelTemplate TEMPLATE_SPAWN_EGG = ModelTemplates.createItem("item/template_spawn_egg", TextureSlot.LAYER0);
    private final static ModelTemplate TEMPLATE_CATERPILLAR = ModelTemplates.createItem(ButterfliesMod.MOD_ID + ":template_caterpillar", TextureSlot.LAYER0);

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

        // Butterfly-based Items.
        for(int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            registerBottledItems(itemModels, i);
            registerButterflyEgg(itemModels, i);
            registerFullButterflyNet(itemModels, i);
            registerButterflyScroll(itemModels, i);
            registerButterflySpawnEggs(itemModels, i);
            registerCaterpillar(itemModels, i);
        }

        // Special Butterfly Nets.
        registerSpecialButterflyNet(itemModels, ItemRegistry.EMPTY_BUTTERFLY_NET);
        registerSpecialButterflyNet(itemModels, ItemRegistry.BURNT_BUTTERFLY_NET);

        // General Items.
        basicItem(itemModels, ItemRegistry.BUTTERFLY_BOOK.get());
        basicItem(itemModels, ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());
        basicItem(itemModels, ItemRegistry.INFESTED_APPLE.get());
        basicItem(itemModels, ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get());
        basicItem(itemModels, ItemRegistry.SILK.get());
        basicItem(itemModels, ItemRegistry.ZHUANGZI_BOOK.get());

        // Golem Spawn Egg.
        singleTextureItem(itemModels,
                SpawnEggRegistry.BUTTERFLY_GOLEM_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM,
                "item/spawn_egg/golem/butterfly");

        // Banner Pattern
        singleTextureItem(itemModels,
                ItemRegistry.BUTTERFLY_BANNER_PATTERN.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM,
                "item/butterfly_banner_pattern");

        // Peacemaker Spawn Eggs.
        registerPeacemakerSpawnEggs(itemModels);
    }

    /**
     * Registers the bottled butterflies.
     */
    private void registerBottledItems(ItemModelGenerators itemModels,
                                      int index) {
        bottledButterflyItem(itemModels, ItemRegistry.BOTTLED_BUTTERFLIES.get(index));
        bottledCaterpillarItem(itemModels, ItemRegistry.BOTTLED_CATERPILLARS.get(index));
    }

    /**
     * Registers a butterfly egg.
     * @param index The butterfly index.
     */
    private void registerButterflyEgg(ItemModelGenerators itemModels,
                                      int index) {
        DeferredHolder<Item, Item> butterflyEgg = ItemRegistry.BUTTERFLY_EGGS.get(index);
        String path = getPath(butterflyEgg);
        singleTextureItem(itemModels, butterflyEgg.get(), TEMPLATE_BUTTERFLY_EGG, "item/butterfly_egg/" + path);
    }

    /**
     * Registers a butterfly scroll.
     * @param index The butterfly index.
     */
    private void registerButterflyScroll(ItemModelGenerators itemModels,
                                         int index) {
        DeferredHolder<Item, Item> butterflyScroll = ItemRegistry.BUTTERFLY_SCROLLS.get(index);
        String path = getPath(butterflyScroll);
        singleTextureItem(itemModels, butterflyScroll.get(), ModelTemplates.FLAT_ITEM, "item/butterfly_scroll/" + path);
    }

    /**
     * Registers a caterpillar.
     * @param index The butterfly index.
     */
    private void registerCaterpillar(ItemModelGenerators itemModels,
                                     int index) {
        DeferredHolder<Item, Item> caterpillar = ItemRegistry.CATERPILLARS.get(index);
        String path = getPath(caterpillar);
        singleTextureItem(itemModels, caterpillar.get(), TEMPLATE_CATERPILLAR, "item/caterpillar/" + path);
    }

    /**
     * Register all buttery-related spawn eggs.
     * @param index The butterfly index.
     */
    private void registerButterflySpawnEggs(ItemModelGenerators itemModels,
                                            int index) {
        DeferredHolder<Item, Item> spawnEgg = SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS.get(index);
        String path = getPath(spawnEgg);
        String species = path.substring(20);

        singleTextureItem(itemModels, spawnEgg.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM,  "item/spawn_egg/butterfly/" + species);

        spawnEgg = SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS.get(index);
        singleTextureItem(itemModels, spawnEgg.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/spawn_egg/caterpillar/" + species);

        spawnEgg = SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS.get(index);
        singleTextureItem(itemModels, spawnEgg.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/spawn_egg/chrysalis/" + species);

        spawnEgg = SpawnEggRegistry.EGG_SPAWN_EGGS.get(index);
        singleTextureItem(itemModels, spawnEgg.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/spawn_egg/egg/" + species);
    }

    /**
     * Registers all the Peacemaker entity spawn eggs.
     */
    public void registerPeacemakerSpawnEggs(ItemModelGenerators itemModels) {
        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/butterfly/peacemaker");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_cow");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_EVOKER_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_evoker");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_ILLUSIONER_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_illusioner");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_PILLAGER_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_pillager");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_VILLAGER_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_villager");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_VINDICATOR_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_vindicator");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_WANDERING_TRADER_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_wandering_trader");

        singleTextureItem(itemModels,
                SpawnEggRegistry.PEACEMAKER_WITCH_SPAWN_EGG.get(),
                ModelTemplates.FLAT_HANDHELD_ROD_ITEM,
                "item/spawn_egg/peacemaker/peacemaker_witch");
    }

    /**
     * Registers a full butterfly net.
     * @param index The butterfly index.
     */
    private void registerFullButterflyNet(ItemModelGenerators itemModels,
                                          int index) {
        DeferredHolder<Item, Item> butterflyNet = ItemRegistry.BUTTERFLY_NETS.get(index);
        singleTextureItem(itemModels, butterflyNet.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/butterfly_net/butterfly_net_full");
    }

    /**
     * Registers a special butterfly net (e.g. burnt or empty).
     * @param item The item to register.
     */
    private void registerSpecialButterflyNet(ItemModelGenerators itemModels,
                                             DeferredHolder<Item, Item> item) {
        String path = getPath(item);
        singleTextureItem(itemModels, item.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/butterfly_net/" + path);
    }

    /**
     * Registers a bottled butterfly item.
     * @param item The item to register.
     */
    private void bottledButterflyItem(ItemModelGenerators itemModels,
                                      DeferredHolder<Item, Item> item)
    {
        bottledItem(itemModels, item, "bottled_butterfly", "bottled", 18);
    }

    /**
     * Registers a bottled caterpillar item.
     * @param item The item to register.
     */
    private void bottledCaterpillarItem(ItemModelGenerators itemModels,
                                        DeferredHolder<Item, Item> item)
    {
        bottledItem(itemModels, item, "bottled_caterpillar", "bottled_caterpillar", 20);
    }

    /**
     * Registers a bottled  item.
     * @param item The item to register.
     * @param textureLocation The location of the texture.
     * @param texturePrefix The prefix for the texture name.
     * @param substrStart The start of the substring for the texture name.
     */
    private void bottledItem(ItemModelGenerators itemModels,
                             DeferredHolder<Item, Item> item,
                             String textureLocation,
                             String texturePrefix,
                             int substrStart)
    {
        ResourceLocation itemKey = Objects.requireNonNull(item.getKey()).location();
        singleTextureItem(itemModels, item.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM, "item/" + textureLocation + "/" + texturePrefix + "_" + itemKey.getPath().substring(substrStart));
    }

    private void basicItem(ItemModelGenerators itemModels,
                           Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void singleTextureItem(ItemModelGenerators itemModels,
                                   Item item,
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
