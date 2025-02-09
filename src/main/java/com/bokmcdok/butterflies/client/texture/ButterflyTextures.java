package com.bokmcdok.butterflies.client.texture;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Holds the textures used for butterfly scrolls.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyTextures {

    public static final ResourceLocation BOOK = new ResourceLocation("minecraft", "textures/gui/book.png");
    public static final ResourceLocation MICROSCOPE = new ResourceLocation(ButterfliesMod.MOD_ID, "textures/gui/butterfly_microscope/butterfly_microscope.png");
    public static final ResourceLocation SCROLL = new ResourceLocation(ButterfliesMod.MOD_ID, "textures/gui/butterfly_scroll/blank.png");
}
