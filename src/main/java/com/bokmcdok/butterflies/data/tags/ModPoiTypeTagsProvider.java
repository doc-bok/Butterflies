package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.PoiTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Generates POI tags.
 */
public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModPoiTypeTagsProvider(PackOutput packOutput,
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
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .replace(false)
                .add(Objects.requireNonNull(PoiTypeRegistry.LEPIDOPTERIST.getKey()));
    }
}
