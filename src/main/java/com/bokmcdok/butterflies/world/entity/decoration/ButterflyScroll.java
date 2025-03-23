package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An entity representing a hanging butterfly scroll.
 */
public class ButterflyScroll extends HangingEntity {

    private static final EntityDataAccessor<Integer> BUTTERFLY_INDEX;
    private static final EntityDataAccessor<Direction> DATA_DIRECTION;

    // Reference to the item registry.
    private ItemRegistry itemRegistry;

    // The name used for registration.
    public static final String NAME = "butterfly_scroll";

    /**
     * Create method, used to register the entity.
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A new Butterfly Scroll Entity.
     */
    @NotNull
    public static ButterflyScroll create(EntityType<? extends ButterflyScroll> entityType, Level level) {
        return new ButterflyScroll(entityType, level);
    }

    /**
     * Create a Butterfly Scroll Entity.
     * @param level The current level.
     * @param blockPos The position of the block it is being placed upon.
     * @param direction The direction the scroll is facing.
     */
    public ButterflyScroll(@NotNull EntityTypeRegistry entityTypeRegistry,
                           @NotNull ItemRegistry itemRegistry,
                           Level level,
                           BlockPos blockPos,
                           Direction direction) {
        this(entityTypeRegistry.getButterflyScroll().get(), level);

        this.itemRegistry = itemRegistry;
        this.pos = blockPos;
        this.setDirection(direction);
    }

    /**
     * Drop a Butterfly Scroll when this gets destroyed
     * @param entity The entity being destroyed.
     */
    @Override
    public void dropItem(@NotNull ServerLevel level,
                         @Nullable Entity entity) {
        if (itemRegistry != null) {
            ItemStack stack = new ItemStack(itemRegistry.getButterflyScrolls().get(getButterflyIndex()).get());
            this.spawnAtLocation(level, stack);
        }
    }

    /**
     * Get the index of the butterfly.
     * @return The butterfly index.
     */
    public int getButterflyIndex() {
        return this.entityData.get(BUTTERFLY_INDEX);
    }

    /**
     * Get the height of the scroll.
     * @return The height of the scroll.
     */
    public int getHeight() {
        return 14;
    }

    /**
     * Get the width of the scroll.
     * @return The width of the scroll.
     */
    public int getWidth() {
        return 10;
    }

    /**
     * Play the sound for placing a scroll.
     */
    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.ITEM_FRAME_PLACE, 1.0F, 1.0F);
    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        builder.define(BUTTERFLY_INDEX, 0);
        builder.define(DATA_DIRECTION, Direction.NORTH);
    }

    /**
     * Calculate the bounding box of the scroll.
     */
    @NotNull
    @Override
    @SuppressWarnings("ConstantConditions")
    protected AABB calculateBoundingBox(@NotNull BlockPos pos,
                                        @NotNull Direction direction) {

        direction = getDirection();

        double x = (double) this.pos.getX() + 0.5D - (double) direction.getStepX() * 0.46875D;
        double y = (double) this.pos.getY() + 0.5D - (double) direction.getStepY() * 0.46875D;
        double z = (double) this.pos.getZ() + 0.5D - (double) direction.getStepZ() * 0.46875D;
        this.setPosRaw(x, y, z);

        double width = this.getWidth();
        double height = this.getHeight();
        double breadth = this.getWidth();

        Direction.Axis axis = direction.getAxis();
        switch (axis) {
            case X -> width = 1.0D;
            case Y -> height = 1.0D;
            case Z -> breadth = 1.0D;
            default -> {
            }
        }

        width /= 32.0D;
        height /= 32.0D;
        breadth /= 32.0D;

        return new AABB(x - width, y - height, z - breadth, x + width, y + height, z + breadth);
    }

    /**
     * Set the butterfly index.
     * @param index The index of the butterfly.
     */
    public void setButterflyIndex(int index) {
        this.entityData.set(BUTTERFLY_INDEX, index);
    }

    @Override
    @NotNull
    public Direction getDirection() {
        return this.getEntityData().get(DATA_DIRECTION);
    }

    /**
     * Set the direction and rotate the entity, so it faces the correct way.
     * @param direction The direction the entity is facing.
     */
    @Override
    protected void setDirection(@NotNull Direction direction) {
        this.getEntityData().set(DATA_DIRECTION, direction);
        this.direction = direction;

        this.setXRot(0.0F);
        this.setYRot((float)(this.direction.get2DDataValue() * 90));

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();

        this.recalculateBoundingBox();
    }

    /**
     * Default constructor.
     * @param entityType The type of the entity.
     * @param level The current level.
     */
    private ButterflyScroll(EntityType<? extends ButterflyScroll> entityType, Level level) {
        super(entityType, level);
    }

    static {
        BUTTERFLY_INDEX = SynchedEntityData.defineId(ButterflyScroll.class, EntityDataSerializers.INT);
        DATA_DIRECTION = SynchedEntityData.defineId(ButterflyScroll.class, EntityDataSerializers.DIRECTION);
    }
}
