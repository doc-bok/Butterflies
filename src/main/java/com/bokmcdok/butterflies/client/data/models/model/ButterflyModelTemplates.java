package com.bokmcdok.butterflies.client.data.models.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Holds model templates for the mod.
 */
@OnlyIn(Dist.CLIENT)
public final class ButterflyModelTemplates {

    // Blocks
    public static final ModelTemplate BOTTLE;
    public static final ModelTemplate BUTTERFLY_FEEDER;
    public static final ModelTemplate BUTTERFLY_MICROSCOPE;
    public static final ModelTemplate BUTTERFLY_ORIGAMI;

    // Items
    public final static ModelTemplate TEMPLATE_BUTTERFLY_EGG;
    public final static ModelTemplate TEMPLATE_CATERPILLAR;

    static {
        BOTTLE = ModelTemplates.create(ButterfliesMod.MOD_ID + ":bottle");
        BUTTERFLY_FEEDER = ModelTemplates.create(ButterfliesMod.MOD_ID + ":template_butterfly_feeder");
        BUTTERFLY_MICROSCOPE = ModelTemplates.create(ButterfliesMod.MOD_ID + ":template_butterfly_microscope");
        BUTTERFLY_ORIGAMI = ModelTemplates.create(ButterfliesMod.MOD_ID + ":butterfly_origami", TextureSlot.ALL);

        TEMPLATE_BUTTERFLY_EGG = ModelTemplates.createItem(ButterfliesMod.MOD_ID + ":template_butterfly_egg", TextureSlot.LAYER0);
        TEMPLATE_CATERPILLAR = ModelTemplates.createItem(ButterfliesMod.MOD_ID + ":template_caterpillar", TextureSlot.LAYER0);

    }

    /**
     * Disable construction.
     */
    private ButterflyModelTemplates() {}
}
