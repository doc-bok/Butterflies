package com.bokmcdok.butterflies.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

/**
 * Contains static methods with common code used by multiple classes.
 */
public class EntityBehaviours {

    /**
     * Custom travel code for flying entities. Used by Peacemaker Butterflies
     * and Butterflies/Moths.
     * @param entity The flying entity.
     * @param velocity The velocity to travel.
     * @param ground The position of the ground that effects the entity's movement.
     */
    public static void travel(@NotNull PathfinderMob entity,
                              @NotNull Vec3 velocity,
                              BlockPos ground) {
        if (entity.isControlledByLocalInstance()) {

            if (entity.isInWater()) {
                entity.moveRelative(0.02F, velocity);
                entity.move(MoverType.SELF, entity.getDeltaMovement());
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.8F));

            } else if (entity.isInLava()) {
                entity.moveRelative(0.02F, velocity);
                entity.move(MoverType.SELF, entity.getDeltaMovement());
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));

            } else {
                float friction = 0.91F;
                if (entity.onGround()) {
                    Level level = entity.level();
                    friction = level.getBlockState(ground).getFriction(level, ground, entity) * 0.91F;
                }

                float frictionCoefficient = 0.16277137F / (friction * friction * friction);

                entity.moveRelative(entity.onGround() ? 0.1F * frictionCoefficient : 0.02F, velocity);
                entity.move(MoverType.SELF, entity.getDeltaMovement());
                entity.setDeltaMovement(entity.getDeltaMovement().scale(friction));
            }
        }

        entity.calculateEntityAnimation(false);
    }

    /**
     * Finalize convert - finishes off spawning mobs that are converted from
     * one entity to another.
     * @param level The current level.
     * @param oldMob The old mob to replace.
     * @param newMob The new mob to spawn.
     * @param <T> The entity type of the old mob.
     * @param <U> The entity type of the new mob.
     */
    @SuppressWarnings({"deprecation", "OverrideOnly"})
    public static <T extends Mob, U extends Mob> void finalizeConvert(ServerLevelAccessor level,
                                                                      T oldMob,
                                                                      U newMob) {
        if (newMob != null) {
            newMob.finalizeSpawn(level,
                    level.getCurrentDifficultyAt(newMob.blockPosition()),
                    MobSpawnType.CONVERSION,
                    null,
                    null);

            EventHooks.onLivingConvert(oldMob, newMob);

            if (!newMob.isSilent()) {
                level.levelEvent(null, 1026, newMob.blockPosition(), 0);
            }
        }
    }
}
