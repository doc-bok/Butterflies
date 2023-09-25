package com.bokmcdok.butterflies.world.entity.ambient;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class DirectionalCreature extends AmbientCreature {

    // Serializers for data stored in the save data.
    protected static final EntityDataAccessor<Integer> DATA_AGE =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Direction> DATA_DIRECTION =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.DIRECTION);

    protected static final EntityDataAccessor<Boolean> DATA_PERSISTENT =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<BlockPos> DATA_SURFACE_BLOCK =
            SynchedEntityData.defineId(DirectionalCreature.class, EntityDataSerializers.BLOCK_POS);

    // Names of the attributes stored in the save data.
    protected static final String AGE = "age";
    protected static final String DIRECTION = "direction";
    protected static final String PERSISTENT = "persistent";
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
        tag.putInt(AGE, this.entityData.get(DATA_AGE));
        tag.putString(DIRECTION, this.entityData.get(DATA_DIRECTION).getName());
        tag.putBoolean(PERSISTENT, this.entityData.get(DATA_PERSISTENT));
        tag.putString(SURFACE_BLOCK, this.entityData.get(DATA_SURFACE_BLOCK).toShortString());
    }

    /**
     * Get the age of the entity.
     * @return The age.
     */
    protected int getAge() {
        return this.entityData.get(DATA_AGE);
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

        // Get the age.
        if (tag.contains(AGE)) {
            this.entityData.set(DATA_AGE, tag.getInt(AGE));
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
                                  EntityType<? extends AmbientCreature> entityType,
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
        this.entityData.define(DATA_AGE, 0);
        this.entityData.define(DATA_PERSISTENT, false);
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

    /**
     * Set the age of the entity.
     * @param age The new age.
     */
    private void setAge(int age) {
        this.entityData.set(DATA_AGE, age);
    }
}
