package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.model.CaterpillarModel;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * The renderer for the caterpillar entity.
 */
public class CaterpillarRenderer
        extends MobRenderer<Caterpillar, CaterpillarModel> {

    /**
     * Bakes a new model for the renderer
     * @param context The current rendering context
     */
    public CaterpillarRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new CaterpillarModel(
                        context.bakeLayer(CaterpillarModel.LAYER_LOCATION)),
                0.05F);
    }

    /**
     * Gets the texture to use
     * @param entity The butterfly entity
     * @return The texture to use for this entity
     */
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull Caterpillar entity) {
        return entity.getTexture();
    }

    /**
     * Scale the entity down
     * @param entity The butterfly entity
     * @param poses The current entity pose
     * @param scale The scale that should be applied
     */
    @Override
    protected void scale(@NotNull Caterpillar entity,
                         PoseStack poses,
                         float scale) {
        float s = entity.getScale();
        poses.scale(s, s, s);
    }

    /**
     * Rotates the caterpillar so it's attached to its block.
     * @param entity The caterpillar entity.
     * @param p_115456_ Unknown.
     * @param p_115457_ Unknown.
     * @param poseStack The posed model to render.
     * @param multiBufferSource The render buffer.
     * @param p_115460_ Unknown.
     */
    @Override
    public void render(@NotNull Caterpillar entity,
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
