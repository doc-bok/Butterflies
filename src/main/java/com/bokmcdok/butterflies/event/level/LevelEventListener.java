package com.bokmcdok.butterflies.event.level;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Listens for events on loading a level.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventListener {


    // GSON build that can parse Butterfly entries.
    private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ButterflyData.class, new ButterflyData.Serializer()).create();

    // Logger for reporting errors.
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Load butterfly mod-specific data when a level loads.
     * @param event The level load event.
     */
    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        LOGGER.debug("LevelEvent.Load");

        // Get the resource manager.
        ResourceManager resourceManager = null;
        if (event.getLevel().isClientSide()) {
            resourceManager = Minecraft.getInstance().getResourceManager();
        } else {
            MinecraftServer server = event.getLevel().getServer();
            if (server == null) {
                LOGGER.error("Failed to get Minecraft Server");
            } else {
                resourceManager = server.getResourceManager();
            }
        }

        if (resourceManager == null) {
            LOGGER.error("Failed to get Resource Manager");
        } else {
            // Get the butterfly JSON files
            Map<ResourceLocation, Resource> resourceMap =
                    resourceManager.listResources(ButterfliesMod.MODID, (x) -> x.getPath().endsWith(".json"));

            // Parse each one and generate the data.
            for (ResourceLocation location : resourceMap.keySet()) {
                try {
                    Resource resource = resourceMap.get(location);
                    BufferedReader reader = resource.openAsReader();
                    ButterflyData butterflyData = GSON.fromJson(reader, ButterflyData.class);
                    ButterflyData.addButterfly(butterflyData);
                } catch (IOException e) {
                    LOGGER.error("Failed to load butterfly data: [" + location.toString() + "]", e);
                }
            }
        }
    }
}
