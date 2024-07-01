package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Renders a butterfly scroll hanging off a surface.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyScrollRenderer extends EntityRenderer<ButterflyScroll> {

    /**
     * The scroll model for rendering the scroll.
     */
    private final ButterflyScrollModel model;

    /**
     * Construction
     * @param context The rendering context.
     */
    public ButterflyScrollRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ButterflyScrollModel(context.bakeLayer(ButterflyScrollModel.LAYER_LOCATION));
    }

    /**
     * Reduce the size of the texture image.
     * @return Around 12%.
     */
    public float getScale() {
        return 0.088f;//0.123f;
    }

    /**
     * Get the texture to use.
     * @param scroll The current scroll.
     * @return The texture to render with.
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ButterflyScroll scroll) {
        return ButterflyData.indexToButterflyScrollTexture(scroll.getButterflyIndex());
    }

    /**
     * Render the scroll entity.
     * @param scroll The scroll entity to render.
     * @param yaw The current yaw.
     * @param partialTicks The current partial ticks.
     * @param poseStack The matrix stack.
     * @param buffers The render buffers.
     * @param overlay The overlay.
     */
    @Override
    public void render(@NotNull ButterflyScroll scroll,
                       float yaw,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffers,
                       int overlay)
    {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(scroll.getDirection().get2DDataValue() * -90));
        poseStack.translate(0.31D, -0.31D, -0.075D);

        float scale = this.getScale();
        poseStack.scale(scale, -scale, -scale);

        RenderType renderType = RenderType.entitySmoothCutout(getTextureLocation(scroll));
        VertexConsumer vertexConsumer = buffers.getBuffer(renderType);
        model.renderToBuffer(poseStack, vertexConsumer, overlay, 0, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }
}
