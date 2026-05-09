package com.bokmcdok.butterflies.world.entity.monster;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A Peacemaker Cow entity.
 */
public class PeacemakerCow extends Monster {

    // Constants for Peacemaker Butterfly attributes.
    private static final double PEACEMAKER_COW_HEALTH = 60.0d;
    private static final double PEACEMAKER_COW_SPEED = 0.1d;

    /**
     * Generates attributes for the Peacemaker Cow.
     * @return A builder containing the mob's attributes.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, PEACEMAKER_COW_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_COW_SPEED);
    }

    /**
     * Construction
     * @param entityType The type of this entity.
     * @param level The current level.
     */
    public PeacemakerCow(EntityType<? extends Monster> entityType,
                            Level level) {
        super(entityType, level);
    }

    /**
     * Override the default bounding box creation so we can have a non-cube BB.
     * @return The bounding box for the Peacemaker Cow.
     */
    @NotNull
    @Override
    protected AABB makeBoundingBox() {
        EntityDimensions dimensions = getDimensions(getPose());
        double width = dimensions.width / 2.0D;
        double height = dimensions.height;
        double length = dimensions.width + 0.25D;
        Vec3 position = position();
        return new AABB(
                position.x - length, position.y,          position.z - width,
                position.x + length, position.y + height, position.z + width);
    }


}
