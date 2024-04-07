package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
        super.render(guiGraphics, x, y, unknown);
        int i = (this.width - 192) / 2;
        guiGraphics.blit(ButterflyTextures.BOOK, i, 2, 0, 0, 192, 192);


        if (this.cache.isEmpty()) {
            FormattedText formattedText = Component.translatable("gui.butterflies.zhuangzi");
            this.cache = this.font.split(formattedText, 114);
        }

        int cachedPageSize = Math.min(128 / 9, this.cache.size());

        for (int line = 0; line < cachedPageSize; ++line) {
            FormattedCharSequence formattedCharSequence = this.cache.get(line);
            guiGraphics.drawString(this.font, formattedCharSequence, i + 36, 32 + line * 9, 0, false);
        }
    }
}
