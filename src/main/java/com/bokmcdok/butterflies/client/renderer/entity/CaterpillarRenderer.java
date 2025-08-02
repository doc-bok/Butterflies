package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.CaterpillarModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.CaterpillarRenderState;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
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

/**
 * The renderer for the caterpillar entity.
 */
@OnlyIn(Dist.CLIENT)
public class CaterpillarRenderer extends MobRenderer<Caterpillar, CaterpillarRenderState, CaterpillarModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public CaterpillarRenderer(EntityRendererProvider.Context context) {
        super(context, new CaterpillarModel(context.bakeLayer(CaterpillarModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public CaterpillarRenderState createRenderState() {
        return new CaterpillarRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull Caterpillar entity,
                                   @NotNull CaterpillarRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        // Only extract debug info if we need it.
        if (ButterfliesConfig.Server.debugInformation.get()) {
            renderState.debugInfo = entity.getDebugInfo();
        }

        renderState.surfaceDirection = entity.getSurfaceDirection();
        renderState.renderScale = entity.getRenderScale();
        renderState.texture = entity.getTexture();
    }

    /**
     * Gets the texture to use
     * @param renderState The current render state.
     * @return The texture to use for this entity
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull CaterpillarRenderState renderState) {
        return renderState.texture;
    }

    /**
     * Scale the entity down
     * @param renderState The current render state.
     * @param poses The current entity pose
     */
    @Override
    protected void scale(@NotNull CaterpillarRenderState renderState,
                         PoseStack poses) {
        float s = renderState.renderScale;
        poses.scale(s, s, s);
    }

    /**
     * Rotates the caterpillar, so it's attached to its block.
     * @param renderState The current render state.
     * @param poseStack The posed model to render.
     * @param multiBufferSource The render buffer.
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull CaterpillarRenderState renderState,
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

        super.render(renderState, poseStack, multiBufferSource, packedLightCoordinates);
    }
}
