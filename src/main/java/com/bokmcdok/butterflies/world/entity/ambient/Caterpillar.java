package com.bokmcdok.butterflies.world.entity.ambient;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

    // Holds a flag that is set to TRUE if a player placed the butterfly.
    // entity.
    private static final EntityDataAccessor<Boolean> DATA_PERSISTENT =
            SynchedEntityData.defineId(Caterpillar.class, EntityDataSerializers.BOOLEAN);

    // The name of the "respawned" attribute in the save data.
    private static final String PERSISTENT = "butterflyPlacedByPlayer";

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
    public static Caterpillar createMorphoCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                  Level level) {
        return new Caterpillar("caterpillar_blue.png", entityType, level);
    }

    /**
     * Create a Forester butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createForesterCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                    Level level) {
        return new Caterpillar("caterpillar_nyan.png", entityType, level);
    }

    /**
     * Create a Common butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createCommonCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                  Level level) {
        return new Caterpillar("caterpillar_birdwing.png", entityType, level);
    }

    /**
     * Create an Emperor butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createEmperorCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                   Level level) {
        return new Caterpillar("caterpillar_purple.png", entityType, level);
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
        return new Caterpillar("caterpillar_purple_trim.png", entityType, level);
    }

    /**
     * Create a Rainbow butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createRainbowCaterpillar(EntityType<? extends Caterpillar> entityType,
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
    public static Caterpillar createHeathCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                 Level level) {
        return new Caterpillar("caterpillar_red.png", entityType, level);
    }

    /**
     * Create a Glasswing butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createGlasswingCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                     Level level) {
        return new Caterpillar("caterpillar_seethru.png", entityType, level);
    }

    /**
     * Create a Chalkhill butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createChalkhillCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                     Level level) {
        return new Caterpillar("caterpillar_sword.png", entityType, level);
    }

    /**
     * Create a Swallowtail butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createSwallowtailCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                       Level level) {
        return new Caterpillar("caterpillar_white.png", entityType, level);
    }

    /**
     * Create a Monarch butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createMonarchCaterpillar(EntityType<? extends Caterpillar> entityType,
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
    public static Caterpillar createCabbageCaterpillar(EntityType<? extends Caterpillar> entityType,
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
    public static Caterpillar createAdmiralCaterpillar(EntityType<? extends Caterpillar> entityType,
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
    public static Caterpillar createLongwingCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                    Level level) {
        return new Caterpillar("caterpillar_longwing.png", entityType, level);
    }

    /**
     * Create a Clipper butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constrycted butterfly.
     */
    @NotNull
    public static Caterpillar createClipperCaterpillar(EntityType<? extends Caterpillar> entityType,
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
    public static Caterpillar createBuckeyeCaterpillar(EntityType<? extends Caterpillar> entityType,
                                                   Level level) {
        return new Caterpillar("caterpillar_buckeye.png", entityType, level);
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
        this.texture = new ResourceLocation("caterpillars:textures/entity/caterpillar/" + texture);
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(PERSISTENT, this.entityData.get(DATA_PERSISTENT));
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
     * The main update loop for the entity.
     */
    @Override
    public void tick() {
        super.tick();
        
        //  TODO: ?
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        // Set a new target position if:
        //  1. We don't have one already
        //  2. After a 1/30 random chance
        //  TODO: 3. We get too close to the current target position
        if (this.targetPosition == null || this.random.nextInt(30) == 0) {
            if (this.targetPosition == null) {
                this.targetPosition = this.position();
            }

            this.targetPosition = new Vec3(Math.floor(this.targetPosition.x()) + (this.random.nextDouble() % 1.0),
                                           this.getY(),
                                           Math.floor(this.targetPosition.z()) + (this.random.nextDouble() % 1.0));
        }

        // Calculate an updated movement delta.
        double dx = this.targetPosition.x() + 0.1d - this.getX();
        double dz = this.targetPosition.z() + 0.1d - this.getZ();

        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 updatedDeltaMovement = deltaMovement.add(
                (Math.signum(dx) * 0.5d - deltaMovement.x) * CATERPILLAR_SPEED,
                0.0,
                (Math.signum(dz) * 0.5d - deltaMovement.z) * CATERPILLAR_SPEED);
        this.setDeltaMovement(updatedDeltaMovement);

        this.zza = 0.5f;

        // Calculate the rotational velocity.
        double yRot = (Mth.atan2(updatedDeltaMovement.z, updatedDeltaMovement.x) * (180.0d / Math.PI)) - 90.0d;
        double yRotDelta = Mth.wrapDegrees(yRot - this.getYRot());
        this.setYRot(this.getYRot() + (float)yRotDelta);
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PERSISTENT, false);
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
}
