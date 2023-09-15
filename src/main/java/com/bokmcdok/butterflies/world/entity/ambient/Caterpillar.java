package com.bokmcdok.butterflies.world.entity.ambient;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Creates the Caterpillar behaviour.
 */
public class Caterpillar extends AmbientCreature {

    // The unique IDs that are used to reference a butterfly entity.
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
    private static final EntityDataAccessor<Boolean> DATA_PERSISTENT =
            SynchedEntityData.defineId(
                    Caterpillar.class,
                    EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Direction> DATA_DIRECTION =
            SynchedEntityData.defineId(
                    Caterpillar.class,
                    EntityDataSerializers.DIRECTION);

    private static final EntityDataAccessor<BlockPos> DATA_SURFACE_BLOCK =
            SynchedEntityData.defineId(
                    Caterpillar.class,
                    EntityDataSerializers.BLOCK_POS);

    // Names of the attributes stored in the save data.
    private static final String PERSISTENT = "persistent";

    private static final String DIRECTION = "direction";

    private static final String SURFACE_BLOCK = "surface_block";

    // Helper constant to modify speed
    private static final double CATERPILLAR_SPEED = 0.00325d;

    //  The location of the texture that the renderer should use.
    private final ResourceLocation texture;

    // The current position the caterpillar is moving toward.
    @Nullable
    private Vec3 targetPosition;

    /**
     * Supplies attributes for the butterfly, in this case just 1 points of
     * maximum health (0.5 hearts).
     * @return The butterfly attribute supplier.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1d);
    }

    /**
     * Create a Morpho butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createMorphoCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_morpho.png", entityType, level);
    }

    /**
     * Create a Forester butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createForesterCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_forester.png", entityType, level);
    }

    /**
     * Create a Common butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createCommonCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_common.png", entityType, level);
    }

    /**
     * Create an Emperor butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createEmperorCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_emperor.png", entityType, level);
    }

    /**
     * Create a Hairstreak butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createHairstreakCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                      Level level) {
        return new Caterpillar("caterpillar_hairstreak.png", entityType, level);
    }

    /**
     * Create a Rainbow butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createRainbowCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_rainbow.png", entityType, level);
    }

    /**
     * Create a Heath butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createHeathCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_heath.png", entityType, level);
    }

    /**
     * Create a Glasswing butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createGlasswingCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_glasswing.png", entityType, level);
    }

    /**
     * Create a Chalkhill butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createChalkhillCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_chalkhill.png", entityType, level);
    }

    /**
     * Create a Swallowtail butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createSwallowtailCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar(
                "caterpillar_swallowtail.png",
                entityType,
                level);
    }

    /**
     * Create a Monarch butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createMonarchCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_monarch.png", entityType, level);
    }

    /**
     * Create a Cabbage butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createCabbageCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_cabbage.png", entityType, level);
    }

    /**
     * Create an Admiral butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createAdmiralCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_admiral.png", entityType, level);
    }

    /**
     * Create a Longwing butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createLongwingCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_longwing.png", entityType, level);
    }

    /**
     * Create a Clipper butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Caterpillar createClipperCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_clipper.png", entityType, level);
    }

    /**
     * Create a Buckeye butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createBuckeyeCaterpillar(
            EntityType<? extends Caterpillar> entityType,
            Level level) {
        return new Caterpillar("caterpillar_buckeye.png", entityType, level);
    }

    /**
     * Spawns a caterpillar into the world.
     * @param entityId The type of butterfly to release.
     * @param position The current position of the player.
     */
    @SuppressWarnings({"deprecation", "OverrideOnly"})
    public static void spawn(ServerLevel level,
                             String entityId,
                             BlockPos position,
                             Direction direction) {

        ResourceLocation key = new ResourceLocation(
                ButterfliesMod.MODID,
                entityId + "_caterpillar");

        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(key);
        if (entityType != null) {
            Entity entity = entityType.create(level);
            if (entity instanceof Caterpillar caterpillar) {
                double  x = position.getX() + 0.45D;
                double  y = position.getY() + 0.2D;
                double  z = position.getZ() + 0.5D;

                BlockPos spawnPosition;
                if (direction == Direction.DOWN) {
                    y = Math.floor(position.getY());
                    spawnPosition = position.below();
                } else if (direction == Direction.UP) {
                    y = Math.floor(position.getY()) + 1.0d;
                    spawnPosition = position.above();
                } else if (direction == Direction.NORTH) {
                    z = Math.floor(position.getZ());
                    spawnPosition = position.north();
                } else if (direction == Direction.SOUTH) {
                    z = Math.floor(position.getZ()) + 1.0d;
                    spawnPosition = position.south();
                } else if (direction == Direction.WEST) {
                    x = Math.floor(position.getX());
                    spawnPosition = position.west();
                } else {
                    x = Math.floor(position.getX()) + 1.0d;
                    spawnPosition = position.east();
                }

                caterpillar.moveTo(x, y, z, 0.0F, 0.0F);
                caterpillar.setSurfaceDirection(direction);
                caterpillar.setSurfaceBlock(spawnPosition);

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
        tag.putBoolean(PERSISTENT, this.entityData.get(DATA_PERSISTENT));
        tag.putString(DIRECTION, this.entityData.get(DATA_DIRECTION).getName());
        tag.putString(
                SURFACE_BLOCK,
                this.entityData.get(DATA_SURFACE_BLOCK).toShortString());
    }

    /**
     * Get the direction to the surface the caterpillar is crawling on.
     * @return The direction of the block (UP, DOWN, NORTH, SOUTH, EAST, WEST).
     */
    @NotNull
    public Direction getSurfaceDirection() {
        return entityData.get(DATA_DIRECTION);
    }

    /**
     * Overrides how an entity handles triggers such as tripwires and pressure
     * plates. Caterpillars aren't heavy enough to trigger either.
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
        boolean isNoGravity = true;

        if (this.level().isEmptyBlock(getSurfaceBlock())) {
            setSurfaceDirection(Direction.DOWN);
            setSurfaceBlock(this.blockPosition().below());
            this.targetPosition = null;
            isNoGravity = false;
        }

        return isNoGravity;
    }

    /**
     * Override this to control if an entity can be pushed or not. Caterpillars
     * can't be pushed by other entities.
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

        // Read the placed-by-player flag if it exists.
        if (tag.contains(PERSISTENT)) {
            this.entityData.set(DATA_PERSISTENT, tag.getBoolean(PERSISTENT));
        }

        // Get the direction
        if (tag.contains(DIRECTION)) {
            String name = tag.getString(DIRECTION);
            Direction direction = Direction.byName(name);
            if (direction != null) {
                this.entityData.set(DATA_DIRECTION, direction);
            }
        }

        if (tag.contains(SURFACE_BLOCK)) {
            String data = tag.getString(SURFACE_BLOCK);
            String[] values = data.split(",");
            BlockPos position = new BlockPos(
                    Integer.parseInt(values[0].trim()),
                    Integer.parseInt(values[1].trim()),
                    Integer.parseInt(values[2].trim()));
            this.entityData.set(DATA_SURFACE_BLOCK, position);
        }
    }

    /**
     * Override to stop an entity despawning. Caterpillars that have been placed
     * by a player won't despawn.
     * @return TRUE if we want to prevent despawning.
     */
    @Override
    public boolean requiresCustomPersistence() {
        return this.entityData.get(DATA_PERSISTENT)
                || super.requiresCustomPersistence();
    }

    /**
     * Sets the placed-by-player flag to true to prevent the butterfly
     * despawning.
     */
    public void setPersistent() {
        entityData.set(DATA_PERSISTENT, true);
    }

    /**
     * Set the position of the block the caterpillar is crawling on.
     * @param position The position of the block.
     */
    public void setSurfaceBlock(BlockPos position) {
        this.entityData.set(DATA_SURFACE_BLOCK, position);
    }

    /**
     * Set the direction of the block that the caterpillar is crawling on.
     * @param direction The direction of the surface block.
     */
    public void setSurfaceDirection(Direction direction) {
        this.entityData.set(DATA_DIRECTION, direction);
    }

    /**
     * The main update loop for the entity.
     */
    @Override
    public void tick() {
        super.tick();
        
        //  TODO: ?
    }

    /**
     * Create a caterpillar entity.
     * @param entityType The entity type.
     * @param level The level we are creating the entity in.
     */
    protected Caterpillar(String texture,
                          EntityType<? extends AmbientCreature> entityType,
                          Level level) {
        super(entityType, level);
        this.texture = new ResourceLocation(
                "butterflies:textures/entity/caterpillar/" + texture);
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

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

            double rotationDelta = Mth.wrapDegrees(updatedRotation - this.getYRot());
            this.setYRot(this.getYRot() + (float) rotationDelta);
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PERSISTENT, false);
        this.entityData.define(DATA_DIRECTION, Direction.DOWN);
        this.entityData.define(DATA_SURFACE_BLOCK, new BlockPos(0,0,0));
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
     * Reduce the size of the caterpillar - they are small!
     * @return The new size of the caterpillar.
     */
    @Override
    public float getScale() {
        return 0.1f;
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
     * Get the texture to use for rendering.
     * @return The resource location of the texture.
     */
    public ResourceLocation getTexture() {
        return texture;
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
     * Get the position of the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    private BlockPos getSurfaceBlock() {
        return this.entityData.get(DATA_SURFACE_BLOCK);
    }
}
