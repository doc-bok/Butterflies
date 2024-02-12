package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
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

/**
 * This class registers items with Forge's Item Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> INSTANCE = DeferredRegister.create(ForgeRegistries.ITEMS, ButterfliesMod.MODID);

    //  Butterfly net - Used to catch butterflies
    public static final RegistryObject<Item> BUTTERFLY_NET = INSTANCE.register(ButterflyNetItem.EMPTY_NAME,
            () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BUTTERFLY_NET_FULL = INSTANCE.register(ButterflyNetItem.FULL_NAME,
            () -> new ButterflyNetItem(new Item.Properties().stacksTo(1)));

    // Bottled butterfly - A butterfly trapped in a bottle.
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY = INSTANCE.register(BottledButterflyItem.NAME,
            () -> new BottledButterflyItem(new Item.Properties().stacksTo(1)));

    // Butterfly Scroll
    public static final RegistryObject<Item> BUTTERFLY_SCROLL =
            INSTANCE.register(ButterflyScrollItem.NAME, ButterflyScrollItem::new);

    // Butterfly Book
    public static final RegistryObject<Item> BUTTERFLY_BOOK =
            INSTANCE.register(ButterflyBookItem.NAME, ButterflyBookItem::new);

    // Zhuangzi
    public static final RegistryObject<Item> BUTTERFLY_ZHUANGZI =
            INSTANCE.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

    // Butterfly Eggs - Eggs that will eventually hatch into a caterpillar.
    public static final RegistryObject<Item> BUTTERFLY_EGG_ADMIRAL = INSTANCE.register(ButterflyEggItem.ADMIRAL_NAME,
            () -> new ButterflyEggItem(Butterfly.ADMIRAL_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_BUCKEYE = INSTANCE.register(ButterflyEggItem.BUCKEYE_NAME,
            () -> new ButterflyEggItem(Butterfly.BUCKEYE_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CABBAGE = INSTANCE.register(ButterflyEggItem.CABBAGE_NAME,
            () -> new ButterflyEggItem(Butterfly.CABBAGE_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CHALKHILL = INSTANCE.register(ButterflyEggItem.CHALKHILL_NAME,
            () -> new ButterflyEggItem(Butterfly.CHALKHILL_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CLIPPER = INSTANCE.register(ButterflyEggItem.CLIPPER_NAME,
            () -> new ButterflyEggItem(Butterfly.CLIPPER_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_COMMON = INSTANCE.register(ButterflyEggItem.COMMON_NAME,
            () -> new ButterflyEggItem(Butterfly.COMMON_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_EMPEROR = INSTANCE.register(ButterflyEggItem.EMPEROR_NAME,
            () -> new ButterflyEggItem(Butterfly.EMPEROR_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_FORESTER = INSTANCE.register(ButterflyEggItem.FORESTER_NAME,
            () -> new ButterflyEggItem(Butterfly.FORESTER_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_GLASSWING = INSTANCE.register(ButterflyEggItem.GLASSWING_NAME,
            () -> new ButterflyEggItem(Butterfly.GLASSWING_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_HAIRSTREAK = INSTANCE.register(ButterflyEggItem.HAIRSTREAK_NAME,
            () -> new ButterflyEggItem(Butterfly.HAIRSTREAK_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_HEATH = INSTANCE.register(ButterflyEggItem.HEATH_NAME,
            () -> new ButterflyEggItem(Butterfly.HEATH_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_LONGWING = INSTANCE.register(ButterflyEggItem.LONGWING_NAME,
            () -> new ButterflyEggItem(Butterfly.LONGWING_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_MONARCH = INSTANCE.register(ButterflyEggItem.MONARCH_NAME,
            () -> new ButterflyEggItem(Butterfly.MONARCH_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_MORPHO = INSTANCE.register(ButterflyEggItem.MORPHO_NAME,
            () -> new ButterflyEggItem(Butterfly.MORPHO_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_RAINBOW = INSTANCE.register(ButterflyEggItem.RAINBOW_NAME,
            () -> new ButterflyEggItem(Butterfly.RAINBOW_NAME, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_SWALLOWTAIL = INSTANCE.register(ButterflyEggItem.SWALLOWTAIL_NAME,
            () -> new ButterflyEggItem(Butterfly.SWALLOWTAIL_NAME, new Item.Properties()));

    //  Caterpillars
    public static final RegistryObject<Item> CATERPILLAR_ADMIRAL = INSTANCE.register(CaterpillarItem.ADMIRAL_NAME,
            () -> new CaterpillarItem(Caterpillar.ADMIRAL_NAME));
    public static final RegistryObject<Item> CATERPILLAR_BUCKEYE = INSTANCE.register(CaterpillarItem.BUCKEYE_NAME,
            () -> new CaterpillarItem(Caterpillar.BUCKEYE_NAME));
    public static final RegistryObject<Item> CATERPILLAR_CABBAGE = INSTANCE.register(CaterpillarItem.CABBAGE_NAME,
            () -> new CaterpillarItem(Caterpillar.CABBAGE_NAME));
    public static final RegistryObject<Item> CATERPILLAR_CHALKHILL = INSTANCE.register(CaterpillarItem.CHALKHILL_NAME,
            () -> new CaterpillarItem(Caterpillar.CHALKHILL_NAME));
    public static final RegistryObject<Item> CATERPILLAR_CLIPPER = INSTANCE.register(CaterpillarItem.CLIPPER_NAME,
            () -> new CaterpillarItem(Caterpillar.CLIPPER_NAME));
    public static final RegistryObject<Item> CATERPILLAR_COMMON = INSTANCE.register(CaterpillarItem.COMMON_NAME,
            () -> new CaterpillarItem(Caterpillar.COMMON_NAME));
    public static final RegistryObject<Item> CATERPILLAR_EMPEROR = INSTANCE.register(CaterpillarItem.EMPEROR_NAME,
            () -> new CaterpillarItem(Caterpillar.EMPEROR_NAME));
    public static final RegistryObject<Item> CATERPILLAR_FORESTER = INSTANCE.register(CaterpillarItem.FORESTER_NAME,
            () -> new CaterpillarItem(Caterpillar.FORESTER_NAME));
    public static final RegistryObject<Item> CATERPILLAR_GLASSWING = INSTANCE.register(CaterpillarItem.GLASSWING_NAME,
            () -> new CaterpillarItem(Caterpillar.GLASSWING_NAME));
    public static final RegistryObject<Item> CATERPILLAR_HAIRSTREAK = INSTANCE.register(CaterpillarItem.HAIRSTREAK_NAME,
            () -> new CaterpillarItem(Caterpillar.HAIRSTREAK_NAME));
    public static final RegistryObject<Item> CATERPILLAR_HEATH = INSTANCE.register(CaterpillarItem.HEATH_NAME,
            () -> new CaterpillarItem(Caterpillar.HEATH_NAME));
    public static final RegistryObject<Item> CATERPILLAR_LONGWING = INSTANCE.register(CaterpillarItem.LONGWING_NAME,
            () -> new CaterpillarItem(Caterpillar.LONGWING_NAME));
    public static final RegistryObject<Item> CATERPILLAR_MONARCH = INSTANCE.register(CaterpillarItem.MONARCH_NAME,
            () -> new CaterpillarItem(Caterpillar.MONARCH_NAME));
    public static final RegistryObject<Item> CATERPILLAR_MORPHO = INSTANCE.register(CaterpillarItem.MORPHO_NAME,
            () -> new CaterpillarItem(Caterpillar.MORPHO_NAME));
    public static final RegistryObject<Item> CATERPILLAR_RAINBOW = INSTANCE.register(CaterpillarItem.RAINBOW_NAME,
            () -> new CaterpillarItem(Caterpillar.RAINBOW_NAME));
    public static final RegistryObject<Item> CATERPILLAR_SWALLOWTAIL = INSTANCE.register(CaterpillarItem.SWALLOWTAIL_NAME,
            () -> new CaterpillarItem(Caterpillar.SWALLOWTAIL_NAME));

    // Bottled Caterpillars
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_ADMIRAL = INSTANCE.register(BottledCaterpillarItem.ADMIRAL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_ADMIRAL, Caterpillar.ADMIRAL_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_BUCKEYE = INSTANCE.register(BottledCaterpillarItem.BUCKEYE_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_BUCKEYE, Caterpillar.BUCKEYE_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_CABBAGE = INSTANCE.register(BottledCaterpillarItem.CABBAGE_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CABBAGE, Caterpillar.CABBAGE_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_CHALKHILL = INSTANCE.register(BottledCaterpillarItem.CHALKHILL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CHALKHILL, Caterpillar.CHALKHILL_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_CLIPPER = INSTANCE.register(BottledCaterpillarItem.CLIPPER_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CLIPPER, Caterpillar.CLIPPER_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_COMMON = INSTANCE.register(BottledCaterpillarItem.COMMON_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_COMMON, Caterpillar.COMMON_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_EMPEROR = INSTANCE.register(BottledCaterpillarItem.EMPEROR_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_EMPEROR, Caterpillar.EMPEROR_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_FORESTER = INSTANCE.register(BottledCaterpillarItem.FORESTER_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_FORESTER, Caterpillar.FORESTER_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_GLASSWING = INSTANCE.register(BottledCaterpillarItem.GLASSWING_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_GLASSWING, Caterpillar.GLASSWING_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_HAIRSTREAK = INSTANCE.register(BottledCaterpillarItem.HAIRSTREAK_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_HAIRSTREAK, Caterpillar.HAIRSTREAK_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_HEATH = INSTANCE.register(BottledCaterpillarItem.HEATH_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_HEATH, Caterpillar.HEATH_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_LONGWING = INSTANCE.register(BottledCaterpillarItem.LONGWING_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_LONGWING, Caterpillar.LONGWING_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_MONARCH = INSTANCE.register(BottledCaterpillarItem.MONARCH_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_MONARCH, Caterpillar.MONARCH_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_MORPHO = INSTANCE.register(BottledCaterpillarItem.MORPHO_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_MORPHO, Caterpillar.MORPHO_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_RAINBOW = INSTANCE.register(BottledCaterpillarItem.RAINBOW_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_RAINBOW, Caterpillar.RAINBOW_NAME));
    public static final RegistryObject<Item> BOTTLED_CATERPILLAR_SWALLOWTAIL = INSTANCE.register(BottledCaterpillarItem.SWALLOWTAIL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_SWALLOWTAIL, Caterpillar.SWALLOWTAIL_NAME));
    
    //  Spawn eggs - Butterflies
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_ADMIRAL = INSTANCE.register(Butterfly.ADMIRAL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_ADMIRAL, 0x880000, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_BUCKEYE = INSTANCE.register(Butterfly.BUCKEYE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_BUCKEYE, 0xcccc88, 0x8888cc, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_CABBAGE = INSTANCE.register(Butterfly.CABBAGE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CABBAGE, 0xeeee77, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_CHALKHILL = INSTANCE.register(Butterfly.CHALKHILL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CHALKHILL, 0x0088ff, 0x00cc55, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_CLIPPER = INSTANCE.register(Butterfly.CLIPPER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_CLIPPER, 0x0044aa, 0x004488, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_COMMON = INSTANCE.register(Butterfly.COMMON_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_COMMON, 0xaaff66, 0xeeee77, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_EMPEROR = INSTANCE.register(Butterfly.EMPEROR_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_EMPEROR, 0xcc44cc, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_FORESTER = INSTANCE.register(Butterfly.FORESTER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_FORESTER, 0xeeee77, 0xff7777, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_GLASSWING = INSTANCE.register(Butterfly.GLASSWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_GLASSWING, 0x880000, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_HAIRSTREAK = INSTANCE.register(Butterfly.HAIRSTREAK_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_HAIRSTREAK, 0xcc44cc, 0x880000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_HEATH = INSTANCE.register(Butterfly.HEATH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_HEATH, 0x880000, 0x000000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_LONGWING = INSTANCE.register(Butterfly.LONGWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_LONGWING, 0x000000, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_MONARCH = INSTANCE.register(Butterfly.MONARCH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_MONARCH, 0xdd8855, 0x000000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_MORPHO = INSTANCE.register(Butterfly.MORPHO_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_MORPHO, 0x0000aa, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_RAINBOW = INSTANCE.register(Butterfly.RAINBOW_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_RAINBOW, 0xff7777, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_BUTTERFLY_SWALLOWTAIL = INSTANCE.register(Butterfly.SWALLOWTAIL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY_SWALLOWTAIL, 0xffffff, 0xeeee77, new Item.Properties()));
    
    //  Spawn eggs - Caterpillars
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_ADMIRAL = INSTANCE.register(Caterpillar.ADMIRAL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_ADMIRAL, 0x880000, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_BUCKEYE = INSTANCE.register(Caterpillar.BUCKEYE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_BUCKEYE, 0xcccc88, 0x8888cc, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_CABBAGE = INSTANCE.register(Caterpillar.CABBAGE_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CABBAGE, 0xeeee77, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_CHALKHILL = INSTANCE.register(Caterpillar.CHALKHILL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CHALKHILL, 0x0088ff, 0x00cc55, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_CLIPPER = INSTANCE.register(Caterpillar.CLIPPER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_CLIPPER, 0x0044aa, 0x004488, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_COMMON = INSTANCE.register(Caterpillar.COMMON_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_COMMON, 0xaaff66, 0xeeee77, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_EMPEROR = INSTANCE.register(Caterpillar.EMPEROR_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_EMPEROR, 0xcc44cc, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_FORESTER = INSTANCE.register(Caterpillar.FORESTER_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_FORESTER, 0xeeee77, 0xff7777, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_GLASSWING = INSTANCE.register(Caterpillar.GLASSWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_GLASSWING, 0x880000, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_HAIRSTREAK = INSTANCE.register(Caterpillar.HAIRSTREAK_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_HAIRSTREAK, 0xcc44cc, 0x880000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_HEATH = INSTANCE.register(Caterpillar.HEATH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_HEATH, 0x880000, 0x000000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_LONGWING = INSTANCE.register(Caterpillar.LONGWING_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_LONGWING, 0x000000, 0xffffff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_MONARCH = INSTANCE.register(Caterpillar.MONARCH_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_MONARCH, 0xdd8855, 0x000000, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_MORPHO = INSTANCE.register(Caterpillar.MORPHO_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_MORPHO, 0x0000aa, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_RAINBOW = INSTANCE.register(Caterpillar.RAINBOW_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_RAINBOW, 0xff7777, 0x0088ff, new Item.Properties()));
    private static final RegistryObject<Item> SPAWN_EGG_CATERPILLAR_SWALLOWTAIL = INSTANCE.register(Caterpillar.SWALLOWTAIL_NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.CATERPILLAR_SWALLOWTAIL, 0xffffff, 0xeeee77, new Item.Properties()));

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerCreativeTabContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(SPAWN_EGG_BUTTERFLY_ADMIRAL);
            event.accept(SPAWN_EGG_BUTTERFLY_BUCKEYE);
            event.accept(SPAWN_EGG_BUTTERFLY_CABBAGE);
            event.accept(SPAWN_EGG_BUTTERFLY_CHALKHILL);
            event.accept(SPAWN_EGG_BUTTERFLY_CLIPPER);
            event.accept(SPAWN_EGG_BUTTERFLY_COMMON);
            event.accept(SPAWN_EGG_BUTTERFLY_EMPEROR);
            event.accept(SPAWN_EGG_BUTTERFLY_FORESTER);
            event.accept(SPAWN_EGG_BUTTERFLY_GLASSWING);
            event.accept(SPAWN_EGG_BUTTERFLY_HAIRSTREAK);
            event.accept(SPAWN_EGG_BUTTERFLY_HEATH);
            event.accept(SPAWN_EGG_BUTTERFLY_LONGWING);
            event.accept(SPAWN_EGG_BUTTERFLY_MONARCH);
            event.accept(SPAWN_EGG_BUTTERFLY_MORPHO);
            event.accept(SPAWN_EGG_BUTTERFLY_RAINBOW);
            event.accept(SPAWN_EGG_BUTTERFLY_SWALLOWTAIL);

            event.accept(SPAWN_EGG_CATERPILLAR_ADMIRAL);
            event.accept(SPAWN_EGG_CATERPILLAR_BUCKEYE);
            event.accept(SPAWN_EGG_CATERPILLAR_CABBAGE);
            event.accept(SPAWN_EGG_CATERPILLAR_CHALKHILL);
            event.accept(SPAWN_EGG_CATERPILLAR_CLIPPER);
            event.accept(SPAWN_EGG_CATERPILLAR_COMMON);
            event.accept(SPAWN_EGG_CATERPILLAR_EMPEROR);
            event.accept(SPAWN_EGG_CATERPILLAR_FORESTER);
            event.accept(SPAWN_EGG_CATERPILLAR_GLASSWING);
            event.accept(SPAWN_EGG_CATERPILLAR_HAIRSTREAK);
            event.accept(SPAWN_EGG_CATERPILLAR_HEATH);
            event.accept(SPAWN_EGG_CATERPILLAR_LONGWING);
            event.accept(SPAWN_EGG_CATERPILLAR_MONARCH);
            event.accept(SPAWN_EGG_CATERPILLAR_MORPHO);
            event.accept(SPAWN_EGG_CATERPILLAR_RAINBOW);
            event.accept(SPAWN_EGG_CATERPILLAR_SWALLOWTAIL);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            event.accept(BUTTERFLY_EGG_ADMIRAL);
            event.accept(BUTTERFLY_EGG_BUCKEYE);
            event.accept(BUTTERFLY_EGG_CABBAGE);
            event.accept(BUTTERFLY_EGG_CHALKHILL);
            event.accept(BUTTERFLY_EGG_CLIPPER);
            event.accept(BUTTERFLY_EGG_COMMON);
            event.accept(BUTTERFLY_EGG_EMPEROR);
            event.accept(BUTTERFLY_EGG_FORESTER);
            event.accept(BUTTERFLY_EGG_GLASSWING);
            event.accept(BUTTERFLY_EGG_HAIRSTREAK);
            event.accept(BUTTERFLY_EGG_HEATH);
            event.accept(BUTTERFLY_EGG_LONGWING);
            event.accept(BUTTERFLY_EGG_MONARCH);
            event.accept(BUTTERFLY_EGG_MORPHO);
            event.accept(BUTTERFLY_EGG_RAINBOW);
            event.accept(BUTTERFLY_EGG_SWALLOWTAIL);

            event.accept(CATERPILLAR_ADMIRAL);
            event.accept(CATERPILLAR_BUCKEYE);
            event.accept(CATERPILLAR_CABBAGE);
            event.accept(CATERPILLAR_CHALKHILL);
            event.accept(CATERPILLAR_CLIPPER);
            event.accept(CATERPILLAR_COMMON);
            event.accept(CATERPILLAR_EMPEROR);
            event.accept(CATERPILLAR_FORESTER);
            event.accept(CATERPILLAR_GLASSWING);
            event.accept(CATERPILLAR_HAIRSTREAK);
            event.accept(CATERPILLAR_HEATH);
            event.accept(CATERPILLAR_LONGWING);
            event.accept(CATERPILLAR_MONARCH);
            event.accept(CATERPILLAR_MORPHO);
            event.accept(CATERPILLAR_RAINBOW);
            event.accept(CATERPILLAR_SWALLOWTAIL);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BUTTERFLY_NET);
            event.accept(BOTTLED_BUTTERFLY);

            event.accept(BOTTLED_CATERPILLAR_ADMIRAL);
            event.accept(BOTTLED_CATERPILLAR_BUCKEYE);
            event.accept(BOTTLED_CATERPILLAR_CABBAGE);
            event.accept(BOTTLED_CATERPILLAR_CHALKHILL);
            event.accept(BOTTLED_CATERPILLAR_CLIPPER);
            event.accept(BOTTLED_CATERPILLAR_COMMON);
            event.accept(BOTTLED_CATERPILLAR_EMPEROR);
            event.accept(BOTTLED_CATERPILLAR_FORESTER);
            event.accept(BOTTLED_CATERPILLAR_GLASSWING);
            event.accept(BOTTLED_CATERPILLAR_HAIRSTREAK);
            event.accept(BOTTLED_CATERPILLAR_HEATH);
            event.accept(BOTTLED_CATERPILLAR_LONGWING);
            event.accept(BOTTLED_CATERPILLAR_MONARCH);
            event.accept(BOTTLED_CATERPILLAR_MORPHO);
            event.accept(BOTTLED_CATERPILLAR_RAINBOW);
            event.accept(BOTTLED_CATERPILLAR_SWALLOWTAIL);
            
            event.accept(BUTTERFLY_SCROLL);
            event.accept(BUTTERFLY_BOOK);
            event.accept(BUTTERFLY_ZHUANGZI);
        }
    }
}
