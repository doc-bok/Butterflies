package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Generates entity type tags.
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     */
    public ModEntityTypeTagsProvider(PackOutput packOutput,
                                     CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, ButterfliesMod.MOD_ID);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
        IntrinsicTagAppender<EntityType<?>> frogFoodTag = tag(EntityTypeTags.FROG_FOOD).replace(false);
        for(int i = 0; i < ButterflyData.getTotalNumSpecies(); ++i) {
            if(!Objects.requireNonNull(ButterflyData.getEntry(i)).hasTrait(ButterflyData.Trait.INEDIBLE)) {
                frogFoodTag.add(ButterflyEntityTypeRegistry.BUTTERFLIES.get(i).get());
            }
        }
    }
}
