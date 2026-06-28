package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.PoiTypeRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Generates POI tags.
 */
public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModPoiTypeTagsProvider(DataGenerator dataGenerator,
                                  @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .replace(false)
                .add(Objects.requireNonNull(PoiTypeRegistry.LEPIDOPTERIST.getKey()));
    }
}
