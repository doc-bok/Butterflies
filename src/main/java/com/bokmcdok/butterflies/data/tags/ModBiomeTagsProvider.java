package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Generates biome tags.
 */
public class ModBiomeTagsProvider extends BiomeTagsProvider {

    private static final TagKey<Biome> LIANGSHANBO_GRAVE =
            TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "has_structure/liangshanbo_grave"));

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     */
    public ModBiomeTagsProvider(PackOutput packOutput,
                                CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, ButterfliesMod.MOD_ID);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
        tag(LIANGSHANBO_GRAVE)
                .replace(false)
                .addTag(BiomeTags.IS_FOREST);
    }
}
