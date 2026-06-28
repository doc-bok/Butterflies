package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        //tag(ItemTags.DECORATED_POT_SHERDS)
        //        .replace(false)
        //        .add(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());
    }
}
