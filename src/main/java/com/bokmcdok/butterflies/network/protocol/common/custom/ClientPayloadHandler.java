package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.zip.DataFormatException;

/**
 * Handles payloads sent to the client.
 */
public class ClientPayloadHandler {

    /**
     * Handles the butterfly data.
     * @param data The inbound data record.
     * @param context The context in which the payload was received.
     */
    public static void handleButterflyData(@NotNull final ClientBoundButterflyDataPacket data,
                                           final IPayloadContext context) {

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {

            // Extract the data from the payload.
            Collection<ButterflyData> butterflyData = data.data();

            // Register the new data.
            for (ButterflyData butterfly : butterflyData) {
                try {
                    ButterflyData.addButterfly(butterfly);
                } catch (DataFormatException e) {
                    LogUtils.getLogger().error("Received invalid butterfly data.", e);
                }
            }
        })
        .exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.translatable("networking.butterflies.data_sync_failed", e.getMessage()));
            return null;
        });
    }
}