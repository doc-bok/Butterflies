package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.ambient.Butterfly;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
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

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerCreativeTabContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
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
        }
    }
}
