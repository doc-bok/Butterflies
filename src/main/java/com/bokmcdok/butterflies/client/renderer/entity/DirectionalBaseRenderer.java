package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.world.entity.animal.DirectionalCreature;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for rendering directional creatures.
 * @param <T> The entity class of the creature.
 * @param <M> The model used to render the creature.
 */
@OnlyIn(Dist.CLIENT)
public abstract class DirectionalBaseRenderer<T extends DirectionalCreature, M extends EntityModel<T>> extends MobRenderer<T, M> {

    /**
     * Construction
     * @param context The rendering context.
     * @param model The model to use with this renderer.
     * @param shadowRadius The radius of the shadow for this model.
     */
    public DirectionalBaseRenderer(EntityRendererProvider.Context context,
                                   M model,
                                   float shadowRadius) {
        super(context, model, shadowRadius);
    }

    /**
     * Rotates the creature so it's attached to its block.
     * @param entity The directional entity.
     * @param yaw The current yaw.
     * @param partialTicks The current partial ticks.
     * @param poseStack The matrix stack.
     * @param buffers The render buffers.
     * @param overlay The overlay.
     */
    @Override
    public void render(@NotNull T entity,
                       float yaw,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffers,
                       int overlay) {

        Direction direction = entity.getSurfaceDirection();
        if (direction == Direction.UP) {
            poseStack.mulPose(Axis.XP.rotationDegrees(180.f));
        } else if (direction == Direction.NORTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.f));
        } else if (direction == Direction.SOUTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.f));
        } else if (direction == Direction.WEST) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(-90.f));
        } else if (direction == Direction.EAST){
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.f));
        }

        super.render(entity, yaw, partialTicks, poseStack, buffers, overlay);
    }
}
