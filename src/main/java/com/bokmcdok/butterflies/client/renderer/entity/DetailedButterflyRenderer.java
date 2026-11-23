package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * This is the renderer for all the butterflies and moths in the game.
 */
@OnlyIn(Dist.CLIENT)
public class DetailedButterflyRenderer extends ButterflyRenderer {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public DetailedButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.DETAILED_LAYER_LOCATION)), 0.2F);
    }
}