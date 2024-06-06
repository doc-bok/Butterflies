package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.ai.ButterflyWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The butterfly entity that flies around the world, adding some ambience and
 * fertilising plants.
 */
public class Butterfly extends Animal {

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Boolean> DATA_IS_FERTILE =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_NUM_EGGS =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);

    // Names of the attributes stored in the save data.
    protected static final String IS_FERTILE = "is_fertile";
    protected static final String NUM_EGGS = "num_eggs";

    // The number of ticks per flap. Used for event emissions.
    private static final int TICKS_PER_FLAP = Mth.ceil(2.4166098f);

    // Helper constant to modify butterfly speed
    private static final double BUTTERFLY_SPEED = 1.8d;

    //  The location of the texture that the renderer should use.
    private final ResourceLocation texture;

    // The position the butterfly is flying towards. Butterflies can spawn
    // anywhere the light level is above 8.
    //@Nullable private BlockPos targetPosition;

    // The size of the butterfly.
    private final ButterflyData.Size size;

    // The butterfly index
    private final int butterflyIndex;

    // The speed of the butterfly.
    private final ButterflyData.Speed speed;
    //private final double speed;

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
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3d)
                .add(Attributes.FLYING_SPEED, BUTTERFLY_SPEED);
    }

    /**
     * Returns the ID to use in the registry.
     * @param butterflyIndex The butterfly index of this species.
     * @return The Registry ID of this entity type.
     */
    public static String getRegistryId(int butterflyIndex) {
        return ButterflySpeciesList.SPECIES[butterflyIndex];
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
     * @param entityType The type of the entity.
     * @param level The level where the entity exists.
     */
    public Butterfly(EntityType<? extends Butterfly> entityType,
                     Level level) {
        super(entityType, level);

        this.moveControl = new FlyingMoveControl(this, 20, true);

        String species = "undiscovered";
        String encodeId = this.getEncodeId();
        if (encodeId != null) {
            String[] split = encodeId.split(":");
            if (split.length >= 2) {
                species = split[1];
            }
        }

        this.texture = new ResourceLocation("butterflies:textures/entity/butterfly/butterfly_" + species + ".png");

        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
        ButterflyData data = ButterflyData.getEntry(location);
        this.size = data.size();
        this.speed = data.speed();

        this.butterflyIndex = data.butterflyIndex();

        setAge(-data.butterflyLifespan());
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(IS_FERTILE, this.entityData.get(DATA_IS_FERTILE));
        tag.putInt(NUM_EGGS, this.entityData.get(DATA_NUM_EGGS));
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

        //  Small chance the butterfly has more eggs.
        if (this.random.nextDouble() < ButterfliesConfig.doubleEggChance.get()) {
            this.setNumEggs(ButterfliesConfig.eggLimit.get() * 2);
        } else {
            this.setNumEggs(ButterfliesConfig.eggLimit.get());
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
     * Get the butterfly's index.
     * @return The butterfly index.
     */
    public int getButterflyIndex() {
        return butterflyIndex;
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
     * Butterflies can't be fed by players.
     * @param stack The item stack the player tried to feed the butterfly.
     * @return FALSE, indicating it isn't food.
     */
    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    /**
     * Create a flying navigator for the butterfly.
     * @param level The current level.
     * @return The flying navigation.
     */
    @Override
    @NotNull
    protected PathNavigation createNavigation(@NotNull Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            public boolean isStableDestination(@NotNull BlockPos blockPos) {
                return this.level.getBlockState(blockPos).isAir();
            }
        };

        if (speed == ButterflyData.Speed.FAST) {

            navigation.setSpeedModifier(1.2);
        }

        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
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

        // Get the fertile state.
        if (tag.contains(IS_FERTILE)) {
            this.entityData.set(DATA_IS_FERTILE, tag.getBoolean(IS_FERTILE));
        }

        // Get the number of remaining eggs.
        if (tag.contains(NUM_EGGS)) {
            this.entityData.set(DATA_NUM_EGGS, tag.getInt(NUM_EGGS));
        }
    }

    /**
     * Register the goals for the entity.
     */
    @Override
    protected void registerGoals() {
        super.registerGoals();

        // TODO: Register a wander goal
        this.goalSelector.addGoal(8, new ButterflyWanderGoal(this));

        // TODO: Register a move to block goal
        // TODO: Register a landing goal
        // TODO: Register a flee goal
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

        if (getNumEggs() > 0) {

            // Don't mate if there are too many butterflies in the area already.
            List<Butterfly> numButterflies = this.level().getNearbyEntities(
                    Butterfly.class,
                    TargetingConditions.forNonCombat(),
                    this,
                    this.getBoundingBox().inflate(32.0D));

            int maxDensity = ButterfliesConfig.maxDensity.get();
            if (maxDensity == 0 || numButterflies.size() <= maxDensity) {

                if (getIsFertile()) {
                    if (this.random.nextInt(320) == 1){

                        // Attempt to lay an egg.
                        Direction direction = switch (this.random.nextInt(6)) {
                            default -> Direction.UP;
                            case 1 -> Direction.DOWN;
                            case 2 -> Direction.NORTH;
                            case 3 -> Direction.EAST;
                            case 4 -> Direction.SOUTH;
                            case 5 -> Direction.WEST;
                        };

                        BlockPos position = this.blockPosition().relative(direction.getOpposite());

                        if (level.getBlockState(position).is(BlockTags.LEAVES)) {
                            ResourceLocation eggEntity = ButterflyData.indexToButterflyEggEntity(this.butterflyIndex);
                            ButterflyEgg.spawn((ServerLevel) level, eggEntity, position, direction);
                            setIsFertile(false);
                            useEgg();
                        }
                    }

                } else {
                    // Attempt to mate
                    List<Butterfly> nearbyButterflies = this.level().getNearbyEntities(
                            Butterfly.class,
                            TargetingConditions.forNonCombat(),
                            this,
                            this.getBoundingBox().inflate(2.0D));

                    for (Butterfly i : nearbyButterflies) {
                        if (i.getType() == this.getType()) {
                            setIsFertile(true);
                            setInLove(null);
                            break;
                        }
                    }
                }
            }
        }

        // If the butterfly gets too old it will die. This won't happen if it
        // has been set to persistent (e.g. by using a name tag).
        if (ButterfliesConfig.enableLifespan.get()) {
            if (!this.isPersistenceRequired() &&
                    this.getAge() >= 0 &&
                    this.random.nextInt(0, 15) == 0) {
                this.kill();
            }
        }
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_FERTILE, false);
        this.entityData.define(DATA_NUM_EGGS, 1);
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
     * Get the number of eggs this butterfly can lay.
     * @return The number of eggs left for this butterfly.
     */
    protected int getNumEggs() {
        return entityData.get(DATA_NUM_EGGS);
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

    /**
     * Set the number of eggs this butterfly can lay.
     * @param numEggs The number of eggs remaining.
     */
    private void setNumEggs(int numEggs) {
        entityData.set(DATA_NUM_EGGS, Math.max(0, numEggs));
    }

    /**
     * Reduce the number of eggs the butterfly can lay by 1.
     */
    private void useEgg() {
        setNumEggs(getNumEggs() - 1);
    }
}
