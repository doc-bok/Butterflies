package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Listens for network-based events.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NetworkEventListener {

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
        ClientBoundButterflyDataPacket packet = new ClientBoundButterflyDataPacket(butterflyDataCollection);

        // Create the payload.
        Packet<?> payload = new ClientboundCustomPayloadPacket(packet);

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
     * @param event The payload event.
     */
    @SubscribeEvent
    public static void onCustomPayload(CustomPayloadEvent event) {

        // Handle a butterfly data collection.
        if (event.getChannel().compareTo(ClientBoundButterflyDataPacket.ID) == 0) {

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
                                                      buffer.readInt(),
                                                      buffer.readInt(),
                                                      buffer.readResourceLocation(),
                                                      buffer.readEnum(ButterflyData.ButterflyType.class),
                                                      buffer.readEnum(ButterflyData.Diurnality.class),
                                                      buffer.readEnum(ButterflyData.ExtraLandingBlocks.class),
                                                      buffer.readEnum(ButterflyData.PlantEffect.class),
                                                      buffer.readResourceLocation(),
                                                      buffer.readEnum(ButterflyData.EggMultiplier.class)));

                // Register the new data.
                for (ButterflyData butterfly : butterflyData) {
                    ButterflyData.addButterfly(butterfly);
                }
            }
        }
    }
}
