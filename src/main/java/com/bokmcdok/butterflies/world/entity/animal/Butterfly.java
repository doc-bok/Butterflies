package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.ai.ButterflyLayEggGoal;
import com.bokmcdok.butterflies.world.entity.ai.ButterflyMatingGoal;
import com.bokmcdok.butterflies.world.entity.ai.ButterflyPollinateFlowerGoal;
import com.bokmcdok.butterflies.world.entity.ai.ButterflyWanderGoal;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * The butterfly entity that flies around the world, adding some ambience and
 * fertilising plants.
 */
public class Butterfly extends Animal {

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Boolean> DATA_IS_FERTILE =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> DATA_LANDED =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_NUM_EGGS =
            SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);

    // Names of the attributes stored in the save data.
    protected static final String IS_FERTILE = "is_fertile";
    protected static final String LANDED = "landed";
    protected static final String NUM_EGGS = "num_eggs";

    // The number of ticks per flap. Used for event emissions.
    private static final int TICKS_PER_FLAP = Mth.ceil(2.4166098f);

    // Helper constant to modify butterfly speed
    private static final double BUTTERFLY_SPEED = 1.8d;

    // The butterfly index
    private final int butterflyIndex;

    // The size of the butterfly.
    private final ButterflyData.Size size;

    // The speed of the butterfly.
    private final ButterflyData.Speed speed;

    //  The location of the texture that the renderer should use.
    private final ResourceLocation texture;

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
            @SuppressWarnings("unused") EntityType<? extends Butterfly> entityType,
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
                .add(Attributes.FLYING_SPEED, BUTTERFLY_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BUTTERFLY_SPEED * 05.d);
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

                    butterfly.setYBodyRot(butterfly.random.nextFloat());

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
        this.setNoGravity(true);

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
        setLanded(false);

        // Register the goals again since they rely on the butterfly index.
        this.goalSelector.removeAllGoals((x) -> true);
        this.targetSelector.removeAllGoals((x) -> true);
        registerGoals();
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(IS_FERTILE, this.entityData.get(DATA_IS_FERTILE));
        tag.putBoolean(LANDED, this.entityData.get(DATA_LANDED));
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
     * Check if the butterfly can lay an egg.
     * @return TRUE if the butterfly is fertile.
     */
    public boolean getIsFertile() {
        return getNumEggs() > 0 && entityData.get(DATA_IS_FERTILE);
    }

    /**
     * Check if the butterfly has landed.
     * @return TRUE if the butterfly has landed.
     */
    public boolean getLanded() {
        return entityData.get(DATA_LANDED);
    }

    /**
     * Get the number of eggs this butterfly can lay.
     * @return The number of eggs left for this butterfly.
     */
    public int getNumEggs() {
        return entityData.get(DATA_NUM_EGGS);
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
     * Reduce vertical movement to minimise collision errors.
     */
    @Override
    public void tick() {
        super.tick();

        //  Reduce the vertical movement to keep the butterfly close to the
        //  same height.
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0d, 0.6d, 1.0d));
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
                return this.level.getBlockState(blockPos).isAir() ||
                       this.level.getBlockState(blockPos).is(BlockTags.FLOWERS);
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

        // Get the landed state.
        if (tag.contains(LANDED)) {
            this.entityData.set(DATA_LANDED, tag.getBoolean(LANDED));
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

        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 3, 0.8, 1.33, (x) -> !(x instanceof Butterfly)));
        this.goalSelector.addGoal(2, new ButterflyLayEggGoal(this, 0.8, 8, 8));
        this.goalSelector.addGoal(2, new ButterflyMatingGoal(this, 1.1, 8));

        // Pollination can be configured to be off.
        if (ButterfliesConfig.enablePollination.get()) {
            this.goalSelector.addGoal(4, new ButterflyPollinateFlowerGoal(this, 0.8d, 8, 8));
        }

        this.goalSelector.addGoal(8, new ButterflyWanderGoal(this));

        // Butterflies use targets to select mates.
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Butterfly.class, true, (target) -> {
            if (target instanceof Butterfly butterfly) {
                return butterfly.getButterflyIndex() == this.getButterflyIndex() &&
                        butterfly.getNumEggs() > 0 &&
                        !butterfly.getIsFertile();
            }

            return false;
        }));
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
     * Set whether the butterfly can lay an egg.
     * @param isFertile Whether the butterfly is fertile.
     */
    public void setIsFertile(boolean isFertile) {
        entityData.set(DATA_IS_FERTILE, isFertile);
    }

    /**
     * Set whether the butterfly has landed.
     * @param landed TRUE if the butterfly has landed.
     */
    public void setLanded(boolean landed) {
        entityData.set(DATA_LANDED, landed);
        this.setNoGravity(!landed);
    }

    /**
     * Hacky fix to stop butterflies teleporting.
     * TODO: We need a better fix than this.
     * @param x The x-position.
     * @param y The y-position.
     * @param z The z-position.
     */
    @Override
    public void setPos(double x, double y, double z) {
        Vec3 delta = new Vec3(x, y, z).subtract(this.position());
        if (delta.lengthSqr() <= 1 || this.position().lengthSqr() == 0) {
            super.setPos(x, y, z);
        }
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
     * Reduce the number of eggs the butterfly can lay by 1.
     */
    public void useEgg() {
        setNumEggs(getNumEggs() - 1);
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
        this.entityData.define(DATA_LANDED, false);
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
     * Set the number of eggs this butterfly can lay.
     * @param numEggs The number of eggs remaining.
     */
    private void setNumEggs(int numEggs) {
        entityData.set(DATA_NUM_EGGS, Math.max(0, numEggs));
    }
}
