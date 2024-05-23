package com.bokmcdok.butterflies.client.texture;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Holds the textures used for butterfly scrolls.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyTextures {

    // The location of the book texture.
    public static final ResourceLocation BOOK = new ResourceLocation("minecraft", "textures/gui/book.png");

    /**
     * The location of the screen textures.
     */
    public static final ResourceLocation[] SCROLLS = {
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
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/swallowtail.png"),
            new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/peacock.png")
    };
}
