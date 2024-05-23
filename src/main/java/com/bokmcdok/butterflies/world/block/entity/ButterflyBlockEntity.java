package com.bokmcdok.butterflies.world.block.entity;

import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Class to represent a block that contains a butterfly.
 * TODO: This class is only here for backward compatibility and should be
 *       removed in a future version.
 */
public class ButterflyBlockEntity extends BlockEntity {

    public static final String NAME = "butterfly_block";

    // The ID of the butterfly contained in this block.
    private ResourceLocation entityId = null;

    @NotNull
    public static ButterflyBlockEntity CreateBottledButterflyBlockEntity(BlockPos position,
                                                                         BlockState blockState) {
        return new ButterflyBlockEntity(BlockEntityTypeRegistry.BOTTLED_BUTTERFLY_BLOCK.get(), position, blockState);
    }

    /**
     * Construction
     * @param position The position of the block that owns this entity.
     * @param blockState The state of the block that owns this entity.
     */
    public ButterflyBlockEntity(BlockEntityType<?> blockEntityType,
                                BlockPos position,
                                BlockState blockState) {
        super(blockEntityType, position, blockState);
    }

    /**
     * Accessor for the entity ID.
     * @return The entity ID.
     */
    public ResourceLocation getEntityLocation() {
        return entityId;
    }

    /**
     * Set the entity ID of the butterfly contained in this block.
     * @param entityId The entity ID of the butterfly contained in this block.
     */
    public void setEntityLocation(ResourceLocation entityId) {
        this.entityId = entityId;
    }

    /**
     * Called when loading the entity from NBT data.
     * @param tag The NBT tags.
     */
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains(CompoundTagId.ENTITY_ID)) {
            this.entityId = new ResourceLocation(tag.getString(CompoundTagId.ENTITY_ID));
        }
    }

    /**
     * Called when saving the entity to NBT data.
     * @param tag The NBT tags.
     */
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        // In future this will always be null and will eventually be removed.
        if (entityId != null) {
            tag.putString(CompoundTagId.ENTITY_ID, entityId.toString());
        }
    }
}
