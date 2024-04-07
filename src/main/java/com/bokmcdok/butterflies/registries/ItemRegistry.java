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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * This class registers items with Forge's Item Registry
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Item> INSTANCE = DeferredRegister.create(BuiltInRegistries.ITEM, ButterfliesMod.MOD_ID);

    //  Butterfly net - Used to catch butterflies
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET = INSTANCE.register(ButterflyNetItem.EMPTY_NAME,
            () -> new ButterflyNetItem(-1));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_ADMIRAL = INSTANCE.register(ButterflyNetItem.ADMIRAL_NAME,
            () -> new ButterflyNetItem(0));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_BUCKEYE = INSTANCE.register(ButterflyNetItem.BUCKEYE_NAME,
            () -> new ButterflyNetItem(1));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_CABBAGE = INSTANCE.register(ButterflyNetItem.CABBAGE_NAME,
            () -> new ButterflyNetItem(2));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_CHALKHILL = INSTANCE.register(ButterflyNetItem.CHALKHILL_NAME,
            () -> new ButterflyNetItem(3));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_CLIPPER = INSTANCE.register(ButterflyNetItem.CLIPPER_NAME,
            () -> new ButterflyNetItem(4));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_COMMON = INSTANCE.register(ButterflyNetItem.COMMON_NAME,
            () -> new ButterflyNetItem(5));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_EMPEROR = INSTANCE.register(ButterflyNetItem.EMPEROR_NAME,
            () -> new ButterflyNetItem(6));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_FORESTER = INSTANCE.register(ButterflyNetItem.FORESTER_NAME,
            () -> new ButterflyNetItem(7));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_GLASSWING = INSTANCE.register(ButterflyNetItem.GLASSWING_NAME,
            () -> new ButterflyNetItem(8));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_HAIRSTREAK = INSTANCE.register(ButterflyNetItem.HAIRSTREAK_NAME,
            () -> new ButterflyNetItem(9));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_HEATH = INSTANCE.register(ButterflyNetItem.HEATH_NAME,
            () -> new ButterflyNetItem(10));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_LONGWING = INSTANCE.register(ButterflyNetItem.LONGWING_NAME,
            () -> new ButterflyNetItem(11));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_MONARCH = INSTANCE.register(ButterflyNetItem.MONARCH_NAME,
            () -> new ButterflyNetItem(12));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_MORPHO = INSTANCE.register(ButterflyNetItem.MORPHO_NAME,
            () -> new ButterflyNetItem(13));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_RAINBOW = INSTANCE.register(ButterflyNetItem.RAINBOW_NAME,
            () -> new ButterflyNetItem(14));
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_SWALLOWTAIL = INSTANCE.register(ButterflyNetItem.SWALLOWTAIL_NAME,
            () -> new ButterflyNetItem(15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final DeferredHolder<Item, Item> BUTTERFLY_NET_FULL = INSTANCE.register(ButterflyNetItem.FULL_NAME,
            () -> new ButterflyNetItem(-1));

    // Bottled butterfly - A butterfly trapped in a bottle.
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_ADMIRAL = INSTANCE.register(BottledButterflyItem.ADMIRAL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_ADMIRAL, 0));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_BUCKEYE = INSTANCE.register(BottledButterflyItem.BUCKEYE_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BUCKEYE, 1));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_CABBAGE = INSTANCE.register(BottledButterflyItem.CABBAGE_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CABBAGE, 2));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_CHALKHILL = INSTANCE.register(BottledButterflyItem.CHALKHILL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CHALKHILL, 3));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_CLIPPER = INSTANCE.register(BottledButterflyItem.CLIPPER_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_CLIPPER, 4));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_COMMON = INSTANCE.register(BottledButterflyItem.COMMON_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_COMMON, 5));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_EMPEROR = INSTANCE.register(BottledButterflyItem.EMPEROR_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_EMPEROR, 6));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_FORESTER = INSTANCE.register(BottledButterflyItem.FORESTER_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_FORESTER, 7));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_GLASSWING = INSTANCE.register(BottledButterflyItem.GLASSWING_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_GLASSWING, 8));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_HAIRSTREAK = INSTANCE.register(BottledButterflyItem.HAIRSTREAK_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_HAIRSTREAK, 9));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_HEATH = INSTANCE.register(BottledButterflyItem.HEATH_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_HEATH, 10));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_LONGWING = INSTANCE.register(BottledButterflyItem.LONGWING_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_LONGWING, 11));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_MONARCH = INSTANCE.register(BottledButterflyItem.MONARCH_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_MONARCH, 12));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_MORPHO = INSTANCE.register(BottledButterflyItem.MORPHO_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_MORPHO, 13));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_RAINBOW = INSTANCE.register(BottledButterflyItem.RAINBOW_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_RAINBOW, 14));
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY_SWALLOWTAIL = INSTANCE.register(BottledButterflyItem.SWALLOWTAIL_NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_SWALLOWTAIL, 15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final DeferredHolder<Item, Item> BOTTLED_BUTTERFLY = INSTANCE.register(BottledButterflyItem.NAME,
            () -> new BottledButterflyItem(BlockRegistry.BOTTLED_BUTTERFLY_BLOCK, -1));

    // Butterfly Scroll
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_ADMIRAL = INSTANCE.register(ButterflyScrollItem.ADMIRAL_NAME,
            () -> new ButterflyScrollItem(0));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_BUCKEYE = INSTANCE.register(ButterflyScrollItem.BUCKEYE_NAME,
            () -> new ButterflyScrollItem(1));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_CABBAGE = INSTANCE.register(ButterflyScrollItem.CABBAGE_NAME,
            () -> new ButterflyScrollItem(2));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_CHALKHILL = INSTANCE.register(ButterflyScrollItem.CHALKHILL_NAME,
            () -> new ButterflyScrollItem(3));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_CLIPPER = INSTANCE.register(ButterflyScrollItem.CLIPPER_NAME,
            () -> new ButterflyScrollItem(4));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_COMMON = INSTANCE.register(ButterflyScrollItem.COMMON_NAME,
            () -> new ButterflyScrollItem(5));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_EMPEROR = INSTANCE.register(ButterflyScrollItem.EMPEROR_NAME,
            () -> new ButterflyScrollItem(6));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_FORESTER = INSTANCE.register(ButterflyScrollItem.FORESTER_NAME,
            () -> new ButterflyScrollItem(7));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_GLASSWING = INSTANCE.register(ButterflyScrollItem.GLASSWING_NAME,
            () -> new ButterflyScrollItem(8));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_HAIRSTREAK = INSTANCE.register(ButterflyScrollItem.HAIRSTREAK_NAME,
            () -> new ButterflyScrollItem(9));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_HEATH = INSTANCE.register(ButterflyScrollItem.HEATH_NAME,
            () -> new ButterflyScrollItem(10));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_LONGWING = INSTANCE.register(ButterflyScrollItem.LONGWING_NAME,
            () -> new ButterflyScrollItem(11));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_MONARCH = INSTANCE.register(ButterflyScrollItem.MONARCH_NAME,
            () -> new ButterflyScrollItem(12));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_MORPHO = INSTANCE.register(ButterflyScrollItem.MORPHO_NAME,
            () -> new ButterflyScrollItem(13));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_RAINBOW = INSTANCE.register(ButterflyScrollItem.RAINBOW_NAME,
            () -> new ButterflyScrollItem(14));
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL_SWALLOWTAIL = INSTANCE.register(ButterflyScrollItem.SWALLOWTAIL_NAME,
            () -> new ButterflyScrollItem(15));

    // TODO: This is the old implementation, included for backwards
    //       compatibility. This needs to be removed in a future version.
    public static final DeferredHolder<Item, Item> BUTTERFLY_SCROLL = INSTANCE.register(ButterflyScrollItem.NAME,
            () -> new ButterflyScrollItem(-1));

    // Butterfly Book
    public static final DeferredHolder<Item, Item> BUTTERFLY_BOOK =
            INSTANCE.register(ButterflyBookItem.NAME, ButterflyBookItem::new);

    // Zhuangzi
    public static final DeferredHolder<Item, Item> BUTTERFLY_ZHUANGZI =
            INSTANCE.register(ButterflyZhuangziItem.NAME, ButterflyZhuangziItem::new);

    // Butterfly Eggs - Eggs that will eventually hatch into a caterpillar.
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_ADMIRAL = INSTANCE.register(ButterflyEggItem.ADMIRAL_NAME,
            () -> new ButterflyEggItem(0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_BUCKEYE = INSTANCE.register(ButterflyEggItem.BUCKEYE_NAME,
            () -> new ButterflyEggItem(1, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_CABBAGE = INSTANCE.register(ButterflyEggItem.CABBAGE_NAME,
            () -> new ButterflyEggItem(2, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_CHALKHILL = INSTANCE.register(ButterflyEggItem.CHALKHILL_NAME,
            () -> new ButterflyEggItem(3, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_CLIPPER = INSTANCE.register(ButterflyEggItem.CLIPPER_NAME,
            () -> new ButterflyEggItem(4, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_COMMON = INSTANCE.register(ButterflyEggItem.COMMON_NAME,
            () -> new ButterflyEggItem(5, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_EMPEROR = INSTANCE.register(ButterflyEggItem.EMPEROR_NAME,
            () -> new ButterflyEggItem(6, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_FORESTER = INSTANCE.register(ButterflyEggItem.FORESTER_NAME,
            () -> new ButterflyEggItem(7, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_GLASSWING = INSTANCE.register(ButterflyEggItem.GLASSWING_NAME,
            () -> new ButterflyEggItem(8, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_HAIRSTREAK = INSTANCE.register(ButterflyEggItem.HAIRSTREAK_NAME,
            () -> new ButterflyEggItem(9, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_HEATH = INSTANCE.register(ButterflyEggItem.HEATH_NAME,
            () -> new ButterflyEggItem(10, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_LONGWING = INSTANCE.register(ButterflyEggItem.LONGWING_NAME,
            () -> new ButterflyEggItem(11, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_MONARCH = INSTANCE.register(ButterflyEggItem.MONARCH_NAME,
            () -> new ButterflyEggItem(12, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_MORPHO = INSTANCE.register(ButterflyEggItem.MORPHO_NAME,
            () -> new ButterflyEggItem(13, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_RAINBOW = INSTANCE.register(ButterflyEggItem.RAINBOW_NAME,
            () -> new ButterflyEggItem(14, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BUTTERFLY_EGG_SWALLOWTAIL = INSTANCE.register(ButterflyEggItem.SWALLOWTAIL_NAME,
            () -> new ButterflyEggItem(15, new Item.Properties()));

    //  Caterpillars
    public static final DeferredHolder<Item, Item> CATERPILLAR_ADMIRAL = INSTANCE.register(CaterpillarItem.ADMIRAL_NAME,
            () -> new CaterpillarItem(Caterpillar.ADMIRAL_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_BUCKEYE = INSTANCE.register(CaterpillarItem.BUCKEYE_NAME,
            () -> new CaterpillarItem(Caterpillar.BUCKEYE_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_CABBAGE = INSTANCE.register(CaterpillarItem.CABBAGE_NAME,
            () -> new CaterpillarItem(Caterpillar.CABBAGE_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_CHALKHILL = INSTANCE.register(CaterpillarItem.CHALKHILL_NAME,
            () -> new CaterpillarItem(Caterpillar.CHALKHILL_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_CLIPPER = INSTANCE.register(CaterpillarItem.CLIPPER_NAME,
            () -> new CaterpillarItem(Caterpillar.CLIPPER_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_COMMON = INSTANCE.register(CaterpillarItem.COMMON_NAME,
            () -> new CaterpillarItem(Caterpillar.COMMON_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_EMPEROR = INSTANCE.register(CaterpillarItem.EMPEROR_NAME,
            () -> new CaterpillarItem(Caterpillar.EMPEROR_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_FORESTER = INSTANCE.register(CaterpillarItem.FORESTER_NAME,
            () -> new CaterpillarItem(Caterpillar.FORESTER_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_GLASSWING = INSTANCE.register(CaterpillarItem.GLASSWING_NAME,
            () -> new CaterpillarItem(Caterpillar.GLASSWING_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_HAIRSTREAK = INSTANCE.register(CaterpillarItem.HAIRSTREAK_NAME,
            () -> new CaterpillarItem(Caterpillar.HAIRSTREAK_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_HEATH = INSTANCE.register(CaterpillarItem.HEATH_NAME,
            () -> new CaterpillarItem(Caterpillar.HEATH_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_LONGWING = INSTANCE.register(CaterpillarItem.LONGWING_NAME,
            () -> new CaterpillarItem(Caterpillar.LONGWING_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_MONARCH = INSTANCE.register(CaterpillarItem.MONARCH_NAME,
            () -> new CaterpillarItem(Caterpillar.MONARCH_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_MORPHO = INSTANCE.register(CaterpillarItem.MORPHO_NAME,
            () -> new CaterpillarItem(Caterpillar.MORPHO_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_RAINBOW = INSTANCE.register(CaterpillarItem.RAINBOW_NAME,
            () -> new CaterpillarItem(Caterpillar.RAINBOW_NAME));
    public static final DeferredHolder<Item, Item> CATERPILLAR_SWALLOWTAIL = INSTANCE.register(CaterpillarItem.SWALLOWTAIL_NAME,
            () -> new CaterpillarItem(Caterpillar.SWALLOWTAIL_NAME));

    // Bottled Caterpillars
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_ADMIRAL = INSTANCE.register(BottledCaterpillarItem.ADMIRAL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_ADMIRAL, Caterpillar.ADMIRAL_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_BUCKEYE = INSTANCE.register(BottledCaterpillarItem.BUCKEYE_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_BUCKEYE, Caterpillar.BUCKEYE_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_CABBAGE = INSTANCE.register(BottledCaterpillarItem.CABBAGE_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CABBAGE, Caterpillar.CABBAGE_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_CHALKHILL = INSTANCE.register(BottledCaterpillarItem.CHALKHILL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CHALKHILL, Caterpillar.CHALKHILL_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_CLIPPER = INSTANCE.register(BottledCaterpillarItem.CLIPPER_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_CLIPPER, Caterpillar.CLIPPER_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_COMMON = INSTANCE.register(BottledCaterpillarItem.COMMON_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_COMMON, Caterpillar.COMMON_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_EMPEROR = INSTANCE.register(BottledCaterpillarItem.EMPEROR_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_EMPEROR, Caterpillar.EMPEROR_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_FORESTER = INSTANCE.register(BottledCaterpillarItem.FORESTER_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_FORESTER, Caterpillar.FORESTER_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_GLASSWING = INSTANCE.register(BottledCaterpillarItem.GLASSWING_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_GLASSWING, Caterpillar.GLASSWING_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_HAIRSTREAK = INSTANCE.register(BottledCaterpillarItem.HAIRSTREAK_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_HAIRSTREAK, Caterpillar.HAIRSTREAK_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_HEATH = INSTANCE.register(BottledCaterpillarItem.HEATH_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_HEATH, Caterpillar.HEATH_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_LONGWING = INSTANCE.register(BottledCaterpillarItem.LONGWING_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_LONGWING, Caterpillar.LONGWING_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_MONARCH = INSTANCE.register(BottledCaterpillarItem.MONARCH_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_MONARCH, Caterpillar.MONARCH_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_MORPHO = INSTANCE.register(BottledCaterpillarItem.MORPHO_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_MORPHO, Caterpillar.MORPHO_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_RAINBOW = INSTANCE.register(BottledCaterpillarItem.RAINBOW_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_RAINBOW, Caterpillar.RAINBOW_NAME));
    public static final DeferredHolder<Item, Item> BOTTLED_CATERPILLAR_SWALLOWTAIL = INSTANCE.register(BottledCaterpillarItem.SWALLOWTAIL_NAME,
            () -> new BottledCaterpillarItem(BlockRegistry.BOTTLED_CATERPILLAR_SWALLOWTAIL, Caterpillar.SWALLOWTAIL_NAME));
    
    //  Spawn eggs - Butterflies
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_ADMIRAL = INSTANCE.register(Butterfly.ADMIRAL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_ADMIRAL.get(), 0x880000, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_BUCKEYE = INSTANCE.register(Butterfly.BUCKEYE_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_BUCKEYE.get(), 0xcccc88, 0x8888cc, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_CABBAGE = INSTANCE.register(Butterfly.CABBAGE_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_CABBAGE.get(), 0xeeee77, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_CHALKHILL = INSTANCE.register(Butterfly.CHALKHILL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_CHALKHILL.get(), 0x0088ff, 0x00cc55, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_CLIPPER = INSTANCE.register(Butterfly.CLIPPER_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_CLIPPER.get(), 0x0044aa, 0x004488, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_COMMON = INSTANCE.register(Butterfly.COMMON_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_COMMON.get(), 0xaaff66, 0xeeee77, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_EMPEROR = INSTANCE.register(Butterfly.EMPEROR_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_EMPEROR.get(), 0xcc44cc, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_FORESTER = INSTANCE.register(Butterfly.FORESTER_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_FORESTER.get(), 0xeeee77, 0xff7777, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_GLASSWING = INSTANCE.register(Butterfly.GLASSWING_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_GLASSWING.get(), 0x880000, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_HAIRSTREAK = INSTANCE.register(Butterfly.HAIRSTREAK_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_HAIRSTREAK.get(), 0xcc44cc, 0x880000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_HEATH = INSTANCE.register(Butterfly.HEATH_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_HEATH.get(), 0x880000, 0x000000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_LONGWING = INSTANCE.register(Butterfly.LONGWING_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_LONGWING.get(), 0x000000, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_MONARCH = INSTANCE.register(Butterfly.MONARCH_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_MONARCH.get(), 0xdd8855, 0x000000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_MORPHO = INSTANCE.register(Butterfly.MORPHO_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_MORPHO.get(), 0x0000aa, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_RAINBOW = INSTANCE.register(Butterfly.RAINBOW_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_RAINBOW.get(), 0xff7777, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_BUTTERFLY_SWALLOWTAIL = INSTANCE.register(Butterfly.SWALLOWTAIL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.BUTTERFLY_SWALLOWTAIL.get(), 0xffffff, 0xeeee77, new Item.Properties()));
    
    //  Spawn eggs - Caterpillars
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_ADMIRAL = INSTANCE.register(Caterpillar.ADMIRAL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_ADMIRAL.get(), 0x880000, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_BUCKEYE = INSTANCE.register(Caterpillar.BUCKEYE_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_BUCKEYE.get(), 0xcccc88, 0x8888cc, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_CABBAGE = INSTANCE.register(Caterpillar.CABBAGE_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_CABBAGE.get(), 0xeeee77, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_CHALKHILL = INSTANCE.register(Caterpillar.CHALKHILL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_CHALKHILL.get(), 0x0088ff, 0x00cc55, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_CLIPPER = INSTANCE.register(Caterpillar.CLIPPER_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_CLIPPER.get(), 0x0044aa, 0x004488, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_COMMON = INSTANCE.register(Caterpillar.COMMON_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_COMMON.get(), 0xaaff66, 0xeeee77, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_EMPEROR = INSTANCE.register(Caterpillar.EMPEROR_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_EMPEROR.get(), 0xcc44cc, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_FORESTER = INSTANCE.register(Caterpillar.FORESTER_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_FORESTER.get(), 0xeeee77, 0xff7777, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_GLASSWING = INSTANCE.register(Caterpillar.GLASSWING_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_GLASSWING.get(), 0x880000, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_HAIRSTREAK = INSTANCE.register(Caterpillar.HAIRSTREAK_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_HAIRSTREAK.get(), 0xcc44cc, 0x880000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_HEATH = INSTANCE.register(Caterpillar.HEATH_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_HEATH.get(), 0x880000, 0x000000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_LONGWING = INSTANCE.register(Caterpillar.LONGWING_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_LONGWING.get(), 0x000000, 0xffffff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_MONARCH = INSTANCE.register(Caterpillar.MONARCH_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_MONARCH.get(), 0xdd8855, 0x000000, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_MORPHO = INSTANCE.register(Caterpillar.MORPHO_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_MORPHO.get(), 0x0000aa, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_RAINBOW = INSTANCE.register(Caterpillar.RAINBOW_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_RAINBOW.get(), 0xff7777, 0x0088ff, new Item.Properties()));
    private static final DeferredHolder<Item, Item> SPAWN_EGG_CATERPILLAR_SWALLOWTAIL = INSTANCE.register(Caterpillar.SWALLOWTAIL_NAME,
            () -> new SpawnEggItem(EntityTypeRegistry.CATERPILLAR_SWALLOWTAIL.get(), 0xffffff, 0xeeee77, new Item.Properties()));

    /**
     * Helper method to get the correct butterfly net item.
     * @param butterflyIndex The butterfly index.
     * @return The registry entry for the related item.
     */
    public static DeferredHolder<Item, Item> getButterflyNetFromIndex(int butterflyIndex) {
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
    public static DeferredHolder<Item, Item> getBottledButterflyFromIndex(int butterflyIndex) {
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
    public static DeferredHolder<Item, Item> getButterflyScrollFromIndex(int butterflyIndex) {
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
            event.accept(SPAWN_EGG_BUTTERFLY_ADMIRAL.get());
            event.accept(SPAWN_EGG_BUTTERFLY_BUCKEYE.get());
            event.accept(SPAWN_EGG_BUTTERFLY_CABBAGE.get());
            event.accept(SPAWN_EGG_BUTTERFLY_CHALKHILL.get());
            event.accept(SPAWN_EGG_BUTTERFLY_CLIPPER.get());
            event.accept(SPAWN_EGG_BUTTERFLY_COMMON.get());
            event.accept(SPAWN_EGG_BUTTERFLY_EMPEROR.get());
            event.accept(SPAWN_EGG_BUTTERFLY_FORESTER.get());
            event.accept(SPAWN_EGG_BUTTERFLY_GLASSWING.get());
            event.accept(SPAWN_EGG_BUTTERFLY_HAIRSTREAK.get());
            event.accept(SPAWN_EGG_BUTTERFLY_HEATH.get());
            event.accept(SPAWN_EGG_BUTTERFLY_LONGWING.get());
            event.accept(SPAWN_EGG_BUTTERFLY_MONARCH.get());
            event.accept(SPAWN_EGG_BUTTERFLY_MORPHO.get());
            event.accept(SPAWN_EGG_BUTTERFLY_RAINBOW.get());
            event.accept(SPAWN_EGG_BUTTERFLY_SWALLOWTAIL.get());

            event.accept(SPAWN_EGG_CATERPILLAR_ADMIRAL.get());
            event.accept(SPAWN_EGG_CATERPILLAR_BUCKEYE.get());
            event.accept(SPAWN_EGG_CATERPILLAR_CABBAGE.get());
            event.accept(SPAWN_EGG_CATERPILLAR_CHALKHILL.get());
            event.accept(SPAWN_EGG_CATERPILLAR_CLIPPER.get());
            event.accept(SPAWN_EGG_CATERPILLAR_COMMON.get());
            event.accept(SPAWN_EGG_CATERPILLAR_EMPEROR.get());
            event.accept(SPAWN_EGG_CATERPILLAR_FORESTER.get());
            event.accept(SPAWN_EGG_CATERPILLAR_GLASSWING.get());
            event.accept(SPAWN_EGG_CATERPILLAR_HAIRSTREAK.get());
            event.accept(SPAWN_EGG_CATERPILLAR_HEATH.get());
            event.accept(SPAWN_EGG_CATERPILLAR_LONGWING.get());
            event.accept(SPAWN_EGG_CATERPILLAR_MONARCH.get());
            event.accept(SPAWN_EGG_CATERPILLAR_MORPHO.get());
            event.accept(SPAWN_EGG_CATERPILLAR_RAINBOW.get());
            event.accept(SPAWN_EGG_CATERPILLAR_SWALLOWTAIL.get());
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            event.accept(BUTTERFLY_EGG_ADMIRAL.get());
            event.accept(BUTTERFLY_EGG_BUCKEYE.get());
            event.accept(BUTTERFLY_EGG_CABBAGE.get());
            event.accept(BUTTERFLY_EGG_CHALKHILL.get());
            event.accept(BUTTERFLY_EGG_CLIPPER.get());
            event.accept(BUTTERFLY_EGG_COMMON.get());
            event.accept(BUTTERFLY_EGG_EMPEROR.get());
            event.accept(BUTTERFLY_EGG_FORESTER.get());
            event.accept(BUTTERFLY_EGG_GLASSWING.get());
            event.accept(BUTTERFLY_EGG_HAIRSTREAK.get());
            event.accept(BUTTERFLY_EGG_HEATH.get());
            event.accept(BUTTERFLY_EGG_LONGWING.get());
            event.accept(BUTTERFLY_EGG_MONARCH.get());
            event.accept(BUTTERFLY_EGG_MORPHO.get());
            event.accept(BUTTERFLY_EGG_RAINBOW.get());
            event.accept(BUTTERFLY_EGG_SWALLOWTAIL.get());

            event.accept(CATERPILLAR_ADMIRAL.get());
            event.accept(CATERPILLAR_BUCKEYE.get());
            event.accept(CATERPILLAR_CABBAGE.get());
            event.accept(CATERPILLAR_CHALKHILL.get());
            event.accept(CATERPILLAR_CLIPPER.get());
            event.accept(CATERPILLAR_COMMON.get());
            event.accept(CATERPILLAR_EMPEROR.get());
            event.accept(CATERPILLAR_FORESTER.get());
            event.accept(CATERPILLAR_GLASSWING.get());
            event.accept(CATERPILLAR_HAIRSTREAK.get());
            event.accept(CATERPILLAR_HEATH.get());
            event.accept(CATERPILLAR_LONGWING.get());
            event.accept(CATERPILLAR_MONARCH.get());
            event.accept(CATERPILLAR_MORPHO.get());
            event.accept(CATERPILLAR_RAINBOW.get());
            event.accept(CATERPILLAR_SWALLOWTAIL.get());
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.accept(BUTTERFLY_NET.get());
            event.accept(BUTTERFLY_NET_ADMIRAL.get());
            event.accept(BUTTERFLY_NET_BUCKEYE.get());
            event.accept(BUTTERFLY_NET_CABBAGE.get());
            event.accept(BUTTERFLY_NET_CHALKHILL.get());
            event.accept(BUTTERFLY_NET_CLIPPER.get());
            event.accept(BUTTERFLY_NET_COMMON.get());
            event.accept(BUTTERFLY_NET_EMPEROR.get());
            event.accept(BUTTERFLY_NET_FORESTER.get());
            event.accept(BUTTERFLY_NET_GLASSWING.get());
            event.accept(BUTTERFLY_NET_HAIRSTREAK.get());
            event.accept(BUTTERFLY_NET_HEATH.get());
            event.accept(BUTTERFLY_NET_LONGWING.get());
            event.accept(BUTTERFLY_NET_MONARCH.get());
            event.accept(BUTTERFLY_NET_MORPHO.get());
            event.accept(BUTTERFLY_NET_RAINBOW.get());
            event.accept(BUTTERFLY_NET_SWALLOWTAIL.get());

            //event.accept(BOTTLED_BUTTERFLY.get());
            event.accept(BOTTLED_BUTTERFLY_ADMIRAL.get());
            event.accept(BOTTLED_BUTTERFLY_BUCKEYE.get());
            event.accept(BOTTLED_BUTTERFLY_CABBAGE.get());
            event.accept(BOTTLED_BUTTERFLY_CHALKHILL.get());
            event.accept(BOTTLED_BUTTERFLY_CLIPPER.get());
            event.accept(BOTTLED_BUTTERFLY_COMMON.get());
            event.accept(BOTTLED_BUTTERFLY_EMPEROR.get());
            event.accept(BOTTLED_BUTTERFLY_FORESTER.get());
            event.accept(BOTTLED_BUTTERFLY_GLASSWING.get());
            event.accept(BOTTLED_BUTTERFLY_HAIRSTREAK.get());
            event.accept(BOTTLED_BUTTERFLY_HEATH.get());
            event.accept(BOTTLED_BUTTERFLY_LONGWING.get());
            event.accept(BOTTLED_BUTTERFLY_MONARCH.get());
            event.accept(BOTTLED_BUTTERFLY_MORPHO.get());
            event.accept(BOTTLED_BUTTERFLY_RAINBOW.get());
            event.accept(BOTTLED_BUTTERFLY_SWALLOWTAIL.get());

            event.accept(BOTTLED_CATERPILLAR_ADMIRAL.get());
            event.accept(BOTTLED_CATERPILLAR_BUCKEYE.get());
            event.accept(BOTTLED_CATERPILLAR_CABBAGE.get());
            event.accept(BOTTLED_CATERPILLAR_CHALKHILL.get());
            event.accept(BOTTLED_CATERPILLAR_CLIPPER.get());
            event.accept(BOTTLED_CATERPILLAR_COMMON.get());
            event.accept(BOTTLED_CATERPILLAR_EMPEROR.get());
            event.accept(BOTTLED_CATERPILLAR_FORESTER.get());
            event.accept(BOTTLED_CATERPILLAR_GLASSWING.get());
            event.accept(BOTTLED_CATERPILLAR_HAIRSTREAK.get());
            event.accept(BOTTLED_CATERPILLAR_HEATH.get());
            event.accept(BOTTLED_CATERPILLAR_LONGWING.get());
            event.accept(BOTTLED_CATERPILLAR_MONARCH.get());
            event.accept(BOTTLED_CATERPILLAR_MORPHO.get());
            event.accept(BOTTLED_CATERPILLAR_RAINBOW.get());
            event.accept(BOTTLED_CATERPILLAR_SWALLOWTAIL.get());

            //event.accept(BUTTERFLY_SCROLL.get());
            event.accept(BUTTERFLY_SCROLL_ADMIRAL.get());
            event.accept(BUTTERFLY_SCROLL_BUCKEYE.get());
            event.accept(BUTTERFLY_SCROLL_CABBAGE.get());
            event.accept(BUTTERFLY_SCROLL_CHALKHILL.get());
            event.accept(BUTTERFLY_SCROLL_CLIPPER.get());
            event.accept(BUTTERFLY_SCROLL_COMMON.get());
            event.accept(BUTTERFLY_SCROLL_EMPEROR.get());
            event.accept(BUTTERFLY_SCROLL_FORESTER.get());
            event.accept(BUTTERFLY_SCROLL_GLASSWING.get());
            event.accept(BUTTERFLY_SCROLL_HAIRSTREAK.get());
            event.accept(BUTTERFLY_SCROLL_HEATH.get());
            event.accept(BUTTERFLY_SCROLL_LONGWING.get());
            event.accept(BUTTERFLY_SCROLL_MONARCH.get());
            event.accept(BUTTERFLY_SCROLL_MORPHO.get());
            event.accept(BUTTERFLY_SCROLL_RAINBOW.get());
            event.accept(BUTTERFLY_SCROLL_SWALLOWTAIL.get());

            event.accept(BUTTERFLY_BOOK.get());
            event.accept(BUTTERFLY_ZHUANGZI.get());
        }
    }
}
