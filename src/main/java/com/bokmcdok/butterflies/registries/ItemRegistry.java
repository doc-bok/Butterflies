package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import com.bokmcdok.butterflies.world.item.BottledCaterpillarItem;
import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import com.bokmcdok.butterflies.world.item.ButterflyEggItem;
import com.bokmcdok.butterflies.world.item.ButterflyNetItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import com.bokmcdok.butterflies.world.item.ButterflyZhuangziItem;
import com.bokmcdok.butterflies.world.item.CaterpillarItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class registers items with Forge's Item Registry
 */
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> REGISTER;

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

    // Spawn Eggs
    public static final List<RegistryObject<Item>> EGG_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> CHRYSALIS_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> CATERPILLAR_SPAWN_EGGS;
    public static final List<RegistryObject<Item>> BUTTERFLY_SPAWN_EGGS;
    public static final RegistryObject<Item> BUTTERFLY_GOLEM_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_BUTTERFLY_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_EVOKER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_ILLUSIONER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_PILLAGER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_VILLAGER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_VINDICATOR_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_WANDERING_TRADER_SPAWN_EGG;
    public static final RegistryObject<Item> PEACEMAKER_WITCH_SPAWN_EGG;
    
    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MOD_ID);

        // Nets
        EMPTY_BUTTERFLY_NET = REGISTER.register(ButterflyNetItem.EMPTY_NAME, () -> new ButterflyNetItem(-1));
        BUTTERFLY_NETS = new ArrayList<>();

        int peacemakerButterflyIndex = 0;
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            int butterflyIndex = i;
            String registryId = ButterflyNetItem.getRegistryId(butterflyIndex);
            RegistryObject<Item> butterflyNet = REGISTER.register(registryId, () -> new ButterflyNetItem(butterflyIndex));
            BUTTERFLY_NETS.add(butterflyNet);

            if (registryId.contains("peacemaker")) {
                peacemakerButterflyIndex = i;
            }
        }

        PEACEMAKER_BUTTERFLY_NET = BUTTERFLY_NETS.get(peacemakerButterflyIndex);
        BURNT_BUTTERFLY_NET = REGISTER.register("butterfly_net_burnt", () -> new Item(new Item.Properties()));

        // Eggs
        BUTTERFLY_EGGS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            BUTTERFLY_EGGS.add(registerButterflyEgg(i));
        }

        // Caterpillars
        CATERPILLARS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            CATERPILLARS.add(registerCaterpillar(i));
        }

        // Bottles
        BOTTLED_BUTTERFLIES = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            BOTTLED_BUTTERFLIES.add(registerBottledButterfly(i));
        }

        BOTTLED_CATERPILLARS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            BOTTLED_CATERPILLARS.add(registerBottledCaterpillar(i));
        }

        // Scrolls
        BUTTERFLY_SCROLLS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            BUTTERFLY_SCROLLS.add(registerButterflyScroll(i));
        }

        // Books
        BUTTERFLY_BOOK = REGISTER.register(ButterflyBookItem.NAME, ButterflyBookItem::new);
        ZHUANGZI_BOOK = REGISTER.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

        // Blocks
        BUTTERFLY_FEEDER = REGISTER.register("butterfly_feeder",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_FEEDER.get(), new Item.Properties()));

        BUTTERFLY_MICROSCOPE = REGISTER.register("butterfly_microscope",
                () -> new BlockItem(BlockRegistry.BUTTERFLY_MICROSCOPE.get(), new Item.Properties()));

        // Infested Apple
        INFESTED_APPLE = REGISTER.register("infested_apple", () -> new Item(new Item.Properties()));

        // Silk
        SILK = REGISTER.register("silk", () -> new Item(new Item.Properties()));

        // Origami
        BUTTERFLY_ORIGAMI = new ArrayList<>();
        for (RegistryObject<Block> block : BlockRegistry.BUTTERFLY_ORIGAMI) {
            ResourceLocation id = block.getId();
            if (id != null) {
                BUTTERFLY_ORIGAMI.add(REGISTER.register(
                        id.getPath(),
                        () -> new BlockItem(block.get(), new Item.Properties())));
            }
        }

        // Sherd
        BUTTERFLY_POTTERY_SHERD = REGISTER.register("butterfly_pottery_sherd",
                () -> new Item(new Item.Properties()));

        // Banner Pattern
        BUTTERFLY_BANNER_PATTERN = REGISTER.register("banner_pattern_butterfly", () -> new BannerPatternItem(
                TagRegistry.BUTTERFLY_BANNER_PATTERN,
                (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

        // Peacemaker Honey
        PEACEMAKER_HONEY_BOTTLE = REGISTER.register("peacemaker_honey_bottle",
                () -> new Item(new Item.Properties().stacksTo(1)));

        // Spawn Eggs
        EGG_SPAWN_EGGS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            EGG_SPAWN_EGGS.add(registerButterflyEggSpawnEgg(i));
        }
        CHRYSALIS_SPAWN_EGGS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            CHRYSALIS_SPAWN_EGGS.add(registerChrysalisSpawnEgg(i));
        }

        CATERPILLAR_SPAWN_EGGS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            CATERPILLAR_SPAWN_EGGS.add(registerCaterpillarSpawnEgg(i));
        }

        BUTTERFLY_SPAWN_EGGS = new ArrayList<>();
        for (int i = 0; i < ButterflyInfo.SPECIES.length; ++i) {
            BUTTERFLY_SPAWN_EGGS.add(registerButterflySpawnEgg(i));
        }

        BUTTERFLY_GOLEM_SPAWN_EGG = REGISTER.register("spawn_egg_golem_butterfly",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_GOLEM,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_BUTTERFLY_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_butterfly",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_BUTTERFLY,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_EVOKER_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_evoker",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_EVOKER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_ILLUSIONER_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_illusioner",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_ILLUSIONER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_PILLAGER_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_pillager",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_PILLAGER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_VILLAGER_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_villager",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_VILLAGER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_VINDICATOR_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_vindicator",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_VINDICATOR,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_WANDERING_TRADER_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_wandering_trader",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_WANDERING_TRADER,
                        0xffffff, 0xffffff, new Item.Properties()));

        PEACEMAKER_WITCH_SPAWN_EGG = REGISTER.register("spawn_egg_peacemaker_witch",
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.PEACEMAKER_WITCH,
                        0xffffff, 0xffffff, new Item.Properties()));
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
        return REGISTER.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    private static RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return REGISTER.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    private static RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return REGISTER.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    private static RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return REGISTER.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(butterflyIndex));
    }

    private static RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return REGISTER.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    private static RegistryObject<Item> registerButterflyEggSpawnEgg(int butterflyIndex) {
        return REGISTER.register("spawn_egg_egg_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_EGGS.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return REGISTER.register("spawn_egg_" + CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLARS.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerChrysalisSpawnEgg(int butterflyIndex) {
        return REGISTER.register("spawn_egg_chrysalis_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.CHRYSALISES.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }

    private static RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return REGISTER.register("spawn_egg_butterfly_" + Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLIES.get(butterflyIndex),
                        0xffffff, 0xffffff, new Item.Properties()));
    }
}
