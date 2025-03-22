package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.ButterflyScrollModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyScrollRenderState;
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
public class ButterflyScrollRenderer extends EntityRenderer<ButterflyScroll, ButterflyScrollRenderState> {

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
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public ButterflyScrollRenderState createRenderState() {
        return new ButterflyScrollRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull ButterflyScroll entity,
                                   @NotNull ButterflyScrollRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        renderState.butterflyIndex = entity.getButterflyIndex();
        renderState.direction = entity.getDirection();
    }

    /**
     * Reduce the size of the texture image.
     * @return Around 12%.
     */
    public float getScale() {
        return 0.088f;
    }

    /**
     * Get the texture to use.
     * @param renderState The current render state.
     * @return The texture to render with.
     */
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ButterflyScrollRenderState renderState) {
        ButterflyData data = ButterflyData.getEntry(renderState.butterflyIndex);
        return data == null ? ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "textures/gui/butterfly_scroll/admiral.png") : data.getScrollTexture();
    }

    /**
     * Render the scroll entity.
     * @param renderState The current render state.
     * @param poseStack The matrix stack.
     * @param buffers The render buffers.
     * @param overlay The overlay.
     */
    @Override
    public void render(@NotNull ButterflyScrollRenderState renderState,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffers,
                       int overlay)
    {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.direction.get2DDataValue() * -90));
        poseStack.translate(0.31D, -0.31D, -0.075D);

        float scale = this.getScale();
        poseStack.scale(scale, -scale, -scale);

        RenderType renderType = RenderType.entitySmoothCutout(getTextureLocation(renderState));
        VertexConsumer vertexConsumer = buffers.getBuffer(renderType);
        model.renderToBuffer(poseStack, vertexConsumer, overlay, 0);
        poseStack.popPose();
    }
}
