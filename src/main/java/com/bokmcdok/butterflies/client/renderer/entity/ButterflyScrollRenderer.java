package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Renders a butterfly scroll hanging off a surface.
 */
public class ButterflyScrollRenderer extends EntityRenderer<ButterflyScroll> {

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/admiral.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/buckeye.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/cabbage.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/chalkhill.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/clipper.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/common.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/emperor.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/forester.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/glasswing.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/hairstreak.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/heath.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/longwing.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/monarch.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/morpho.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/rainbow.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/swallowtail.png")
    };

    private final ButterflyScrollModel model;

    /**
     * Construction
     * @param context The rendering context.
     */
    public ButterflyScrollRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ButterflyScrollModel(context.bakeLayer(ButterflyScrollModel.LAYER_LOCATION));
    }

    public float getScale() {
        return 0.123f;
    }

    @Override
    public void render(@NotNull ButterflyScroll scroll,
                       float yaw,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffers,
                       int overlay)
    {
        poseStack.pushPose();
        float scale = this.getScale();
        poseStack.scale(scale, -scale, -scale);
        RenderType renderType = RenderType.itemEntityTranslucentCull(getTextureLocation(scroll));
        VertexConsumer vertexConsumer = buffers.getBuffer(renderType);
        model.renderToBuffer(poseStack, vertexConsumer, overlay, 0, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ButterflyScroll scroll) {
        return TEXTURES[0];
    }
}
