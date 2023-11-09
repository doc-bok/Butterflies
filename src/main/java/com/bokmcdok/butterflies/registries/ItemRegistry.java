package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import com.bokmcdok.butterflies.world.item.ButterflyEggItem;
import com.bokmcdok.butterflies.world.item.ButterflyNetItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * This class registers items with Forge's Item Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> INSTANCE = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MODID);

    //  Butterfly net - Used to catch butterflies
    public static final RegistryObject<Item> BUTTERFLY_NET = INSTANCE.register(ButterflyNetItem.NAME,
            () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)));

    // Bottled butterfly - A butterfly trapped in a bottle.
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY = INSTANCE.register(BottledButterflyItem.NAME,
            () -> new BottledButterflyItem(new Item.Properties().stacksTo(1)));

    // Butterfly Scroll
    public static final RegistryObject<Item> BUTTERFLY_SCROLL = INSTANCE.register(ButterflyScrollItem.NAME,
            () -> new ButterflyScrollItem(new Item.Properties()));

    // Butterfly Eggs - Eggs that will eventually hatch into a caterpillar.
    public static final RegistryObject<Item> ADMIRAL_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.ADMIRAL_NAME,
            () -> new ButterflyEggItem(Butterfly.ADMIRAL_NAME, new Item.Properties()));

    public static final RegistryObject<Item> BUCKEYE_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.BUCKEYE_NAME,
            () -> new ButterflyEggItem(Butterfly.BUCKEYE_NAME, new Item.Properties()));

    public static final RegistryObject<Item> CABBAGE_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.CABBAGE_NAME,
            () -> new ButterflyEggItem(Butterfly.CABBAGE_NAME, new Item.Properties()));

    public static final RegistryObject<Item> CHALKHILL_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.CHALKHILL_NAME,
            () -> new ButterflyEggItem(Butterfly.CHALKHILL_NAME, new Item.Properties()));

    public static final RegistryObject<Item> CLIPPER_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.CLIPPER_NAME,
            () -> new ButterflyEggItem(Butterfly.CLIPPER_NAME, new Item.Properties()));

    public static final RegistryObject<Item> COMMON_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.COMMON_NAME,
            () -> new ButterflyEggItem(Butterfly.COMMON_NAME, new Item.Properties()));

    public static final RegistryObject<Item> EMPEROR_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.EMPEROR_NAME,
            () -> new ButterflyEggItem(Butterfly.EMPEROR_NAME, new Item.Properties()));

    public static final RegistryObject<Item> FORESTER_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.FORESTER_NAME,
            () -> new ButterflyEggItem(Butterfly.FORESTER_NAME, new Item.Properties()));

    public static final RegistryObject<Item> GLASSWING_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.GLASSWING_NAME,
            () -> new ButterflyEggItem(Butterfly.GLASSWING_NAME, new Item.Properties()));

    public static final RegistryObject<Item> HAIRSTREAK_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.HAIRSTREAK_NAME,
            () -> new ButterflyEggItem(Butterfly.HAIRSTREAK_NAME, new Item.Properties()));

    public static final RegistryObject<Item> HEATH_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.HEATH_NAME,
            () -> new ButterflyEggItem(Butterfly.HEATH_NAME, new Item.Properties()));

    public static final RegistryObject<Item> LONGWING_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.LONGWING_NAME,
            () -> new ButterflyEggItem(Butterfly.LONGWING_NAME, new Item.Properties()));

    public static final RegistryObject<Item> MONARCH_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.MONARCH_NAME,
            () -> new ButterflyEggItem(Butterfly.MONARCH_NAME, new Item.Properties()));

    public static final RegistryObject<Item> MORPHO_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.MORPHO_NAME,
            () -> new ButterflyEggItem(Butterfly.MORPHO_NAME, new Item.Properties()));

    public static final RegistryObject<Item> RAINBOW_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.RAINBOW_NAME,
            () -> new ButterflyEggItem(Butterfly.RAINBOW_NAME, new Item.Properties()));

    public static final RegistryObject<Item> SWALLOWTAIL_BUTTERFLY_EGG = INSTANCE.register(ButterflyEggItem.SWALLOWTAIL_NAME,
            () -> new ButterflyEggItem(Butterfly.SWALLOWTAIL_NAME, new Item.Properties()));

    //  Spawn eggs
    private static final RegistryObject<Item> BUTTERFLY_MORPHO_EGG = INSTANCE.register(Butterfly.MORPHO_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_MORPHO, 0x0000aa, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_FORESTER_EGG = INSTANCE.register(Butterfly.FORESTER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_FORESTER, 0xeeee77, 0xff7777, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_COMMON_EGG = INSTANCE.register(Butterfly.COMMON_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_COMMON, 0xaaff66, 0xeeee77, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_EMPEROR_EGG = INSTANCE.register(Butterfly.EMPEROR_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_EMPEROR, 0xcc44cc, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_HAIRSTREAK_EGG = INSTANCE.register(Butterfly.HAIRSTREAK_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_HAIRSTREAK, 0xcc44cc, 0x880000, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_RAINBOW_EGG = INSTANCE.register(Butterfly.RAINBOW_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_RAINBOW, 0xff7777, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_HEATH_EGG = INSTANCE.register(Butterfly.HEATH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_HEATH, 0x880000, 0x000000, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_GLASSWING_EGG = INSTANCE.register(Butterfly.GLASSWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_GLASSWING, 0x880000, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_CHALKHILL_EGG = INSTANCE.register(Butterfly.CHALKHILL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CHALKHILL, 0x0088ff, 0x00cc55, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_SWALLOWTAIL_EGG = INSTANCE.register(Butterfly.SWALLOWTAIL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_SWALLOWTAIL, 0xffffff, 0xeeee77, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_MONARCH_EGG = INSTANCE.register(Butterfly.MONARCH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_MONARCH, 0xdd8855, 0x000000, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_CABBAGE_EGG = INSTANCE.register(Butterfly.CABBAGE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CABBAGE, 0xeeee77, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_ADMIRAL_EGG = INSTANCE.register(Butterfly.ADMIRAL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_ADMIRAL, 0x880000, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_LONGWING_EGG = INSTANCE.register(Butterfly.LONGWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_LONGWING, 0x000000, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_CLIPPER_EGG = INSTANCE.register(Butterfly.CLIPPER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CLIPPER, 0x0044aa, 0x004488, new Item.Properties()));

    private static final RegistryObject<Item> BUTTERFLY_BUCKEYE_EGG = INSTANCE.register(Butterfly.BUCKEYE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_BUCKEYE, 0xcccc88, 0x8888cc, new Item.Properties()));

    //  Spawn eggs
    private static final RegistryObject<Item> CATERPILLAR_MORPHO_EGG = INSTANCE.register(Caterpillar.MORPHO_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_MORPHO, 0x0000aa, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_FORESTER_EGG = INSTANCE.register(Caterpillar.FORESTER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_FORESTER, 0xeeee77, 0xff7777, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_COMMON_EGG = INSTANCE.register(Caterpillar.COMMON_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_COMMON, 0xaaff66, 0xeeee77, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_EMPEROR_EGG = INSTANCE.register(Caterpillar.EMPEROR_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_EMPEROR, 0xcc44cc, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_HAIRSTREAK_EGG = INSTANCE.register(Caterpillar.HAIRSTREAK_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_HAIRSTREAK, 0xcc44cc, 0x880000, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_RAINBOW_EGG = INSTANCE.register(Caterpillar.RAINBOW_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_RAINBOW, 0xff7777, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_HEATH_EGG = INSTANCE.register(Caterpillar.HEATH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_HEATH, 0x880000, 0x000000, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_GLASSWING_EGG = INSTANCE.register(Caterpillar.GLASSWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_GLASSWING, 0x880000, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_CHALKHILL_EGG = INSTANCE.register(Caterpillar.CHALKHILL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CHALKHILL, 0x0088ff, 0x00cc55, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_SWALLOWTAIL_EGG = INSTANCE.register(Caterpillar.SWALLOWTAIL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_SWALLOWTAIL, 0xffffff, 0xeeee77, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_MONARCH_EGG = INSTANCE.register(Caterpillar.MONARCH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_MONARCH, 0xdd8855, 0x000000, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_CABBAGE_EGG = INSTANCE.register(Caterpillar.CABBAGE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CABBAGE, 0xeeee77, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_ADMIRAL_EGG = INSTANCE.register(Caterpillar.ADMIRAL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_ADMIRAL, 0x880000, 0x0088ff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_LONGWING_EGG = INSTANCE.register(Caterpillar.LONGWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_LONGWING, 0x000000, 0xffffff, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_CLIPPER_EGG = INSTANCE.register(Caterpillar.CLIPPER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CLIPPER, 0x0044aa, 0x004488, new Item.Properties()));

    private static final RegistryObject<Item> CATERPILLAR_BUCKEYE_EGG = INSTANCE.register(Caterpillar.BUCKEYE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_BUCKEYE, 0xcccc88, 0x8888cc, new Item.Properties()));

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(BOTTLED_BUTTERFLY);
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BUTTERFLY_MORPHO_EGG);
            event.accept(BUTTERFLY_FORESTER_EGG);
            event.accept(BUTTERFLY_COMMON_EGG);
            event.accept(BUTTERFLY_EMPEROR_EGG);
            event.accept(BUTTERFLY_HAIRSTREAK_EGG);
            event.accept(BUTTERFLY_RAINBOW_EGG);
            event.accept(BUTTERFLY_HEATH_EGG);
            event.accept(BUTTERFLY_GLASSWING_EGG);
            event.accept(BUTTERFLY_CHALKHILL_EGG);
            event.accept(BUTTERFLY_SWALLOWTAIL_EGG);
            event.accept(BUTTERFLY_MONARCH_EGG);
            event.accept(BUTTERFLY_CABBAGE_EGG);
            event.accept(BUTTERFLY_ADMIRAL_EGG);
            event.accept(BUTTERFLY_LONGWING_EGG);
            event.accept(BUTTERFLY_CLIPPER_EGG);
            event.accept(BUTTERFLY_BUCKEYE_EGG);

            event.accept(CATERPILLAR_MORPHO_EGG);
            event.accept(CATERPILLAR_FORESTER_EGG);
            event.accept(CATERPILLAR_COMMON_EGG);
            event.accept(CATERPILLAR_EMPEROR_EGG);
            event.accept(CATERPILLAR_HAIRSTREAK_EGG);
            event.accept(CATERPILLAR_RAINBOW_EGG);
            event.accept(CATERPILLAR_HEATH_EGG);
            event.accept(CATERPILLAR_GLASSWING_EGG);
            event.accept(CATERPILLAR_CHALKHILL_EGG);
            event.accept(CATERPILLAR_SWALLOWTAIL_EGG);
            event.accept(CATERPILLAR_MONARCH_EGG);
            event.accept(CATERPILLAR_CABBAGE_EGG);
            event.accept(CATERPILLAR_ADMIRAL_EGG);
            event.accept(CATERPILLAR_LONGWING_EGG);
            event.accept(CATERPILLAR_CLIPPER_EGG);
            event.accept(CATERPILLAR_BUCKEYE_EGG);

            event.accept(ADMIRAL_BUTTERFLY_EGG);
            event.accept(BUCKEYE_BUTTERFLY_EGG);
            event.accept(CABBAGE_BUTTERFLY_EGG);
            event.accept(CHALKHILL_BUTTERFLY_EGG);
            event.accept(CLIPPER_BUTTERFLY_EGG);
            event.accept(COMMON_BUTTERFLY_EGG);
            event.accept(EMPEROR_BUTTERFLY_EGG);
            event.accept(FORESTER_BUTTERFLY_EGG);
            event.accept(GLASSWING_BUTTERFLY_EGG);
            event.accept(HAIRSTREAK_BUTTERFLY_EGG);
            event.accept(HEATH_BUTTERFLY_EGG);
            event.accept(LONGWING_BUTTERFLY_EGG);
            event.accept(MONARCH_BUTTERFLY_EGG);
            event.accept(MORPHO_BUTTERFLY_EGG);
            event.accept(RAINBOW_BUTTERFLY_EGG);
            event.accept(SWALLOWTAIL_BUTTERFLY_EGG);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BUTTERFLY_SCROLL);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BUTTERFLY_NET);
        }
    }
}
