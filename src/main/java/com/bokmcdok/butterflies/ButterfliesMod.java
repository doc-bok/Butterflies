package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.client.event.ClientEventListener;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.event.ForgeEventListener;
import com.bokmcdok.butterflies.event.ModEventListener;
import com.bokmcdok.butterflies.event.entity.EntityEventListener;
import com.bokmcdok.butterflies.event.entity.living.LivingEventListener;
import com.bokmcdok.butterflies.event.entity.living.MobSpawnEventListener;
import com.bokmcdok.butterflies.event.entity.player.PlayerEventListener;
import com.bokmcdok.butterflies.event.server.ServerEventListener;
import com.bokmcdok.butterflies.event.lifecycle.LifecycleEventListener;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.event.village.VillageEventListener;
import com.bokmcdok.butterflies.registries.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Main mod class for Butterflies.
 * Handles mod setup including registries, event listeners, and configs.
 */
@Mod(ButterfliesMod.MOD_ID)
public final class ButterfliesMod {

    public static final String MOD_ID = "butterflies";

    /**
     * Initialize and configure the mod.
     */
    public ButterfliesMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        registerRegistries(modEventBus);
        registerListeners(modEventBus,  forgeEventBus);
        registerClientListeners(modEventBus);
        registerConfigs();
    }

    /**
     * Register the mod-specific registries.
     * @param modEventBus The mod's event bus.
     */
    private void registerRegistries(IEventBus modEventBus) {
        BannerPatternRegistry.BANNER_PATTERNS.register(modEventBus);
        BlockEntityTypeRegistry.BLOCK_ENTITY_TYPES.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        ButterflyEntityTypeRegistry.BUTTERFLY_ENTITY_TYPES.register(modEventBus);
        CreativeTabRegistry.CREATIVE_TABS.register(modEventBus);
        DecoratedPotPatternsRegistry.DECORATED_POT_PATTERNS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        LootModifierRegistry.LOOT_MODIFIERS.register(modEventBus);
        MenuTypeRegistry.MENU_TYPES.register(modEventBus);
        PeacemakerEntityTypeRegistry.PEACEMAKER_ENTITY_TYPES.register(modEventBus);
        PoiTypeRegistry.POI_TYPES.register(modEventBus);
        SpawnEggRegistry.SPAWN_EGGS.register(modEventBus);
        VillagerProfessionRegistry.VILLAGER_PROFESSIONS.register(modEventBus);
    }

    /**
     * Register the listeners for the mod.
     * @param modEventBus The mod's event bus.
     * @param forgeEventBus Forge's event bus.
     */
    private void registerListeners(IEventBus modEventBus, IEventBus forgeEventBus) {

        // Register mod lifecycle and mod-specific event listeners
        new LifecycleEventListener(modEventBus);
        new ModEventListener(modEventBus);

        // Register Forge event listeners
        new EntityEventListener(forgeEventBus, modEventBus);
        new ForgeEventListener(forgeEventBus);
        new LivingEventListener(forgeEventBus);
        new MobSpawnEventListener(forgeEventBus);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);
        new ServerEventListener(forgeEventBus);
        new VillageEventListener(forgeEventBus);
    }

    /**
     * Register the client-specific listeners for the mod.
     * @param modEventBus The mod's event bus.
     */
    private void registerClientListeners(IEventBus modEventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new ClientEventListener(modEventBus);
        }
    }

    /**
     * Register the mod's configs.
     */
    private void registerConfigs() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ButterfliesConfig.COMMON_CONFIG);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
