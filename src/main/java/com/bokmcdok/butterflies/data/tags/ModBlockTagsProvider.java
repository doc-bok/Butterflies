package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * Generates block tags.
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    private static final TagKey<Block> SIMPLE_HARVEST_BLACKLISTED =
            TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation("quark", "simple_harvest_blacklisted"));

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
    protected void addTags() {
        tag(SIMPLE_HARVEST_BLACKLISTED)
                .replace(false)
                .add(new Tag.TagEntry(new ResourceLocation("minecraft:small_flowers")));
    }
}
