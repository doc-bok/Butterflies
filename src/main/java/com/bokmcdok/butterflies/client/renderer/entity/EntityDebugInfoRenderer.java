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
 * Helper class that provides debug rendering to entities.
 */
@OnlyIn(Dist.CLIENT)
public class EntityDebugInfoRenderer {

    /**
     * Renders debug information for the butterfly.
     * @param entity The butterfly entity.
     * @param poseStack The current pose stack.
     * @param multiBufferSource The render buffer.
     * @param cameraOrientation The current camera orientation.
     * @param font The font to use for rendering.
     * @param packedLightCoordinates The light coordinates.
     */
    public static <T extends Entity & DebugInfoSupplier>
    void renderDebugInfo(T entity,
                         PoseStack poseStack,
                         MultiBufferSource multiBufferSource,
                         Quaternionf cameraOrientation,
                         Font font,
                         int packedLightCoordinates) {
        if (ButterfliesConfig.debugInformation.get()) {
            String debugInfo = entity.getDebugInfo();
            if (!debugInfo.isBlank()) {

                MutableComponent component = Component.literal(debugInfo);

                poseStack.pushPose();
                poseStack.translate(0.0F, 0.5F, 0.0F);
                poseStack.mulPose(cameraOrientation);
                poseStack.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f pose = poseStack.last().pose();
                float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int alpha = (int) (backgroundOpacity * 255.0F) << 24;
                float fontWidth = (float) (-font.width(component) / 2);
                font.drawInBatch(component, fontWidth, 0, 553648127, false, pose, multiBufferSource, Font.DisplayMode.SEE_THROUGH, alpha, packedLightCoordinates);
                font.drawInBatch(component, fontWidth, 0, -1, false, pose, multiBufferSource, Font.DisplayMode.NORMAL, 0, packedLightCoordinates);

                poseStack.popPose();
            }
        }
    }
}
