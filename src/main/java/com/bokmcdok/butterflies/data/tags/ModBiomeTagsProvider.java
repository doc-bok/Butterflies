package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates biome tags.
 */
public class ModBiomeTagsProvider extends BiomeTagsProvider {

    private static final TagKey<Biome> LIANGSHANBO_GRAVE =
            TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ButterfliesMod.MOD_ID, "has_structure/liangshanbo_grave"));

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModBiomeTagsProvider(DataGenerator dataGenerator,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {
        tag(LIANGSHANBO_GRAVE)
                .replace(false)
                .addTag(BiomeTags.IS_FOREST);
    }
}
