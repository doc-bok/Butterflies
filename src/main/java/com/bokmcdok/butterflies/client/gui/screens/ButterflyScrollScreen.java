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
public class ButterflyScrollScreen extends Screen {

    /**
     * The location of the screen textures.
     */
    public static final ResourceLocation[] TEXTURES =
            {
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/admiral.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/buckeye.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/cabbage.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/chalkhill.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/clipper.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/common.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/emperor.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/forester.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/glasswing.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/hairstreak.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/heath.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/longwing.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/monarch.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/morpho.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/rainbow.png"),
                    new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/swallowtail.png")
            };

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
        guiGraphics.blit(TEXTURES[this.butterflyIndex], i, 2, 0, 0, 192, 192);
        super.render(guiGraphics, x, y, unknown);
    }
}
