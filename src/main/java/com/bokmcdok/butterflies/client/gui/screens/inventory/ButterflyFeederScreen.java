package com.bokmcdok.butterflies.client.gui.screens.inventory;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
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
public class ButterflyFeederScreen extends AbstractContainerScreen<ButterflyFeederMenu> {

    // The screen texture.
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ButterfliesMod.MOD_ID, "textures/gui/butterfly_feeder/butterfly_feeder.png");

    /**
     * Construction
     * @param butterflyFeederMenu The butterfly feeder menu.
     * @param inventory The player inventory.
     * @param title The menu title.
     */
    public ButterflyFeederScreen(ButterflyFeederMenu butterflyFeederMenu,
                                 Inventory inventory,
                                 Component title) {
        super(butterflyFeederMenu, inventory, title);
        this.imageHeight = 128;
        this.inventoryLabelY = this.imageHeight - 92;
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
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
