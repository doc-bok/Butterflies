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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class DirectionalCreature extends Animal {

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Direction> DATA_DIRECTION =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.DIRECTION);

    // Names of the attributes stored in the save data.
    protected static final String DIRECTION = "direction";

    // The butterfly's data - created on access.
    private ButterflyData data = null;

    // The location of the texture that the renderer should use.
    private ResourceLocation texture;

    /**
     * Check if a directional creature can spawn in this position.
     * @param entityType The type of the entity.
     * @param level The current level.
     * @param spawnType The type of spawn.
     * @param blockPos The position we want to spawn the entity.
     * @param random A random source.
     * @return TRUE if there is a leaf block nearby.
     */
    @SuppressWarnings("deprecation, unused")
    public static boolean checkDirectionalSpawnRules(EntityType<? extends Mob> entityType,
                                                     LevelAccessor level,
                                                     EntitySpawnReason spawnType,
                                                     BlockPos blockPos,
                                                     RandomSource random) {

        // Get the relevant butterfly data.
        String descriptionId = entityType.getDescriptionId();
        String[] components = descriptionId.split("\\.");

        // Check each direction.
        for (Direction direction : Direction.values()) {

            // Get the surface block properly.
            BlockPos surfacePosition = blockPos;
            if (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                surfacePosition = surfacePosition.relative(direction);
            }

            // Check if the entity can spawn on this surface.
            if (level.hasChunkAt(surfacePosition)) {
                ButterflyData data = ButterflyData.getEntry(ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, components[2]));

                // Fall back to leaves if we don't have the data yet.
                if (data == null) {
                    return level.getBlockState(surfacePosition).is(BlockTags.LEAVES);
                } else if (data.isValidLandingBlock(level.getBlockState(surfacePosition))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Supplies attributes for the entity, in this case just 1 points of
     * maximum health (0.5 hearts).
     * @return The updated attribute supplier.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1d);
    }

    /**
     * Used to add extra parameters to the entity's save data.
     * @param tag The tag containing the extra save data.
     */
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(DIRECTION, this.entityData.get(DATA_DIRECTION).getName());
    }

    /**
     * Set persistence if we are spawning from a spawn egg.
     * Attach to leaf block if not already attached.
     * @param levelAccessor Access to the level.
     * @param difficulty The local difficulty.
     * @param spawnType The type of spawn.
     * @param groupData The group data.
     * @return The updated group data.
     */
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor,
                                        @NotNull DifficultyInstance difficulty,
                                        @NotNull EntitySpawnReason spawnType,
                                        @Nullable SpawnGroupData groupData) {
        if (spawnType == EntitySpawnReason.SPAWN_ITEM_USE) {
            setPersistenceRequired();
        }

        if (!levelAccessor.hasChunkAt(getSurfaceBlockPos())) {
            this.setSurfaceDirection(Direction.DOWN);
        }

        if (getIsReleased()) {
            if (levelAccessor.hasChunkAt(getSurfaceBlockPos()) &&
                    !this.getData().isValidLandingBlock(levelAccessor.getBlockState(getSurfaceBlockPos()))) {

                for (Direction direction : Direction.values()) {
                    BlockPos surfacePosition = this.blockPosition().relative(direction);
                    if (levelAccessor.hasChunkAt(surfacePosition) &&
                            this.getData().isValidLandingBlock(levelAccessor.getBlockState(surfacePosition))) {

                        this.setSurfaceDirection(direction);

                        Vec3 position = this.position();
                        double x = position.x();
                        double y = position.y();
                        double z = position.z();

                        switch (direction) {
                            case DOWN -> y = Math.floor(position.y());
                            case UP -> y = Math.floor(position.y()) + 1.0d;
                            case NORTH -> z = Math.floor(position.z());
                            case SOUTH -> z = Math.floor(position.z()) + 1.0d;
                            case WEST -> x = Math.floor(position.x());
                            case EAST -> x = Math.floor(position.x()) + 1.0d;
                        }

                        this.moveTo(x, y, z, 0.0F, 0.0F);

                        break;
                    }
                }
            }
        }

        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, groupData);
    }

    /**
     * Caterpillars and chrysalises won't produce offspring.
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
     * Get the direction to the surface the caterpillar is crawling on.
     * @return The direction of the block (UP, DOWN, NORTH, SOUTH, EAST, WEST).
     */
    @NotNull
    public Direction getSurfaceDirection() {
        return entityData.get(DATA_DIRECTION);
    }

    /**
     * Override to read any additional save data.
     * @param tag The tag containing the entity's save data.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        // Get the direction
        if (tag.contains(DIRECTION)) {
            String name = tag.getString(DIRECTION);
            Direction direction = Direction.byName(name);
            if (direction != null) {
                this.entityData.set(DATA_DIRECTION, direction);
            }
        }
    }

    /**
     * Set the direction of the block that the caterpillar is crawling on.
     * @param direction The direction of the surface block.
     */
    public void setSurfaceDirection(Direction direction) {
            this.entityData.set(DATA_DIRECTION, direction);
    }

    /**
     * Overridden so that butterfly entities will render at a decent distance.
     * @param distance The distance to check.
     * @return TRUE if we should render the entity.
     */
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize();
        if (d0 < 1.0D) {
            d0 = 1.0D;
        }

        d0 *= 64.0D * getViewScale();
        return distance < d0 * d0;
    }

    /**
     * Increase age at a quicker rate.
     */
    @Override
    protected void customServerAiStep(@NotNull ServerLevel level) {
        super.customServerAiStep(level);
        this.setAge(this.getAge() + 1);
    }

    /**
     * Construction
     * @param entityType The type of the entity.
     * @param level      The current level.
     */
    protected DirectionalCreature(EntityType<? extends DirectionalCreature> entityType,
                                  Level level) {
        super(entityType, level);
    }

    /**
     * Set the texture to use.
     * @param texture The entity's texture.
     */
    protected void setTexture(String texture) {
        this.texture = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, texture);
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DIRECTION, Direction.DOWN);
    }

    /**
     * Accessor to help get butterfly data when needed.
     * @return A valid butterfly data entry.
     */
    protected ButterflyData getData() {
        if (this.data == null) {
            this.data = ButterflyData.getButterflyDataForEntity(this);
        }

        return this.data;
    }

    /**
     * Check if the entity is free to move around.
     * @return TRUE if the entity is free.
     */
    protected boolean getIsReleased() {
        return true;
    }

    /**
     * Get the position of the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    protected BlockPos getSurfaceBlockPos() {
        return switch (this.getSurfaceDirection().getAxisDirection()) {
            case POSITIVE -> this.blockPosition();
            case NEGATIVE -> this.blockPosition().relative(this.getSurfaceDirection());
        };
    }

    /**
     * Get the texture to use for rendering.
     * @return The resource location of the texture.
     */
    public ResourceLocation getTexture() {
        return texture;
    }
}
