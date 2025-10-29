package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.zip.DataFormatException;

/**
 * Handles payloads sent to the client.
 */
public class ClientPayloadHandler {

    //  The static instance of the handler.
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    /**
     * Get the instance of the handler.
     * @return The singleton instance.
     */
    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Handles the butterfly data.
     * @param data The inbound data record.
     * @param context The context in which the payload was received.
     */
    public void handleButterflyData(@NotNull final ClientBoundButterflyDataPacket data,
                                    final PlayPayloadContext context) {

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            
                    // First reset the Butterfly Data
                    ButterflyData.reset();

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
                    context.packetHandler().disconnect(Component.translatable("networking.butterflies.data_sync_failed", e.getMessage()));
                    return null;
                });

    }
}