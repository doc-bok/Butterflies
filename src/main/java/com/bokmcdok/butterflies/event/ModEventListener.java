package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientPayloadHandler;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;

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

        this.itemRegistry = itemRegistry;
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
    private void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(itemRegistry.getButterflyFeeder().get());
            event.accept(itemRegistry.getButterflyMicroscope().get());
        }

        else if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(itemRegistry.getButterflyOrigamiBlack().get());
            event.accept(itemRegistry.getButterflyOrigamiBlue().get());
            event.accept(itemRegistry.getButterflyOrigamiBrown().get());
            event.accept(itemRegistry.getButterflyOrigamiCyan().get());
            event.accept(itemRegistry.getButterflyOrigamiGray().get());
            event.accept(itemRegistry.getButterflyOrigamiGreen().get());
            event.accept(itemRegistry.getButterflyOrigamiLightBlue().get());
            event.accept(itemRegistry.getButterflyOrigamiLightGray().get());
            event.accept(itemRegistry.getButterflyOrigamiLime().get());
            event.accept(itemRegistry.getButterflyOrigamiMagenta().get());
            event.accept(itemRegistry.getButterflyOrigamiOrange().get());
            event.accept(itemRegistry.getButterflyOrigamiPink().get());
            event.accept(itemRegistry.getButterflyOrigamiPurple().get());
            event.accept(itemRegistry.getButterflyOrigamiRed().get());
            event.accept(itemRegistry.getButterflyOrigamiWhite().get());
            event.accept(itemRegistry.getButterflyOrigamiYellow().get());
        }

        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(itemRegistry.getButterflyPotterySherd().get());
            event.accept(itemRegistry.getInfestedApple().get());
            event.accept(itemRegistry.getSilk().get());
            event.accept(itemRegistry.getButterflyBannerPattern().get());
        }

        else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            for (DeferredHolder<Item, Item> i : itemRegistry.getButterflyEggs()) {
                event.accept(i.get());
            }

            for (DeferredHolder<Item, Item> i : itemRegistry.getCaterpillars()) {
                event.accept(i.get());
            }
        }

        else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            for (DeferredHolder<Item, Item> i : itemRegistry.getButterflySpawnEggs()) {
                event.accept(i.get());
            }

            for (DeferredHolder<Item, Item> i : itemRegistry.getCaterpillarSpawnEggs()) {
                event.accept(i.get());
            }

            event.accept(itemRegistry.getButterflyGolemSpawnEgg().get());
        }

        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.accept(itemRegistry.getEmptyButterflyNet().get());
            for (DeferredHolder<Item, Item> i : itemRegistry.getButterflyNets()) {
                event.accept(i.get());
            }

            event.accept(itemRegistry.getBurntButterflyNet().get());

            for (DeferredHolder<Item, Item> i : itemRegistry.getBottledButterflies()) {
                event.accept(i.get());
            }

            for (DeferredHolder<Item, Item> i : itemRegistry.getBottledCaterpillars()) {
                event.accept(i.get());
            }

            for (DeferredHolder<Item, Item> i : itemRegistry.getButterflyScrolls()) {
                event.accept(i.get());
            }

            event.accept(itemRegistry.getButterflyBook().get());
            event.accept(itemRegistry.getZhuangziBook().get());
        }
    }

    /**
     * Register a network payload namespace for our mod.
     * @param event The event fired when payload handlers are being registered.
     */
    @SubscribeEvent
    private void onRegisterPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ButterfliesMod.MOD_ID);
        registrar.playToClient(ClientBoundButterflyDataPacket.TYPE_PAYLOAD,
                ClientBoundButterflyDataPacket::new,
                ClientPayloadHandler::handleButterflyData);
    }
}
