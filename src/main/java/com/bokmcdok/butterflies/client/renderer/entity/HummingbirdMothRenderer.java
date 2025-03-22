package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.HummingbirdMothModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.HummingbirdMothRenderState;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
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
public class HummingbirdMothRenderer extends MobRenderer<Butterfly, HummingbirdMothRenderState, HummingbirdMothModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public HummingbirdMothRenderer(EntityRendererProvider.Context context) {
        super(context, new HummingbirdMothModel(context.bakeLayer(HummingbirdMothModel.LAYER_LOCATION)), 0.2F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public HummingbirdMothRenderState createRenderState() {
        return new HummingbirdMothRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull Butterfly entity,
                                   @NotNull HummingbirdMothRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        renderState.renderScale = entity.getRenderScale();
        renderState.texture = entity.getTexture();
    }

    /**
     * Gets the texture to use.
     * @param renderState The current render state.
     * @return The texture to use for this entity
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull HummingbirdMothRenderState renderState) {
        return renderState.texture;
    }

    /**
     * Scale the entity down
     * @param renderState The current render state.
     * @param poses The current entity pose
     */
    @Override
    protected void scale(@NotNull HummingbirdMothRenderState renderState, PoseStack poses) {
        float s = renderState.renderScale;
        poses.scale(s, s, s);
    }
}
