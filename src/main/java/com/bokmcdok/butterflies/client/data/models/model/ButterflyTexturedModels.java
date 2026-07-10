package com.bokmcdok.butterflies.client.data.models.model;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

/**
 * Holds textured models used in the mod.
 */
public final class ButterflyTexturedModels {

    public static final TexturedModel.Provider BOTTLE;
    public static final TexturedModel.Provider BUTTERFLY_FEEDER;
    public static final TexturedModel.Provider BUTTERFLY_MICROSCOPE;
    public static final TexturedModel.Provider BUTTERFLY_ORIGAMI;

    static {
        BOTTLE = TexturedModel.createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BOTTLE);
        BUTTERFLY_FEEDER = TexturedModel.createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_FEEDER);
        BUTTERFLY_MICROSCOPE = TexturedModel.createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_MICROSCOPE);
        BUTTERFLY_ORIGAMI = TexturedModel.createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_ORIGAMI);
    }

    /**
     * Prevent construction.
     */
    private ButterflyTexturedModels() {}
}
