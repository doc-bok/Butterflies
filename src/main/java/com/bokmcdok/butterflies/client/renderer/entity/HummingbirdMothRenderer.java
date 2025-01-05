package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.HummingbirdMothModel;
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
public class HummingbirdMothRenderer extends MobRenderer<Butterfly, HummingbirdMothModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public HummingbirdMothRenderer(EntityRendererProvider.Context context) {
        super(context, new HummingbirdMothModel(context.bakeLayer(HummingbirdMothModel.LAYER_LOCATION)), 0.2F);
    }

    /**
     * Gets the texture to use
     * @param entity The butterfly entity
     * @return The texture to use for this entity
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Butterfly entity) {
        return entity.getTexture();
    }

    /**
     * Scale the entity down
     * @param entity The butterfly entity
     * @param poses The current entity pose
     * @param scale The scale that should be applied
     */
    @Override
    protected void scale(@NotNull Butterfly entity, PoseStack poses, float scale) {
        float s = entity.getScale();
        poses.scale(s, s, s);
    }
}
