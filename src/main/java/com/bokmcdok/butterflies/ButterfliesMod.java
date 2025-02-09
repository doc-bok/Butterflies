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
 * The main entry point for the mod.
 */
@Mod(ButterfliesMod.MOD_ID)
public class ButterfliesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "butterflies";

    /**
     * Constructor.
     */
    public ButterfliesMod() {
        final IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        final IEventBus forgeEventBus = NeoForge.EVENT_BUS;

        // Create the registries.
        BannerPatternRegistry bannerPatternRegistry = new BannerPatternRegistry(modEventBus);
        BlockEntityTypeRegistry blockEntityTypeRegistry = new BlockEntityTypeRegistry(modEventBus);
        BlockRegistry blockRegistry = new BlockRegistry(modEventBus);
        DecoratedPotPatternsRegistry decoratedPotPatternsRegistry = new DecoratedPotPatternsRegistry(modEventBus);
        EntityTypeRegistry entityTypeRegistry = new EntityTypeRegistry(modEventBus);
        ItemRegistry itemRegistry = new ItemRegistry(modEventBus);
        LootModifierRegistry lootModifierRegistry = new LootModifierRegistry(modEventBus);
        MenuTypeRegistry menuTypeRegistry = new MenuTypeRegistry(modEventBus);
        PoiTypeRegistry poiTypesRegistry = new PoiTypeRegistry(modEventBus);
        VillagerProfessionRegistry villagerProfessionRegistry = new VillagerProfessionRegistry(modEventBus);

        // Initialise the registries. Do this here because (e.g.)
        // blockEntityTypeRegistry requires blockRegistry to be created and
        // vice-versa.
        bannerPatternRegistry.initialise();
        blockEntityTypeRegistry.initialise(blockRegistry, menuTypeRegistry);
        blockRegistry.initialise(blockEntityTypeRegistry, itemRegistry, menuTypeRegistry);
        decoratedPotPatternsRegistry.initialise();
        entityTypeRegistry.initialise(blockRegistry);
        itemRegistry.initialise(bannerPatternRegistry, blockRegistry, entityTypeRegistry);
        lootModifierRegistry.initialise(itemRegistry);
        menuTypeRegistry.initialise();
        poiTypesRegistry.initialise(blockRegistry);
        villagerProfessionRegistry.initialise(poiTypesRegistry);

        // Create the Mod event listeners
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new ClientEventListener(modEventBus, blockEntityTypeRegistry, entityTypeRegistry);
        }

        new LifecycleEventListener(modEventBus, decoratedPotPatternsRegistry, itemRegistry,menuTypeRegistry);
        new ModEntityEventListener(modEventBus, entityTypeRegistry);
        new ModEventListener(modEventBus, itemRegistry);

        // Create the Forge event listeners.
        new ForgeEventListener(forgeEventBus);
        new ForgeEntityEventListener(forgeEventBus);
        new LivingEventListener(forgeEventBus);
        new MobSpawnEventListener(forgeEventBus, entityTypeRegistry);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);
        new ServerEventListener(forgeEventBus);
        new VillageEventListener(forgeEventBus, itemRegistry, villagerProfessionRegistry);

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
