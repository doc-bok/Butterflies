package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A class to handle player interactions with the rope.
 */
public class RopeKnotEntity extends AbstractRopeEntity {

    protected static final EntityDataAccessor<Direction> DATA_DIRECTION =
            SynchedEntityData.defineId(RopeKnotEntity.class, EntityDataSerializers.DIRECTION);

    public static final String NAME = "rope_knot_entity";

    public static final double ROPE_KNOT_Y_OFFSET = 0.375d;
    public static final double ROPE_KNOT_XZ_OFFSET = 0.5d;

    /**
     * Construction.
     * @param entityType The entity type of the rope.
     * @param level The current level.
     */
    public RopeKnotEntity(EntityType<? extends RopeKnotEntity> entityType,
                          Level level) {
        super(entityType, level);
    }

    /**
     * Construction.
     * @param level The current level.
     * @param blockPos The position of the rope.
     */
    public RopeKnotEntity(Level level,
                          BlockPos blockPos) {
        super(EntityTypeRegistry.ROPE_KNOT.get(), level, blockPos);
    }

    /**
     * Calculate the bounding boxes.
     */
    @Override
    protected void recalculateBoundingBox() {
        this.setPosRaw(
                (double)this.pos.getX() + ROPE_KNOT_XZ_OFFSET,
                (double)this.pos.getY() + ROPE_KNOT_Y_OFFSET,
                (double)this.pos.getZ() + ROPE_KNOT_XZ_OFFSET);

        super.recalculateBoundingBox();
    }

    /**
     * Get the current direction of the rope.
     * @return The direction the rope knot is facing.
     */
    @NotNull
    @Override
    public Direction getDirection() {
        return entityData.get(DATA_DIRECTION);
    }

    /**
     * Set the direction on the entity data so that it is synced with the client.
     * @param direction The direction the rope is connected by.
     */
    @Override
    public void setDirection(@NotNull Direction direction) {
        super.setDirection(direction);
        entityData.set(DATA_DIRECTION, this.direction);
    }

    /**
     * Check if the rope survives.
     * @return True if it is attached to a fence.
     */
    @Override
    public boolean survives() {
        return this.level().getBlockState(this.pos).is(BlockTags.FENCES);
    }

    /**
     * Create a new knot.
     * @param level The current level.
     * @param blockPos The position to check.
     * @return A knot in the specified position.
     */
    @NotNull
    public static RopeKnotEntity createRopeKnot(Level level,
                                                BlockPos blockPos) {
        RopeKnotEntity newEntity = new RopeKnotEntity(level, blockPos);
        level.addFreshEntity(newEntity);
        return newEntity;
    }

    /**
     * Define data that is synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_DIRECTION, Direction.NORTH);
    }
}