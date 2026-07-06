package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class registers items with Forge's Item Registry
 */
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> ITEMS;

    // Nets
    public static final DeferredHolder<Item, Item> EMPTY_BUTTERFLY_NET;
    public static final List<DeferredHolder<Item, Item>> BUTTERFLY_NETS;
    public static final DeferredHolder<Item, Item> BURNT_BUTTERFLY_NET;
    public static final DeferredHolder<Item, Item> PEACEMAKER_BUTTERFLY_NET;

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
    
    static {
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);

        // Species-based.
        List<DeferredHolder<Item, Item>> butterflyNets = new ArrayList<>();
        List<DeferredHolder<Item, Item>> butterflyEggs = new ArrayList<>();
        List<DeferredHolder<Item, Item>> caterpillars = new ArrayList<>();
        List<DeferredHolder<Item, Item>> bottledButterflies = new ArrayList<>();
        List<DeferredHolder<Item, Item>> bottledCaterpillars = new ArrayList<>();
        List<DeferredHolder<Item, Item>> butterflyScrolls = new ArrayList<>();

        int peacemakerButterflyIndex = 0;
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            int butterflyIndex = i;
            String registryId = ButterflyNetItem.getRegistryId(butterflyIndex);

            DeferredHolder<Item, Item> butterflyNet = ITEMS.register(registryId,
                    () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)
                            .setId(createResourceKey(registryId)), butterflyIndex));

            butterflyNets.add(butterflyNet);

            if (registryId.contains("peacemaker")) {
                peacemakerButterflyIndex = butterflyIndex;
            }

            butterflyEggs.add(registerButterflyEgg(butterflyIndex));
            caterpillars.add(registerCaterpillar(butterflyIndex));
            bottledButterflies.add(registerBottledButterfly(butterflyIndex));
            bottledCaterpillars.add(registerBottledCaterpillar(butterflyIndex));
            butterflyScrolls.add(registerButterflyScroll(butterflyIndex));
        }

        BUTTERFLY_NETS = Collections.unmodifiableList(butterflyNets);
        BUTTERFLY_EGGS = Collections.unmodifiableList(butterflyEggs);
        CATERPILLARS = Collections.unmodifiableList(caterpillars);
        BOTTLED_BUTTERFLIES = Collections.unmodifiableList(bottledButterflies);
        BOTTLED_CATERPILLARS = Collections.unmodifiableList(bottledCaterpillars);
        BUTTERFLY_SCROLLS = Collections.unmodifiableList(butterflyScrolls);

        // Nets
        EMPTY_BUTTERFLY_NET = ITEMS.register(ButterflyNetItem.EMPTY_NAME,
                () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)
                        .setId(createResourceKey(ButterflyNetItem.EMPTY_NAME)), -1));

        PEACEMAKER_BUTTERFLY_NET = BUTTERFLY_NETS.get(peacemakerButterflyIndex);
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
        BUTTERFLY_ORIGAMI = new ArrayList<>();
        for (DeferredHolder<Block, Block> block : BlockRegistry.BUTTERFLY_ORIGAMI) {
            ResourceLocation id = block.getId();
            if (id != null) {
                BUTTERFLY_ORIGAMI.add(ITEMS.register(
                        id.getPath(),
                        () -> new BlockItem(block.get(), new Item.Properties().setId(createResourceKey(id.getPath())))));
            }
        }

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
        } else if (Objects.equals(ButterflyInfo.SPECIES[butterflyIndex], "lava")) {
            return BURNT_BUTTERFLY_NET;
        } else {
            return BUTTERFLY_NETS.get(butterflyIndex);
        }
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
