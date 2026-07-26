package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Generates entity type tags.
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    /**
     * Construction.
     * @param dataGenerator The pack to output to.
     * @param existingFileHelper Helps to check existing files.
     */
    public ModEntityTypeTagsProvider(DataGenerator dataGenerator,
                                     @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void addTags() {
        TagAppender<EntityType<?>> frogFoodTag = tag(EntityTypeTags.FROG_FOOD).replace(false);
        for(int i = 0; i < ButterflyRegistry.getTotalNumSpecies(); ++i) {
            if(!Objects.requireNonNull(ButterflyRegistry.getEntry(i)).hasTrait(ButterflyTrait.INEDIBLE)) {
                frogFoodTag.add(ButterflyEntityTypeRegistry.BUTTERFLIES.get(i).get());
            }
        }
    }
}
