package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyModel;
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
public class ButterflyRenderer extends ButterflyBaseRenderer<Butterfly, ButterflyModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.2F);
    }

    /**
     * Override to fix a bug with the model's orientation.
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
        rotateIfLanded(entity, poseStack);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, packedLightCoordinates);

        poseStack.popPose();

        renderDebugInfo(entity, poseStack, multiBufferSource, packedLightCoordinates);
    }
}