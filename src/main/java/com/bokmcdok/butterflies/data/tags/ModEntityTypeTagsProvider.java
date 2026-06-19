package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Generates entity type tags.
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    private static final TagKey<EntityType<?>> FROG_FOOD =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft", "frog_food"));

    /**
     * Construction.
     * @param packOutput The pack to output to.
     * @param lookupProvider Helps with registry lookups.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModEntityTypeTagsProvider(PackOutput packOutput,
                                     CompletableFuture<HolderLookup.Provider> lookupProvider,
                                     @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     * @param lookupProvider Helps with registry lookups.
     */
    @Override
    protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
        IntrinsicTagAppender<EntityType<?>> frogFoodTag = tag(FROG_FOOD).replace(false);
        for(int i = 0; i < ButterflyData.getTotalNumSpecies(); ++i) {
            if(!Objects.requireNonNull(ButterflyData.getEntry(i)).hasTrait(ButterflyData.Trait.INEDIBLE)) {
                frogFoodTag.add(ButterflyEntityTypeRegistry.BUTTERFLIES.get(i).get());
            }
        }
    }
}
