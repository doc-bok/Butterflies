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
     * Construct from a byte buffer. Reads the data ready for use.
     * @param buffer The buffer to read the data from.
     */
    public ClientBoundButterflyDataPacket(final FriendlyByteBuf buffer) {
        this((Collection<ButterflyData>) buffer.readCollection(ArrayList::new,
            (entry) -> new ButterflyData(entry.readInt(),
                    entry.readUtf(),
                    entry.readEnum(ButterflyData.Size.class),
                    entry.readEnum(ButterflyData.Speed.class),
                    entry.readEnum(ButterflyData.Rarity.class),
                    entry.readList((x) -> x.readEnum(ButterflyData.Habitat.class)),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readResourceLocation(),
                    entry.readEnum(ButterflyData.ButterflyType.class),
                    entry.readEnum(ButterflyData.Diurnality.class),
                    entry.readEnum(ButterflyData.ExtraLandingBlocks.class),
                    entry.readEnum(ButterflyData.PlantEffect.class),
                    entry.readResourceLocation(),
                    entry.readEnum(ButterflyData.EggMultiplier.class),
                    entry.readBoolean(),
                    entry.readBoolean(),    
                    buffer.readList((x) -> x.readEnum(ButterflyData.Trait.class)))));
    }

    /**
     * Write the data to a network buffer.
     * @param buffer The buffer to write to.
     */
    @Override
    public void write(@NotNull FriendlyByteBuf buffer) {
        buffer.writeCollection(data, (collectionBuffer, i) -> {
            collectionBuffer.writeInt(i.butterflyIndex());
            collectionBuffer.writeUtf(i.entityId());
            collectionBuffer.writeEnum(i.size());
            collectionBuffer.writeEnum(i.speed());
            collectionBuffer.writeEnum(i.rarity());
            collectionBuffer.writeCollection(i.habitats(), FriendlyByteBuf::writeEnum);
            collectionBuffer.writeInt(i.eggLifespan());
            collectionBuffer.writeInt(i.caterpillarLifespan());
            collectionBuffer.writeInt(i.chrysalisLifespan());
            collectionBuffer.writeInt(i.butterflyLifespan());
            collectionBuffer.writeResourceLocation(i.preferredFlower());
            collectionBuffer.writeEnum(i.type());
            collectionBuffer.writeEnum(i.diurnality());
            collectionBuffer.writeEnum(i.extraLandingBlocks());
            collectionBuffer.writeEnum(i.plantEffect());
            collectionBuffer.writeResourceLocation(i.breedTarget());
            collectionBuffer.writeEnum(i.eggMultiplier());
            collectionBuffer.writeBoolean(i.caterpillarSounds());
            collectionBuffer.writeBoolean(i.butterflySounds());
            collectionBuffer.writeCollection(i.traits(), FriendlyByteBuf::writeEnum);
        });
    }

    /**
     * Get the ID of this buffer.
     * @return ResourceLocation containing the buffer ID.
     */
    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE_PAYLOAD;
    }
}
