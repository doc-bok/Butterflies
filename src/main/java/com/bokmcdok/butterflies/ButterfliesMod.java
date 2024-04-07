package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.LootModifierRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ButterfliesMod.MOD_ID)
public class ButterfliesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "butterflies";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructor - The main entry point for the mod.
     */
    public ButterfliesMod() {
        final IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        if (modEventBus != null) {
            // Register ourselves for server and other game events we are interested in
            // modEventBus.register(this);

            // Deferred registries.
            BlockRegistry.INSTANCE.register(modEventBus);
            BlockEntityTypeRegistry.INSTANCE.register(modEventBus);
            EntityTypeRegistry.INSTANCE.register(modEventBus);
            ItemRegistry.INSTANCE.register(modEventBus);
            LootModifierRegistry.INSTANCE.register(modEventBus);
        }

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }
}
