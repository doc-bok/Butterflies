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
        BUTTERFLY_BANNER_PATTERN = createTag(Registries.BANNER_PATTERN, "banner_pattern_butterfly");
    }

    /**
     * Create a new tag using the current mod's ID.
     * @param registry The registry to create a tag for.
     * @param tagName The name of the tag.
     * @return The new tag key.
     * @param <T> The type of the tag.
     */
    private static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> registry,
                                           String tagName) {
        return TagKey.create(registry, new ResourceLocation(ButterfliesMod.MOD_ID, tagName));
    }

    // Prevent construction.
    private TagRegistry() {}
}
