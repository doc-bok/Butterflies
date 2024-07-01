package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
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
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) ->
                this.onClose()).bounds(this.width / 2 - 100, 196, 200, 20).build());
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

        guiGraphics.blit(ButterflyData.indexToButterflyScrollTexture(this.butterflyIndex), i, 2, 0, 0, 192, 192);
        super.render(guiGraphics, x, y, unknown);
    }
}
