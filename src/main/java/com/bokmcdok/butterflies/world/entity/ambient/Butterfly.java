package com.bokmcdok.butterflies.world.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * The butterfly entity that flies around the world, adding some ambience and
 * fertilising plants.
 */
public class Butterfly extends AmbientCreature {

    // The unique ID that is used to reference a butterfly entity.
    public static final String NAME = "butterfly";

    // Holds a flag that is set to TRUE if a player placed the butterfly.
    // entity.
    private static final EntityDataAccessor<Boolean> DATA_PLACED_BY_PLAYER =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);

    // Holds an integer that represents the variant of the butterfly.
    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);

    // The number of ticks per flap. Used for event emissions.
    private static final int TICKS_PER_FLAP = Mth.ceil(2.4166098f);

    // The maximum number of butterfly variants.
    private static final int MAX_VARIANTS = 10;

    // The name of the "respawned" attribute in the save data.
    private static final String PLACED_BY_PLAYER = "butterflyPlacedByPlayer";

    // The name of the "variant" attribute in the save data.
    private static final String VARIANT = "butterflyVariant";

    // The position the butterfly is flying towards. Butterflies can spawn
    // anywhere the light level is above 8.
    @Nullable private BlockPos targetPosition;

    /**
     * Checks custom rules to determine if the entity can spawn.
     * @param entityType The type of the entity to spawn.
     * @param level The level/world to spawn the entity into.
     * @param spawnType The type of spawn happening.
     * @param position The position to spawn the entity into.
     * @param rng The global random number generator.
     * @return TRUE if the butterfly can spawn.
     */
    public static boolean checkButterflySpawnRules(@SuppressWarnings("unused") EntityType<Butterfly> entityType,
                                                   ServerLevelAccessor level,
                                                   @SuppressWarnings("unused") MobSpawnType spawnType,
                                                   BlockPos position,
                                                   @SuppressWarnings("unused") RandomSource rng) {
        return level.getRawBrightness(position, 0) > 8;
    }

    /**
     * Supplies attributes for the butterfly, in this case just 3 points of
     * maximum health (1.5 hearts).
     * @return The butterfly attribute supplier.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3d);
    }

    /**
     * The default constructor.
     * @param entityType The type of the entity.
     * @param level The level where the entity exists.
     */
    public Butterfly(EntityType<? extends Butterfly> entityType,
                     Level level) {
        super(entityType, level);
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(VARIANT, this.entityData.get(DATA_VARIANT));
        tag.putBoolean(PLACED_BY_PLAYER, this.entityData.get(DATA_PLACED_BY_PLAYER));
    }

    /**
     * Overrides how fall damage is applied to the entity. Butterflies ignore
     * all fall damage.
     * @param fallDistance The distance fallen.
     * @param blockModifier The damage modifier for the block landed on.
     * @param damageSource The source of the damage.
     * @return Always FALSE, as no damage is applied.
     */
    @Override
    public boolean causeFallDamage(float fallDistance,
                                   float blockModifier,
                                   @NotNull DamageSource damageSource) {
        return false;
    }

    /**
     * Used to finalise an entity's data after spawning. This will set the butterfly to a random variant.
     * @param level The level the entity is spawning into.
     * @param difficulty The difficulty of the level.
     * @param spawnType The type of spawn happening.
     * @param spawnGroupData The group data for the spawn.
     * @param tag The data tag for the entity.
     * @return Updated group data for the entity.
     */
    @Nullable
    @Override
    @SuppressWarnings( {"deprecation", "OverrideOnly"} )
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level,
                                        @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData,
                                        @Nullable CompoundTag tag) {
        setVariant(this.random.nextInt(MAX_VARIANTS));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    /**
     * Get the variant (species) of this butterfly.
     * @return The index representing this butterfly's species.
     */
    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    /**
     * Controls when a flapping event should be emitted.
     * @return TRUE when a flapping event should be emitted.
     */
    @Override
    protected boolean isFlapping() {
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    /**
     * Overrides how an entity handles triggers such as tripwires and pressure
     * plates. Butterflies aren't heavy enough to trigger either.
     * @return Always TRUE, so butterflies ignore block triggers.
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    /**
     * Override this to control if an entity can be pushed or not. Butterflies
     * can't be pushed by other entities.
     * @return Always FALSE, so butterflies cannot be pushed.
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

        // Read the variant (species) of the butterfly if it exists.
        if (tag.contains(VARIANT)) {
            this.entityData.set(DATA_VARIANT, tag.getInt(VARIANT));
        }

        // Read the placed-by-player flag if it exists.
        if (tag.contains(PLACED_BY_PLAYER)) {
            this.entityData.set(DATA_PLACED_BY_PLAYER, tag.getBoolean(PLACED_BY_PLAYER));
        }
    }

    /**
     * Override to stop an entity despawning. Butterflies that have been placed
     * by a player won't despawn.
     * @return TRUE if we want to prevent despawning.
     */
    @Override
    public boolean requiresCustomPersistence() {
        return this.entityData.get(DATA_PLACED_BY_PLAYER)
                || super.requiresCustomPersistence();
    }

    /**
     * Sets the placed-by-player flag to true to prevent the butterfly
     * despawning.
     */
    public void setPlacedByPlayer() {
        entityData.set(DATA_PLACED_BY_PLAYER, true);
    }

    /**
     * Sets the variant (species) of this butterfly.
     * @param variant The variant of this butterfly.
     */
    public void setVariant(int variant) {
        entityData.set(DATA_VARIANT, variant);
    }

    /**
     * The main update loop for the entity.
     */
    @Override
    public void tick() {
        super.tick();

        //  Reduce the vertical movement to keep the butterfly close to the
        //  same height.
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0d, 0.6d, 1.0d));
    }

    /**
     * Override to control how an entity checks for fall damage. In this case
     * butterflies just ignore the check.
     * @param yPos The current height of the entity.
     * @param onGround TRUE if the entity is on the ground.
     * @param blockState The state of the block just below the entity.
     * @param position The entity's current position.
     */
    @Override
    protected void checkFallDamage(double yPos,
                                   boolean onGround,
                                   @NotNull BlockState blockState,
                                   @NotNull BlockPos position) {
        //No-op
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        // Check the current move target is still an empty block.
        if (this.targetPosition != null && (!this.level.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
            this.targetPosition = null;
        }

        // Set a new target position if:
        //  1. We don't have one already
        //  2. After a 1/30 random chance
        //  3. We get too close to the current target position
        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0d)) {
            this.targetPosition = new BlockPos((int) this.getX() + this.random.nextInt(7) - this.random.nextInt(7),
                                               (int) this.getY() + this.random.nextInt(6) - 2,
                                               (int) this.getZ() + this.random.nextInt(7) - this.random.nextInt(7));
        }

        // Calculate an updated movement delta.
        double dx = this.targetPosition.getX() + 0.5d - this.getX();
        double dy = this.targetPosition.getY() + 0.1d - this.getY();
        double dz = this.targetPosition.getZ() + 0.5d - this.getZ();

        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 updatedDeltaMovement = deltaMovement.add((Math.signum(dx) * 0.5d - deltaMovement.x) * 0.1d,
                                                      (Math.signum(dy) * 0.7d - deltaMovement.y) * 0.1d,
                                                      (Math.signum(dz) * 0.5d - deltaMovement.z) * 0.1d);
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
        this.entityData.define(DATA_VARIANT, 0);
        this.entityData.define(DATA_PLACED_BY_PLAYER, false);
    }

    /**
     * Override to change how pushing other entities affects them. Butterflies
     * don't push other entities.
     * @param otherEntity The other entity pushing/being pushed.
     */
    @Override
    protected void doPush(@NotNull Entity otherEntity) {
        // No-op
    }

    /**
     * Override to control what kind of movement events the entity will emit.
     * Butterflies will not emit sounds.
     * @return Movement events only.
     */
    @NotNull
    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    /**
     * Override to control an entity's relative volume. Butterflies are silent.
     * @return Always zero, so butterflies are silent.
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
     * Override to change how pushing other entities affects them. Butterflies
     * don't push other entities.
     */
    @Override
    protected void pushEntities() {
        // No-op
    }
}
