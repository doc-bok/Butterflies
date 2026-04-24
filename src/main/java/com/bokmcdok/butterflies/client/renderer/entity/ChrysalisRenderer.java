package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChrysalisRenderer extends DirectionalBaseRenderer<Chrysalis, ChrysalisModel> {

    /**
     * Construction
     * @param context The current rendering context.
     */
    public ChrysalisRenderer(EntityRendererProvider.Context context) {
        super(context, new ChrysalisModel(context.bakeLayer(ChrysalisModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Gets the texture to use.
     * @param entity The  entity.
     * @return The texture to use for this entity.
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull Chrysalis entity) {
        return entity.getTexture();
    }

    /**
     * Scale the entity down.
     * @param entity The  entity.
     * @param poses The current entity pose.
     * @param scale The scale that should be applied.
     */
    @Override
    protected void scale(@NotNull Chrysalis entity,
                         PoseStack poses,
                         float scale) {
        float s = entity.getRenderScale();
        poses.scale(s, s, s);
    }
}
