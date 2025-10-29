package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens for network-based events.
 */
public class NetworkEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public NetworkEventListener(IEventBus forgeEventBus) {
        forgeEventBus.register(this);

    }

    /**
     * Called when there is a datapack sync requested. Used to send butterfly
     * data to the clients.
     * @param event The sync event.
     */
    @SubscribeEvent
    private void onDatapackSync(OnDatapackSyncEvent event) {

        // Get a COPY of the butterfly data collection.
        List<ButterflyData> butterflyDataCollection = new ArrayList<>(ButterflyData.getButterflyDataList());

        // Create our packet.
        ClientBoundButterflyDataPacket packet = new ClientBoundButterflyDataPacket(butterflyDataCollection);

        // Create the payload.
        Packet<?> payload = new ClientboundCustomPayloadPacket(packet);

        // Handle a single player.
        if (event.getPlayer() != null) {
            event.getPlayer().connection.send(payload);
        }

        // Handle multiple players.
        else {
            for (ServerPlayer i : event.getPlayerList().getPlayers()) {
                i.connection.send(payload);
            }
        }
    }
}
