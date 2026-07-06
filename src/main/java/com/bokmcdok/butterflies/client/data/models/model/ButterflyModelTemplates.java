package com.bokmcdok.butterflies.client.data.models.model;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButterflyModelTemplates extends ModelTemplates {

    public static final ModelTemplate BOTTLE;
    public static final ModelTemplate BUTTERFLY_FEEDER;
    public static final ModelTemplate BUTTERFLY_MICROSCOPE;
    public static final ModelTemplate BUTTERFLY_ORIGAMI;

    static {
        BOTTLE = create(ButterfliesMod.MOD_ID + ":bottle");
        BUTTERFLY_FEEDER = create(ButterfliesMod.MOD_ID + ":template_butterfly_feeder");
        BUTTERFLY_MICROSCOPE = create(ButterfliesMod.MOD_ID + ":template_butterfly_microscope");
        BUTTERFLY_ORIGAMI = create(ButterfliesMod.MOD_ID + ":butterfly_origami", TextureSlot.ALL);
    }
}
