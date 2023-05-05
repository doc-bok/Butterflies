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
    private static final RegistryObject<Item> BUTTERFLY_EGG = INSTANCE.register(Butterfly.NAME,
            () -> new ForgeSpawnEggItem(EntityTypeRegistry.BUTTERFLY, 0xff0000, 0x000000, new Item.Properties()));

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    public static void registerCreativeTabContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BUTTERFLY_EGG);
        }
    }
}
