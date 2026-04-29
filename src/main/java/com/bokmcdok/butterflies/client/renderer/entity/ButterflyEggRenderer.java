package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyEggModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyEggRenderState;
import com.bokmcdok.butterflies.client.renderer.entity.state.ButterflyRenderState;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Renderer for a butterfly egg entity.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyEggRenderer extends DirectionalBaseRenderer<ButterflyEgg, ButterflyEggRenderState, ButterflyEggModel> {

    /**
     * Construction
     * @param context The current rendering context.
     */
    public ButterflyEggRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyEggModel(context.bakeLayer(ButterflyEggModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public ButterflyEggRenderState createRenderState() {
        return new ButterflyEggRenderState();
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param state The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull ButterflyEgg entity,
                                   @NotNull ButterflyEggRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.surfaceDirection = entity.getSurfaceDirection();
        state.renderScale = entity.getRenderScale();
        state.texture = entity.getTexture();
    }

    /**
     * Gets the texture to use.
     * @param renderState The current render state.
     * @return The texture to use for this entity.
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ButterflyEggRenderState renderState) {
        return renderState.texture;
    }

    /**
     * Scale the entity down.
     * @param renderState The current render state.
     * @param poses The current entity pose.
     */
    @Override
    protected void scale(@NotNull ButterflyEggRenderState renderState,
                         PoseStack poses) {
        float s = renderState.renderScale;
        poses.scale(s, s, s);
    }
}
