package com.bokmcdok.butterflies.event.server;

import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Listens for events on starting a server.
 */
public class ServerEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public ServerEventListener(IEventBus forgeEventBus) {

        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onServerAboutToStart);
    }

    /**
     * Load butterfly mod-specific data when a level loads.
     * @param event The level load event.
     */
    public void onServerAboutToStart(ServerAboutToStartEvent event) {

        // Get the resource manager.
        MinecraftServer server = event.getServer();
        ResourceManager resourceManager;
        if (server == null) {
            resourceManager = Minecraft.getInstance().getResourceManager();
        } else {
            resourceManager = server.getResourceManager();
        }

        ButterflyData.load(resourceManager);
    }
}
