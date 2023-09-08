package com.bokmcdok.butterflies.renderer.entity;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.model.CaterpillarModel;
import com.bokmcdok.butterflies.world.entity.ambient.Caterpillar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * The renderer for the caterpillar entity.
 */
public class CaterpillarRenderer extends MobRenderer<Caterpillar, CaterpillarModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public CaterpillarRenderer(EntityRendererProvider.Context context) {
        super(context, new CaterpillarModel(context.bakeLayer(CaterpillarModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Gets the texture to use
     * @param entity The butterfly entity
     * @return The texture to use for this entity
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Caterpillar entity) {
        //  TODO: This is a temporary texture for testing purposes.
        return new ResourceLocation(ButterfliesMod.MODID, "textures/entity/caterpillar/caterpillar_buckeye.png");
        //return entity.getTexture();
    }

    /**
     * Scale the entity down
     * @param entity The butterfly entity
     * @param poses The current entity pose
     * @param scale The scale that should be applied
     */
    @Override
    protected void scale(@NotNull Caterpillar entity, PoseStack poses, float scale) {
        float s = entity.getScale();
        poses.scale(s, s, s);
    }
}
