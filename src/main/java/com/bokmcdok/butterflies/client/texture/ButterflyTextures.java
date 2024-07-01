package com.bokmcdok.butterflies.client.texture;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Holds the textures used for butterfly scrolls.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyTextures {

    // The location of the book texture.
    public static final ResourceLocation BOOK = new ResourceLocation("minecraft", "textures/gui/book.png");
}
