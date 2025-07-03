package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.HummingbirdMothModel;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
     * Override to provide debug information.
     * @param entity            The butterfly entity.
     * @param p_115456_         Unknown.
     * @param p_115457_         Unknown.
     * @param poseStack         The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull Butterfly entity,
                       float p_115456_,
                       float p_115457_,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int packedLightCoordinates) {

        // Render any debug information for this entity.
        EntityDebugInfoRenderer.renderDebugInfo(
                entity,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);

        poseStack.pushPose();

        // Shift the model down slightly, so it fits within the bounding box.
        poseStack.translate(0.0, -0.1, 0.0);

        // Rotate the butterfly if it is landed.
        if (entity.getIsLanded()) {
            switch (entity.getLandedDirection()) {
                case UP:
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(180.f));
                    break;

                case NORTH:
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(90.f));
                    break;

                case SOUTH:
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.f));
                    break;

                case EAST:
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.f));
                    break;

                case WEST:
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.f));
                    break;

                default:
                    break;
            }
        }

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, packedLightCoordinates);
        poseStack.popPose();
    }

    /**
     * Scale the entity down
     * @param entity The butterfly entity
     * @param poses The current entity pose
     * @param scale The scale that should be applied
     */
    @Override
    protected void scale(@NotNull Butterfly entity, PoseStack poses, float scale) {
        float s = entity.getRenderScale();
        poses.scale(s, s, s);
    }
}
