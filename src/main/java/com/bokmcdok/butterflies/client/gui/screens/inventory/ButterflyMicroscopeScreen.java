package com.bokmcdok.butterflies.client.gui.screens.inventory;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * The GUI screen for a butterfly feeder.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyMicroscopeScreen extends AbstractContainerScreen<ButterflyMicroscopeMenu> {

    // A cache for the page components.
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();

    // The currently cached page.
    private int cachedButterflyIndex = -1;

    /**
     * Construction
     * @param menu The menu.
     * @param inventory The player inventory.
     * @param title The menu title.
     */
    public ButterflyMicroscopeScreen(ButterflyMicroscopeMenu menu,
                                     Inventory inventory,
                                     Component title) {
        super(menu, inventory, title);

        this.imageHeight = 128;
        this.inventoryLabelY = this.imageHeight - 92;
        this.titleLabelX = 96;
        this.inventoryLabelX = 96;
    }

    /**
     * Render the tool tip.
     * @param guiGraphics The graphics object.
     * @param mouseX Mouse x-position.
     * @param mouseY Mouse y-position.
     * @param unknown Unknown.
     */
    public void render(@NotNull GuiGraphics guiGraphics,
                       int mouseX,
                       int mouseY,
                       float unknown) {
        super.render(guiGraphics, mouseX, mouseY, unknown);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * Render the background.
     * @param guiGraphics The graphics object.
     * @param unknown Unknown.
     * @param mouseX Mouse x-position.
     * @param mouseY Mouse y-position.
     */
    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics,
                            float unknown,
                            int mouseX,
                            int mouseY) {
        int x = (this.width) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured, ButterflyTextures.MICROSCOPE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        x = ((this.width) / 2) - 176;
        y = (this.height - 192) / 2;

        ResourceLocation scrollTexture = ButterflyTextures.SCROLL;
        int butterflyIndex = this.menu.getButterflyScrollIndex();
        if (butterflyIndex >= 0) {
            ButterflyData data = ButterflyData.getEntry(butterflyIndex);
            if (data != null) {
                if (this.cachedButterflyIndex != butterflyIndex) {
                    FormattedText formattedText = ButterflyData.getFormattedButterflyData(butterflyIndex);
                    if (formattedText != null) {
                        this.cachedPageComponents = this.font.split(formattedText, 114);
                    }
                }
            }
        } else {
            this.cachedPageComponents = Collections.emptyList();
        }

        this.cachedButterflyIndex = butterflyIndex;

        guiGraphics.blit(RenderType::guiTextured, scrollTexture, x, y, 0, 0, 192, 192, 256, 256);

        int cachedPageSize = Math.min(128 / 9, this.cachedPageComponents.size());
        for (int line = 0; line < cachedPageSize; ++line) {
            FormattedCharSequence formattedCharSequence = this.cachedPageComponents.get(line);
            guiGraphics.drawString(this.font, formattedCharSequence, x + 36, 50 + line * 9, 0, false);
        }
    }
}
