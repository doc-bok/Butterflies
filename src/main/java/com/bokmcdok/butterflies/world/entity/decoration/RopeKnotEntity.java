package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A class to handle player interactions with the rope.
 */
public class RopeKnotEntity extends AbstractRopeEntity {
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
     * Ropes don't have a specific direction.
     * @param direction The direction to ignore.
     */
    @Override
    public void setDirection(@NotNull Direction direction) {
        // No-op
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
}