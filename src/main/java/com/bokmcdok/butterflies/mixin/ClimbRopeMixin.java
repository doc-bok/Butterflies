package com.bokmcdok.butterflies.mixin;

import com.bokmcdok.butterflies.world.entity.decoration.RopeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

/**
 * A mixin to allow ropes to be climbed.
 */
@SuppressWarnings("UnstableApiUsage")
@Mixin(value = ForgeHooks.class, priority = 888)
public class ClimbRopeMixin {

    @Unique
    private static final double SEARCH_XZ = 0.25d;

    @Unique
    private static final double SEARCH_Y = 0.1d;

    /**
     * Inject code into `isOnLivingLadder()`
     * @param state The current block state.
     * @param level The current level.
     * @param pos The current block position.
     * @param entity The entity we are testing.
     * @param returnable The return value callback.
     */
    @Inject(remap = false, method = "isLivingOnLadder", at = @At("HEAD"), cancellable = true)
    private static void isLivingOnLadder(@NotNull BlockState state,
                                         @NotNull Level level,
                                         @NotNull BlockPos pos,
                                         @NotNull LivingEntity entity,
                                         CallbackInfoReturnable<Optional<BlockPos>> returnable) {

        if (entity instanceof Player player &&
                player.isSpectator()) {
            returnable.setReturnValue(Optional.empty());
        }

        RopeEntity rope = butterflies$findClimbableRope(entity);
        if (rope != null) {
            returnable.setReturnValue(Optional.of(pos));
        }
    }

    /**
     * Find any climbable ropes the entity is colliding with.
     * @param entity The entity we are testing.
     * @return A rope if the entity is colliding with one, otherwise NULL.
     */
    @Unique
    private static RopeEntity butterflies$findClimbableRope(Entity entity) {

        AABB searchBox = entity.getBoundingBox().inflate(SEARCH_XZ, SEARCH_Y, SEARCH_XZ);
        List<RopeEntity> ropes = entity.level().getEntitiesOfClass(RopeEntity.class, searchBox);

        for (RopeEntity rope : ropes) {
            AABB playerBox = entity.getBoundingBox();
            if (playerBox.intersects(butterflies$getClimbBox(rope))) {
                return rope;
            }
        }

        return null;
    }

    /**
     * Helper to get the collision box for climbing a rope.
     * @param rope The rope to test.
     * @return A bounding box for climbing.
     */
    @Unique
    private static AABB butterflies$getClimbBox(RopeEntity rope) {
        AABB box = rope.getBoundingBox();
        return new AABB(
                box.minX - 0.1d, box.minY, box.minZ - 0.1d,
                box.maxX + 0.1d, box.maxY, box.maxZ + 0.1d
        );
    }
}
