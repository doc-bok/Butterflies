package com.bokmcdok.butterflies.data.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.generators.ModBlockStateProvider;
import com.bokmcdok.butterflies.client.model.generators.ModItemModelProvider;
import com.bokmcdok.butterflies.common.data.ModAdvancementGenerator;
import com.bokmcdok.butterflies.common.data.ModGlobalLootModifierProvider;
import com.bokmcdok.butterflies.common.data.ModWorldGenProvider;
import com.bokmcdok.butterflies.data.loot.ModLootTableProvider;
import com.bokmcdok.butterflies.data.recipes.ModRecipeProvider;
import com.bokmcdok.butterflies.data.tags.ModBannerPatternTagsProvider;
import com.bokmcdok.butterflies.data.tags.ModBiomeTagsProvider;
import com.bokmcdok.butterflies.data.tags.ModEntityTypeTagsProvider;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.resource.ResourcePackLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Class used to generate data for the mod.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        final List<ForgeAdvancementProvider.AdvancementGenerator> advancements = List.of(
                new ModAdvancementGenerator());

        generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(packOutput, lookupProvider, existingFileHelper, advancements));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

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
        candidateServerResources.add(ResourcePackLoader.createPackForMod(modFileInfo));
        MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, candidateServerResources);
        ButterflyData.load(resourceManager);
    }
}
