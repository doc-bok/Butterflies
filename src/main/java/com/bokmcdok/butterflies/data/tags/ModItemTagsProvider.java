package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates Item Tags.
 */
public class ModItemTagsProvider extends ItemTagsProvider {

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param blockTagsProvider Helps with block lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModItemTagsProvider(DataGenerator dataGenerator,
                               BlockTagsProvider blockTagsProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {

        IntrinsicTagAppender<Item> appender = tag(TagRegistry.FIREPROOF_BUTTERFLY_NETS)
                .add(ItemRegistry.FIREPROOF_BUTTERFLY_NET.get());

        for (RegistryObject<Item> item : ItemRegistry.FIREPROOF_BUTTERFLY_NETS) {
            appender.add(item.get());
        }
    }
}
