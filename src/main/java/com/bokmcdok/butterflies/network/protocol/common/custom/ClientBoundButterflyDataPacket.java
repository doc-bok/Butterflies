package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A network packet used to send butterfly data to the clients.
 * @param data A collection of all the butterfly data the server has.
 */
public record ClientBoundButterflyDataPacket(List<ButterflyData> data) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientBoundButterflyDataPacket> TYPE_PAYLOAD = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "butterfly_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundButterflyDataPacket> STREAM_CODEC =
            ButterflyData.STREAM_CODEC
                    .apply(ByteBufCodecs.list())
                    .map(ClientBoundButterflyDataPacket::new, ClientBoundButterflyDataPacket::data);

    /**
     * Get the Type of this buffer.
     * @return ResourceLocation containing the buffer ID.
     */
    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE_PAYLOAD;
    }
}
