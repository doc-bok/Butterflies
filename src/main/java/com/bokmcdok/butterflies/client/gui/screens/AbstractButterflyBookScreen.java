package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

/**
 * Class that contains common code for book-like screens.
 */
public abstract class AbstractButterflyBookScreen extends Screen {

    /**
     * Construction.
     * @param title The screen's title, which should be a localization string.
     */
    protected AbstractButterflyBookScreen(Component title) {
        super(title);
    }

    /**
     * Render a book background.
     * @param poseStack The current matrix stack.
     */
    protected void blitBook(PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ButterflyTextures.BOOK);

        int i = (this.width - 192) / 2;
        blit(poseStack, i, 2, 0, 0, 192, 192);
    }
}
