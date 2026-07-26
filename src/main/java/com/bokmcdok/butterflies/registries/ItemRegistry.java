package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.ButterflyInfo;
import com.bokmcdok.butterflies.world.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

/**
 * This class registers items with Forge's Item Registry
 */
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> ITEMS;

    // Nets
    public static final DeferredHolder<Item, Item> EMPTY_BUTTERFLY_NET;
    public static final DeferredHolder<Item, Item> FIREPROOF_BUTTERFLY_NET;
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_NETS;
    public static final List<DeferredHolder<Item, Item>> FIREPROOF_BUTTERFLY_NETS;
    public static final DeferredHolder<Item, Item> BURNT_BUTTERFLY_NET;
    public static final DeferredHolder<Item, Item> PEACEMAKER_BUTTERFLY_NET;
    public static final DeferredHolder<Item, Item> FIREPROOF_PEACEMAKER_BUTTERFLY_NET;

    // Eggs
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_EGGS;

    // Caterpillars
    public static final List<DeferredHolder<Item, Item>> CATERPILLARS;

    // Bottles
    public static final List<DeferredHolder<Item, Item>> BOTTLED_BUTTERFLIES;
    public static final List<DeferredHolder<Item, Item>> BOTTLED_CATERPILLARS;

    // Scrolls
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_SCROLLS;

    // Books
    public static final DeferredHolder<Item, Item> BUTTERFLY_BOOK;
    public static final DeferredHolder<Item, Item> ZHUANGZI_BOOK;

    // Blocks
    public static final DeferredHolder<Item, Item> BUTTERFLY_FEEDER;
    public static final DeferredHolder<Item, Item> BUTTERFLY_MICROSCOPE;

    // Infested Apple
    public static final DeferredHolder<Item, Item> INFESTED_APPLE;

    // Silk
    public static final DeferredHolder<Item, Item> SILK;

    // Origami
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_ORIGAMI;

    // Sherd
    public static final DeferredHolder<Item, Item> BUTTERFLY_POTTERY_SHERD;

    // Banner Pattern
    public static final DeferredHolder<Item, Item> BUTTERFLY_BANNER_PATTERN;

    // Peacemaker Honey
    public static final DeferredHolder<Item, Item> PEACEMAKER_HONEY_BOTTLE;

    /**
     * Helper method to create a resource key.
     * @param registryId The registry ID of the item.
     * @return A new resource key.
     */
    public static ResourceKey<Item> createResourceKey(String registryId) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, registryId);
        return ResourceKey.create(Registries.ITEM, location);
    }

    /**
     * Registers all items that are based on the species of butterflies.
     * @return A record of all registered species-based items.
     */
    private static SpeciesRegistrations registerSpeciesItems() {
        int speciesCount = ButterflyInfo.SPECIES.length;
        List<DeferredHolder<Item, Item>> butterflyNets = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> fireproofButterflyNets = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> butterflyEggs = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> caterpillars = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> bottledButterflies = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> bottledCaterpillars = new ArrayList<>(speciesCount);
        List<DeferredHolder<Item, Item>> butterflyScrolls = new ArrayList<>(speciesCount);

        int peacemakerButterflyIndex = -1;
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            int butterflyIndex = i;
            String registryId = ButterflyNetItem.getRegistryId(butterflyIndex);

            DeferredHolder<Item, Item> butterflyNet = ITEMS.register(registryId,
                    () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)
                            .setId(createResourceKey(registryId)), butterflyIndex));

            butterflyNets.add(butterflyNet);

            DeferredHolder<Item, Item> fireproofButterflyNet = ITEMS.register("fireproof_" + registryId, () -> new ButterflyNetItem(butterflyIndex));
            fireproofButterflyNets.add(fireproofButterflyNet);

            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyTrait.PEACEMAKER)) {
                peacemakerButterflyIndex = butterflyIndex;
            }

            butterflyEggs.add(registerButterflyEgg(butterflyIndex));
            caterpillars.add(registerCaterpillar(butterflyIndex));
            bottledButterflies.add(registerBottledButterfly(butterflyIndex));
            bottledCaterpillars.add(registerBottledCaterpillar(butterflyIndex));
            butterflyScrolls.add(registerButterflyScroll(butterflyIndex));
        }

        return new SpeciesRegistrations(
                butterflyNets,
                fireproofButterflyNets,
                butterflyEggs,
                caterpillars,
                bottledButterflies,
                bottledCaterpillars,
                butterflyScrolls,
                peacemakerButterflyIndex);
    }
    
    static {
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);

        // Species-based.
        SpeciesRegistrations species = registerSpeciesItems();

        BUTTERFLY_NETS = Collections.unmodifiableList(species.butterflyNets());
        FIREPROOF_BUTTERFLY_NETS = Collections.unmodifiableList(species.fireproofButterflyNets());
        BUTTERFLY_EGGS = Collections.unmodifiableList(species.butterflyEggs());
        CATERPILLARS = Collections.unmodifiableList(species.caterpillars());
        BOTTLED_BUTTERFLIES = Collections.unmodifiableList(species.bottledButterflies());
        BOTTLED_CATERPILLARS = Collections.unmodifiableList(species.bottledCaterpillars());
        BUTTERFLY_SCROLLS = Collections.unmodifiableList(species.butterflyScrolls());

        // Nets
        EMPTY_BUTTERFLY_NET = ITEMS.register(ButterflyNetItem.EMPTY_NAME,
                () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)
                        .setId(createResourceKey(ButterflyNetItem.EMPTY_NAME)), -1));

        FIREPROOF_BUTTERFLY_NET = ITEMS.register("fireproof_" + ButterflyNetItem.EMPTY_NAME,
                () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)
                        .setId(createResourceKey("fireproof_" + ButterflyNetItem.EMPTY_NAME)), -1));

        PEACEMAKER_BUTTERFLY_NET = BUTTERFLY_NETS.get(species.peacemakerIndex());
        FIREPROOF_PEACEMAKER_BUTTERFLY_NET = FIREPROOF_BUTTERFLY_NETS.get(species.peacemakerIndex());

        BURNT_BUTTERFLY_NET = ITEMS.register("butterfly_net_burnt", () ->
                new Item(new Item.Properties().setId(createResourceKey("butterfly_net_burnt"))));

        // Books
        BUTTERFLY_BOOK = ITEMS.register(ButterflyBookItem.NAME, ButterflyBookItem::new);
        ZHUANGZI_BOOK = ITEMS.register(ButterflyZhuangZiItem.NAME, ButterflyZhuangZiItem::new);


        // Blocks
        BUTTERFLY_FEEDER = ITEMS.register("butterfly_feeder",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_FEEDER.get(),
                        new Item.Properties().setId(createResourceKey("butterfly_feeder"))));

        BUTTERFLY_MICROSCOPE = ITEMS.register("butterfly_microscope",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_MICROSCOPE.get(),
                        new Item.Properties().setId(createResourceKey("butterfly_microscope"))));

        // Infested Apple
        INFESTED_APPLE = ITEMS.register("infested_apple",
                () -> new Item(new Item.Properties().setId(createResourceKey("infested_apple"))));

        // Silk
        SILK = ITEMS.register("silk", () -> new Item(new Item.Properties().setId(createResourceKey("silk"))));

        // Origami
        List<DeferredHolder<Item, Item>> origami = new ArrayList<>(BlockRegistry.BUTTERFLY_ORIGAMI.size());
        for (DeferredHolder<Block, Block> block : BlockRegistry.BUTTERFLY_ORIGAMI) {
            ResourceLocation id = block.getId();
            if (id != null) {
                origami.add(ITEMS.register(
                        id.getPath(),
                        () -> new BlockItem(block.get(), new Item.Properties().setId(createResourceKey(id.getPath())))));
            }
        }

        BUTTERFLY_ORIGAMI = Collections.unmodifiableList(origami);

        // Sherd
        BUTTERFLY_POTTERY_SHERD = ITEMS.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties().setId(createResourceKey("butterfly_pottery_sherd"))));

        // Banner Pattern
        BUTTERFLY_BANNER_PATTERN = ITEMS.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                TagRegistry.BUTTERFLY_BANNER_PATTERN,
                (new Item.Properties())
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON)
                        .setId(createResourceKey("banner_pattern_butterfly"))));

        // Peacemaker Honey
        PEACEMAKER_HONEY_BOTTLE = ITEMS.register("peacemaker_honey_bottle",
                () -> new Item(new Item.Properties().stacksTo(1).setId(createResourceKey("peacemaker_honey_bottle"))));
    }

    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static DeferredHolder<Item, Item> getButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return EMPTY_BUTTERFLY_NET;
        }

        ButterflyData dataEntry = ButterflyRegistry.getEntry(butterflyIndex);
        if (dataEntry != null && dataEntry.hasTrait(ButterflyTrait.LAVA)) {
            return BURNT_BUTTERFLY_NET;
        }

        return BUTTERFLY_NETS.get(butterflyIndex);
    }

    /**
     * Helper method to get the correct FIREPROOF butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static DeferredHolder<Item, Item> getFireproofButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return FIREPROOF_BUTTERFLY_NET;
        }

        return FIREPROOF_BUTTERFLY_NETS.get(butterflyIndex);
    }

    // Register Methods

    private static DeferredHolder<Item, Item> registerBottledButterfly(int butterflyIndex) {
        String registryId = BottledButterflyItem.getRegistryId(butterflyIndex);
        return ITEMS.register(registryId,
                () -> new BottledButterflyItem(
                        new Item.Properties().stacksTo(1).setId(createResourceKey(registryId)),
                        BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(butterflyIndex),
                        butterflyIndex));
    }

    private static DeferredHolder<Item, Item> registerBottledCaterpillar(int butterflyIndex) {
        String registryId = BottledCaterpillarItem.getRegistryId(butterflyIndex);
        return ITEMS.register(registryId,
                () -> new BottledCaterpillarItem(
                        new Item.Properties().stacksTo(1).setId(createResourceKey(registryId)),
                        BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(butterflyIndex),
                        butterflyIndex));
    }

    private static DeferredHolder<Item, Item> registerButterflyEgg(int butterflyIndex) {
        String registryId = ButterflyEggItem.getRegistryId(butterflyIndex);
        return ITEMS.register(registryId,
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties().setId(createResourceKey(registryId))));
    }

    private static DeferredHolder<Item, Item> registerButterflyScroll(int butterflyIndex) {
        String registryId = ButterflyScrollItem.getRegistryId(butterflyIndex);
        return ITEMS.register(registryId,
                () -> new ButterflyScrollItem(butterflyIndex, new Item.Properties().setId(createResourceKey(registryId))));
    }

    private static DeferredHolder<Item, Item> registerCaterpillar(int butterflyIndex) {
        String registryId = CaterpillarItem.getRegistryId(butterflyIndex);
        return ITEMS.register(registryId,
                () -> new CaterpillarItem(
                        new Item.Properties().setId(createResourceKey(registryId)),
                        registryId));
    }

    /**
     * Prevent construction.
     */
    private ItemRegistry() {}
}
