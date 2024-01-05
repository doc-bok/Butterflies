package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The GUI screen for a butterfly page.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyScrollScreen extends Screen {

    private final int butterflyIndex;

    /**
     * Constructs a basic butterfly page screen.
     */
    public ButterflyScrollScreen(int butterflyIndex) {
        super(GameNarrator.NO_TITLE);

        this.butterflyIndex = butterflyIndex;
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
        this.renderBackground(guiGraphics);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ButterflyTextures.SCROLLS[this.butterflyIndex]);

        int i = (this.width - 192) / 2;
        this.blit(guiGraphics, i, 2, 0, 0, 192, 192);
        super.render(guiGraphics, x, y, unknown);
    }
}
