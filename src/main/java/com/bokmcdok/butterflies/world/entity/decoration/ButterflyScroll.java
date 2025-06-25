package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An entity representing a hanging butterfly scroll.
 */
public class ButterflyScroll extends HangingEntity {

    private static final EntityDataAccessor<Integer> BUTTERFLY_INDEX = SynchedEntityData.defineId(
            ButterflyScroll.class, EntityDataSerializers.INT
    );

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
    public ButterflyScroll(EntityTypeRegistry entityTypeRegistry,
                           Level level,
                           BlockPos blockPos,
                           Direction direction) {
        this(entityTypeRegistry.getButterflyScroll().get(), level);

        this.pos = blockPos;
        this.setDirection(direction);
    }

    /**
     * Add extra data for this entity to a save.
     * @param tag The tag with the entity's save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte(CompoundTagId.FACING, (byte)this.direction.get3DDataValue());
        tag.putInt(CompoundTagId.CUSTOM_MODEL_DATA, getButterflyIndex());
    }

    /**
     * Drop a Butterfly Scroll when this gets destroyed
     * @param entity The entity being destroyed.
     */
    @Override
    public void dropItem(@Nullable Entity entity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(
                    ButterfliesMod.MOD_ID,
                    ButterflyScrollItem.getRegistryId(getButterflyIndex()));
            Item item = BuiltInRegistries.ITEM.get(location);
            ItemStack stack = new ItemStack(item);
            this.spawnAtLocation(stack);
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
    }

    /**
     * Read the entity's data from a save.
     * @param tag The tag loaded from the save data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setDirection(Direction.from3DDataValue(tag.getByte(CompoundTagId.FACING)));

        if (tag.contains(CompoundTagId.CUSTOM_MODEL_DATA)) {
            this.setButterflyIndex(tag.getInt(CompoundTagId.CUSTOM_MODEL_DATA));
        }
    }

    /**
     * Calculate the bounding box of the scroll.
     */
    @NotNull
    @Override
    @SuppressWarnings("ConstantConditions")
    protected AABB calculateBoundingBox(@NotNull BlockPos pos,
                                        @NotNull Direction direction) {

        //  Direction can actually be null here.
        if (this.direction != null) {

            double x = (double) this.pos.getX() + 0.5D - (double) this.direction.getStepX() * 0.46875D;
            double y = (double) this.pos.getY() + 0.5D - (double) this.direction.getStepY() * 0.46875D;
            double z = (double) this.pos.getZ() + 0.5D - (double) this.direction.getStepZ() * 0.46875D;
            this.setPosRaw(x, y, z);

            double width = this.getWidth();
            double height = this.getHeight();
            double breadth = this.getWidth();

            Direction.Axis axis = this.direction.getAxis();
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

        return null;
    }

    /**
     * Recreate the entity from a received packet.
     * @param packet The packet sent from the server.
     */
    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);

        int data = packet.getData();
        int direction = ((data >> 16) & 0xFFFF);
        this.setButterflyIndex(data & 0xFFFF);
        this.setDirection(Direction.from3DDataValue(direction));
    }

    /**
     * Set the butterfly index.
     * @param index The index of the butterfly.
     */
    public void setButterflyIndex(int index) {
        this.entityData.set(BUTTERFLY_INDEX, index);
    }

    /**
     * Set the direction and rotate the entity, so it faces the correct way.
     * @param direction The direction the entity is facing.
     */
    @Override
    protected void setDirection(@NotNull Direction direction) {
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
}
