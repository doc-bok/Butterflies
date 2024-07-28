package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class Chrysalis extends DirectionalCreature {

    //  The name this entity is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return ButterflySpeciesList.SPECIES[butterflyIndex] + "_chrysalis";
    }

    /**
     * Spawns a chrysalis into the world.
     * @param level The level to spawn the chrysalis.
     * @param location The resource location of the chrysalis to spawn.
     * @param spawnBlock The block to spawn the chrysalis on.
     * @param surfaceDirection The direction the chrysalis is facing.
     * @param position The position of the chrysalis.
     * @param yRotation The y-rotation of the chrysalis.
     */
    public static void spawn(ServerLevel level,
                             ResourceLocation location,
                             BlockPos spawnBlock,
                             Direction surfaceDirection,
                             Vec3 position,
                             float yRotation) {
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(location);
        if (entityType != null) {
            Entity entity = entityType.create(level);
            if (entity instanceof Chrysalis chrysalis) {

                chrysalis.moveTo(position.x, position.y, position.z, 0.0F, 0.0F);
                chrysalis.setYRot(yRotation);
                chrysalis.setSurfaceDirection(surfaceDirection);

                chrysalis.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(spawnBlock),
                        MobSpawnType.NATURAL,
                        null,
                        null);

                level.addFreshEntity(chrysalis);
            }
        }
    }

    /**
     * Reduce the size of the caterpillar - they are small!
     * @return The new size of the caterpillar.
     */
    @Override
    public float getScale() {
        float scale = (float)getAge() / -24000.0f;
        scale = scale * 0.06f;
        scale = scale + 0.1f;

        switch (this.getData().size()) {
            case SMALL -> { return 0.7f * scale; }
            case LARGE ->{ return 1.28f * scale; }
            default -> { return scale; }
        }
    }

    /**
     * Chrysalises can't be fed by players.
     * @param stack The item stack the player tried to feed the chrysalis.
     * @return FALSE, indicating it isn't food.
     */
    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    /**
     * Overrides how an entity handles triggers such as tripwires and pressure
     * plates. Chrysalises aren't heavy enough to trigger either.
     *
     * @return Always TRUE, so caterpillars ignore block triggers.
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    /**
     * Chrysalises ignore gravity.
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * Override this to control if an entity can be pushed or not. Chrysalises
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
     * @param entityType The type of the entity.
     * @param level      The current level.
     */
    public Chrysalis(EntityType<? extends Chrysalis> entityType,
                        Level level) {
        super(entityType, level);

        String species = "undiscovered";
        String encodeId = this.getEncodeId();
        if (encodeId != null) {
            String[] split = encodeId.split(":");
            if (split.length >= 2) {
                species = split[1];
                split = species.split("_");
                if (split.length >=2) {
                    species = split[0];
                }
            }
        }

        setTexture("textures/entity/chrysalis/chrysalis_" + species + ".png");

        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
        setAge(-getData().chrysalisLifespan());
    }

    /**
     * A custom step for the AI update loop.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        // If the surface block is destroyed then the chrysalis dies.
        if (this.level().isEmptyBlock(getSurfaceBlockPos())) {
            kill();
        }

        // Spawn Butterfly.
        if (this.getAge() >= 0 && this.random.nextInt(0, 15) == 0) {
            ResourceLocation location = EntityType.getKey(this.getType());
            int index = ButterflyData.getButterflyIndex(location);
            ResourceLocation newLocation = ButterflyData.indexToButterflyEntity(index);
            if (newLocation != null) {
                Butterfly.spawn(this.level(), newLocation, this.blockPosition(), false);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    /**
     * Override to change how pushing other entities affects them. Chrysalises
     * don't push other entities.
     * @param otherEntity The other entity pushing/being pushed.
     */
    @Override
    protected void doPush(@NotNull Entity otherEntity) {
        // No-op
    }

    /**
     * Override to control what kind of movement events the entity will emit.
     * Chrysalises will not emit sounds.
     * @return Movement events only.
     */
    @NotNull
    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    /**
     * Override to control an entity's relative volume. Chrysalises are silent.
     * @return Always zero, so caterpillars are silent.
     */
    @Override
    protected float getSoundVolume() {
        return 0.0f;
    }

    /**
     * Override to change how pushing other entities affects them. Chrysalises
     * don't push other entities.
     */
    @Override
    protected void pushEntities() {
        // No-op
    }
}
