package com.bokmcdok.butterflies.data.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.generators.ModBlockStateProvider;
import com.bokmcdok.butterflies.client.model.generators.ModItemModelProvider;
import com.bokmcdok.butterflies.common.data.ModAdvancementGenerator;
import com.bokmcdok.butterflies.common.data.ModGlobalLootModifierProvider;
import com.bokmcdok.butterflies.common.data.ModWorldGenProvider;
import com.bokmcdok.butterflies.data.loot.ModLootTableProvider;
import com.bokmcdok.butterflies.data.recipes.ModRecipeProvider;
import com.bokmcdok.butterflies.data.tags.*;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.resource.ResourcePackLoader;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Class used to generate data for the mod.
 */
@EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    /**
     * Gather up all the data generators.
     * @param event The event information.
     */
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        preloadButterflyData();

        // Server Data
        final List<AdvancementProvider.AdvancementGenerator> advancements = List.of(
                new ModAdvancementGenerator());

        generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, lookupProvider, existingFileHelper, advancements));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider =  generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPoiTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        // Client Assets
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
    }

    /**
     * Loads the butterfly data so it is ready for the data generators to reference.
     */
    private static void preloadButterflyData() {
        List<PackResources> candidateServerResources = new ArrayList<>();
        IModFileInfo modFileInfo = ModList.get().getModFileById(ButterfliesMod.MOD_ID);
        PackLocationInfo packLocationInfo = new PackLocationInfo(
                "mod/" + ButterfliesMod.MOD_ID,
                Component.empty(),
                PackSource.BUILT_IN,
                Optional.empty()
        );

        candidateServerResources.add(ResourcePackLoader.createPackForMod(modFileInfo).openPrimary(packLocationInfo));


        MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, candidateServerResources);
        ButterflyData.load(resourceManager);
    }
}
