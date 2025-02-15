package com.bokmcdok.butterflies.client.renderer.entity;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.world.entity.DebugInfoSupplier;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
                         Quaternion cameraOrientation,
                         Font font,
                         int packedLightCoordinates) {
        if (ButterfliesConfig.debugInformation.get()) {
            String debugInfo = entity.getDebugInfo();
            if (!debugInfo.isBlank()) {

                MutableComponent component = new TextComponent(debugInfo);

                float nameTagOffsetY = entity.getBbHeight() + 0.5f;
                poseStack.pushPose();
                poseStack.translate(0.0F, nameTagOffsetY, 0.0F);
                poseStack.mulPose(cameraOrientation);
                poseStack.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f pose = poseStack.last().pose();
                float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int alpha = (int) (backgroundOpacity * 255.0F) << 24;
                float fontWidth = (float) (-font.width(component) / 2);
                font.drawInBatch(component, fontWidth, 0, 553648127, false, pose, multiBufferSource, false, alpha, packedLightCoordinates);
                font.drawInBatch(component, fontWidth, 0, -1, false, pose, multiBufferSource, false, 0, packedLightCoordinates);
                poseStack.popPose();
            }
        }
    }
}
