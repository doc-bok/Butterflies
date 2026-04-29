package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ChrysalisRenderState;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChrysalisRenderer extends DirectionalBaseRenderer<Chrysalis, ChrysalisRenderState, ChrysalisModel> {

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
}
