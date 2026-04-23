package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyEggModel;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Renderer for a butterfly egg entity.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyEggRenderer extends DirectionalBaseRenderer<ButterflyEgg, ButterflyEggModel> {

    /**
     * Construction
     * @param context The current rendering context.
     */
    public ButterflyEggRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyEggModel(context.bakeLayer(ButterflyEggModel.LAYER_LOCATION)), 0.05F);
    }

    /**
     * Gets the texture to use.
     * @param entity The  entity.
     * @return The texture to use for this entity.
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull ButterflyEgg entity) {
        return entity.getTexture();
    }

    /**
     * Scale the entity down.
     * @param entity The  entity.
     * @param poses The current entity pose.
     * @param scale The scale that should be applied.
     */
    @Override
    protected void scale(@NotNull ButterflyEgg entity,
                         PoseStack poses,
                         float scale) {
        float s = entity.getRenderScale();
        poses.scale(s, s, s);
    }
}
