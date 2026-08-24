package com.bokmcdok.butterflies.world.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A class to handle player interactions with the rope.
 */
public class RopeKnotEntity extends HangingEntity {
    public static final String NAME = "rope_knot_entity";

    public static final int PIXEL_WIDTH = 9;
    public static final int PIXEL_HEIGHT = 9;
    public static final double SEARCH_RADIUS = 7.0d;
    public static final double RENDER_DISTANCE_SQR = 1024.0d;
    public static final double ROPE_HOLD_Y_OFFSET = 0.375D;

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
        this.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    /**
     * Calculate the bounding boxes.
     */
    @Override
    protected void recalculateBoundingBox() {
        this.setPosRaw((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + ROPE_HOLD_Y_OFFSET, (double)this.pos.getZ() + 0.5D);
        double halfWidth = (double)this.getType().getWidth() / 2.0D;
        double height = this.getType().getHeight();
        this.setBoundingBox(new AABB(
                this.getX() - halfWidth, this.getY(), this.getZ() - halfWidth,
                this.getX() + halfWidth, this.getY() + height, this.getZ() + halfWidth));
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
     * Get the width of the rope "block".
     * @return The current width.
     */
    @Override
    public int getWidth() {
        return PIXEL_WIDTH;
    }

    /**
     * Get the height of the rope "block".
     * @return The current width.
     */
    @Override
    public int getHeight() {
        return PIXEL_HEIGHT;
    }

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
     * Check if we should render the entity at this distance.
     * @param distance The distance to check.
     * @return True if we should render the entity.
     */
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < RENDER_DISTANCE_SQR;
    }

    /**
     * Respond to the item being dropped.
     * @param entity The entity dropping the item.
     */
    @Override
    public void dropItem(@Nullable Entity entity) {
        this.playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0F, 1.0F);
    }

    /**
     * We don't need extra save data here.
     * @param compoundTag The tag containing the save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        // No-op
    }

    /**
     * We don't need extra save data here.
     * @param compoundTag The tag containing the save data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        // No-op
    }

    /**
     * Handle players interacting with the rope.
     * @param player The player interacting with the rope.
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

        List<Mob> nearbyMobs = this.getNearbyMobs();
        if (this.attachPlayerLeashedMobs(player, nearbyMobs)
                || this.removeKnotIfUnused(player, nearbyMobs)) {
            this.gameEvent(GameEvent.BLOCK_ATTACH, player);
        }

        return InteractionResult.CONSUME;
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
     * Create a new knot, or get one that is already there.
     * @param level The current level.
     * @param blockPos The position to check.
     * @return A knot in the specified position.
     */
    public static RopeKnotEntity getOrCreateKnot(Level level,
                                                 BlockPos blockPos) {
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();

        for(RopeKnotEntity existingEntity : level.getEntitiesOfClass(RopeKnotEntity.class, new AABB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
            if (existingEntity.getPos().equals(blockPos)) {
                return existingEntity;
            }
        }

        RopeKnotEntity newEntity = new RopeKnotEntity(level, blockPos);
        level.addFreshEntity(newEntity);
        return newEntity;
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
     * Find any nearby mobs.
     * @return A list of mobs.
     */
    private List<Mob> getNearbyMobs() {
        AABB searchBox = new AABB(
                this.getX() - SEARCH_RADIUS, this.getY() - SEARCH_RADIUS, this.getZ() - SEARCH_RADIUS,
                this.getX() + SEARCH_RADIUS, this.getY() + SEARCH_RADIUS, this.getZ() + SEARCH_RADIUS
        );

        return this.level().getEntitiesOfClass(Mob.class, searchBox);
    }

    /**
     * Attach any mobs leashed by the player.
     * @param player The current player.
     * @param nearbyMobs A list of nearby mobs.
     * @return True if any mobs are attached.
     */
    private boolean attachPlayerLeashedMobs(@NotNull Player player,
                                            @NotNull List<Mob> nearbyMobs) {
        boolean attachedAny = false;

        for (Mob mob : nearbyMobs) {
            if (mob.getLeashHolder() == player) {
                mob.setLeashedTo(this, true);
                attachedAny = true;
            }
        }

        return attachedAny;
    }

    /**
     * Remove any unused knots.
     * @param player The current player.
     * @param nearbyMobs A list of nearby mobs.
     * @return True if any knots are removed.
     */
    private boolean removeKnotIfUnused(@NotNull Player player,
                                       @NotNull List<Mob> nearbyMobs) {

        this.discard();

        if (!player.getAbilities().instabuild) {
            return false;
        }

        return this.dropCreativeLeashesFromKnot(nearbyMobs);
    }

    /**
     * Drops leashes from any nearby mobs.
     * @param nearbyMobs A list of nearby mobs.
     * @return True if any knots are removed.
     */
    private boolean dropCreativeLeashesFromKnot(@NotNull List<Mob> nearbyMobs) {
        boolean detachedAny = false;

        for (Mob mob : nearbyMobs) {
            if (mob.isLeashed() && mob.getLeashHolder() == this) {
                mob.dropLeash(true, false);
                detachedAny = true;
            }
        }

        return detachedAny;
    }
}