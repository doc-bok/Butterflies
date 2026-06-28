package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates block tags.
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModBlockTagsProvider(PackOutput packOutput,
                                CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {}
}
