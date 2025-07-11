package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for butterfly renderers. Contains some common methods.
 * @param <T> The type of entity, which must be a Butterfly type.
 * @param <M> The model used to render the entity.
 */
@OnlyIn(Dist.CLIENT)
public abstract class ButterflyBaseRenderer<T extends Butterfly, M extends EntityModel<ButterflyRenderState>> extends MobRenderer<T, ButterflyRenderState, M> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public ButterflyBaseRenderer(EntityRendererProvider.Context context,
                                 M model) {
        super(context, model, 0.2F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public ButterflyRenderState createRenderState() {
        return new ButterflyRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull T entity,
                                   @NotNull ButterflyRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        // Only extract debug info if we need it.
        if (ButterfliesConfig.debugInformation.get()) {
            renderState.debugInfo = entity.getDebugInfo();
        }

        renderState.isLanded = entity.getIsLanded();
        renderState.isMoth = entity.getIsMoth();
        renderState.landedDirection = entity.getLandedDirection();
        renderState.renderScale = entity.getRenderScale();
        renderState.texture = entity.getTexture();
    }

    /**
     * Gets the texture to use.
     * @param renderState The current render state.
     * @return The texture to use for this entity
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull ButterflyRenderState renderState) {
        return renderState.texture;
    }

    /**
     * Render any debug information for this entity.
     * @param renderState The current render state.
     * @param poseStack The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    protected void renderDebugInfo(@NotNull ButterflyRenderState renderState,
                                   @NotNull PoseStack poseStack,
                                   @NotNull MultiBufferSource multiBufferSource,
                                   int packedLightCoordinates) {
        EntityDebugInfoRenderer.renderDebugInfo(
                renderState.debugInfo,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);

    }

    /**
     * Rotate the butterfly if it is landed.
     * @param renderState The current render state.
     * @param poseStack The pose stack.
     */
    protected void rotateIfLanded(@NotNull ButterflyRenderState renderState,
                                  @NotNull PoseStack poseStack) {

        if (renderState.isLanded) {
            switch (renderState.landedDirection) {
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
     * @param renderState The current render state.
     * @param poses  The current entity pose
     */
    @Override
    protected void scale(@NotNull ButterflyRenderState renderState,
                         PoseStack poses) {
        float s = renderState.renderScale;
        poses.scale(s, s, s);
    }
}
