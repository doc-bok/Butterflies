package com.bokmcdok.butterflies.data.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.ButterflyDataLoader;
import com.bokmcdok.butterflies.client.model.generators.ButterflyModelProvider;
import com.bokmcdok.butterflies.client.model.generators.ModBlockModelProvider;
import com.bokmcdok.butterflies.client.model.generators.ModItemModelProvider;
import com.bokmcdok.butterflies.common.data.ModAdvancementGenerator;
import com.bokmcdok.butterflies.common.data.ModGlobalLootModifierProvider;
import com.bokmcdok.butterflies.common.data.ModWorldGenProvider;
import com.bokmcdok.butterflies.data.loot.ModLootTableProvider;
import com.bokmcdok.butterflies.data.recipes.ModRecipeProvider;
import com.bokmcdok.butterflies.data.tags.*;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
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
     * Gather all the data generators.
     * @param event The event information.
     */
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBuiltInEntriesProvider datapack = new ModBuiltInEntriesProvider(output, lookupProvider);
        generator.addProvider(true, datapack);

        var pack = generator.getVanillaPack(true);

        preloadButterflyData();

        // Client Assets
        pack.addProvider(ButterflyModelProvider.create(
                ModBlockModelProvider::new,
                ModItemModelProvider::new));

        // Server Assets
        pack.addProvider(packOutput -> new AdvancementProvider(packOutput, lookupProvider, List.of(new ModAdvancementGenerator())));
        pack.addProvider(packOutput -> new ModWorldGenProvider(packOutput, lookupProvider));
        pack.addProvider(packOutput -> new ModGlobalLootModifierProvider(packOutput, lookupProvider));
        pack.addProvider(packOutput -> ModLootTableProvider.create(packOutput, lookupProvider));
        pack.addProvider(packOutput -> new ModRecipeProvider.Runner(packOutput, lookupProvider));
        pack.addProvider(packOutput -> new ModBannerPatternTagsProvider(packOutput, datapack.getRegistryProvider()));
        pack.addProvider(packOutput -> new ModBiomeTagsProvider(packOutput, lookupProvider));
        pack.addProvider(packOutput -> new ModEntityTypeTagsProvider(packOutput, lookupProvider));

        ModBlockTagsProvider blockTagsProvider =  pack.addProvider(packOutput -> new ModBlockTagsProvider(packOutput, lookupProvider));
        pack.addProvider(packOutput -> new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
        pack.addProvider(packOutput -> new ModPoiTypeTagsProvider(packOutput, lookupProvider));
    }

    /**
     * Loads the butterfly data so it is ready for the data generators to
     * reference. This is initialized this way so we can use the data-driven
     * approach to generate all our other data.
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
        ButterflyDataLoader.load(resourceManager);
    }
}
