package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.client.event.ClientEventListener;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.event.ModEventListener;
import com.bokmcdok.butterflies.event.entity.EntityEventListener;
import com.bokmcdok.butterflies.event.entity.player.PlayerEventListener;
import com.bokmcdok.butterflies.event.level.LevelEventListener;
import com.bokmcdok.butterflies.event.lifecycle.LifecycleEventListener;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.LootModifierRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ButterfliesMod.MOD_ID)
public class ButterfliesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "butterflies";

    private static BlockRegistry BLOCK_REGISTRY;
    private static EntityTypeRegistry ENTITY_TYPE_REGISTRY;
    private static ItemRegistry ITEM_REGISTRY;

    /**
     * Constructor - The main entry point for the mod.
     */
    public ButterfliesMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        // Create the Mod event listeners
        new ClientEventListener(modEventBus);
        new LifecycleEventListener(modEventBus);
        new ModEventListener(modEventBus);

        // Create the Forge event listeners.
        new EntityEventListener(forgeEventBus, modEventBus);
        new LevelEventListener(forgeEventBus);
        new NetworkEventListener(forgeEventBus);
        new PlayerEventListener(forgeEventBus);

        // Create the registries.
        BLOCK_REGISTRY = new BlockRegistry(modEventBus);
        ENTITY_TYPE_REGISTRY = new EntityTypeRegistry(modEventBus);
        ITEM_REGISTRY = new ItemRegistry(modEventBus, BLOCK_REGISTRY);
        new LootModifierRegistry(modEventBus);

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }

    /**
     * Accessor for the block registry.
     * @return The block registry.
     */
    public static BlockRegistry getBlockRegistry() {
        return BLOCK_REGISTRY;
    }

    /**
     * Accessor for the entity type registry.
     * @return The entity type registry.
     */
    public static EntityTypeRegistry getEntityTypeRegistry() {
        return ENTITY_TYPE_REGISTRY;
    }

    /**
     * Accessor for the item registry.
     * @return The item registry.
     */
    public static ItemRegistry getItemRegistry() {
        return ITEM_REGISTRY;
    }
}
