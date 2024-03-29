package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.LootModifierRegistry;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ButterfliesMod.MODID)
public class ButterfliesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "butterflies";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructor - The main entry point for the mod.
     */
    public ButterfliesMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        modEventBus.register(this);

        // Deferred registries.
        BlockRegistry.INSTANCE.register(modEventBus);
        BlockEntityTypeRegistry.INSTANCE.register(modEventBus);
        EntityTypeRegistry.INSTANCE.register(modEventBus);
        ItemRegistry.INSTANCE.register(modEventBus);
        LootModifierRegistry.INSTANCE.register(modEventBus);

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
