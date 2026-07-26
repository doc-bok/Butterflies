package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.*;
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
                    entry.readEnum(ButterflySize.class),
                    entry.readEnum(ButterflySpeed.class),
                    entry.readEnum(ButterflyRarity.class),
                    entry.readList((x) -> x.readEnum(ButterflyHabitat.class)),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readInt(),
                    entry.readResourceLocation(),
                    entry.readEnum(ButterflyType.class),
                    entry.readEnum(Diurnality.class),
                    entry.readEnum(ExtraLandingBlocks.class),
                    entry.readEnum(PlantEffect.class),
                    entry.readEnum(EggMultiplier.class),
                    entry.readBoolean(),
                    entry.readBoolean(),    
                    entry.readList((x) -> x.readEnum(ButterflyTrait.class)),
                    entry.readUtf(),
                    entry.readUtf(),
                    entry.readUtf(),
                    entry.readUtf(),
                    entry.readUtf())));
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
            collectionBuffer.writeEnum(i.eggMultiplier());
            collectionBuffer.writeBoolean(i.caterpillarSounds());
            collectionBuffer.writeBoolean(i.butterflySounds());
            collectionBuffer.writeCollection(i.traits(), FriendlyByteBuf::writeEnum);
            collectionBuffer.writeUtf(i.baseVariant());
            collectionBuffer.writeUtf(i.coldVariant());
            collectionBuffer.writeUtf(i.mateVariant());
            collectionBuffer.writeUtf(i.warmVariant());
            collectionBuffer.writeUtf(i.agedVariant());
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
