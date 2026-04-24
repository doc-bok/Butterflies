package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.CaterpillarModel;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The renderer for the caterpillar entity.
 */
@OnlyIn(Dist.CLIENT)
public class CaterpillarRenderer extends DirectionalBaseRenderer<Caterpillar, CaterpillarModel> {

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
        float s = entity.getRenderScale();
        poses.scale(s, s, s);
    }

    /**
     * Render any debug information, if any.
     * @param entity The entity.
     * @param yaw The current yaw.
     * @param partialTicks The current partial ticks.
     * @param poseStack The matrix stack.
     * @param buffers The render buffers.
     * @param overlay The overlay.
     */
    @Override
    public void render(@NotNull Caterpillar entity,
                       float yaw,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffers,
                       int overlay) {

        // Render any debug information for this entity.
        EntityDebugInfoRenderer.renderDebugInfo(
                entity,
                poseStack,
                buffers,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                overlay);

        super.render(entity, yaw, partialTicks, poseStack, buffers, overlay);
    }
}
