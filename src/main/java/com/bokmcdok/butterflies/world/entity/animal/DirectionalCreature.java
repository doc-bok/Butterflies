package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.mojang.logging.LogUtils;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public abstract class DirectionalCreature extends Animal {

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Direction> DATA_DIRECTION =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.DIRECTION);

    protected static final EntityDataAccessor<BlockPos> DATA_SURFACE_BLOCK =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.BLOCK_POS);

    // Names of the attributes stored in the save data.
    protected static final String DIRECTION = "direction";
    protected static final String SURFACE_BLOCK = "surface_block";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The location of the texture that the renderer should use.
    private final ResourceLocation texture;

    /**
     * Check if a directional creature can spawn in this position.
     * @param entityType The type of the entity.
     * @param level The current level.
     * @param spawnType The type of spawn.
     * @param blockPos The position we want to spawn the entity.
     * @param random A random source.
     * @return TRUE if there is a leaf block nearby.
     */
    public static boolean checkDirectionalSpawnRules(EntityType<? extends Mob> entityType,
                                                     LevelAccessor level,
                                                     MobSpawnType spawnType,
                                                     BlockPos blockPos,
                                                     RandomSource random) {
        for (Direction direction : Direction.values()) {
            BlockPos surfacePosition = blockPos.relative(direction);
            if (level.hasChunkAt(surfacePosition)) {
                if (level.getBlockState(surfacePosition).is(BlockTags.LEAVES)) {
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
        tag.putString(SURFACE_BLOCK, this.entityData.get(DATA_SURFACE_BLOCK).toShortString());
    }

    /**
     * Set persistence if we are spawning from a spawn egg.
     * Attach to leaf block if not already attached.
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

        if (!levelAccessor.hasChunkAt(getSurfaceBlockPos())) {
            this.setSurfaceBlockPos(this.blockPosition().below());
            this.setSurfaceDirection(Direction.DOWN);
        }

        if (levelAccessor.hasChunkAt(getSurfaceBlockPos()) &&
                !levelAccessor.getBlockState(getSurfaceBlockPos()).is(BlockTags.LEAVES)) {

            for (Direction direction : Direction.values()) {
                BlockPos surfacePosition = this.blockPosition().relative(direction);
                if (levelAccessor.hasChunkAt(surfacePosition) &&
                        levelAccessor.getBlockState(surfacePosition).is(BlockTags.LEAVES)) {

                    this.setSurfaceDirection(direction);
                    this.setSurfaceBlockPos(surfacePosition);

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

        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, groupData, compoundTag);
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

        // Get the surface block
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
     * Set the position of the block the caterpillar is crawling on.
     * @param position The position of the block.
     */
    public void setSurfaceBlockPos(BlockPos position) {
        this.entityData.set(DATA_SURFACE_BLOCK, position);
    }

    /**
     * Set the direction of the block that the caterpillar is crawling on.
     * @param direction The direction of the surface block.
     */
    public void setSurfaceDirection(Direction direction) {
        this.entityData.set(DATA_DIRECTION, direction);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.setAge(this.getAge() + 1);
    }

    /**
     * Construction
     * @param texture The location of the texture used to render the entity.
     * @param entityType The entity's type.
     * @param level The current level.
     */
    protected DirectionalCreature(String texture,
                                  EntityType<? extends DirectionalCreature> entityType,
                                  Level level) {
        super(entityType, level);
        this.texture = new ResourceLocation(ButterfliesMod.MODID, texture);
    }

    /**
     * Override to define extra data to be synced between server and client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DIRECTION, Direction.DOWN);
        this.entityData.define(DATA_SURFACE_BLOCK, new BlockPos(0,0,0));
    }

    /**
     * Get the texture to use for rendering.
     * @return The resource location of the texture.
     */
    public ResourceLocation getTexture() {
        return texture;
    }

    /**
     * Get the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    protected Block getSurfaceBlock() {
        return getSurfaceBlockState().getBlock();
    }

    /**
     * Get the position of the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    protected BlockPos getSurfaceBlockPos() {
        return this.entityData.get(DATA_SURFACE_BLOCK);
    }

    /**
     * Get the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    protected BlockState getSurfaceBlockState() {
        BlockPos position = getSurfaceBlockPos();
        return this.level().getBlockState(position);
    }
}
