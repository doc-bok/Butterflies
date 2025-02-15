package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * The GUI screen for a butterfly page.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyZhuangziScreen extends Screen {

    // A cache holding the string data.
    private List<FormattedCharSequence> cache = Collections.emptyList();

    /**
     * Constructs a basic butterfly page screen.
     */
    public ButterflyZhuangziScreen() {
        super(NarratorChatListener.NO_TITLE);
    }

    /**
     * Called when the screen initialises.
     */
    @Override
    protected void init() {
        super.init();
        this.createMenuControls();
    }

    /**
     * Creates a close button for the screen.
     */
    protected void createMenuControls() {
        this.addRenderableWidget(new Button(this.width / 2 - 100, 196, 200, 20, CommonComponents.GUI_DONE, (p_98299_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(null);
            }
        }));
    }

    /**
     * Render the screen.
     * @param guiGraphics The graphics buffer for the gui.
     * @param x The x position of the cursor.
     * @param y The y position of the cursor.
     * @param unknown Unknown.
     */
    public void render(@NotNull PoseStack guiGraphics, int x, int y, float unknown) {
        super.render(guiGraphics, x, y, unknown);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ButterflyTextures.BOOK);

        int i = (this.width - 192) / 2;
        this.blit(guiGraphics, i, 2, 0, 0, 192, 192);


        if (this.cache.isEmpty()) {
            FormattedText formattedText = new TranslatableComponent("gui.butterflies.zhuangzi");
            this.cache = this.font.split(formattedText, 114);
        }

        int cachedPageSize = Math.min(128 / 9, this.cache.size());

        for (int line = 0; line < cachedPageSize; ++line) {
            FormattedCharSequence formattedCharSequence = this.cache.get(line);
            this.font.draw(guiGraphics, formattedCharSequence, (float)i + 36, (float)(32 + line * 9), 0);
        }
    }
}
