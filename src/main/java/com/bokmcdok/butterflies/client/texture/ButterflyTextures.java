package com.bokmcdok.butterflies.client.texture;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Holds the textures used for butterfly items and entities.
 */
@OnlyIn(Dist.CLIENT)
public final class ButterflyTextures {

    public static final ResourceLocation BOOK =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/book.png");

    public static final ResourceLocation MICROSCOPE =
            modResource("textures/gui/butterfly_microscope/butterfly_microscope.png");

    public static final ResourceLocation PEACEMAKER_BUTTERFLY =
            modResource("textures/entity/peacemaker/peacemaker_butterfly.png");

    public static final ResourceLocation PEACEMAKER_COW =
            modResource("textures/entity/peacemaker/peacemaker_cow.png");

    public static final ResourceLocation SCROLL =
            modResource("textures/gui/butterfly_scroll/blank.png");

    /**
     * Prevent instantiation of utility class.
     */
    private ButterflyTextures() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    /**
     * Helper method for creating resource locations.
     * @param path The path to the resource.
     * @return A ResourceLocation instance.
     */
    private static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, path);
    }
}
