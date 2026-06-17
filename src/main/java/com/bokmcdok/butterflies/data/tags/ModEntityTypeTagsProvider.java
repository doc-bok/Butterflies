package com.bokmcdok.butterflies.data.tags;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.PeacemakerEntityTypeRegistry;
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

import java.util.concurrent.CompletableFuture;

/**
 * Generates Entity Type Tags.
 */
public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    private static final TagKey<EntityType<?>> PEACEMAKER_ENTITIES =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(ButterfliesMod.MOD_ID, "peacemaker_entities"));

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
    public void addTags(@NotNull HolderLookup.Provider lookupProvider)
    {
        tag(PEACEMAKER_ENTITIES)
                .add(PeacemakerEntityTypeRegistry.PEACEMAKER_BUTTERFLY.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_COW.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_EVOKER.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_ILLUSIONER.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_PILLAGER.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_VILLAGER.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_VINDICATOR.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_WANDERING_TRADER.get(),
                     PeacemakerEntityTypeRegistry.PEACEMAKER_WITCH.get())
                .replace(false);
    }
}
