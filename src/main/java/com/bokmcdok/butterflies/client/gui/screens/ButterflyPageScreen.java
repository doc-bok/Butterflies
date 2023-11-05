package com.bokmcdok.butterflies.client.gui.screens;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The GUI screen for a butterfly page.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyPageScreen extends Screen {

    /**
     * The location of the screen texture.
     */
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/book.png");

    /**
     * Constructs a basic butterfly page screen.
     */
    public ButterflyPageScreen() {
        super(GameNarrator.NO_TITLE);
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
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.onClose();
        }).bounds(this.width / 2 - 100, 196, 200, 20).build());
    }

    /**
     * Render the screen.
     * @param guiGraphics The graphics buffer for the gui.
     * @param x The x position of the cursor.
     * @param y The y position of the cursor.
     * @param unknown Unknown.
     */
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float unknown) {
        this.renderBackground(guiGraphics);
        int i = (this.width - 192) / 2;
        guiGraphics.blit(TEXTURE, i, 2, 0, 0, 192, 192);
        super.render(guiGraphics, x, y, unknown);
    }
}
