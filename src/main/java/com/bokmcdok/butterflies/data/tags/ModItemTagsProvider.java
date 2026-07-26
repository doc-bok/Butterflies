package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Generates Item Tags.
 */
public class ModItemTagsProvider extends ItemTagsProvider {

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     * @param blockLookupProvider Helps with block lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModItemTagsProvider(PackOutput packOutput,
                               CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockLookupProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockLookupProvider, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
        tag(ItemTags.DECORATED_POT_SHERDS)
                .replace(false)
                .add(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());

        IntrinsicTagAppender<Item> appender = tag(TagRegistry.FIREPROOF_BUTTERFLY_NETS)
                .add(ItemRegistry.FIREPROOF_BUTTERFLY_NET.get());

        for (DeferredHolder<Item, Item> item : ItemRegistry.FIREPROOF_BUTTERFLY_NETS) {
            appender.add(item.get());
        }
    }
}
