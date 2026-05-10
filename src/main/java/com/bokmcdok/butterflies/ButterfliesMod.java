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
public class ButterfliesMod {
    public static final String MOD_ID = "butterflies";

    public static TagRegistry TAG_REGISTRY;

    public ButterfliesMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        BannerPatternRegistry.REGISTER.register(modEventBus);
        BlockEntityTypeRegistry.REGISTER.register(modEventBus);
        BlockRegistry.REGISTER.register(modEventBus);
        CreativeTabRegistry.REGISTER.register(modEventBus);
        DecoratedPotPatternsRegistry.REGISTER.register(modEventBus);
        EntityTypeRegistry.REGISTER.register(modEventBus);
        ItemRegistry.REGISTER.register(modEventBus);
        LootModifierRegistry.REGISTER.register(modEventBus);
        MenuTypeRegistry.REGISTER.register(modEventBus);

        // Initialize registries with explicit dependency ordering
        PoiTypeRegistry poiTypesRegistry = new PoiTypeRegistry(modEventBus);
        TAG_REGISTRY = new TagRegistry();
        VillagerProfessionRegistry villagerProfessionRegistry = new VillagerProfessionRegistry(modEventBus);

        poiTypesRegistry.initialise();
        villagerProfessionRegistry.initialise(poiTypesRegistry);

        // Register client-only listeners
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new ClientEventListener(modEventBus);
        }

        // Register mod lifecycle and mod-specific event listeners
        new LifecycleEventListener(modEventBus);
        new ModEventListener(modEventBus);

        // Register Forge event listeners
        new EntityEventListener(forgeEventBus, modEventBus);
        new ForgeEventListener(forgeEventBus);
        new LivingEventListener(forgeEventBus);
        new MobSpawnEventListener(forgeEventBus, TAG_REGISTRY);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);
        new ServerEventListener(forgeEventBus);
        new VillageEventListener(forgeEventBus, villagerProfessionRegistry);

        // Register mod configuration files
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ButterfliesConfig.COMMON_CONFIG);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
