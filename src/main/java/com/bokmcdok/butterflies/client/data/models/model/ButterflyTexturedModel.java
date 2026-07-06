package com.bokmcdok.butterflies.client.data.models.model;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

public class ButterflyTexturedModel extends TexturedModel {

    public static final Provider BOTTLE;
    public static final Provider BUTTERFLY_FEEDER;
    public static final Provider BUTTERFLY_MICROSCOPE;
    public static final Provider BUTTERFLY_ORIGAMI;

    static {
        BOTTLE = createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BOTTLE);
        BUTTERFLY_FEEDER = createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_FEEDER);
        BUTTERFLY_MICROSCOPE = createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_MICROSCOPE);
        BUTTERFLY_ORIGAMI = createDefault(TextureMapping::defaultTexture, ButterflyModelTemplates.BUTTERFLY_ORIGAMI);
    }

    public ButterflyTexturedModel(TextureMapping mapping,
                                  ModelTemplate template) {
        super(mapping, template);
    }
}
