package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.client.event.ClientEventListener;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.event.ForgeEventListener;
import com.bokmcdok.butterflies.event.ModEventListener;
import com.bokmcdok.butterflies.event.entity.ForgeEntityEventListener;
import com.bokmcdok.butterflies.event.entity.ModEntityEventListener;
import com.bokmcdok.butterflies.event.entity.living.LivingEventListener;
import com.bokmcdok.butterflies.event.entity.living.MobSpawnEventListener;
import com.bokmcdok.butterflies.event.entity.player.PlayerEventListener;
import com.bokmcdok.butterflies.event.lifecycle.LifecycleEventListener;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.event.server.ServerEventListener;
import com.bokmcdok.butterflies.event.village.VillageEventListener;
import com.bokmcdok.butterflies.registries.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Main mod class for Butterflies.
 * Handles mod setup including registries, event listeners, and configs.
 */
@Mod(ButterfliesMod.MOD_ID)
public class ButterfliesMod {
    public static final String MOD_ID = "butterflies";

    public ButterfliesMod() {

        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        IEventBus forgeEventBus = NeoForge.EVENT_BUS;

        // Initialize registries with explicit dependency ordering
        BannerPatternRegistry bannerPatternRegistry = new BannerPatternRegistry(modEventBus);
        BlockEntityTypeRegistry blockEntityTypeRegistry = new BlockEntityTypeRegistry(modEventBus);
        BlockRegistry blockRegistry = new BlockRegistry(modEventBus);
        CreativeTabRegistry creativeTabRegistry = new CreativeTabRegistry(modEventBus);
        DecoratedPotPatternsRegistry decoratedPotPatternsRegistry = new DecoratedPotPatternsRegistry(modEventBus);
        EntityTypeRegistry entityTypeRegistry = new EntityTypeRegistry(modEventBus);
        ItemRegistry itemRegistry = new ItemRegistry(modEventBus);
        LootModifierRegistry lootModifierRegistry = new LootModifierRegistry(modEventBus);
        MenuTypeRegistry menuTypeRegistry = new MenuTypeRegistry(modEventBus);
        PoiTypeRegistry poiTypesRegistry = new PoiTypeRegistry(modEventBus);
        TagRegistry tagRegistry = new TagRegistry();
        VillagerProfessionRegistry villagerProfessionRegistry = new VillagerProfessionRegistry(modEventBus);

        bannerPatternRegistry.initialise();
        blockEntityTypeRegistry.initialise(blockRegistry, menuTypeRegistry);
        blockRegistry.initialise(blockEntityTypeRegistry, itemRegistry, menuTypeRegistry);
        creativeTabRegistry.initialise(itemRegistry);
        decoratedPotPatternsRegistry.initialise();
        entityTypeRegistry.initialise(blockRegistry, tagRegistry);
        itemRegistry.initialise(bannerPatternRegistry, blockRegistry, entityTypeRegistry, tagRegistry);
        lootModifierRegistry.initialise(itemRegistry);
        menuTypeRegistry.initialise();
        poiTypesRegistry.initialise(blockRegistry);
        villagerProfessionRegistry.initialise(poiTypesRegistry);

        // Register client-only listeners
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new ClientEventListener(modEventBus, blockEntityTypeRegistry, entityTypeRegistry);
        }

        // Register mod lifecycle and mod-specific event listeners
        new LifecycleEventListener(modEventBus, decoratedPotPatternsRegistry, itemRegistry, menuTypeRegistry);
        new ModEntityEventListener(modEventBus, entityTypeRegistry);
        new ModEventListener(modEventBus, creativeTabRegistry, itemRegistry);

        // Register Forge event listeners
        new ForgeEventListener(forgeEventBus);
        new ForgeEntityEventListener(forgeEventBus);
        new LivingEventListener(forgeEventBus);
        new MobSpawnEventListener(forgeEventBus, entityTypeRegistry, tagRegistry);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);
        new ServerEventListener(forgeEventBus);
        new VillageEventListener(forgeEventBus, itemRegistry, villagerProfessionRegistry);

        // Register mod configuration files
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ButterfliesConfig.COMMON_CONFIG);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
