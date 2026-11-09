package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Base class for rope entities.
 */
public abstract class AbstractRopeEntity extends HangingEntity {

    // Cache the block state below to avoid too many updates.
    @Nullable
    private BlockState cachedBelowState;

    @Nullable
    private AbstractRopeEntity child;

    protected static final EntityDataAccessor<Boolean> DATA_TRANSITIONING =
            SynchedEntityData.defineId(AbstractRopeEntity.class, EntityDataSerializers.BOOLEAN);

    public static final double RENDER_DISTANCE_SQR = 1024.0d;
    public static final int PIXEL_WIDTH = 9;
    public static final int PIXEL_HEIGHT = 9;

    /**
     * Construction.
     * @param entityType The entity type of the rope.
     * @param level The current level.
     */
    public AbstractRopeEntity(EntityType<? extends AbstractRopeEntity> entityType,
                              Level level) {
        super(entityType, level);
    }

    /**
     * Construction.
     * @param level The current level.
     * @param blockPos The position of the rope.
     */
    public AbstractRopeEntity(EntityType<? extends AbstractRopeEntity> entityType,
                              Level level,
                              BlockPos blockPos) {
        super(entityType, level, blockPos);
        this.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    /**
     * Re-calculate the bounding box.
     */
    @Override
    protected void recalculateBoundingBox() {
        double halfWidth = (double)this.getType().getWidth() / 2.0D;
        double height = this.getType().getHeight();
        this.setBoundingBox(new AABB(
                this.getX() - halfWidth, this.getY(), this.getZ() - halfWidth,
                this.getX() + halfWidth, this.getY() + height, this.getZ() + halfWidth));
    }

    /**
     * Get the width of the rope "block".
     * @return The current width.
     */
    @Override
    public int getWidth() {
        return PIXEL_WIDTH;
    }

    /**
     * Get the height of the rope "block".
     * @return The current height.
     */
    @Override
    public int getHeight() {
        return PIXEL_HEIGHT;
    }

    /**
     * Ropes don't have a specific direction.
     * @param direction The direction to ignore.
     */
    @Override
    public void setDirection(@NotNull Direction direction) {
        super.setDirection(direction);
    }

    /**
     * Check if we should render the entity at this distance.
     * @param distance The distance to check.
     * @return True if we should render the entity.
     */
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < RENDER_DISTANCE_SQR;
    }

