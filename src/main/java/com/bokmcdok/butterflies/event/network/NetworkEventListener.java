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

/**
 * Listens for network-based events.
 */
public class NetworkEventListener {

    @Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventListener {
        /**
         * Called when there is a datapack sync requested. Used to send butterfly
         * data to the clients.
         *
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
            event.getPlayerList();
            for (ServerPlayer i : event.getPlayerList().getPlayers()) {
                i.connection.send(payload);
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
