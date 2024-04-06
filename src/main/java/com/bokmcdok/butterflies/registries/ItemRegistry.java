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
            () -> new ButterflyNetItem(-1));
    public static final RegistryObject<Item> BUTTERFLY_NET_ADMIRAL = INSTANCE.register(ButterflyNetItem.ADMIRAL_NAME,
            () -> new ButterflyNetItem(0));
    public static final RegistryObject<Item> BUTTERFLY_NET_BUCKEYE = INSTANCE.register(ButterflyNetItem.BUCKEYE_NAME,
            () -> new ButterflyNetItem(1));
    public static final RegistryObject<Item> BUTTERFLY_NET_CABBAGE = INSTANCE.register(ButterflyNetItem.CABBAGE_NAME,
            () -> new ButterflyNetItem(2));
    public static final RegistryObject<Item> BUTTERFLY_NET_CHALKHILL = INSTANCE.register(ButterflyNetItem.CHALKHILL_NAME,
            () -> new ButterflyNetItem(3));
    public static final RegistryObject<Item> BUTTERFLY_NET_CLIPPER = INSTANCE.register(ButterflyNetItem.CLIPPER_NAME,
            () -> new ButterflyNetItem(4));
    public static final RegistryObject<Item> BUTTERFLY_NET_COMMON = INSTANCE.register(ButterflyNetItem.COMMON_NAME,
            () -> new ButterflyNetItem(5));
    public static final RegistryObject<Item> BUTTERFLY_NET_EMPEROR = INSTANCE.register(ButterflyNetItem.EMPEROR_NAME,
            () -> new ButterflyNetItem(6));
    public static final RegistryObject<Item> BUTTERFLY_NET_FORESTER = INSTANCE.register(ButterflyNetItem.FORESTER_NAME,
            () -> new ButterflyNetItem(7));
    public static final RegistryObject<Item> BUTTERFLY_NET_GLASSWING = INSTANCE.register(ButterflyNetItem.GLASSWING_NAME,
            () -> new ButterflyNetItem(8));
    public static final RegistryObject<Item> BUTTERFLY_NET_HAIRSTREAK = INSTANCE.register(ButterflyNetItem.HAIRSTREAK_NAME,
            () -> new ButterflyNetItem(9));
    public static final RegistryObject<Item> BUTTERFLY_NET_HEATH = INSTANCE.register(ButterflyNetItem.HEATH_NAME,
            () -> new ButterflyNetItem(10));
    public static final RegistryObject<Item> BUTTERFLY_NET_LONGWING = INSTANCE.register(ButterflyNetItem.LONGWING_NAME,
            () -> new ButterflyNetItem(11));
    public static final RegistryObject<Item> BUTTERFLY_NET_MONARCH = INSTANCE.register(ButterflyNetItem.MONARCH_NAME,
            () -> new ButterflyNetItem(12));
    public static final RegistryObject<Item> BUTTERFLY_NET_MORPHO = INSTANCE.register(ButterflyNetItem.MORPHO_NAME,
            () -> new ButterflyNetItem(13));
    public static final RegistryObject<Item> BUTTERFLY_NET_RAINBOW = INSTANCE.register(ButterflyNetItem.RAINBOW_NAME,
            () -> new ButterflyNetItem(14));
    public static final RegistryObject<Item> BUTTERFLY_NET_SWALLOWTAIL = INSTANCE.register(ButterflyNetItem.SWALLOWTAIL_NAME,
            () -> new ButterflyNetItem(15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final RegistryObject<Item> BUTTERFLY_NET_FULL = INSTANCE.register(ButterflyNetItem.FULL_NAME,
            () -> new ButterflyNetItem(-1));

    // Bottled butterfly - A butterfly trapped in a bottle.
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_ADMIRAL = INSTANCE.register(BottledButterflyItem.ADMIRAL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_ADMIRAL, 0));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_BUCKEYE = INSTANCE.register(BottledButterflyItem.BUCKEYE_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BUCKEYE, 1));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_CABBAGE = INSTANCE.register(BottledButterflyItem.CABBAGE_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CABBAGE, 2));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_CHALKHILL = INSTANCE.register(BottledButterflyItem.CHALKHILL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CHALKHILL, 3));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_CLIPPER = INSTANCE.register(BottledButterflyItem.CLIPPER_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CLIPPER, 4));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_COMMON = INSTANCE.register(BottledButterflyItem.COMMON_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_COMMON, 5));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_EMPEROR = INSTANCE.register(BottledButterflyItem.EMPEROR_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_EMPEROR, 6));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_FORESTER = INSTANCE.register(BottledButterflyItem.FORESTER_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_FORESTER, 7));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_GLASSWING = INSTANCE.register(BottledButterflyItem.GLASSWING_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_GLASSWING, 8));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_HAIRSTREAK = INSTANCE.register(BottledButterflyItem.HAIRSTREAK_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_HAIRSTREAK, 9));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_HEATH = INSTANCE.register(BottledButterflyItem.HEATH_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_HEATH, 10));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_LONGWING = INSTANCE.register(BottledButterflyItem.LONGWING_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_LONGWING, 11));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_MONARCH = INSTANCE.register(BottledButterflyItem.MONARCH_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_MONARCH, 12));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_MORPHO = INSTANCE.register(BottledButterflyItem.MORPHO_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_MORPHO, 13));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_RAINBOW = INSTANCE.register(BottledButterflyItem.RAINBOW_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_RAINBOW, 14));
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY_SWALLOWTAIL = INSTANCE.register(BottledButterflyItem.SWALLOWTAIL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_SWALLOWTAIL, 15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final RegistryObject<Item> BOTTLED_BUTTERFLY = INSTANCE.register(BottledButterflyItem.NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BLOCK, -1));

    // Butterfly Scroll
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_ADMIRAL = INSTANCE.register(ButterflyScrollItem.ADMIRAL_NAME,
            () -> new ButterflyScrollItem(0));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_BUCKEYE = INSTANCE.register(ButterflyScrollItem.BUCKEYE_NAME,
            () -> new ButterflyScrollItem(1));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_CABBAGE = INSTANCE.register(ButterflyScrollItem.CABBAGE_NAME,
            () -> new ButterflyScrollItem(2));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_CHALKHILL = INSTANCE.register(ButterflyScrollItem.CHALKHILL_NAME,
            () -> new ButterflyScrollItem(3));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_CLIPPER = INSTANCE.register(ButterflyScrollItem.CLIPPER_NAME,
            () -> new ButterflyScrollItem(4));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_COMMON = INSTANCE.register(ButterflyScrollItem.COMMON_NAME,
            () -> new ButterflyScrollItem(5));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_EMPEROR = INSTANCE.register(ButterflyScrollItem.EMPEROR_NAME,
            () -> new ButterflyScrollItem(6));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_FORESTER = INSTANCE.register(ButterflyScrollItem.FORESTER_NAME,
            () -> new ButterflyScrollItem(7));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_GLASSWING = INSTANCE.register(ButterflyScrollItem.GLASSWING_NAME,
            () -> new ButterflyScrollItem(8));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_HAIRSTREAK = INSTANCE.register(ButterflyScrollItem.HAIRSTREAK_NAME,
            () -> new ButterflyScrollItem(9));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_HEATH = INSTANCE.register(ButterflyScrollItem.HEATH_NAME,
            () -> new ButterflyScrollItem(10));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_LONGWING = INSTANCE.register(ButterflyScrollItem.LONGWING_NAME,
            () -> new ButterflyScrollItem(11));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_MONARCH = INSTANCE.register(ButterflyScrollItem.MONARCH_NAME,
            () -> new ButterflyScrollItem(12));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_MORPHO = INSTANCE.register(ButterflyScrollItem.MORPHO_NAME,
            () -> new ButterflyScrollItem(13));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_RAINBOW = INSTANCE.register(ButterflyScrollItem.RAINBOW_NAME,
            () -> new ButterflyScrollItem(14));
    public static final RegistryObject<Item> BUTTERFLY_SCROLL_SWALLOWTAIL = INSTANCE.register(ButterflyScrollItem.SWALLOWTAIL_NAME,
            () -> new ButterflyScrollItem(15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final RegistryObject<Item> BUTTERFLY_SCROLL = INSTANCE.register(ButterflyScrollItem.NAME,
            () -> new ButterflyScrollItem(-1));

    // Butterfly Book
    public static final RegistryObject<Item> BUTTERFLY_BOOK =
            INSTANCE.register(ButterflyBookItem.NAME, ButterflyBookItem::new);

    // Zhuangzi
    public static final RegistryObject<Item> BUTTERFLY_ZHUANGZI =
            INSTANCE.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

    // Butterfly Eggs - Eggs that will eventually hatch into a caterpillar.
    public static final RegistryObject<Item> BUTTERFLY_EGG_ADMIRAL = INSTANCE.register(ButterflyEggItem.ADMIRAL_NAME,
            () -> new ButterflyEggItem(0, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_BUCKEYE = INSTANCE.register(ButterflyEggItem.BUCKEYE_NAME,
            () -> new ButterflyEggItem(1, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CABBAGE = INSTANCE.register(ButterflyEggItem.CABBAGE_NAME,
            () -> new ButterflyEggItem(2, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CHALKHILL = INSTANCE.register(ButterflyEggItem.CHALKHILL_NAME,
            () -> new ButterflyEggItem(3, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_CLIPPER = INSTANCE.register(ButterflyEggItem.CLIPPER_NAME,
            () -> new ButterflyEggItem(4, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_COMMON = INSTANCE.register(ButterflyEggItem.COMMON_NAME,
            () -> new ButterflyEggItem(5, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_EMPEROR = INSTANCE.register(ButterflyEggItem.EMPEROR_NAME,
            () -> new ButterflyEggItem(6, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_FORESTER = INSTANCE.register(ButterflyEggItem.FORESTER_NAME,
            () -> new ButterflyEggItem(7, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_GLASSWING = INSTANCE.register(ButterflyEggItem.GLASSWING_NAME,
            () -> new ButterflyEggItem(8, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_HAIRSTREAK = INSTANCE.register(ButterflyEggItem.HAIRSTREAK_NAME,
            () -> new ButterflyEggItem(9, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_HEATH = INSTANCE.register(ButterflyEggItem.HEATH_NAME,
            () -> new ButterflyEggItem(10, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_LONGWING = INSTANCE.register(ButterflyEggItem.LONGWING_NAME,
            () -> new ButterflyEggItem(11, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_MONARCH = INSTANCE.register(ButterflyEggItem.MONARCH_NAME,
            () -> new ButterflyEggItem(12, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_MORPHO = INSTANCE.register(ButterflyEggItem.MORPHO_NAME,
            () -> new ButterflyEggItem(13, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_RAINBOW = INSTANCE.register(ButterflyEggItem.RAINBOW_NAME,
            () -> new ButterflyEggItem(14, new Item.Properties()));
    public static final RegistryObject<Item> BUTTERFLY_EGG_SWALLOWTAIL = INSTANCE.register(ButterflyEggItem.SWALLOWTAIL_NAME,
            () -> new ButterflyEggItem(15, new Item.Properties()));

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
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static RegistryObject<Item> getButterflyNetFromIndex(int butterflyIndex) {
        return switch (butterflyIndex) {
            case -1 -> BUTTERFLY_NET;
            case 0 -> BUTTERFLY_NET_ADMIRAL;
            case 1 -> BUTTERFLY_NET_BUCKEYE;
            case 2 -> BUTTERFLY_NET_CABBAGE;
            case 3 -> BUTTERFLY_NET_CHALKHILL;
            case 4 -> BUTTERFLY_NET_CLIPPER;
            case 5 -> BUTTERFLY_NET_COMMON;
            case 6 -> BUTTERFLY_NET_EMPEROR;
            case 7 -> BUTTERFLY_NET_FORESTER;
            case 8 -> BUTTERFLY_NET_GLASSWING;
            case 9 -> BUTTERFLY_NET_HAIRSTREAK;
            case 10 -> BUTTERFLY_NET_HEATH;
            case 11 -> BUTTERFLY_NET_LONGWING;
            case 12 -> BUTTERFLY_NET_MONARCH;
            case 13 -> BUTTERFLY_NET_MORPHO;
            case 14 -> BUTTERFLY_NET_RAINBOW;
            case 15 -> BUTTERFLY_NET_SWALLOWTAIL;
            default -> null;
        };
    }

    /**
     * Helper method to get the correct bottled butterfly item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static RegistryObject<Item> getBottledButterflyFromIndex(int butterflyIndex) {
        return switch (butterflyIndex) {
            case 0 -> BOTTLED_BUTTERFLY_ADMIRAL;
            case 1 -> BOTTLED_BUTTERFLY_BUCKEYE;
            case 2 -> BOTTLED_BUTTERFLY_CABBAGE;
            case 3 -> BOTTLED_BUTTERFLY_CHALKHILL;
            case 4 -> BOTTLED_BUTTERFLY_CLIPPER;
            case 5 -> BOTTLED_BUTTERFLY_COMMON;
            case 6 -> BOTTLED_BUTTERFLY_EMPEROR;
            case 7 -> BOTTLED_BUTTERFLY_FORESTER;
            case 8 -> BOTTLED_BUTTERFLY_GLASSWING;
            case 9 -> BOTTLED_BUTTERFLY_HAIRSTREAK;
            case 10 -> BOTTLED_BUTTERFLY_HEATH;
            case 11 -> BOTTLED_BUTTERFLY_LONGWING;
            case 12 -> BOTTLED_BUTTERFLY_MONARCH;
            case 13 -> BOTTLED_BUTTERFLY_MORPHO;
            case 14 -> BOTTLED_BUTTERFLY_RAINBOW;
            case 15 -> BOTTLED_BUTTERFLY_SWALLOWTAIL;
            default -> null;
        };
    }

    /**
     * Helper method to get the correct butterfly scroll item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static RegistryObject<Item> getButterflyScrollFromIndex(int butterflyIndex) {
        return switch (butterflyIndex) {
            case 0 -> BUTTERFLY_SCROLL_ADMIRAL;
            case 1 -> BUTTERFLY_SCROLL_BUCKEYE;
            case 2 -> BUTTERFLY_SCROLL_CABBAGE;
            case 3 -> BUTTERFLY_SCROLL_CHALKHILL;
            case 4 -> BUTTERFLY_SCROLL_CLIPPER;
            case 5 -> BUTTERFLY_SCROLL_COMMON;
            case 6 -> BUTTERFLY_SCROLL_EMPEROR;
            case 7 -> BUTTERFLY_SCROLL_FORESTER;
            case 8 -> BUTTERFLY_SCROLL_GLASSWING;
            case 9 -> BUTTERFLY_SCROLL_HAIRSTREAK;
            case 10 -> BUTTERFLY_SCROLL_HEATH;
            case 11 -> BUTTERFLY_SCROLL_LONGWING;
            case 12 -> BUTTERFLY_SCROLL_MONARCH;
            case 13 -> BUTTERFLY_SCROLL_MORPHO;
            case 14 -> BUTTERFLY_SCROLL_RAINBOW;
            case 15 -> BUTTERFLY_SCROLL_SWALLOWTAIL;
            default -> null;
        };
    }

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
            event.accept(BUTTERFLY_NET_ADMIRAL);
            event.accept(BUTTERFLY_NET_BUCKEYE);
            event.accept(BUTTERFLY_NET_CABBAGE);
            event.accept(BUTTERFLY_NET_CHALKHILL);
            event.accept(BUTTERFLY_NET_CLIPPER);
            event.accept(BUTTERFLY_NET_COMMON);
            event.accept(BUTTERFLY_NET_EMPEROR);
            event.accept(BUTTERFLY_NET_FORESTER);
            event.accept(BUTTERFLY_NET_GLASSWING);
            event.accept(BUTTERFLY_NET_HAIRSTREAK);
            event.accept(BUTTERFLY_NET_HEATH);
            event.accept(BUTTERFLY_NET_LONGWING);
            event.accept(BUTTERFLY_NET_MONARCH);
            event.accept(BUTTERFLY_NET_MORPHO);
            event.accept(BUTTERFLY_NET_RAINBOW);
            event.accept(BUTTERFLY_NET_SWALLOWTAIL);

            //event.accept(BOTTLED_BUTTERFLY);
            event.accept(BOTTLED_BUTTERFLY_ADMIRAL);
            event.accept(BOTTLED_BUTTERFLY_BUCKEYE);
            event.accept(BOTTLED_BUTTERFLY_CABBAGE);
            event.accept(BOTTLED_BUTTERFLY_CHALKHILL);
            event.accept(BOTTLED_BUTTERFLY_CLIPPER);
            event.accept(BOTTLED_BUTTERFLY_COMMON);
            event.accept(BOTTLED_BUTTERFLY_EMPEROR);
            event.accept(BOTTLED_BUTTERFLY_FORESTER);
            event.accept(BOTTLED_BUTTERFLY_GLASSWING);
            event.accept(BOTTLED_BUTTERFLY_HAIRSTREAK);
            event.accept(BOTTLED_BUTTERFLY_HEATH);
            event.accept(BOTTLED_BUTTERFLY_LONGWING);
            event.accept(BOTTLED_BUTTERFLY_MONARCH);
            event.accept(BOTTLED_BUTTERFLY_MORPHO);
            event.accept(BOTTLED_BUTTERFLY_RAINBOW);
            event.accept(BOTTLED_BUTTERFLY_SWALLOWTAIL);

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

            //event.accept(BUTTERFLY_SCROLL);
            event.accept(BUTTERFLY_SCROLL_ADMIRAL);
            event.accept(BUTTERFLY_SCROLL_BUCKEYE);
            event.accept(BUTTERFLY_SCROLL_CABBAGE);
            event.accept(BUTTERFLY_SCROLL_CHALKHILL);
            event.accept(BUTTERFLY_SCROLL_CLIPPER);
            event.accept(BUTTERFLY_SCROLL_COMMON);
            event.accept(BUTTERFLY_SCROLL_EMPEROR);
            event.accept(BUTTERFLY_SCROLL_FORESTER);
            event.accept(BUTTERFLY_SCROLL_GLASSWING);
            event.accept(BUTTERFLY_SCROLL_HAIRSTREAK);
            event.accept(BUTTERFLY_SCROLL_HEATH);
            event.accept(BUTTERFLY_SCROLL_LONGWING);
            event.accept(BUTTERFLY_SCROLL_MONARCH);
            event.accept(BUTTERFLY_SCROLL_MORPHO);
            event.accept(BUTTERFLY_SCROLL_RAINBOW);
            event.accept(BUTTERFLY_SCROLL_SWALLOWTAIL);

            event.accept(BUTTERFLY_BOOK);
            event.accept(BUTTERFLY_ZHUANGZI);
        }
    }
}
