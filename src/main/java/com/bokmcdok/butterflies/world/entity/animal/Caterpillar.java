package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Creates the Caterpillar behaviour.
 */
public class Caterpillar extends DirectionalCreature {

    // The unique IDs that are used to reference a caterpillar entity.
    public static final String MORPHO_NAME = "morpho_caterpillar";
    public static final String FORESTER_NAME = "forester_caterpillar";
    public static final String COMMON_NAME = "common_caterpillar";
    public static final String EMPEROR_NAME = "emperor_caterpillar";
    public static final String HAIRSTREAK_NAME = "hairstreak_caterpillar";
    public static final String RAINBOW_NAME = "rainbow_caterpillar";
    public static final String HEATH_NAME = "heath_caterpillar";
    public static final String GLASSWING_NAME = "glasswing_caterpillar";
    public static final String CHALKHILL_NAME = "chalkhill_caterpillar";
    public static final String SWALLOWTAIL_NAME = "swallowtail_caterpillar";
    public static final String MONARCH_NAME = "monarch_caterpillar";
    public static final String CABBAGE_NAME = "cabbage_caterpillar";
    public static final String ADMIRAL_NAME = "admiral_caterpillar";
    public static final String LONGWING_NAME = "longwing_caterpillar";
    public static final String BUCKEYE_NAME = "buckeye_caterpillar";
    public static final String CLIPPER_NAME = "clipper_caterpillar";

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

    // The size of the caterpillar.
    private final ButterflyData.Size size;

    // The caterpillar item location.
    private final ResourceLocation caterpillarItem;

    /**
     * Create a Morpho butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static Caterpillar createMorphoCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("morpho", entityType, level);
    }

    /**
     * Create a Forester butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createForesterCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("forester", entityType, level);
    }

    /**
     * Create a Common butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createCommonCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("common", entityType, level);
    }

    /**
     * Create an Emperor butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createEmperorCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("emperor", entityType, level);
    }

    /**
     * Create a Hairstreak butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createHairstreakCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("hairstreak", entityType, level);
    }

    /**
     * Create a Rainbow butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createRainbowCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("rainbow", entityType, level);
    }

    /**
     * Create a Heath butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createHeathCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("heath", entityType, level);
    }

    /**
     * Create a Glasswing butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createGlasswingCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("glasswing", entityType, level);
    }

    /**
     * Create a Chalkhill butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createChalkhillCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("chalkhill", entityType, level);
    }

    /**
     * Create a Swallowtail butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createSwallowtailCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar(
                "swallowtail",
                entityType,
                level);
    }

    /**
     * Create a Monarch butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createMonarchCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("monarch", entityType, level);
    }

    /**
     * Create a Cabbage butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createCabbageCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("cabbage", entityType, level);
    }

    /**
     * Create an Admiral butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createAdmiralCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("admiral", entityType, level);
    }

    /**
     * Create a Longwing butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createLongwingCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("longwing", entityType, level);
    }

    /**
     * Create a Clipper butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createClipperCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("clipper", entityType, level);
    }

    /**
     * Create a Buckeye butterfly
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createBuckeyeCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("buckeye", entityType, level);
    }

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
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(location);
        if (entityType != null) {
            Entity entity = entityType.create(level);
            if (entity instanceof Caterpillar caterpillar) {
                caterpillar.setIsBottled(isBottled);

                double x = position.getX() + 0.45D;
                double y = position.getY() + 0.4D;
                double z = position.getZ() + 0.5D;

                if (isBottled) {
                    direction = Direction.DOWN;
                    y = Math.floor(position.getY()) + 0.07d;

                    caterpillar.setInvulnerable(true);
                    caterpillar.setPersistenceRequired();
                } else {
                    switch (direction) {
                        case DOWN -> y = Math.floor(position.getY());
                        case UP -> y = Math.floor(position.getY()) + 1.0d;
                        case NORTH -> z = Math.floor(position.getZ());
                        case SOUTH -> z = Math.floor(position.getZ()) + 1.0d;
                        case WEST -> x = Math.floor(position.getX());
                        case EAST -> x = Math.floor(position.getX()) + 1.0d;
                    }
                }

                caterpillar.moveTo(x, y, z, 0.0F, 0.0F);
                caterpillar.setSurfaceDirection(direction);

                caterpillar.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(position),
                        MobSpawnType.NATURAL,
                        null,
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
     *
     * @return The new size of the caterpillar.
     */
    @Override
    public float getScale() {
        float scale = (float) getAge() / -24000.0f;
        scale *= 0.04;
        scale += 0.08;

        switch (this.size) {
            case SMALL -> {
                return 0.7f * scale;
            }
            case LARGE -> {
                return 1.28f * scale;
            }
            default -> {
                return scale;
            }
        }
    }

