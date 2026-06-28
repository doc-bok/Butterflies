package com.bokmcdok.butterflies.data.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.generators.ModBlockStateProvider;
import com.bokmcdok.butterflies.client.model.generators.ModItemModelProvider;
import com.bokmcdok.butterflies.common.data.ModGlobalLootModifierProvider;
import com.bokmcdok.butterflies.data.loot.ModLootTableProvider;
import com.bokmcdok.butterflies.data.recipes.ModRecipeProvider;
import com.bokmcdok.butterflies.data.tags.*;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
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
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        preloadButterflyData();

        // Server Data
        generator.addProvider(new ModGlobalLootModifierProvider(generator));
        generator.addProvider(new ModLootTableProvider(generator));
        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModBiomeTagsProvider(generator, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper));

        // Client Assets
        generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
        generator.addProvider(new ModBlockStateProvider(generator, existingFileHelper));
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
