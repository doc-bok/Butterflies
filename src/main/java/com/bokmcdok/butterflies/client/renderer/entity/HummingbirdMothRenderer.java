package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.HummingbirdMothModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The renderer for a hummingbird moth.
 */
@OnlyIn(Dist.CLIENT)
public class HummingbirdMothRenderer extends ButterflyBaseRenderer<Butterfly, HummingbirdMothModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public HummingbirdMothRenderer(EntityRendererProvider.Context context) {
        super(context, new HummingbirdMothModel(context.bakeLayer(HummingbirdMothModel.LAYER_LOCATION)));
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
        poseStack.translate(0.0, -0.1, 0.0);
        rotateIfLanded(renderState, poseStack);

        super.render(renderState, poseStack, multiBufferSource, packedLightCoordinates);

        poseStack.popPose();

        renderDebugInfo(renderState, poseStack, multiBufferSource, packedLightCoordinates);
    }
}
