package com.bokmcdok.butterflies.client.gui.screens.inventory;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * The GUI screen for a butterfly feeder.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyMicroscopeScreen extends AbstractContainerScreen<ButterflyMicroscopeMenu> {

    // The screen texture.
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ButterfliesMod.MOD_ID, "textures/gui/butterfly_microscope/butterfly_microscope.png");

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
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
