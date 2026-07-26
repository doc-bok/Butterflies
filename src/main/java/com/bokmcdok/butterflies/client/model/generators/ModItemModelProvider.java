package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * Generates Item Models for the mod.
 */
public class ModItemModelProvider extends ItemModelProvider {

    private final static ResourceLocation HANDHELD_ROD = ResourceLocation.withDefaultNamespace("item/handheld_rod");

    /**
     * Construction
     * @param output The pack to output the resources to.
     * @param existingFileHelper A helper containing the existing files.
     */
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void registerModels() {

        // Butterfly-based Items.
        for(int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            registerBottledItems(i);
            registerButterflyEgg(i);
            registerFullButterflyNet(i);
            registerFireproofButterflyNet(i);
            registerButterflyScroll(i);
            registerButterflySpawnEggs(i);
            registerCaterpillar(i);
        }

        // Special Butterfly Nets.
        registerSpecialButterflyNet(ItemRegistry.EMPTY_BUTTERFLY_NET);
        registerSpecialButterflyNet(ItemRegistry.BURNT_BUTTERFLY_NET);
        registerSpecialButterflyNet(ItemRegistry.FIREPROOF_BUTTERFLY_NET);

        // General Items.
        basicItem(ItemRegistry.BUTTERFLY_BOOK.get());
        basicItem(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());
        basicItem(ItemRegistry.INFESTED_APPLE.get());
        basicItem(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get());
        basicItem(ItemRegistry.SILK.get());
        basicItem(ItemRegistry.ZHUANGZI_BOOK.get());

        // Golem Spawn Egg.
        singleTexture(getPath(SpawnEggRegistry.BUTTERFLY_GOLEM_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/golem/butterfly"));

        // Peacemaker Spawn Eggs.
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
        final ResourceLocation parent = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "template_butterfly_egg");
        String path = getPath(ItemRegistry.BUTTERFLY_EGGS.get(index));
        singleTexture(path, parent, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/butterfly_egg/" + path));
    }

    /**
     * Registers a butterfly scroll.
     * @param index The butterfly index.
     */
    private void registerButterflyScroll(int index) {
        String name = getPath(ItemRegistry.BUTTERFLY_SCROLLS.get(index));
        singleTexture(name, mcLoc("generated"), "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/butterfly_scroll/" + name));
    }

    /**
     * Registers a caterpillar.
     * @param index The butterfly index.
     */
    private void registerCaterpillar(int index) {
        final ResourceLocation parent = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "template_caterpillar");
        String path = getPath(ItemRegistry.CATERPILLARS.get(index));
        singleTexture(path, parent, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/caterpillar/" + path));
    }

    /**
     * Register all buttery-related spawn eggs.
     * @param index The butterfly index.
     */
    private void registerButterflySpawnEggs(int index) {
        String path = getPath(SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS.get(index));
        String species = path.substring(20);

        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/butterfly/" + species));

        path = getPath(SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS.get(index));
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/caterpillar/" + species));

        path = getPath(SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS.get(index));
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/chrysalis/" + species));

        path = getPath(SpawnEggRegistry.EGG_SPAWN_EGGS.get(index));
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/egg/" + species));
    }

    /**
     * Registers all the Peacemaker entity spawn eggs.
     */
    public void registerPeacemakerSpawnEggs() {
        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/butterfly/peacemaker"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_cow"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_EVOKER_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_evoker"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_ILLUSIONER_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_illusioner"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_PILLAGER_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_pillager"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_VILLAGER_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_villager"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_VINDICATOR_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_vindicator"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_WANDERING_TRADER_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_wandering_trader"));

        singleTexture(getPath(SpawnEggRegistry.PEACEMAKER_WITCH_SPAWN_EGG), HANDHELD_ROD, "layer0",
                ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/spawn_egg/peacemaker/peacemaker_witch"));
    }

    /**
     * Registers a full butterfly net.
     * @param index The butterfly index.
     */
    private void registerFullButterflyNet(int index) {
        String path = getPath(ItemRegistry.BUTTERFLY_NETS.get(index));
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/butterfly_net/butterfly_net_full"));
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
        singleTexture(path, HANDHELD_ROD, "layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "item/butterfly_net/" + path));
    }

    /**
     * Registers a bottled butterfly item.
     * @param item The item to register.
     */
    private void bottledButterflyItem(DeferredHolder<Item, Item> item)
    {
        bottledItem(item, "bottled_butterfly", "bottled", 18);
    }

    /**
     * Registers a bottled caterpillar item.
     * @param item The item to register.
     */
    private void bottledCaterpillarItem(DeferredHolder<Item, Item> item)
    {
        bottledItem(item, "bottled_caterpillar", "bottled_caterpillar", 20);
    }

    /**
     * Registers a bottled  item.
     * @param item The item to register.
     * @param textureLocation The location of the texture.
     * @param texturePrefix The prefix for the texture name.
     * @param substrStart The start of the substring for the texture name.
     */
    private void bottledItem(DeferredHolder<Item, Item> item,
                             String textureLocation,
                             String texturePrefix,
                             int substrStart)
    {
        ResourceLocation itemKey = Objects.requireNonNull(item.getKey()).location();
        getBuilder(itemKey.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld_rod"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID,"item/" + textureLocation + "/" + texturePrefix + "_" + itemKey.getPath().substring(substrStart)));
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
