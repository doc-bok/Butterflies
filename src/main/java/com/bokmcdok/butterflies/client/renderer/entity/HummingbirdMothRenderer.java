package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.HummingbirdMothModel;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
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
        super(context, new HummingbirdMothModel(context.bakeLayer(HummingbirdMothModel.LAYER_LOCATION)), 0.2F);
    }

    /**
     * Override to provide debug information.
     * @param entity            The butterfly entity.
     * @param p_115456_         Unknown.
     * @param p_115457_         Unknown.
     * @param poseStack         The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull Butterfly entity,
                       float p_115456_,
                       float p_115457_,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int packedLightCoordinates) {

        poseStack.pushPose();
        poseStack.translate(0.0, -0.1, 0.0);
        rotateIfLanded(entity, poseStack);

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, packedLightCoordinates);

        poseStack.popPose();

        renderDebugInfo(entity, poseStack, multiBufferSource, packedLightCoordinates);
    }
}
