package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.PeacemakerCowModel;
import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.entity.animal.PeacemakerCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * A renderer for a Peacemaker Cow.
 */
public class PeacemakerCowRenderer extends MobRenderer<PeacemakerCow, PeacemakerCowModel> {

    /**
     * Create a new renderer for the Peacemaker Cow.
     * @param context The current rendering context
     */
    public PeacemakerCowRenderer(EntityRendererProvider.Context context) {
        super(context, new PeacemakerCowModel(context.bakeLayer(PeacemakerCowModel.LAYER_LOCATION)), 2.0F);
    }

    /**
     * Get the texture to map to the model.
     * @param peacemakerCow The Peacemaker Cow entity.
     * @return The Resource Location of the texture.
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull PeacemakerCow peacemakerCow) {
        return ButterflyTextures.PEACEMAKER_COW;
    }
}