    /**
     * If a player hits a caterpillar it will be converted to an item in their inventory.
     * @param damageSource The source of the damage.
     * @param damage The amount of damage.
     * @return The result from hurting the caterpillar.
     */
    @Override
    public boolean hurt(@NotNull DamageSource damageSource,
                        float damage) {
        if (damageSource.getEntity() instanceof Player player) {
            if (this.getLevel().isClientSide) {
                player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);
            } else {
                this.remove(RemovalReason.DISCARDED);
                Item caterpillarItem = ForgeRegistries.ITEMS.getValue(this.caterpillarItem);
                if (caterpillarItem != null) {
                    ItemStack itemStack = new ItemStack(caterpillarItem);
                    player.addItem(itemStack);
                }
            }
            
            return true;
        }

        return super.hurt(damageSource, damage);
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
     * Overridden so that butterfly entities will render at a decent distance.
     * @param distance The distance to check.
     * @return TRUE if we should render the entity.
     */
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 *= 64.0D * getViewScale();
        return distance < d0 * d0;
    }

    /**
     * Create a caterpillar entity.
     *
     * @param species    The species of the butterfly
     * @param entityType The entity type.
     * @param level      The level we are creating the entity in.
     */
    protected Caterpillar(String species,
                          EntityType<? extends Caterpillar> entityType,
                          Level level) {
        super("textures/entity/caterpillar/caterpillar_" + species + ".png", entityType, level);

        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
        ButterflyData data = ButterflyData.getEntry(location);
        this.size = data.size;
        this.caterpillarItem = ButterflyData.indexToCaterpillarItem(data.butterflyIndex);
        setAge(-data.caterpillarLifespan);
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    @SuppressWarnings("deprecation")
    protected void customServerAiStep() {
        super.customServerAiStep();

        // Update gravity
        isNoGravity = true;


        if (this.getIsReleased()) {
            BlockPos surfaceBlockPos = this.getSurfaceBlockPos();
            if (this.getLevel().hasChunkAt(surfaceBlockPos)) {

                // If the surface block is empty then we try to look for one below.
                if (this.getLevel().isEmptyBlock(surfaceBlockPos)) {
                    setSurfaceDirection(Direction.DOWN);
                }

                // Caterpillars will only fall if their surface direction is
                // down.
                if (getSurfaceDirection() == Direction.DOWN) {

                    // If the surface block is still empty, or the caterpillar is
                    // too far above the surface block, then it should fall.
                    if (this.getLevel().isEmptyBlock(surfaceBlockPos)
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
                            Math.floor(this.targetPosition.y())
                                    + (this.random.nextDouble() % 1.0),
                            Math.floor(this.targetPosition.z())
                                    + (this.random.nextDouble() % 1.0));
                } else if (axis == Direction.Axis.Y) {
                    this.targetPosition = new Vec3(
                            Math.floor(this.targetPosition.x())
                                    + (this.random.nextDouble() % 1.0),
                            this.targetPosition.y(),
                            Math.floor(this.targetPosition.z())
                                    + (this.random.nextDouble() % 1.0));

                } else {
                    this.targetPosition = new Vec3(
                            Math.floor(this.targetPosition.x())
                                    + (this.random.nextDouble() % 1.0),
                            Math.floor(this.targetPosition.y())
                                    + (this.random.nextDouble() % 1.0),
                            this.targetPosition.z());
                }
            }

            Vec3 deltaMovement = this.getDeltaMovement();
            Vec3 updatedDeltaMovement;
            if (axis == Direction.Axis.X) {
                double dy = this.targetPosition.y() + 0.1d - this.getY();
                double dz = this.targetPosition.z() + 0.1d - this.getZ();
                updatedDeltaMovement = deltaMovement.add(
                        0.0,
                        (Math.signum(dy) * 0.5d - deltaMovement.y)
                                * CATERPILLAR_SPEED,
                        (Math.signum(dz) * 0.5d - deltaMovement.z)
                                * CATERPILLAR_SPEED);
            } else if (axis == Direction.Axis.Y) {
                double dx = this.targetPosition.x() + 0.1d - this.getX();
                double dz = this.targetPosition.z() + 0.1d - this.getZ();
                updatedDeltaMovement = deltaMovement.add(
                        (Math.signum(dx) * 0.5d - deltaMovement.x)
                                * CATERPILLAR_SPEED,
                        0.0,
                        (Math.signum(dz) * 0.5d - deltaMovement.z)
                                * CATERPILLAR_SPEED);
            } else {
                double dx = this.targetPosition.x() + 0.1d - this.getX();
                double dy = this.targetPosition.y() + 0.1d - this.getY();
                updatedDeltaMovement = deltaMovement.add(
                        (Math.signum(dx) * 0.5d - deltaMovement.x)
                                * CATERPILLAR_SPEED,
                        (Math.signum(dy) * 0.5d - deltaMovement.y)
                                * CATERPILLAR_SPEED,
                        0.0);
            }

            this.setDeltaMovement(updatedDeltaMovement);

            this.zza = 0.5f;

            // Calculate the rotational velocity.
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
                                updatedDeltaMovement.x,
                                updatedDeltaMovement.z)
                                * (180.0d / Math.PI)) - 180.0d;
            } else if (direction == Direction.NORTH) {
                updatedRotation =
                        (Mth.atan2(
                                updatedDeltaMovement.x,
                                updatedDeltaMovement.y)
                                * (180.0d / Math.PI)) - 180.0d;
            } else if (direction == Direction.SOUTH) {
                updatedRotation =
                        (Mth.atan2(
                                updatedDeltaMovement.y,
                                updatedDeltaMovement.x)
                                * (180.0d / Math.PI)) - 90.0d;
            } else if (direction == Direction.EAST) {
                updatedRotation =
                        (Mth.atan2(
                                updatedDeltaMovement.z,
                                updatedDeltaMovement.y)
                                * (180.0d / Math.PI)) - 90.0d;
            } else {
                updatedRotation =
                        (Mth.atan2(
                                updatedDeltaMovement.y,
                                updatedDeltaMovement.z)
                                * (180.0d / Math.PI));
            }

            double rotationDelta =
                    Mth.wrapDegrees(updatedRotation - this.getYRot());
            this.setYRot(this.getYRot() + (float) rotationDelta);

            // Spawn Chrysalis.
            if (this.getIsReleased()
                    && this.getAge() >= 0
                    && this.random.nextInt(0, 15) == 0) {
                BlockPos surfaceBlockPos = this.getSurfaceBlockPos();

                // If the caterpillar is not on a leaf block it will starve instead.
                if (getLevel().getBlockState(surfaceBlockPos).getBlock() instanceof LeavesBlock) {
                    ResourceLocation location = EntityType.getKey(this.getType());
                    int index = ButterflyData.getButterflyIndex(location);
                    ResourceLocation newLocation = ButterflyData.indexToChrysalisEntity(index);
                    if (newLocation != null) {
                        Chrysalis.spawn((ServerLevel) this.getLevel(),
                                newLocation,
                                this.getSurfaceBlockPos(),
                                this.getSurfaceDirection(),
                                this.position(),
                                this.getYRot());
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    this.hurt(DamageSource.STARVE, 1.0f);
                }
            }
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_BOTTLED, false);
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
     * Override to control an entity's relative volume. Caterpillars are silent.
     * @return Always zero, so caterpillars are silent.
     */
    @Override
    protected float getSoundVolume() {
        return 0.0f;
    }

    /**
     * Override to set the entity's eye height.
     * @param pose The current pose of the entity.
     * @param dimensions The dimensions of the entity.
     * @return The height of the entity's eyes.
     */
    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose,
                                         EntityDimensions dimensions) {
        return dimensions.height / 2.0f;
    }

    /**
     * Overriding so we can increase the bounding box for the caterpillar.
     * @return A bounding box that is bigger than the default.
     */
    @Override
    @NotNull
    protected AABB makeBoundingBox() {
        AABB boundingBox = super.makeBoundingBox();
        return boundingBox.inflate(0.05);
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
     * Set whether the caterpillar is in a bottle.
     * @param isBottled TRUE if the caterpillar is bottled.
     */
    private void setIsBottled(boolean isBottled) {
        entityData.set(DATA_IS_BOTTLED, isBottled);
    }
}
