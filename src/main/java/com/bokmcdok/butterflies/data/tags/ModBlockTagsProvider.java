package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates block tags.
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModBlockTagsProvider(DataGenerator dataGenerator,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {}
}
