package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientPayloadHandler;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

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
        forgeEventBus.addListener(this::onDatapackSync);
        forgeEventBus.addListener(this::onCustomPayload);

    }

    /**
     * Called when there is a datapack sync requested. Used to send butterfly
     * data to the clients.
     * @param event The sync event.
     */
    private void onDatapackSync(OnDatapackSyncEvent event) {

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
    private void onCustomPayload(CustomPayloadEvent event) {

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
                                                      buffer.readEnum(ButterflyData.EggMultiplier.class),
                                                      buffer.readBoolean(),
                                                      buffer.readBoolean()));

                // Register the new data.
                for (ButterflyData butterfly : butterflyData) {
                    try {
                        ButterflyData.addButterfly(butterfly);
                    } catch (DataFormatException e) {
                        LogUtils.getLogger().error("Received invalid butterfly data.", e);
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventListener {
        /**
         * Register a network payload namespace for our mod.
         *
         * @param event The event fired when payload handlers are being registered.
         */
        @SubscribeEvent
        public static void register(final RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar registrar = event.registrar(ButterfliesMod.MOD_ID);
            registrar.play(ClientBoundButterflyDataPacket.ID, ClientBoundButterflyDataPacket::new, handler -> handler
                    .client(ClientPayloadHandler.getInstance()::handleButterflyData));
        }
    }
}
