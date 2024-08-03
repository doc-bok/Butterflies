package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
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
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class registers items with Forge's Item Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> INSTANCE = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MODID);

    //  Butterfly net - Used to catch butterflies
    private static RegistryObject<Item> registerButterflyNet(int butterflyIndex) {
        return INSTANCE.register(ButterflyNetItem.getRegistryId(butterflyIndex),
                () -> new ButterflyNetItem(butterflyIndex));
    }

    public static final List<RegistryObject<Item>> BUTTERFLY_NET_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterflyNet(i));
            }
        }
    };

    public static final RegistryObject<Item> BUTTERFLY_NET = INSTANCE.register(ButterflyNetItem.EMPTY_NAME,
            () -> new ButterflyNetItem(-1));

    // Bottled butterfly - A butterfly trapped in a bottle.
    private static RegistryObject<Item> registerBottledButterfly(int butterflyIndex) {
        return INSTANCE.register(BottledButterflyItem.getRegistryId(butterflyIndex),
                () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    public static final List<RegistryObject<Item>> BOTTLED_BUTTERFLY_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledButterfly(i));
            }
        }
    };

    // Butterfly Scroll
    private static RegistryObject<Item> registerButterflyScroll(int butterflyIndex) {
        return INSTANCE.register(ButterflyScrollItem.getRegistryId(butterflyIndex),
                () -> new ButterflyScrollItem(butterflyIndex));
    }

    public static final List<RegistryObject<Item>> BUTTERFLY_SCROLL_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterflyScroll(i));
            }
        }
    };

    // Butterfly Book
    public static final RegistryObject<Item> BUTTERFLY_BOOK =
            INSTANCE.register(ButterflyBookItem.NAME, ButterflyBookItem::new);

    // Zhuangzi
    public static final RegistryObject<Item> BUTTERFLY_ZHUANGZI =
            INSTANCE.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

    // Butterfly Eggs - Eggs that will eventually hatch into a caterpillar.
    private static RegistryObject<Item> registerButterflyEgg(int butterflyIndex) {
        return INSTANCE.register(ButterflyEggItem.getRegistryId(butterflyIndex),
                () -> new ButterflyEggItem(butterflyIndex, new Item.Properties()));
    }

    public static final List<RegistryObject<Item>> BUTTERFLY_EGG_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterflyEgg(i));
            }
        }
    };

    //  Caterpillars
    private static RegistryObject<Item> registerCaterpillar(int butterflyIndex) {
        return INSTANCE.register(CaterpillarItem.getRegistryId(butterflyIndex),
                () -> new CaterpillarItem(Caterpillar.getRegistryId(butterflyIndex)));
    }

    public static final List<RegistryObject<Item>> CATERPILLAR_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerCaterpillar(i));
            }
        }
    };

    // Bottled Caterpillars
    private static RegistryObject<Item> registerBottledCaterpillar(int butterflyIndex) {
        return INSTANCE.register(BottledCaterpillarItem.getRegistryId(butterflyIndex),
                () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS.get(butterflyIndex), butterflyIndex));
    }

    public static final List<RegistryObject<Item>> BOTTLED_CATERPILLAR_ITEMS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledCaterpillar(i));
            }
        }
    };
    
    //  Spawn eggs - Butterflies
    private static RegistryObject<Item> registerButterflySpawnEgg(int butterflyIndex) {
        return INSTANCE.register(Butterfly.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_ENTITIES.get(butterflyIndex),
                        0x880000, 0x0088ff, new Item.Properties()));
    }

    public static final List<RegistryObject<Item>> BUTTERFLY_SPAWN_EGGS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerButterflySpawnEgg(i));
            }
        }
    };
    
    //  Spawn eggs - Caterpillars
    private static RegistryObject<Item> registerCaterpillarSpawnEgg(int butterflyIndex) {
        return INSTANCE.register(Caterpillar.getRegistryId(butterflyIndex),
                () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_ENTITIES.get(butterflyIndex),
                        0x0088ff, 0x880000, new Item.Properties()));
    }

    public static final List<RegistryObject<Item>> CATERPILLAR_SPAWN_EGGS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerCaterpillarSpawnEgg(i));
            }
        }
    };

    // Silk dropped by some moths.
    public static final RegistryObject<Item> SILK = INSTANCE.register("silk", () -> new Item(new Item.Properties()));

    // Apples infested with a Codling Larva.
    public static final RegistryObject<Item> INFESTED_APPLE =
            INSTANCE.register("infested_apple", () -> new Item(new Item.Properties()));


    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static RegistryObject<Item> getButterflyNetFromIndex(int butterflyIndex) {
        if (butterflyIndex < 0) {
            return BUTTERFLY_NET;
        } else {
            return BUTTERFLY_NET_ITEMS.get(butterflyIndex);
        }
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerCreativeTabContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SILK);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            for (RegistryObject<Item> i : BUTTERFLY_EGG_ITEMS) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : CATERPILLAR_ITEMS) {
                event.accept(i);
            }
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            for (RegistryObject<Item> i : BUTTERFLY_SPAWN_EGGS) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : CATERPILLAR_SPAWN_EGGS) {
                event.accept(i);
            }
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.accept(BUTTERFLY_NET);
            for (RegistryObject<Item> i : BUTTERFLY_NET_ITEMS) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : BOTTLED_BUTTERFLY_ITEMS) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : BOTTLED_CATERPILLAR_ITEMS) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : BUTTERFLY_SCROLL_ITEMS) {
                event.accept(i);
            }

            event.accept(BUTTERFLY_BOOK);
            event.accept(BUTTERFLY_ZHUANGZI);
        }
    }
}
