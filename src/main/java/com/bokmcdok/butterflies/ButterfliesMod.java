package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.client.event.ClientEventListener;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.event.ModEventListener;
import com.bokmcdok.butterflies.event.entity.EntityEventListener;
import com.bokmcdok.butterflies.event.entity.player.PlayerEventListener;
import com.bokmcdok.butterflies.event.level.LevelEventListener;
import com.bokmcdok.butterflies.event.lifecycle.LifecycleEventListener;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.registries.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        // Create the registries.
        BlockEntityTypeRegistry blockEntityTypeRegistry = new BlockEntityTypeRegistry(modEventBus);
        BlockRegistry blockRegistry = new BlockRegistry(modEventBus);
        EntityTypeRegistry entityTypeRegistry = new EntityTypeRegistry(modEventBus);
        ItemRegistry itemRegistry = new ItemRegistry(modEventBus);
        LootModifierRegistry lootModifierRegistry = new LootModifierRegistry(modEventBus);
        MenuTypeRegistry menuTypeRegistry = new MenuTypeRegistry(modEventBus);

        // Initialise the registries. Do this here because (e.g.)
        // blockEntityTypeRegistry requires blockRegistry to be created and
        // vice-versa.
        blockEntityTypeRegistry.initialise(blockRegistry, menuTypeRegistry);
        blockRegistry.initialise(blockEntityTypeRegistry, menuTypeRegistry);
        entityTypeRegistry.initialise(blockRegistry);
        itemRegistry.initialise(blockRegistry, entityTypeRegistry);
        lootModifierRegistry.initialise(itemRegistry);
        menuTypeRegistry.initialise();

        // Create the Mod event listeners
        new ClientEventListener(modEventBus, entityTypeRegistry);
        new LifecycleEventListener(modEventBus, itemRegistry,menuTypeRegistry);
        new ModEventListener(modEventBus, itemRegistry);

        // Create the Forge event listeners.
        new EntityEventListener(forgeEventBus, modEventBus, entityTypeRegistry);
        new LevelEventListener(forgeEventBus);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
