package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Listens for mod based events.
 */
public class ModEventListener {

    // Reference to the item registry.
    private final ItemRegistry itemRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEventListener(IEventBus modEventBus,
                            ItemRegistry itemRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::onBuildCreativeModeTabContents);

        this.itemRegistry = itemRegistry;
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(itemRegistry.getButterflyFeeder());
            event.accept(itemRegistry.getButterflyMicroscope());
        }

        else if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(itemRegistry.getButterflyOrigamiBlack());
            event.accept(itemRegistry.getButterflyOrigamiBlue());
            event.accept(itemRegistry.getButterflyOrigamiBrown());
            event.accept(itemRegistry.getButterflyOrigamiCyan());
            event.accept(itemRegistry.getButterflyOrigamiGray());
            event.accept(itemRegistry.getButterflyOrigamiGreen());
            event.accept(itemRegistry.getButterflyOrigamiLightBlue());
            event.accept(itemRegistry.getButterflyOrigamiLightGray());
            event.accept(itemRegistry.getButterflyOrigamiLime());
            event.accept(itemRegistry.getButterflyOrigamiMagenta());
            event.accept(itemRegistry.getButterflyOrigamiOrange());
            event.accept(itemRegistry.getButterflyOrigamiPink());
            event.accept(itemRegistry.getButterflyOrigamiPurple());
            event.accept(itemRegistry.getButterflyOrigamiRed());
            event.accept(itemRegistry.getButterflyOrigamiWhite());
            event.accept(itemRegistry.getButterflyOrigamiYellow());
        }

        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(itemRegistry.getButterflyPotterySherd());
            event.accept(itemRegistry.getInfestedApple());
            event.accept(itemRegistry.getSilk());
            event.accept(itemRegistry.getButterflyBannerPattern());
        }

        else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            for (RegistryObject<Item> i : itemRegistry.getButterflyEggs()) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : itemRegistry.getCaterpillars()) {
                event.accept(i);
            }
        }

        else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            for (RegistryObject<Item> i : itemRegistry.getButterflySpawnEggs()) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : itemRegistry.getCaterpillarSpawnEggs()) {
                event.accept(i);
            }

            event.accept(itemRegistry.getButterflyGolemSpawnEgg());
        }

        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.accept(itemRegistry.getEmptyButterflyNet());
            for (RegistryObject<Item> i : itemRegistry.getButterflyNets()) {
                event.accept(i);
            }

            event.accept(itemRegistry.getBurntButterflyNet());

            for (RegistryObject<Item> i : itemRegistry.getBottledButterflies()) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : itemRegistry.getBottledCaterpillars()) {
                event.accept(i);
            }

            for (RegistryObject<Item> i : itemRegistry.getButterflyScrolls()) {
                event.accept(i);
            }

            event.accept(itemRegistry.getButterflyBook());
            event.accept(itemRegistry.getZhuangziBook());
        }
    }
}
