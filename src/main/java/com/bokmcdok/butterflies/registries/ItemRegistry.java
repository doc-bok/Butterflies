package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<Item> EMPTY_BUTTERFLY_NET;
    public static final List<RegistryObject<Item>> BUTTERFLY_NETS;
    public static final RegistryObject<Item> BURNT_BUTTERFLY_NET;
    public static final RegistryObject<Item> PEACEMAKER_BUTTERFLY_NET;

    // Eggs
    public static final List<RegistryObject<Item>> BUTTERFLY_EGGS;

    // Caterpillars
    public static final List<RegistryObject<Item>> CATERPILLARS;

    // Bottles
    public static final List<RegistryObject<Item>> BOTTLED_BUTTERFLIES;
    public static final List<RegistryObject<Item>> BOTTLED_CATERPILLARS;

    // Scrolls
    public static final List<RegistryObject<Item>> BUTTERFLY_SCROLLS;

    // Books
    public static final RegistryObject<Item> BUTTERFLY_BOOK;
    public static final RegistryObject<Item> ZHUANGZI_BOOK;

    // Blocks
    public static final RegistryObject<Item> BUTTERFLY_FEEDER;
    public static final RegistryObject<Item> BUTTERFLY_MICROSCOPE;

    // Infested Apple
    public static final RegistryObject<Item> INFESTED_APPLE;

    // Silk
    public static final RegistryObject<Item> SILK;

    // Origami
    public static final List<RegistryObject<Item>> BUTTERFLY_ORIGAMI;

    // Sherd
    public static final RegistryObject<Item> BUTTERFLY_POTTERY_SHERD;

    // Banner Pattern
    public static final RegistryObject<Item> BUTTERFLY_BANNER_PATTERN;

    // Peacemaker Honey
    public static final RegistryObject<Item> PEACEMAKER_HONEY_BOTTLE;
    
    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MOD_ID);

        // Species-based.
        List<RegistryObject<Item>> butterflyNets = new ArrayList<>();
        List<RegistryObject<Item>> butterflyEggs = new ArrayList<>();
        List<RegistryObject<Item>> caterpillars = new ArrayList<>();
        List<RegistryObject<Item>> bottledButterflies = new ArrayList<>();
        List<RegistryObject<Item>> bottledCaterpillars = new ArrayList<>();
        List<RegistryObject<Item>> butterflyScrolls = new ArrayList<>();

        int peacemakerButterflyIndex = 0;
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            int butterflyIndex = i;
            String registryId = ButterflyNetItem.getRegistryId(butterflyIndex);
            RegistryObject<Item> butterflyNet = ITEMS.register(registryId, () -> new ButterflyNetItem(butterflyIndex));
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
        EMPTY_BUTTERFLY_NET = ITEMS.register(ButterflyNetItem.EMPTY_NAME, () -> new ButterflyNetItem(-1));
        PEACEMAKER_BUTTERFLY_NET = BUTTERFLY_NETS.get(peacemakerButterflyIndex);
        BURNT_BUTTERFLY_NET = ITEMS.register("butterfly_net_burnt", () -> new Item(new Item.Properties()));

        // Books
        BUTTERFLY_BOOK = ITEMS.register(ButterflyBookItem.NAME, ButterflyBookItem::new);
        ZHUANGZI_BOOK = ITEMS.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

        // Blocks
        BUTTERFLY_FEEDER = ITEMS.register("butterfly_feeder",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_FEEDER.get(), new Item.Properties()));

        BUTTERFLY_MICROSCOPE = ITEMS.register("butterfly_microscope",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_MICROSCOPE.get(), new Item.Properties()));

        // Infested Apple
        INFESTED_APPLE = ITEMS.register("infested_apple", () -> new Item(new Item.Properties()));

        // Silk
        SILK = ITEMS.register("silk", () -> new Item(new Item.Properties()));

        // Origami
        BUTTERFLY_ORIGAMI = new ArrayList<>();
        for (RegistryObject<Block> block : BlockRegistry.BUTTERFLY_ORIGAMI) {
            ResourceLocation id = block.getId();
            if (id != null) {
                BUTTERFLY_ORIGAMI.add(ITEMS.register(
                        id.getPath(),
                        () -> new BlockItem(block.get(), baseProperties)));
            }
        }

        // Sherd
        BUTTERFLY_POTTERY_SHERD = ITEMS.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties()));

        // Banner Pattern
        BUTTERFLY_BANNER_PATTERN = ITEMS.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                TagRegistry.BUTTERFLY_BANNER_PATTERN,
                (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

        // Peacemaker Honey
        PEACEMAKER_HONEY_BOTTLE = ITEMS.register("peacemaker_honey_bottle",
                () -> new Item(new Item.Properties().stacksTo(1)));
    }

    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static RegistryObject<Item> getButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return EMPTY_BUTTERFLY_NET;
        } else if (Objects.equals(ButterflyInfo.SPECIES[butterflyIndex], "lava")) {
            return BURNT_BUTTERFLY_NET;
        } else {
            return BUTTERFLY_NETS.get(butterflyIndex);
        }
    }

    // Register Methods
    private static RegistryObject<Item> registerBottledButterfly(int butterflyIndex) {
        return ITEMS.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    private static RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return ITEMS.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    private static RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return ITEMS.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    private static RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return ITEMS.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(butterflyIndex));
    }

    private static RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return ITEMS.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    /**
     * Prevent construction.
     */
    private ItemRegistry() {}
}
