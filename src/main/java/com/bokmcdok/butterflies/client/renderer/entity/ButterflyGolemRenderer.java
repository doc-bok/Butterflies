package com.bokmcdok.butterflies.client.renderer.entity;


import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.ButterflyGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.state.IronGolemRenderState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A custom renderer for the butterfly golem.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyGolemRenderer extends IronGolemRenderer {

    // The golem's texture location.
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            ButterfliesMod.MOD_ID,
            "textures/entity/butterfly_golem/butterfly_golem.png");

    /**
     * Construction - replace the iron golem model.
     * @param context The rendering context.
     */
    public ButterflyGolemRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ButterflyGolemModel(context.bakeLayer(ButterflyGolemModel.LAYER_LOCATION));
    }

    /**
     * Override the Iron Golem texture.
     * @param renderState The current render state.
     * @return The texture location.
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull IronGolemRenderState renderState) {
        return TEXTURE;
    }
}
