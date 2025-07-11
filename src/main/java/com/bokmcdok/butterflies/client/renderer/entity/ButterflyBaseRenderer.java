package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for butterfly renderers. Contains some common methods.
 * @param <T> The type of entity, which must be a Butterfly type.
 * @param <M> The model used to render the entity.
 */
@OnlyIn(Dist.CLIENT)
public abstract class ButterflyBaseRenderer<T extends Butterfly, M extends EntityModel<T>> extends MobRenderer<T, M> {

    /**
     * Construction
     * @param context The rendering context.
     * @param entityModel The model to use with this renderer.
     * @param shadowRadius The radius of the shadow for this model.
     */
    public ButterflyBaseRenderer(EntityRendererProvider.Context context,
                                 M entityModel,
                                 float shadowRadius) {
        super(context, entityModel, shadowRadius);
    }

    /**
     * Gets the texture to use
     * @param entity The butterfly entity
     * @return The texture to use for this entity
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return entity.getTexture();
    }

    /**
     * Render any debug information for this entity.
     * @param entity The entity to render the information for.
     * @param poseStack The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    protected void renderDebugInfo(@NotNull T entity,
                                   @NotNull PoseStack poseStack,
                                   @NotNull MultiBufferSource multiBufferSource,
                                   int packedLightCoordinates) {
        EntityDebugInfoRenderer.renderDebugInfo(
                entity,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);

    }

    /**
     * Rotate the butterfly if it is landed.
     * @param entity The entity to render the information for.
     * @param poseStack The pose stack.
     */
    protected void rotateIfLanded(@NotNull T entity,
                                  @NotNull PoseStack poseStack) {

        if (entity.getIsLanded()) {
            switch (entity.getLandedDirection()) {
                case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(180.f));
                case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90.f));
                case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.f));
                case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90.f));
                case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90.f));
                default -> {
                }
            }
        }
    }

    /**
     * Scale the entity down
     * @param entity The butterfly entity
     * @param poses  The current entity pose
     * @param scale  The scale that should be applied
     */
    @Override
    protected void scale(@NotNull Butterfly entity, PoseStack poses, float scale) {
        float s = entity.getRenderScale();
        poses.scale(s, s, s);
    }
}
