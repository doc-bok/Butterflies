package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ButterflyModel;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * This is the renderer for all the butterflies in the game.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer  extends MobRenderer<Butterfly, ButterflyModel> {
    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.2F);
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
     * Override to fix a bug with the model's orientation.
     * @param entity The butterfly entity.
     * @param p_115456_ Unknown.
     * @param p_115457_ Unknown.
     * @param poseStack The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param p_115460_ Unknown.
     */
    @Override
    public void render(@NotNull Butterfly entity,
                       float p_115456_,
                       float p_115457_,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int p_115460_) {

        // When the models were initially created no thought was given as to
        // what orientation they needed to be in. Rotating them here allows
        // them to use Minecraft's pathfinding systems without having to redo
        // the model from scratch.
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.f));

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, p_115460_);
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
