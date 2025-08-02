package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.DebugInfoSupplier;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/**
 * Helper class that renders debug information for entities implementing DebugInfoSupplier.
 * This debug info is shown only if enabled in server config.
 */
@OnlyIn(Dist.CLIENT)
public class EntityDebugInfoRenderer {

    /**
     * Renders debug information above the entity if enabled.
     *
     * @param <T>                   The entity type that extends Entity and implements DebugInfoSupplier
     * @param entity                The entity to render debug info for
     * @param poseStack             The current rendering pose stack
     * @param multiBufferSource     The buffer source for rendering
     * @param cameraOrientation     The orientation quaternion of the camera
     * @param font                  The font renderer
     * @param packedLightCoordinates The packed light coordinates for the render
     */
    public static <T extends Entity & DebugInfoSupplier> void renderDebugInfo(
            final T entity,
            final PoseStack poseStack,
            final MultiBufferSource multiBufferSource,
            final Quaternionf cameraOrientation,
            final Font font,
            final int packedLightCoordinates) {

        if (!ButterfliesConfig.Server.debugInformation.get()) {
            return;
        }

        final String debugInfo = entity.getDebugInfo();
        if (debugInfo.isBlank()) {
            return;
        }

        final MutableComponent debugTextComponent = Component.literal(debugInfo);

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        poseStack.mulPose(cameraOrientation);
        poseStack.scale(-0.025F, -0.025F, 0.025F);

        final Matrix4f poseMatrix = poseStack.last().pose();

        final float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        final int alpha = (int) (backgroundOpacity * 255.0F) << 24; // background alpha for text box

        final float fontWidthCenter = (float) (-font.width(debugTextComponent) / 2);

        // Draw background text (semi-transparent)
        font.drawInBatch(
                debugTextComponent,
                fontWidthCenter,
                0,
                553648127,
                false,
                poseMatrix,
                multiBufferSource,
                Font.DisplayMode.SEE_THROUGH,
                alpha,
                packedLightCoordinates);

        // Draw foreground text (solid)
        font.drawInBatch(
                debugTextComponent,
                fontWidthCenter,
                0,
                -1,
                false,
                poseMatrix,
                multiBufferSource,
                Font.DisplayMode.NORMAL,
                0,
                packedLightCoordinates);

        poseStack.popPose();
    }
}
