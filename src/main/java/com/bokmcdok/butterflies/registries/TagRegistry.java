package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

/**
 * Holds all the tags used by the mod.
 */
public class TagRegistry {

    // The available tags for this mod.
    public static final TagKey<BannerPattern> BUTTERFLY_BANNER_PATTERN;

    static {
        BUTTERFLY_BANNER_PATTERN = TagKey.create(Registries.BANNER_PATTERN,
                new ResourceLocation(ButterfliesMod.MOD_ID, "banner_pattern_butterfly"));
    }

    // Prevent construction.
    private TagRegistry() {}
}
