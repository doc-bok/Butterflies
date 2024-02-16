package com.bokmcdok.butterflies.network.protocol.common.custom;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * A network packet used to send butterfly data to the clients.
 * @param data A collection of all the butterfly data the server has.
 */
public record ClientboundButterflyDataPacket(Collection<ButterflyData> data) {

    //  The ID of this packet.
    public static final ResourceLocation ID = new ResourceLocation(ButterfliesMod.MODID, "butterfly_data");

    /**
     * Write the data to a network buffer.
     * @param buffer The buffer to write to.
     */
    public void write(@NotNull FriendlyByteBuf buffer) {
        buffer.writeCollection(data, (collectionBuffer, i) -> {
            collectionBuffer.writeInt(i.butterflyIndex);
            collectionBuffer.writeUtf(i.entityId);
            collectionBuffer.writeEnum(i.size);
            collectionBuffer.writeEnum(i.speed);
            collectionBuffer.writeEnum(i.rarity);
            collectionBuffer.writeEnum(i.habitat);
            collectionBuffer.writeInt(i.caterpillarLifespan);
            collectionBuffer.writeInt(i.chrysalisLifespan);
            collectionBuffer.writeInt(i.butterflyLifespan);
        });
    }

    /**
     * Get the buffer to send to a client.
     * @return The buffer to send.
     */
    public FriendlyByteBuf getBuffer() {
        FriendlyByteBuf result = new FriendlyByteBuf(Unpooled.buffer());
        result.writeResourceLocation(ID);
        write(result);
        return result;
    }
}
