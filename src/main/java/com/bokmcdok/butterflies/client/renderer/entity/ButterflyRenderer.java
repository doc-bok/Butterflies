package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * This is the renderer for all the butterflies and moths in the game.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer extends ButterflyBaseRenderer<Butterfly, ButterflyModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)));
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
        rotateIfLanded(renderState, poseStack);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));

        super.render(renderState, poseStack, multiBufferSource, packedLightCoordinates);

        poseStack.popPose();

        renderDebugInfo(renderState, poseStack, multiBufferSource, packedLightCoordinates);
    }
}