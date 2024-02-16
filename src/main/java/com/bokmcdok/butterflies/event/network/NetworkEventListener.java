package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientboundButterflyDataPacket;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.event.EventNetworkChannel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Listens for network-based events.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NetworkEventListener {

    public static final EventNetworkChannel BUTTERFLY_NETWORK_CHANNEL = NetworkRegistry.ChannelBuilder.
            named(ClientboundButterflyDataPacket.ID).
            clientAcceptedVersions(a -> true).
            serverAcceptedVersions(a -> true).
            networkProtocolVersion(() -> NetworkConstants.NETVERSION).
            eventNetworkChannel();

    /**
     * Called when there is a datapack sync requested. Used to send butterfly
     * data to the clients.
     * @param event The sync event.
     */
    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {

        // Get the butterfly data collection.
        Collection<ButterflyData> butterflyDataCollection = ButterflyData.getButterflyDataCollection();

        // Create our packet.
        ClientboundButterflyDataPacket packet = new ClientboundButterflyDataPacket(butterflyDataCollection);

        // Create the payload.
        Packet<?> payload = new ClientboundCustomPayloadPacket(packet.getBuffer());

        // Handle a single player.
        if (event.getPlayer() != null) {
            event.getPlayer().connection.send(payload);
        }

        // Handle multiple players.
        if (event.getPlayerList() != null) {
            for (ServerPlayer i : event.getPlayerList().getPlayers()) {
                i.connection.send(payload);
            }
        }
    }

    /**
     * Called when a custom payload is received.
     * @param event The payload event.
     */
    public static void onButterflyCollectionPayload(NetworkEvent.ServerCustomPayloadEvent event) {

        // Extract the data from the payload.
        FriendlyByteBuf payload = event.getPayload();
        if (payload != null) {
            List<ButterflyData> butterflyData = payload.readCollection(ArrayList::new,
                    (buffer) -> new ButterflyData(buffer.readInt(),
                            buffer.readUtf(),
                            buffer.readEnum(ButterflyData.Size.class),
                            buffer.readEnum(ButterflyData.Speed.class),
                            buffer.readEnum(ButterflyData.Rarity.class),
                            buffer.readEnum(ButterflyData.Habitat.class),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readInt()));

            // Register the new data.
            for (ButterflyData butterfly : butterflyData) {
                ButterflyData.addButterfly(butterfly);
            }
        }
    }
}
