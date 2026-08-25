package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * A class to handle hanging ropes.
 */
public class RopeEntity extends AbstractRopeEntity {
    public static final String NAME = "rope_entity";

    private static final EntityDataAccessor<Boolean> DATA_ON_FULL_BLOCK =
            SynchedEntityData.defineId(RopeEntity.class, EntityDataSerializers.BOOLEAN);

    public static final double Y_CENTER_OFFSET = -0.375d;
    public static final double XZ_CENTER_OFFSET = 0.5d;
    public static final double DIRECTION_OFFSET = 0.2d;
    public static final double DIRECTION_OFFSET_FULL_BLOCK = 0.6d;

    /**
     * Construction.
     * @param entityType The entity type of the rope.
     * @param level The current level.
     */
    public RopeEntity(EntityType<? extends RopeEntity> entityType,
                      Level level) {
        super(entityType, level);
    }

    /**
     * Construction.
     * @param level The current level.
     * @param blockPos The position of the rope.
     */
    public RopeEntity(Level level,
                      BlockPos blockPos) {
        super(EntityTypeRegistry.ROPE.get(), level, blockPos);
    }

    /**
     * Calculate the bounding boxes.
     */
    @Override
    protected void recalculateBoundingBox() {
        // This is called during the constructor so direction can actually be
        // null as the object is only partially initialized.
        if (this.direction != null) {
            double x = (double) this.pos.getX() + XZ_CENTER_OFFSET;
            double y = (double) this.pos.getY() - Y_CENTER_OFFSET;
            double z = (double) this.pos.getZ() + XZ_CENTER_OFFSET;

            double offset = this.getOnFullBlock() ?
                    DIRECTION_OFFSET_FULL_BLOCK : DIRECTION_OFFSET;

            switch (this.direction) {
                case EAST -> x += offset;
                case WEST -> x -= offset;
                case NORTH -> z -= offset;
                case SOUTH -> z += offset;
            }

            this.setPosRaw(x, y, z);

            super.recalculateBoundingBox();
        }
    }

    /**
     * Gets extra information to send to clients when creating this entity.
     * @return A new client-bound packet.
     */
    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    /**
     * Recreate an entity from a server packet.
     * @param entityPacket The entity packet holding entity data.
     */
    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket entityPacket) {
        super.recreateFromPacket(entityPacket);
        this.setDirection(Direction.from3DDataValue(entityPacket.getData()));
    }

    /**
     * Set up any additional save data.
     * @param compoundTag The compound tag containing the data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("Facing", (byte)this.direction.get3DDataValue());
        compoundTag.putBoolean("OnFullBlock", this.getOnFullBlock());
    }

    /**
     * Read any additional save data.
     * @param compoundTag The compound tag containing the data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDirection(Direction.from3DDataValue(compoundTag.getByte("Facing")));
        this.setOnFullBlock(compoundTag.getBoolean("OnFullBlock"), false);
    }

    /**
     * Define data that is synced between server and client.
     */
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ON_FULL_BLOCK, false);
    }

    /**
     * Recalculate the bounding box if ON_FULL_BLOCK is updated.
     * @param dataAccessor The data accessor being updated.
     */
    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);
        if (DATA_ON_FULL_BLOCK.equals(dataAccessor)) {
            recalculateBoundingBox();
        }
    }

    /**
     * Get whether this rope is on a full block.
     * @return True if the rope is on a full block.
     */
    public boolean getOnFullBlock() {
        return this.getEntityData().get(DATA_ON_FULL_BLOCK);
    }

    /**
     * Set this rope to being on a full block.
     * @param value The value to set.
     */
    public void setOnFullBlock(boolean value) {
        this.setOnFullBlock(value, true);
    }

    /**
     * Set this rope to being on a full block.
     * @param value The value to set.
     * @param isServer True if the value is sent from a server context.
     */
    @SuppressWarnings("boxing")
    private void setOnFullBlock(boolean value,
                                boolean isServer) {
        this.getEntityData().set(DATA_ON_FULL_BLOCK, value);

        // This is called during the constructor so direction can actually be
        // null as the object is only partially initialized.
        if (isServer && this.pos != null) {
            this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }

    }

    /**
     * Check if the rope survives. It survives if it is attached to a rope
     * or rope knot above it.
     * @return True if it is attached to a fence.
     */
    @Override
    public boolean survives() {
        return AbstractRopeEntity.tryGetAbstractRope(this.level(), this.pos.above()).isPresent();
    }

    /**
     * Create a new rope.
     * @param level The current level.
     * @param blockPos The position of the rope.
     * @param direction The direction the rope is facing.
     * @param onFullBlock True if the rope is on a full block.
     * @return A new rope entity.
     */
    @NotNull
    public static RopeEntity createRope(Level level,
                                        BlockPos blockPos,
                                        Direction direction,
                                        boolean onFullBlock) {
        RopeEntity newEntity = new RopeEntity(level, blockPos);
        newEntity.setOnFullBlock(onFullBlock);
        newEntity.setDirection(direction);
        level.addFreshEntity(newEntity);
        return newEntity;
    }
}
