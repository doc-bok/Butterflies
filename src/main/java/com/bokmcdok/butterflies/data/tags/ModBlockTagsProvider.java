package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates block tags.
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    private static final TagKey<Block> SIMPLE_HARVEST_BLACKLISTED =
            TagKey.create(Registries.BLOCK, new ResourceLocation("quark", "simple_harvest_blacklisted"));

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
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        tag(SIMPLE_HARVEST_BLACKLISTED)
                .replace(false)
                .add(TagEntry.tag(new ResourceLocation("minecraft:small_flowers")));
    }
}
