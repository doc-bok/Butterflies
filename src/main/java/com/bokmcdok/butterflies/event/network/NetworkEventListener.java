package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.butterfly_data.*;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.event.EventNetworkChannel;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Listens for network-based events.
 */
public class NetworkEventListener {

    public static final EventNetworkChannel BUTTERFLY_NETWORK_CHANNEL = NetworkRegistry.ChannelBuilder.
            named(ClientBoundButterflyDataPacket.ID).
            clientAcceptedVersions(a -> true).
            serverAcceptedVersions(a -> true).
            networkProtocolVersion(() -> NetworkConstants.NETVERSION).
            eventNetworkChannel();

    /**
     * Construction
     *
     * @param forgeEventBus The event bus to register with.
     */
    public NetworkEventListener(IEventBus forgeEventBus) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onDatapackSync);
    }

    /**
     * Called when there is a datapack sync requested. Used to send butterfly
     * data to the clients.
     *
     * @param event The sync event.
     */
    private void onDatapackSync(OnDatapackSyncEvent event) {

        // Get the butterfly data collection.
        Collection<ButterflyData> butterflyDataCollection = new ArrayList<>(ButterflyRegistry.getButterflyDataCollection());

        // Create our packet.
        ClientBoundButterflyDataPacket packet = new ClientBoundButterflyDataPacket(butterflyDataCollection);

        // Create the payload.
        Packet<?> payload = new ClientboundCustomPayloadPacket(packet.getBuffer());

        // Handle a single player.
        if (event.getPlayer() != null) {
            event.getPlayer().connection.send(payload);
        }

        // Handle multiple players.
        else if (event.getPlayerList() != null) {
            for (ServerPlayer i : event.getPlayerList().getPlayers()) {
                i.connection.send(payload);
            }
        }
    }

    /**
     * Called when a custom payload is received.
     *
     * @param event The payload event.
     */
    public static void onButterflyCollectionPayload(NetworkEvent.ServerCustomPayloadEvent event) {

        // Extract the data from the payload.
        FriendlyByteBuf payload = event.getPayload();
        if (payload != null) {

            // First reset the Butterfly Data
            ButterflyRegistry.reset();

            List<ButterflyData> butterflyData = payload.readCollection(ArrayList::new,
                    (buffer) -> new ButterflyData(buffer.readInt(),
                            buffer.readUtf(),
                            buffer.readEnum(ButterflySize.class),
                            buffer.readEnum(ButterflySpeed.class),
                            buffer.readEnum(ButterflyRarity.class),
                            buffer.readList((x) -> x.readEnum(ButterflyHabitat.class)),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readResourceLocation(),
                            buffer.readEnum(ButterflyType.class),
                            buffer.readEnum(Diurnality.class),
                            buffer.readEnum(ExtraLandingBlocks.class),
                            buffer.readEnum(PlantEffect.class),
                            buffer.readEnum(EggMultiplier.class),
                            buffer.readBoolean(),
                            buffer.readBoolean(),
                            buffer.readList((x) -> x.readEnum(ButterflyTrait.class)),
                            buffer.readUtf(),
                            buffer.readUtf(),
                            buffer.readUtf(),
                            buffer.readUtf(),
                            buffer.readUtf()));

            // Register the new data.
            for (ButterflyData butterfly : butterflyData) {
                try {
                    ButterflyRegistry.addButterfly(butterfly);
                } catch (DataFormatException e) {
                    LogUtils.getLogger().error("Received invalid butterfly data.", e);
                }
            }
        }
    }
}
