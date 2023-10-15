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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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

    // The location of the texture that the renderer should use.
    private final ResourceLocation texture;

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
     * Get the position of the block the caterpillar is crawling on.
     * @return The position of the block.
     */
    protected BlockPos getSurfaceBlock() {
        return this.entityData.get(DATA_SURFACE_BLOCK);
    }
}
