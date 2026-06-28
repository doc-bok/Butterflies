package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BannerPatternRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Generates Banner Pattern Tags.
 */
public class ModBannerPatternTagsProvider extends BannerPatternTagsProvider {

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModBannerPatternTagsProvider(DataGenerator dataGenerator,
                                        @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {
        tag(TagRegistry.BUTTERFLY_BANNER_PATTERN)
                .add(Objects.requireNonNull(BannerPatternRegistry.BANNER_PATTERN_BUTTERFLY.getKey()))
                .replace(false);
    }
}
