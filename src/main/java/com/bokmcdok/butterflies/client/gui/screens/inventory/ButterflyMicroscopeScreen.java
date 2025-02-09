package com.bokmcdok.butterflies.client.gui.screens.inventory;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
     * @param poseStack The graphics object.
     * @param mouseX Mouse x-position.
     * @param mouseY Mouse y-position.
     * @param unknown Unknown.
     */
    public void render(@NotNull PoseStack poseStack,
                       int mouseX,
                       int mouseY,
                       float unknown) {
        super.render(poseStack, mouseX, mouseY, unknown);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    /**
     * Render the background.
     * @param poseStack The graphics object.
     * @param unknown Unknown.
     * @param mouseX Mouse x-position.
     * @param mouseY Mouse y-position.
     */
    @Override
    protected void renderBg(@NotNull PoseStack poseStack,
                            float unknown,
                            int mouseX,
                            int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ButterflyTextures.MICROSCOPE);

        int x = (this.width) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, x,y, 0, 0, this.imageWidth, this.imageHeight);

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

        RenderSystem.setShaderTexture(0, scrollTexture);
        this.blit(poseStack, x, y, 0, 0, 192, 192);

        int cachedPageSize = Math.min(128 / 9, this.cachedPageComponents.size());
        for (int line = 0; line < cachedPageSize; ++line) {
            FormattedCharSequence formattedCharSequence = this.cachedPageComponents.get(line);
            this.font.draw(poseStack, formattedCharSequence, x + 36, 50 + line * 9, 0);
        }
    }
}
