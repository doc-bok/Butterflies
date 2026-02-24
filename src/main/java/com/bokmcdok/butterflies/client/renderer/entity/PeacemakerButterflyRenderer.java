package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.PeacemakerButterflyModel;
import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the Peacemaker Butterfly model.
 */
@OnlyIn(Dist.CLIENT)
public class PeacemakerButterflyRenderer extends MobRenderer<PeacemakerButterfly, LivingEntityRenderState, PeacemakerButterflyModel> {

    /**
     * Create a new renderer for the Peacemaker Butterfly.
     * @param context The current rendering context
     */
    public PeacemakerButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new PeacemakerButterflyModel(context.bakeLayer(PeacemakerButterflyModel.LAYER_LOCATION)), 0.2F);
    }

    /**
     * Creates a reusable render state.
     * @return The new render state.
     */
    @NotNull
    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    /**
     * Get the texture resource location for the Peacemaker Butterfly.
     * @param renderState The current render state.
     * @return The Resource Location of the Peacemaker Butterfly texture.
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull LivingEntityRenderState renderState) {
        return ButterflyTextures.PEACEMAKER_BUTTERFLY;
    }

    /**
     * Override to fix a bug with the model's orientation.
     * @param entity            The entity.
     * @param p_115456_         Unknown.
     * @param p_115457_         Unknown.
     * @param poseStack         The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull PeacemakerButterfly entity,
                       float p_115456_,
                       float p_115457_,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int packedLightCoordinates) {

        super.render(entity, p_115456_, p_115457_, poseStack, multiBufferSource, packedLightCoordinates);

        EntityDebugInfoRenderer.renderDebugInfo(
                entity,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);
    }
}
