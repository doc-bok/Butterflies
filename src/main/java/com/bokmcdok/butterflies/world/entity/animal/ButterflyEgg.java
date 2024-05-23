package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ButterflyEgg extends DirectionalCreature {

    // The unique IDs that are used to reference a butterfly egg entity.
    public static final String ADMIRAL_NAME = "admiral_egg";
    public static final String BUCKEYE_NAME = "buckeye_egg";
    public static final String CABBAGE_NAME = "cabbage_egg";
    public static final String CHALKHILL_NAME = "chalkhill_egg";
    public static final String CLIPPER_NAME = "clipper_egg";
    public static final String COMMON_NAME = "common_egg";
    public static final String EMPEROR_NAME = "emperor_egg";
    public static final String FORESTER_NAME = "forester_egg";
    public static final String GLASSWING_NAME = "glasswing_egg";
    public static final String HAIRSTREAK_NAME = "hairstreak_egg";
    public static final String HEATH_NAME = "heath_egg";
    public static final String LONGWING_NAME = "longwing_egg";
    public static final String MONARCH_NAME = "monarch_egg";
    public static final String MORPHO_NAME = "morpho_egg";
    public static final String RAINBOW_NAME = "rainbow_egg";
    public static final String SWALLOWTAIL_NAME = "swallowtail_egg";
    public static final String PEACOCK_NAME = "peacock_egg";

    // The size of the butterfly egg.
    private final ButterflyData.Size size;

    // The butterfly egg item location.
    private final ResourceLocation butterflyEggItem;

    /**
     * Create an Admiral butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createAdmiral(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("admiral", entityType, level);
    }

    /**
     * Create a Buckeye butterfly egg.
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createBuckeye(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("buckeye", entityType, level);
    }

    /**
     * Create a Peacock butterfly egg.
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createPeacock(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("peacock", entityType, level);
    }

    /**
     * Create a Cabbage butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createCabbage(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("cabbage", entityType, level);
    }

    /**
     * Create a Chalkhill butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createChalkhill(EntityType<? extends ButterflyEgg> entityType,
                                               Level level) {
        return new ButterflyEgg("chalkhill", entityType, level);
    }

    /**
     * Create a Clipper butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createClipper(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("clipper", entityType, level);
    }

    /**
     * Create a Common butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createCommon(EntityType<? extends ButterflyEgg> entityType,
                                            Level level) {
        return new ButterflyEgg("common", entityType, level);
    }

    /**
     * Create an Emperor butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createEmperor(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("emperor", entityType, level);
    }

    /**
     * Create a Forester butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createForester(EntityType<? extends ButterflyEgg> entityType,
                                              Level level) {
        return new ButterflyEgg("forester", entityType, level);
    }

    /**
     * Create a Glasswing butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createGlasswing(EntityType<? extends ButterflyEgg> entityType,
                                               Level level) {
        return new ButterflyEgg("glasswing", entityType, level);
    }

    /**
     * Create a Hairstreak butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createHairstreak(EntityType<? extends ButterflyEgg> entityType,
                                                Level level) {
        return new ButterflyEgg("hairstreak", entityType, level);
    }

    /**
     * Create a Heath butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createHeath(EntityType<? extends ButterflyEgg> entityType,
                                           Level level) {
        return new ButterflyEgg("heath", entityType, level);
    }

    /**
     * Create a Longwing butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createLongwing(EntityType<? extends ButterflyEgg> entityType,
                                              Level level) {
        return new ButterflyEgg("longwing", entityType, level);
    }

    /**
     * Create a Monarch butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createMonarch(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("monarch", entityType, level);
    }

    /**
     * Create a Morpho butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createMorpho(EntityType<? extends ButterflyEgg> entityType,
                                            Level level) {
        return new ButterflyEgg("morpho", entityType, level);
    }

    /**
     * Create a Rainbow butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createRainbow(EntityType<? extends ButterflyEgg> entityType,
                                             Level level) {
        return new ButterflyEgg("rainbow", entityType, level);
    }

    /**
     * Create a Swallowtail butterfly egg.
     *
     * @param entityType The type of the entity.
     * @param level      The current level.
     * @return A newly constructed entity.
     */
    @NotNull
    public static ButterflyEgg createSwallowtail(EntityType<? extends ButterflyEgg> entityType,
                                                 Level level) {
        return new ButterflyEgg("swallowtail", entityType, level);
    }

    /**
     * Spawns a butterfly egg into the world.
     * @param level The level to spawn the entity.
     * @param location The resource location of the entity to spawn.
     * @param spawnBlock The block to spawn the entity on.
     * @param surfaceDirection The direction the entity is facing.
     */
    public static void spawn(ServerLevel level,
                             ResourceLocation location,
                             BlockPos spawnBlock,
                             Direction surfaceDirection) {
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(location);
        if (entityType != null) {
            Entity entity = entityType.create(level);
            if (entity instanceof ButterflyEgg egg) {

                double x = spawnBlock.getX() + level.random.nextDouble();
                double y = spawnBlock.getY() + level.random.nextDouble();
                double z = spawnBlock.getZ() + level.random.nextDouble();

                switch (surfaceDirection) {
                    case WEST -> x = spawnBlock.getX();
                    case EAST -> x = spawnBlock.getX() + 1.0d;
                    case DOWN -> y = spawnBlock.getY();
                    case UP -> y = spawnBlock.getY() + 1.0d;
                    case NORTH -> z = spawnBlock.getZ();
                    case SOUTH -> z = spawnBlock.getZ() + 1.0d;
                }

                egg.moveTo(x, y, z, 0.0F, 0.0F);
                egg.setSurfaceDirection(surfaceDirection);

                egg.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(spawnBlock),
                        MobSpawnType.NATURAL,
                        null,
                        null);

                level.addFreshEntity(egg);
            }
        }
    }

    /**
     * Reduce the size of the butterfly egg - they are small!
     * @return The new size of the caterpillar.
     */
    @Override
    public float getScale() {
        float scale = 0.05f;
        switch (this.size) {
            case SMALL -> { return 0.7f * scale; }
            case LARGE ->{ return 1.28f * scale; }
            default -> { return scale; }
        }
    }

    /**
     * If a player hits a butterfly egg it will be converted to an item in their inventory.
     * @param damageSource The source of the damage.
     * @param damage The amount of damage.
     * @return The result from hurting the caterpillar.
     */
    @Override
    public boolean hurt(@NotNull DamageSource damageSource,
                        float damage) {
        if (damageSource.getEntity() instanceof Player player) {
            if (this.level().isClientSide) {
                player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);
            } else {
                this.remove(RemovalReason.DISCARDED);

                Item itemToAdd = ForgeRegistries.ITEMS.getValue(this.butterflyEggItem);
                if (itemToAdd != null) {
                    ItemStack itemStack = new ItemStack(itemToAdd);
                    player.addItem(itemStack);
                }
            }

            return true;
        }

        return super.hurt(damageSource, damage);
    }

    /**
     * Eggs can't be fed by players.
     * @param stack The item stack the player tried to feed the butterfly egg.
     * @return FALSE, indicating it isn't food.
     */
    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    /**
     * Overrides how an entity handles triggers such as tripwires and pressure
     * plates. Butterfly eggs aren't heavy enough to trigger either.
     * @return Always TRUE, so butterfly eggs ignore block triggers.
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    /**
     * Butterfly eggs ignore gravity.
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * Override this to control if an entity can be pushed or not. Butterfly eggs
     * can't be pushed by other entities.
     *
     * @return Always FALSE.
     */
    @Override
    public boolean isPushable() {
        return false;
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
     * Construction
     * @param species The species of the butterfly
     * @param entityType The type of the entity.
     * @param level      The current level.
     */
    protected ButterflyEgg(String species,
                           EntityType<? extends ButterflyEgg> entityType,
                           Level level) {
        super("textures/item/butterfly_egg/" + species + "_egg.png", entityType, level);

        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
        ButterflyData data = ButterflyData.getEntry(location);
        this.size = data.size;
        setAge(-data.eggLifespan);
        this.butterflyEggItem = ButterflyData.indexToButterflyEggItem(data.butterflyIndex);
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        // If the surface block is destroyed then the butterfly egg dies.
        if (this.level().isEmptyBlock(getSurfaceBlockPos())) {
            kill();
        }

        // Spawn Butterfly.
        if (this.getAge() >= 0 && this.random.nextInt(0, 15) == 0) {
            ResourceLocation location = EntityType.getKey(this.getType());
            int index = ButterflyData.getButterflyIndex(location);
            ResourceLocation newLocation = ButterflyData.indexToCaterpillarEntity(index);
            if (newLocation != null) {
                Caterpillar.spawn((ServerLevel)this.level(), newLocation, this.blockPosition(), this.getDirection(), false);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    /**
     * Override to change how pushing other entities affects them. Butterfly eggs
     * don't push other entities.
     * @param otherEntity The other entity pushing/being pushed.
     */
    @Override
    protected void doPush(@NotNull Entity otherEntity) {
        // No-op
    }

    /**
     * Override to control what kind of movement events the entity will emit.
     * Butterfly eggs will not emit sounds.
     * @return Movement events only.
     */
    @NotNull
    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    /**
     * Override to control an entity's relative volume. Butterfly eggs are silent.
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
     * Override to change how pushing other entities affects them. Butterfly eggs
     * don't push other entities.
     */
    @Override
    protected void pushEntities() {
        // No-op
    }
}
