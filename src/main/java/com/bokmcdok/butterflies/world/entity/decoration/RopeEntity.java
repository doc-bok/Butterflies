package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A class to handle hanging ropes.
 */
public class RopeEntity extends AbstractRopeEntity {
    public static final String NAME = "rope_entity";

    public static final double Y_CENTER_OFFSET = -0.375d;
    public static final double XZ_CENTER_OFFSET = 0.5d;
    public static final double ROPE_DIR_OFFSET = 0.2d;

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

            switch (this.direction) {
                case EAST -> x += ROPE_DIR_OFFSET;
                case WEST -> x -= ROPE_DIR_OFFSET;
                case NORTH -> z -= ROPE_DIR_OFFSET;
                case SOUTH -> z += ROPE_DIR_OFFSET;
            }

            this.setPosRaw(x, y, z);

            super.recalculateBoundingBox();
        }
    }

    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket entityPacket) {
        super.recreateFromPacket(entityPacket);
        this.setDirection(Direction.from3DDataValue(entityPacket.getData()));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("Facing", (byte)this.direction.get3DDataValue());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDirection(Direction.from3DDataValue(compoundTag.getByte("Facing")));
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

    @NotNull
    public static RopeEntity createRope(Level level,
                                        BlockPos blockPos,
                                        Direction direction) {
        RopeEntity newEntity = new RopeEntity(level, blockPos);
        newEntity.setDirection(direction);
        level.addFreshEntity(newEntity);
        return newEntity;
    }
}
