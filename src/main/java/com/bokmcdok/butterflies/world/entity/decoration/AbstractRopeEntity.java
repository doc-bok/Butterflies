package com.bokmcdok.butterflies.world.entity.decoration;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Base class for rope entities.
 */
public abstract class AbstractRopeEntity extends HangingEntity {

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
     * Calculate the bounding boxes.
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

        Direction direction = player.getDirection().getOpposite();
        boolean onFullBlock = false;

        if (this instanceof RopeEntity rope) {
            direction = rope.direction;
            onFullBlock = rope.getOnFullBlock();
        }

        if (tryAttachRope(direction, onFullBlock)) {
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
        Optional<AbstractRopeEntity> abstractRopeEntity = tryGetAbstractRope(this.level(), this.pos.below());
        if (abstractRopeEntity.isPresent() && abstractRopeEntity.get() instanceof RopeEntity rope) {
            rope.hurt(damageSource, amount);
        }
        
        return super.hurt(damageSource, amount);
    }

    /**
     * Try and attach a rope to the block below this entity.
     * @return True if a rope was attached.
     */
    public boolean tryAttachRope(Direction direction,
                                 boolean onFullBlock) {
        BlockPos below = this.pos.below();

        // Check for solid blocks.
        BlockState blockState = this.level().getBlockState(below);
        if (!blockState.isAir() && !blockState.is(BlockTags.FENCES) && !blockState.is(BlockTags.WALLS)) {
            if (this.level().getBlockState(below.relative(direction)).isAir()) {
                onFullBlock = true;
            } else {
            return false;
            }
        }

        // We can keep attaching rope from the top.
        Optional<AbstractRopeEntity> rope = tryGetAbstractRope(this.level(), this.pos.below());
        if (rope.isPresent()) {
            return rope.get().tryAttachRope(direction, onFullBlock);
        }

        // Attach a new rope entity.
        RopeEntity newRope = RopeEntity.createRope(this.level(), below, direction, onFullBlock);
        newRope.playPlacementSound();
        return true;
    }

    /**
     * Create a new knot, or get one that is already there.
     * @param level The current level.
     * @param blockPos The position to check.
     * @return A knot in the specified position.
     */
    public static Optional<AbstractRopeEntity> tryGetAbstractRope(Level level,
                                                                  BlockPos blockPos) {
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();

        for(AbstractRopeEntity existingEntity : level.getEntitiesOfClass(AbstractRopeEntity.class, new AABB(
                (double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D,
                (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
            if (existingEntity.getPos().equals(blockPos)) {
                return Optional.of(existingEntity);
            }
        }

        return Optional.empty();
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
}
