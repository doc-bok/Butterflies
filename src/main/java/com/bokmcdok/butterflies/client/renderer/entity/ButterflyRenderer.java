package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * This is the renderer for all the butterflies and moths in the game.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyRenderState, ButterflyModel> {
    /**
     * Bakes a new model for the renderer
     *
     * @param context The current rendering context
     */
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.2F);
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
    public void extractRenderState(@NotNull Butterfly entity,
                                   @NotNull ButterflyRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        // Only extract debug info if we need it.
        if (ButterfliesConfig.debugInformation.get()) {
            renderState.debugInfo = entity.getDebugInfo();
        }

        renderState.isLanded = entity.getIsLanded();
        renderState.isMoth = entity.getIsMoth();
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
     * Override to fix a bug with the model's orientation.
     * @param renderState The current render state.
     * @param poseStack         The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull ButterflyRenderState renderState,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int packedLightCoordinates) {

        // Render any debug information for this entity.
        EntityDebugInfoRenderer.renderDebugInfo(
                renderState.debugInfo,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);

        poseStack.pushPose();

        // Rotate the butterfly if it is landed.
        if (entity.getIsLanded()) {
            switch (entity.getLandedDirection()) {
                case UP:
                    poseStack.mulPose(Axis.XP.rotationDegrees(180.f));
                    break;

                case NORTH:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.f));
                    break;

                case SOUTH:
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90.f));
                    break;

                case EAST:
                    poseStack.mulPose(Axis.ZP.rotationDegrees(90.f));
                    break;

                case WEST:
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90.f));
                    break;

                default:
                    break;
            }
        }

        // When the models were initially created no thought was given as to
        // what orientation they needed to be in. Rotating them here allows
        // them to use Minecraft's pathfinding systems without having to redo
        // the model from scratch.
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));

        super.render(renderState, poseStack, multiBufferSource, packedLightCoordinates);
        poseStack.popPose();
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