package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.block.ButterflyLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The butterfly entity that flies around the world, adding some ambience and
 * fertilising plants.
 */
public class Butterfly extends Animal {

    // The unique IDs that are used to reference a butterfly entity.
    public static final String MORPHO_NAME = "morpho";
    public static final String FORESTER_NAME = "forester";
    public static final String COMMON_NAME = "common";
    public static final String EMPEROR_NAME = "emperor";
    public static final String HAIRSTREAK_NAME = "hairstreak";
    public static final String RAINBOW_NAME = "rainbow";
    public static final String HEATH_NAME = "heath";
    public static final String GLASSWING_NAME = "glasswing";
    public static final String CHALKHILL_NAME = "chalkhill";
    public static final String SWALLOWTAIL_NAME = "swallowtail";
    public static final String MONARCH_NAME = "monarch";
    public static final String CABBAGE_NAME = "cabbage";
    public static final String ADMIRAL_NAME = "admiral";
    public static final String LONGWING_NAME = "longwing";
    public static final String BUCKEYE_NAME = "buckeye";
    public static final String CLIPPER_NAME = "clipper";

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Boolean> DATA_IS_FERTILE =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);

    // Names of the attributes stored in the save data.
    protected static final String IS_FERTILE = "is_fertile";

    // The number of ticks per flap. Used for event emissions.
    private static final int TICKS_PER_FLAP = Mth.ceil(2.4166098f);

    // Helper constant to modify butterfly speed
    private static final double BUTTERFLY_SPEED = 0.0325d;

    //  The location of the texture that the renderer should use.
    private final ResourceLocation texture;

    // The position the butterfly is flying towards. Butterflies can spawn
    // anywhere the light level is above 8.
    @Nullable private BlockPos targetPosition;

    // The size of the butterfly.
    private final ButterflyData.Size size;

    // The speed of the butterfly.
    private final double speed;

    /**
     * Checks custom rules to determine if the entity can spawn.
     * @param entityType (Unused) The type of the entity to spawn.
     * @param level The level/world to spawn the entity into.
     * @param spawnType (Unused) The type of spawn happening.
     * @param position The position to spawn the entity into.
     * @param rng (Unused) The global random number generator.
     * @return TRUE if the butterfly can spawn.
     */
    public static boolean checkButterflySpawnRules(
            @SuppressWarnings("unused") EntityType<Butterfly> entityType,
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
     * Create an Admiral butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createAdmiralButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "admiral",
                entityType, level);
    }

    /**
     * Create a Buckeye butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createBuckeyeButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "buckeye",
                entityType, level);
    }

    /**
     * Create a Cabbage butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createCabbageButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "cabbage",
                entityType, level);
    }

    /**
     * Create a Chalkhill butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createChalkhillButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "chalkhill",
                entityType, level);
    }

    /**
     * Create a Clipper butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createClipperButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "clipper",
                entityType, level);
    }

    /**
     * Create a Common butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createCommonButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "common",
                entityType, level);
    }

    /**
     * Create an Emperor butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createEmperorButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "emperor",
                entityType, level);
    }

    /**
     * Create a Forester butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createForesterButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "forester",
                entityType, level);
    }

    /**
     * Create a Glasswing butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createGlasswingButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "glasswing",
                entityType, level);
    }

    /**
     * Create a Hairstreak butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createHairstreakButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "hairstreak",
                entityType, level);
    }

    /**
     * Create a Heath butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createHeathButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "heath",
                entityType, level);
    }

    /**
     * Create a Longwing butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createLongwingButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "longwing",
                entityType, level);
    }

    /**
     * Create a Monarch butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createMonarchButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "monarch",
                entityType, level);
    }

    /**
     * Create a Morpho butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createMorphoButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "morpho", entityType, level);
    }

    /**
     * Create a Rainbow butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createRainbowButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "rainbow",
                entityType, level);
    }

    /**
     * Create a Swallowtail butterfly
     * @param entityType The type of the entity.
     * @param level The current level.
     * @return A newly constructed butterfly.
     */
    @NotNull
    public static Butterfly createSwallowtailButterfly(
            EntityType<? extends Butterfly> entityType,
            Level level) {
        return new Butterfly(
                "swallowtail",
                entityType, level);
    }

    /**
     * Used to spawn a butterfly into the world.
     * @param level The current level.
     * @param location The type of butterfly to release.
     * @param position The current position of the player.
     */
    public static void spawn(Level level,
                             ResourceLocation location,
                             BlockPos position,
                             Boolean placed) {
        if (level instanceof ServerLevel) {
            EntityType<?> entityType =
                    ForgeRegistries.ENTITY_TYPES.getValue(location);
            if (entityType != null) {
                Entity entity = entityType.create(level);
                if (entity instanceof Butterfly butterfly) {

                    butterfly.moveTo(position.getX() + 0.45D,
                            position.getY() + 0.2D,
                            position.getZ() + 0.5D,
                            0.0F, 0.0F);

                    butterfly.finalizeSpawn((ServerLevel) level,
                            level.getCurrentDifficultyAt(position),
                            MobSpawnType.NATURAL,
                            null,
                            null);

                    if (placed) {
                        butterfly.setInvulnerable(true);
                        butterfly.setPersistenceRequired();
                    }

                    level.addFreshEntity(butterfly);
                }
            }
        } else {
            level.playSound(null, position.getX(), position.getY(), position.getZ(), SoundEvents.PLAYER_ATTACK_WEAK,
                            SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
    }

    /**
     * The default constructor.
     * @param species The species of the butterfly.
     * @param entityType The type of the entity.
     * @param level The level where the entity exists.
     */
    public Butterfly(String species,
                     EntityType<? extends Butterfly> entityType,
                     Level level) {
        super(entityType, level);

        this.texture = new ResourceLocation("butterflies:textures/entity/butterfly/butterfly_" + species + ".png");

        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
        ButterflyData data = ButterflyData.getEntry(location);
        this.size = data.size;

        if (data.speed == ButterflyData.Speed.FAST) {
            this.speed = BUTTERFLY_SPEED * 1.2d;
        } else {
            this.speed = BUTTERFLY_SPEED;
        }

        setAge(-data.butterflyLifespan);
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(IS_FERTILE, this.entityData.get(DATA_IS_FERTILE));
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
     * Set persistence if we are spawning from a spawn egg.
     * @param levelAccessor Access to the level.
     * @param difficulty The local difficulty.
     * @param spawnType The type of spawn.
     * @param groupData The group data.
     * @param compoundTag Tag data for the entity.
     * @return The updated group data.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor,
                                        @NotNull DifficultyInstance difficulty,
                                        @NotNull MobSpawnType spawnType,
                                        @Nullable SpawnGroupData groupData,
                                        @Nullable CompoundTag compoundTag) {
        if (spawnType == MobSpawnType.SPAWN_EGG) {
            setPersistenceRequired();
        }

        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, groupData, compoundTag);
    }

    /**
     * Butterflies won't produce offspring: they lay eggs instead.
     * @param level The current level
     * @param mob The parent mod
     * @return NULL as there are no offspring
     */
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level,
                                        @NotNull AgeableMob mob) {
        return null;
    }

    /**
     * Get the scale to use for the butterfly.
     * @return A scale value based on the butterfly's size.
     */
    public float getScale() {
        switch (this.size) {
            case SMALL -> { return 0.25f; }
            case LARGE ->{ return 0.45f; }
            default -> { return 0.35f; }
        }
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

        // Get the bottle state
        if (tag.contains(IS_FERTILE)) {
            this.entityData.set(DATA_IS_FERTILE, tag.getBoolean(IS_FERTILE));
        }
    }

    /**
     * Override so that the bounding box isn't recalculated for "babies".
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
     * The main update loop for the entity.
     */
    @Override
    public void tick() {
        super.tick();

        //  Reduce the vertical movement to keep the butterfly close to the
        //  same height.
        this.setDeltaMovement(
                this.getDeltaMovement().multiply(1.0d, 0.6d, 1.0d));
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

        Level level = this.level();

        // Check the current move target is still an empty block.
        if (this.targetPosition != null
                && (!level.isEmptyBlock(this.targetPosition)
                || this.targetPosition.getY() <= level.getMinBuildHeight())) {
            this.targetPosition = null;
        }

        // Set a new target position if:
        //  1. We don't have one already
        //  2. After a 1/30 random chance
        //  3. We get too close to the current target position
        if (this.targetPosition == null
                || this.random.nextInt(30) == 0
                || this.targetPosition.closerToCenterThan(
                        this.position(), 2.0d)) {
            this.targetPosition = new BlockPos(
                    (int) this.getX() + this.random.nextInt(8) - 4,
                    (int) this.getY() + this.random.nextInt(6) - 2,
                    (int) this.getZ() + this.random.nextInt(8) - 4);
        }

        // Calculate an updated movement delta.
        double dx = this.targetPosition.getX() + 0.5d - this.getX();
        double dy = this.targetPosition.getY() + 0.1d - this.getY();
        double dz = this.targetPosition.getZ() + 0.5d - this.getZ();

        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 updatedDeltaMovement = deltaMovement.add(
                (Math.signum(dx) * 0.5d - deltaMovement.x) * this.speed,
                (Math.signum(dy) * 0.7d - deltaMovement.y) * 0.1d,
                (Math.signum(dz) * 0.5d - deltaMovement.z) * this.speed);
        this.setDeltaMovement(updatedDeltaMovement);

        this.zza = 0.5f;

        // Calculate the rotational velocity.
        double yRot = (Mth.atan2(updatedDeltaMovement.z, updatedDeltaMovement.x)
                * (180.0d / Math.PI)) - 90.0d;
        double yRotDelta = Mth.wrapDegrees(yRot - this.getYRot());
        this.setYRot(this.getYRot() + (float)yRotDelta);

        if (this.getIsFertile() && this.random.nextInt(320) == 1) {

            // Attempt to lay an egg.
            BlockPos position = this.blockPosition();
            position = switch (this.random.nextInt(6)) {
                default -> position.above();
                case 1 -> position.below();
                case 2 -> position.north();
                case 3 -> position.east();
                case 4 -> position.south();
                case 5 -> position.west();
            };

            ButterflyLeavesBlock.swapLeavesBlock(
                    level,
                    position,
                    EntityType.getKey(this.getType()));

            setIsFertile(false);
        } else {
            // Attempt to mate
            List<Butterfly> nearbyButterflies = this.level().getNearbyEntities(
                    Butterfly.class,
                    TargetingConditions.forNonCombat(),
                    this,
                    this.getBoundingBox().inflate(2.0D));

            for(Butterfly i : nearbyButterflies) {
                if (i.getType() == this.getType()) {
                    setIsFertile(true);
                    break;
                }
            }
        }

        // If the caterpillar gets too old it will die. This won't happen if it
        // has been set to persistent (e.g. by using a name tag).
        if (!this.isPersistenceRequired() &&
                this.getAge() >= 0 &&
                this.random.nextInt(0, 15) == 0) {
            this.kill();
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_FERTILE, false);
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
     * Check if the butterfly can lay an egg.
     * @return TRUE if the butterfly is fertile.
     */
    protected boolean getIsFertile() {
        return entityData.get(DATA_IS_FERTILE);
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
     * Get the texture to use for rendering.
     * @return The resource location of the texture.
     */
    public ResourceLocation getTexture() {
        return texture;
    }

    /**
     * Override to change how pushing other entities affects them. Butterflies
     * don't push other entities.
     */
    @Override
    protected void pushEntities() {
        // No-op
    }

    /**
     * Set whether the butterfly can lay an egg.
     * @param isFertile Whether the butterfly is fertile.
     */
    private void setIsFertile(boolean isFertile) {
        entityData.set(DATA_IS_FERTILE, isFertile);
    }
}
