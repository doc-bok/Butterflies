package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.DecoratedPotPatternsRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * Events fired during the overall life cycle of the mod.
 */
public class LifecycleEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus) {
        modEventBus.register(this);
    }

    /**
     * Common setup event where we register brewing recipes and pottery sherd
     * patterns.
     * @param event The event class.
     */
    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(DecoratedPotPatternsRegistry::expandVanillaPatterns);
    }

    /**
     * Register the screens with their respective menus.
     * @param event The client setup event.
     */
    @SubscribeEvent
    private void clientSetup(RegisterMenuScreensEvent event) {
        event.register(MenuTypeRegistry.BUTTERFLY_FEEDER_MENU.get(), ButterflyFeederScreen::new);
        event.register(MenuTypeRegistry.BUTTERFLY_MICROSCOPE_MENU.get(), ButterflyMicroscopeScreen::new);
    }
}
