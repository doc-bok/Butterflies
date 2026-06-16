package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.BannerPatternRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Generates Banner Pattern Tags.
 */
public class ModBannerPatternTagsProvider extends BannerPatternTagsProvider {

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModBannerPatternTagsProvider(PackOutput packOutput,
                                        CompletableFuture<HolderLookup.Provider> lookupProvider,
                                        @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        tag(TagRegistry.BUTTERFLY_BANNER_PATTERN)
                .add(Objects.requireNonNull(BannerPatternRegistry.BANNER_PATTERN_BUTTERFLY.getKey()))
                .replace(false);
    }
}
