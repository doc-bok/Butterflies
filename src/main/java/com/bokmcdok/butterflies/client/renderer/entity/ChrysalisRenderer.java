package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.CaterpillarRenderState;
import com.bokmcdok.butterflies.client.renderer.entity.state.ChrysalisRenderState;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChrysalisRenderer extends MobRenderer<Chrysalis, ChrysalisRenderState, ChrysalisModel> {

    /**
     * Construction
     * @param context The current rendering context.
     */
    public ChrysalisRenderer(EntityRendererProvider.Context context) {
        super(context, new ChrysalisModel(context.bakeLayer(ChrysalisModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public ChrysalisRenderState createRenderState() {
        return new ChrysalisRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull Chrysalis entity,
                                   @NotNull ChrysalisRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        renderState.surfaceDirection = entity.getSurfaceDirection();
        renderState.renderScale = entity.getRenderScale();
        renderState.texture = entity.getTexture();
    }

    /**
     * Gets the texture to use.
     * @param renderState The current render state.
     * @return The texture to use for this entity.
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ChrysalisRenderState renderState) {
        return renderState.texture;
    }

    /**
     * Scale the entity down.
     * @param renderState The current render state.
     * @param poses The current entity pose.
     */
    @Override
    protected void scale(@NotNull ChrysalisRenderState renderState,
                         PoseStack poses) {
        float s = renderState.renderScale;
        poses.scale(s, s, s);
    }

    /**
     * Rotates the chrysalis, so it's attached to its block.
     * @param renderState The current render state.
     * @param poseStack The posed model to render.
     * @param multiBufferSource The render buffer.
     * @param p_115460_ Unknown.
     */
    @Override
    public void render(@NotNull ChrysalisRenderState renderState,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int p_115460_) {
        Direction direction = renderState.surfaceDirection;
        if (direction == Direction.UP) {
            poseStack.mulPose(Axis.XP.rotationDegrees(180.f));
        } else if (direction == Direction.NORTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.f));
        } else if (direction == Direction.SOUTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.f));
        } else if (direction == Direction.WEST) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(-90.f));
        } else if (direction == Direction.EAST){
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.f));
        }

        super.render(renderState, poseStack, multiBufferSource, p_115460_);
    }
}
