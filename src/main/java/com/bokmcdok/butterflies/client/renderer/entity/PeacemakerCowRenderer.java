package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.PeacemakerCowModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.PeacemakerButterflyRenderState;
import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.entity.animal.PeacemakerCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * A renderer for a Peacemaker Cow.
 */
public class PeacemakerCowRenderer extends MobRenderer<PeacemakerCow, LivingEntityRenderState, PeacemakerCowModel> {

    /**
     * Create a new renderer for the Peacemaker Cow.
     * @param context The current rendering context
     */
    public PeacemakerCowRenderer(EntityRendererProvider.Context context) {
        super(context, new PeacemakerCowModel(context.bakeLayer(PeacemakerCowModel.LAYER_LOCATION)), 2.0F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    /**
     * Get the texture to map to the model.
     * @param renderState The current render state.
     * @return The Resource Location of the texture.
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull LivingEntityRenderState renderState) {
        return ButterflyTextures.PEACEMAKER_COW;
    }
}