    /**
     * Drop a rope item when a rope knot is destroyed.
     * @param entity The entity dropping the item.
     */
    @Override
    public void dropItem(@Nullable Entity entity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0F, 1.0F);
            if (entity instanceof Player player) {
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            this.spawnAtLocation(ItemRegistry.ROPE.get());
        }
    }

    /**
     * Handle players interacting with the rope knot.
     * @param player The player interacting with the rope knot.
     * @param interactionHand The hand they are using to interact.
     * @return The result of the interaction.
     */
    @NotNull
    @Override
    public InteractionResult interact(@NotNull Player player,
                                      @NotNull InteractionHand interactionHand) {
        if (this.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack held = player.getItemInHand(interactionHand);
        if (!held.is(ItemRegistry.ROPE.get())) {
            return InteractionResult.PASS;
        }

        Direction direction = getAttachmentDirection(player);

        if (tryAttachRope(direction)) {
            player.getItemInHand(interactionHand).shrink(1);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    /**
     * When hurt, also hurt any rope attached to this one.
     * @param damageSource The source of the damage.
     * @param amount The amount of damage.
     * @return True if damage is inflicted.
     */
    @Override
    public boolean hurt(@NotNull DamageSource damageSource,
                        float amount) {
        boolean damaged = super.hurt(damageSource, amount);

        if (damaged) {

            // Destroy any ropes below.
            AbstractRopeEntity belowRopeEntity = getChild();
            if (belowRopeEntity instanceof RopeEntity rope) {
                rope.hurt(damageSource, amount);
            }

            // Event handler.
            onRemovedFromLevel();
        }
        
        return damaged;
    }

    /**
     * Try and attach a rope to the block below this entity.
     * @return True if a rope was attached.
     */
    public boolean tryAttachRope(Direction direction) {
        BlockPos below = this.pos.below();

        // Check for solid blocks.
        BlockState blockState = this.level().getBlockState(below);
        if (!blockState.isAir() && !blockState.is(BlockTags.FENCES) && !blockState.is(BlockTags.WALLS)) {
            if (!this.level().getBlockState(below.relative(direction)).isAir()) {
                return false;
            }
        }

        // We can keep attaching rope from the top.
        AbstractRopeEntity rope = getChild();
        if (rope != null) {
            return rope.tryAttachRope(direction);
        }

        // Attach a new rope entity.
        RopeEntity newRope = RopeEntity.createRope(this.level(), this, below, direction);
        newRope.onAddedToLevel();
        newRope.playPlacementSound();
        return true;
    }

    /**
     * Create a new knot, or get one that is already there.
     * @param level The current level.
     * @param pos The position to check.
     * @return A knot in the specified position.
     */
    public static AbstractRopeEntity getRopeAt(LevelAccessor level,
                                               BlockPos pos) {
        AABB searchBox = new AABB(
                pos.getX() - 1.0D,
                pos.getY(),
                pos.getZ() - 1.0D,

                pos.getX() + 2.0D,
                pos.getY() + 1.0D,
                pos.getZ() + 2.0D);

        for (AbstractRopeEntity rope : level.getEntitiesOfClass(AbstractRopeEntity.class, searchBox)) {
            if (rope.getPos().equals(pos)) {
                return rope;
            }
        }

        return null;
    }

    /**
     * Play a sound when the entity is placed.
     */
    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.LEASH_KNOT_PLACE, 1.0F, 1.0F);
    }

    /**
     * Add a packet containing the entity's position.
     * @return A new packet for the entity.
     */
    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, 0, this.getPos());
    }

    /**
     * Get the position the rope is being held at.
     * @param v ???
     * @return The rope hold position.
     */
    @NotNull
    @Override
    public Vec3 getRopeHoldPosition(float v) {
        return this.getPosition(v).add(0.0D, 0.2D, 0.0D);
    }

    /**
     * Get the item the entity drops.
     * @return The Rope item.
     */
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ItemRegistry.ROPE.get());
    }

    /**
     * Get whether this rope is on a full block.
     * @return True if the rope is on a full block.
     */
    public boolean isTransitioning() {
        return this.getEntityData().get(DATA_TRANSITIONING);
    }

    /**
     * Update the rope's connection state.
     */
    public void updateBlockConnections() {
        if (this.level().isClientSide) {
            return;
        }

        boolean hasRopeBelow = this.hasRopeEntityBelow();
        boolean hasSolidSupport = this.hasSolidSupportBelow();

        boolean connected = hasRopeBelow && hasSolidSupport;

        this.getEntityData().set(DATA_TRANSITIONING, connected);
    }

    /**
     * Check for any below block state changes and update the connections if necessary.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }

        // Only update on block state changes.
        BlockState belowState = this.level().getBlockState(this.pos.below());
        if (belowState != this.cachedBelowState) {
            this.cachedBelowState = belowState;
            this.updateBlockConnections();
        }
    }

    /**
     * Set up any additional save data.
     * @param compoundTag The compound tag containing the data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Transitioning", this.isTransitioning());
    }

    /**
     * Read any additional save data.
     * @param compoundTag The compound tag containing the data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.getEntityData().set(DATA_TRANSITIONING, compoundTag.getBoolean("Transitioning"));
    }

    /**
     * Get the direction to attach a rope.
     * @param player The player attempting to attach a rope.
     * @return The direction to attach.
     */
    protected abstract Direction getAttachmentDirection(Player player);

    /**
     * Get the "eye height" of the entity.
     * @param pose The current pose.
     * @param entityDimensions The dimensions of the entity.
     * @return The entity's "eye height"
     */
    @Override
    protected float getEyeHeight(@NotNull Pose pose,
                                 @NotNull EntityDimensions entityDimensions) {
        return 0.0625F;
    }

    /**
     * Define data that is synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_TRANSITIONING, false);
    }

    /**
     * Check if there is a rope entity below this one.
     * @return TRUE if there is a rope entity below.
     */
    protected boolean hasRopeEntityBelow() {
        return getChild() instanceof RopeEntity;
    }

    /**
     * Check if there is a solid block below this entity.
     * @return TRUE if the block below is solid.
     */
    protected boolean hasSolidSupportBelow() {
        BlockState state = this.level().getBlockState(this.pos.below());

        return !state.isAir()
                && !state.is(BlockTags.FENCES)
                && !state.is(BlockTags.WALLS);
    }

    /**
     * Called whenever a rope entity is added to the level.
     */
    protected void onAddedToLevel() {}

    /**
     * Called whenever a rope entity is removed from the level.
     */
    protected void onRemovedFromLevel() {}

    /**
     * Update the child on the entity.
     * @param newChild The new child, which can be NULL.
     */
    protected void setChild(@Nullable AbstractRopeEntity newChild) {
        this.child = newChild;
    }

    /**
     * Get the child rope, if any.
     * @return The rope below this one, otherwise NULL.
     */
    protected AbstractRopeEntity getChild() {
        if (child == null) {
            child = getRopeAt(this.level(), this.getPos().below());
        }

        return child;
    }
}
