package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Listens for mod lifecycle events.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LifecycleEventListener {

    /**
     * Common setup - initialise some network event handlers.
     * @param event The event and its context.
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkEventListener.BUTTERFLY_NETWORK_CHANNEL.addListener(NetworkEventListener::onButterflyCollectionPayload);
    }
}
