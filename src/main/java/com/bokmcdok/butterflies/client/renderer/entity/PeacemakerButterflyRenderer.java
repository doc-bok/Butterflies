package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.client.model.PeacemakerButterflyModel;
import com.bokmcdok.butterflies.client.renderer.entity.state.PeacemakerButterflyRenderState;
import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the Peacemaker Butterfly model.
 */
@OnlyIn(Dist.CLIENT)
public class PeacemakerButterflyRenderer extends MobRenderer<PeacemakerButterfly, PeacemakerButterflyRenderState, PeacemakerButterflyModel> {

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
    public PeacemakerButterflyRenderState createRenderState() {
        return new PeacemakerButterflyRenderState();
    }

    /**
     * Get the texture resource location for the Peacemaker Butterfly.
     * @param renderState The current render state.
     * @return The Resource Location of the Peacemaker Butterfly texture.
     */
    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull PeacemakerButterflyRenderState renderState) {
        return ButterflyTextures.PEACEMAKER_BUTTERFLY;
    }

    /**
     * Extracts the render state for use in rendering.
     * @param entity The butterfly entity.
     * @param renderState The current render state.
     * @param partialTick The number of partial ticks.
     */
    @Override
    public void extractRenderState(@NotNull PeacemakerButterfly entity,
                                   @NotNull PeacemakerButterflyRenderState renderState,
                                   float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        // Only extract debug info if we need it.
        if (ButterfliesConfig.Server.debugInformation.get()) {
            renderState.debugInfo = entity.getDebugInfo();
        }
    }

    /**
     * Render debug information if its enabled.
     * @param renderState The current render state.
     * @param poseStack         The pose stack.
     * @param multiBufferSource The render buffer (I think...)
     * @param packedLightCoordinates The light coordinates.
     */
    @Override
    public void render(@NotNull PeacemakerButterflyRenderState renderState,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource multiBufferSource,
                       int packedLightCoordinates) {

        super.render(renderState, poseStack, multiBufferSource, packedLightCoordinates);

        EntityDebugInfoRenderer.renderDebugInfo(
                renderState.debugInfo,
                poseStack,
                multiBufferSource,
                this.entityRenderDispatcher.cameraOrientation(),
                this.getFont(),
                packedLightCoordinates);
    }
}
