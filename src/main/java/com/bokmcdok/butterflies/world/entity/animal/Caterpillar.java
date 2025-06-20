package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.DebugInfoSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Creates the Caterpillar behaviour.
 */
public class Caterpillar extends DirectionalCreature implements DebugInfoSupplier {

    //  The name this block is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return ButterflyInfo.SPECIES[butterflyIndex] + "_caterpillar";
    }

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Boolean> DATA_IS_BOTTLED =
            SynchedEntityData.defineId(Caterpillar.class, EntityDataSerializers.BOOLEAN);

    // Names of the attributes stored in the save data.
    protected static final String IS_BOTTLED = "is_bottled";

    // Helper constant to modify speed
    private static final double CATERPILLAR_SPEED = 0.00325d;

    // The current position the caterpillar is moving toward.
    @Nullable
    private Vec3 targetPosition;

    // Whether gravity is being applied.
    private boolean isNoGravity = true;

    /**
     * Spawns a caterpillar at the specified position.
     * @param level     The current level.
     * @param location  The resource location of the caterpillar to spawn.
     * @param position  The position to spawn the caterpillar.
     * @param direction The direction of "up" for the caterpillar.
     */
    public static void spawn(ServerLevel level,
                             ResourceLocation location,
                             BlockPos position,
                             Direction direction,
                             boolean isBottled) {
        Optional<Holder.Reference<EntityType<?>>> entityType = BuiltInRegistries.ENTITY_TYPE.get(location);

        if (entityType.isPresent()) {
            Entity entity = entityType.get().value().create(level, EntitySpawnReason.NATURAL);
            if (entity instanceof Caterpillar caterpillar) {
                caterpillar.setIsBottled(isBottled);

                double x = position.getX() + 0.45D;
                double y = position.getY() + 0.4D;
                double z = position.getZ() + 0.5D;

                if (isBottled) {
                    direction = Direction.DOWN;
                    y = position.getY() + 0.07d;

                    caterpillar.setInvulnerable(true);
                    caterpillar.setPersistenceRequired();
                } else {
                    switch (direction) {
                        case DOWN -> y = position.getY();
                        case UP -> y = position.getY() + 1.0d;
                        case NORTH -> z = position.getZ();
                        case SOUTH -> z = position.getZ() + 1.0d;
                        case WEST -> x = position.getX();
                        case EAST -> x = position.getX() + 1.0d;
                    }
                }

                caterpillar.moveTo(x, y, z, 0.0F, 0.0F);
                caterpillar.setSurfaceDirection(direction);

                caterpillar.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(position),
                        EntitySpawnReason.NATURAL,
                        null);

                level.addFreshEntity(caterpillar);
            }
        }
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(IS_BOTTLED, this.entityData.get(DATA_IS_BOTTLED));
    }

    /**
     * Reduce the size of the caterpillar - they are small!
     * @return The new size of the caterpillar.
     */
    public float getRenderScale() {
        float scale = (float) getAge() / -24000.0f;
        scale = scale * 0.04f;
        scale = scale + 0.08f;
        return scale * getData().getSizeMultiplier();
    }

    /**
     * Play a sound when a player hits the entity.
     * @param damageSource The source of the damage.
     * @return The result from hurting the entity.
     */
    @Override
    public boolean hurtClient(@NotNull DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player) {
            player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);
            return true;
        }

        return super.hurtClient(damageSource);
    }

    /**
     * If a player hits the entity it will be converted to an item in their inventory.
     * @param damageSource The source of the damage.
     * @param damage The amount of damage.
     * @return The result from hurting the entity.
     */
    @Override
    public boolean hurtServer(@NotNull ServerLevel level,
                              @NotNull DamageSource damageSource,
                              float damage) {
        if (damageSource.getEntity() instanceof Player player) {
            this.remove(RemovalReason.DISCARDED);

            ResourceLocation location = this.getData().getCaterpillarItem();
            if (location != null) {
                Optional<Holder.Reference<Item>> caterpillarItem = BuiltInRegistries.ITEM.get(location);
                if (caterpillarItem.isPresent()) {
                    ItemStack itemStack = new ItemStack(caterpillarItem.get());
                    player.addItem(itemStack);
                }
            }

            return true;
        }

        return super.hurtServer(level, damageSource, damage);
    }

    /**
     * Caterpillars can't be fed by players.
     * @param stack The item stack the player tried to feed the caterpillar.
     * @return FALSE, indicating it isn't food.
     */
    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    /**
     * Overrides how an entity handles triggers such as tripwires and pressure
     * plates. Caterpillars aren't heavy enough to trigger either.
     *
     * @return Always TRUE, so caterpillars ignore block triggers.
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    /**
     * Only apply gravity if the caterpillar isn't attached to a block.
     */
    @Override
    public boolean isNoGravity() {
        return this.isNoGravity;
    }

    /**
     * Override this to control if an entity can be pushed or not. Caterpillars
     * can't be pushed by other entities.
     *
     * @return Always FALSE, so caterpillars cannot be pushed.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * Override to read any additional save data.
     * @param tag The tag containing the entity's save data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        // Get the bottle state
        if (tag.contains(IS_BOTTLED)) {
            this.entityData.set(DATA_IS_BOTTLED, tag.getBoolean(IS_BOTTLED));
        }
    }

    /**
     * Override so that the bounding box isn't recalculated for "babies".
     *
     * @param age The age of the entity.
     */
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Create a caterpillar entity.
     * @param entityType The entity type.
     * @param level      The level we are creating the entity in.
     */
    public Caterpillar(EntityType<? extends Caterpillar> entityType,
                          Level level) {
        super(entityType, level);

        setTexture("textures/entity/caterpillar/caterpillar_" + ButterflyData.getSpeciesString(this) + ".png");
        setAge(-getData().caterpillarLifespan());
    }

    /**
     * Prevent a caterpillar's block position from changing.
     * @param x The x-position.
     * @param y The y-position.
     * @param z The z-position.
     */
    @Override
    public void setPos(double x, double y, double z) {

        // Set the initial position.
        if (this.getX() == 0 && this.getY() == 0 && this.getZ() == 0) {
            super.setPos(x, y, z);
        }

        // Only set the position if the block position doesn't change.
        if (    Mth.floor(x) == this.blockPosition().getX() &&
                Mth.floor(y) == this.blockPosition().getY() &&
                Mth.floor(z) == this.blockPosition().getZ()) {
            super.setPos(x, y, z);
        }
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    @SuppressWarnings("deprecation")
    protected void customServerAiStep(@NotNull ServerLevel level) {
        super.customServerAiStep(level);

        // Update gravity
        isNoGravity = true;

        if (this.getIsReleased()) {
            BlockPos surfaceBlockPos = this.getSurfaceBlockPos();
            if (this.level().hasChunkAt(surfaceBlockPos)) {

                // If the surface block is empty then we try to look for one below.
                if (this.level().isEmptyBlock(surfaceBlockPos)) {
                    setSurfaceDirection(Direction.DOWN);
                }

                // Caterpillars will only fall if their surface direction is
                // down.
                if (getSurfaceDirection() == Direction.DOWN) {

                    // If the surface block is still empty, or the caterpillar is
                    // too far above the surface block, then it should fall.
                    if (this.level().isEmptyBlock(surfaceBlockPos)
                            || this.position().y() - (double) this.blockPosition().getY() > 0.01) {
                        this.targetPosition = null;
                        isNoGravity = false;
                    }
                }
            }
        }

        // If the caterpillar is falling then it can't crawl.
        if (this.isNoGravity()) {

            Direction direction = this.getSurfaceDirection();
            Direction.Axis axis = direction.getAxis();

            // Set a new target position if:
            //  1. We don't have one already
            //  2. After a 1/30 random chance
            //  3. We get too close to the current target position
            if (this.targetPosition == null ||
                    this.targetPosition.distanceToSqr(this.position()) < 0.007d ||
                    this.random.nextInt(30) == 0) {

                if (this.targetPosition == null) {
                    this.targetPosition = this.position();
                }

                if (axis == Direction.Axis.X) {
                    this.targetPosition = new Vec3(
                            this.targetPosition.x(),
                            Math.floor(this.targetPosition.y()) + clampedRandomDouble(),
                            Math.floor(this.targetPosition.z()) + clampedRandomDouble());
                } else if (axis == Direction.Axis.Y) {
                    this.targetPosition = new Vec3(
                            Math.floor(this.targetPosition.x()) + clampedRandomDouble(),
                            this.targetPosition.y(),
                            Math.floor(this.targetPosition.z()) + clampedRandomDouble());

                } else {
                    this.targetPosition = new Vec3(
                            Math.floor(this.targetPosition.x()) + clampedRandomDouble(),
                            Math.floor(this.targetPosition.y()) + clampedRandomDouble(),
                            this.targetPosition.z());
                }
            }

            Vec3 updatedDeltaMovement = getUpdatedDeltaMovement(axis);
            this.setDeltaMovement(updatedDeltaMovement);

            this.zza = 0.5f;

            // Calculate the rotational velocity.
            double rotationDelta = getRotationDelta(direction, updatedDeltaMovement);
            this.setYRot(this.getYRot() + (float) rotationDelta);

            // Spawn Chrysalis.
            if (this.getIsReleased()
                    && this.getAge() >= 0
                    && this.random.nextInt(0, 15) == 0) {
                BlockPos surfaceBlockPos = this.getSurfaceBlockPos();

                // If the caterpillar is not on a valid block it will starve instead.
                if (getData().isValidLandingBlock(level().getBlockState(surfaceBlockPos))) {
                    ResourceLocation newLocation = this.getData().getChrysalisEntity();
                    Chrysalis.spawn((ServerLevel) this.level(),
                            newLocation,
                            this.getSurfaceBlockPos(),
                            this.getSurfaceDirection(),
                            this.position(),
                            this.getYRot());
                    this.remove(RemovalReason.DISCARDED);
                } else {
                    this.hurt(this.damageSources().starve(), 1.0f);
                }
            }
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_BOTTLED, false);
    }

    /**
     * Override to change how pushing other entities affects them. Caterpillars
     * don't push other entities.
     * @param otherEntity The other entity pushing/being pushed.
     */
    @Override
    protected void doPush(@NotNull Entity otherEntity) {
        // No-op
    }

    /**
     * Return an ambient sound for the caterpillar. If the sound doesn't exist
     * it just won't play.
     * @return A reference to the ambient sound.
     */
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (getData().caterpillarSounds()) {
            return SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, ButterflyData.getSpeciesString(this)));
        }

        return super.getAmbientSound();
    }

    /**
     * Get the current state, used for debugging.
     * @return The current goal state.
     */
    @Override
    public String getDebugInfo() {
        return "Position = [" +
                String.format("%.3f", this.getX() % 1.f) + ", " +
                String.format("%.3f", this.getY() % 1.f) + ", " +
                String.format("%.3f", this.getZ() % 1.f) +
                "] / BlockPos = [" + this.blockPosition() +
                "] / SurfaceBlock = [" + this.getSurfaceBlockPos() +
                "] / SurfaceDirection = [" + this.getSurfaceDirection().getName() +
                "] / IsNoGravity = [" + this.isNoGravity +
                "]";
    }

    /**
     * Check if the caterpillar is in a bottle or not.
     * @return TRUE if the caterpillar is free.
     */
    @Override
    protected boolean getIsReleased() {
        return !entityData.get(DATA_IS_BOTTLED);
    }

    /**
     * Override to control what kind of movement events the entity will emit.
     * Caterpillars will not emit sounds.
     * @return Movement events only.
     */
    @NotNull
    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    /**
     * Override to change how pushing other entities affects them. Caterpillars
     * don't push other entities.
     */
    @Override
    protected void pushEntities() {
        // No-op
    }

    /**
     * Helper method to return a clamped double.
     * @return A double value between 0.05 and 0.95.
     */
    private double clampedDouble(double x) {
        return Math.max(0.05, Math.min(0.95, x));
    }

    /**
     * Helper method to generate a clamped random double.
     * @return A random clamped double.
     */
    private double clampedRandomDouble() {
        return clampedDouble(this.random.nextDouble() % 1.0);
    }

    /**
     * Get this frame's change in orientation
     * @param direction The direction of the "ground" for the caterpillar
     * @param updatedDeltaMovement This frame's updated movement delta.
     * @return The rotational velocity of the caterpillar.
     */
    private double getRotationDelta(Direction direction, Vec3 updatedDeltaMovement) {
        double updatedRotation;
        if (direction == Direction.DOWN) {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.z,
                            updatedDeltaMovement.x)
                            * (180.0d / Math.PI)) - 90.0d;
        } else if (direction == Direction.UP) {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.x(),
                            updatedDeltaMovement.z())
                            * (180.0d / Math.PI)) - 180.0d;
        } else if (direction == Direction.NORTH) {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.x(),
                            updatedDeltaMovement.y())
                            * (180.0d / Math.PI)) - 180.0d;
        } else if (direction == Direction.SOUTH) {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.y(),
                            updatedDeltaMovement.x())
                            * (180.0d / Math.PI)) - 90.0d;
        } else if (direction == Direction.EAST) {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.z(),
                            updatedDeltaMovement.y())
                            * (180.0d / Math.PI)) - 90.0d;
        } else {
            updatedRotation =
                    (Mth.atan2(
                            updatedDeltaMovement.y,
                            updatedDeltaMovement.z)
                            * (180.0d / Math.PI));
        }

        return Mth.wrapDegrees(updatedRotation - this.getYRot());
    }

    /**
     * Get this frame's updated movement delta.
     * @param axis The axis the caterpillar is aligned to.
     * @return The caterpillar's velocity.
     */
    @NotNull
    private Vec3 getUpdatedDeltaMovement(Direction.Axis axis) {
        Vec3 updatedDeltaMovement = Vec3.ZERO;
        if (this.targetPosition != null) {
            Vec3 updatedMovementDelta = targetPosition.subtract(this.position()).add(0.1d, 0.1d, 0.1d);
            double signumX = Math.signum(updatedMovementDelta.x);
            double signumY = Math.signum(updatedMovementDelta.y);
            double signumZ = Math.signum(updatedMovementDelta.z);
            Vec3 deltaMovement = this.getDeltaMovement();

            double xMod = 0.0;
            double yMod = 0.0;
            double zMod = 0.0;

            if (axis == Direction.Axis.X) {
                yMod = (signumY * 0.5d - deltaMovement.y) * CATERPILLAR_SPEED;
                zMod = (signumZ * 0.5d - deltaMovement.z) * CATERPILLAR_SPEED;
            } else if (axis == Direction.Axis.Y) {
                xMod = (signumX * 0.5d - deltaMovement.x) * CATERPILLAR_SPEED;
                zMod = (signumZ * 0.5d - deltaMovement.z) * CATERPILLAR_SPEED;
            } else {
                xMod = (signumX * 0.5d - deltaMovement.x) * CATERPILLAR_SPEED;
                yMod = (signumY * 0.5d - deltaMovement.y) * CATERPILLAR_SPEED;
            }

            updatedDeltaMovement = deltaMovement.add(xMod, yMod, zMod);

            // Clamp the speed if the caterpillar is too close to the edge.
            if (this.getX() % 1.0 > 0.95 || this.getX() < 0.05) {
                updatedDeltaMovement.multiply(0.0, 1.0, 1.0);
            }

            if (this.getY() % 1.0 > 0.95 || this.getY() < 0.05) {
                updatedDeltaMovement.multiply(1.0, 0.0, 1.0);
            }

            if (this.getZ() % 1.0 > 0.95 || this.getZ() < 0.05) {
                updatedDeltaMovement.multiply(1.0, 1.0, 0.0);
            }
        }

        return updatedDeltaMovement;
    }

    /**
     * Set whether the caterpillar is in a bottle.
     * @param isBottled TRUE if the caterpillar is bottled.
     */
    private void setIsBottled(boolean isBottled) {
        entityData.set(DATA_IS_BOTTLED, isBottled);
    }
}
