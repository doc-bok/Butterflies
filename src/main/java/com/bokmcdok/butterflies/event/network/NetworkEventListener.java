package com.bokmcdok.butterflies.event.network;

import com.bokmcdok.butterflies.butterfly_data.*;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
        Collection<ButterflyData> butterflyDataCollection = new ArrayList<>(ButterflyRegistry.getButterflyDataCollection());

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

                // First reset the Butterfly Data
                ButterflyRegistry.reset();

                List<ButterflyData> butterflyData = payload.readCollection(ArrayList::new,
                        (buffer) -> new ButterflyData.Builder(buffer.readInt())
                                .speciesId(buffer.readUtf())
                                .size(buffer.readEnum(ButterflySize.class))
                                .speed(buffer.readEnum(ButterflySpeed.class))
                                .rarity(buffer.readEnum(ButterflyRarity.class))
                                .habitats(buffer.readEnumSet(ButterflyHabitat.class))
                                .eggLifespan(buffer.readInt())
                                .caterpillarLifespan(buffer.readInt())
                                .chrysalisLifespan(buffer.readInt())
                                .butterflyLifespan(buffer.readInt())
                                .foodBlock(buffer.readResourceLocation())
                                .foodItem(buffer.readResourceLocation())
                                .type(buffer.readEnum(ButterflyType.class))
                                .diurnality(buffer.readEnum(Diurnality.class))
                                .extraLandingBlocks(buffer.readCollection(
                                        FriendlyByteBuf.limitValue(HashSet::new, 4),
                                        FriendlyByteBuf::readUtf))
                                .plantEffect(buffer.readEnum(PlantEffect.class))
                                .eggMultiplier(buffer.readEnum(EggMultiplier.class))
                                .caterpillarSounds(buffer.readBoolean())
                                .butterflySounds(buffer.readBoolean())
                                .traits(buffer.readEnumSet(ButterflyTrait.class))
                                .baseVariant(buffer.readUtf())
                                .coldVariant(buffer.readUtf())
                                .mateVariant(buffer.readUtf())
                                .warmVariant(buffer.readUtf())
                                .agedVariant(buffer.readUtf())
                                .build());

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
}
