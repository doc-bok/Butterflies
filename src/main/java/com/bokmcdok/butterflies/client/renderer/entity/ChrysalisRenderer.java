package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.ChrysalisModel;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ChrysalisRenderer extends MobRenderer<Chrysalis, ChrysalisModel> {

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
        float s = entity.getScale() * ButterflyData.DIRECTIONAL_SIZE_MOD;
        poses.scale(s, s, s);
    }

    /**
     * Rotates the chrysalis, so it's attached to its block.
     * @param entity The entity.
     * @param p_115456_ Unknown.
     * @param p_115457_ Unknown.
     * @param poseStack The posed model to render.
     * @param multiBufferSource The render buffer.
     * @param p_115460_ Unknown.
     */
    @Override
    public void render(@NotNull Chrysalis entity,
                       float p_115456_,
                       float p_115457_,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int p_115460_) {
        Direction direction = entity.getSurfaceDirection();
        if (direction == Direction.UP) {
            poseStack.mulPose(Axis.XP.rotationDegrees(180.f));
        } else if (direction == Direction.NORTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.f));
        } else if (direction == Direction.SOUTH) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.f));
        } else if (direction == Direction.WEST) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(-90.f));
        } else if (direction == Direction.EAST){
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.f));
        }

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, p_115460_);
    }
}
