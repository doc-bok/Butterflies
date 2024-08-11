package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A network packet used to send butterfly data to the clients.
 * @param data A collection of all the butterfly data the server has.
 */
public record ClientBoundButterflyDataPacket(Collection<ButterflyData> data) implements CustomPacketPayload {

    //  The ID of this packet.
    public static final ResourceLocation ID = new ResourceLocation(ButterfliesMod.MOD_ID, "butterfly_data");

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
                    entry.readEnum(ButterflyData.Habitat.class),
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
                    entry.readEnum(ButterflyData.EggMultiplier.class))));
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
            collectionBuffer.writeEnum(i.habitat());
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
        });
    }

    /**
     * Get the ID of this buffer.
     * @return ResourceLocation containing the buffer ID.
     */
    @Override
    @NotNull
    public ResourceLocation id() {
        return ID;
    }
}
