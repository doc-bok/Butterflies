package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.client.event.ClientEventListener;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.LootModifierRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;

import java.util.Arrays;

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
        blockRegistry.initialise(blockEntityTypeRegistry, menuTypeRegistry);
        decoratedPotPatternsRegistry.initialise();
        entityTypeRegistry.initialise(blockRegistry);
        itemRegistry.initialise(bannerPatternRegistry, blockRegistry, entityTypeRegistry);
        lootModifierRegistry.initialise(itemRegistry);
        menuTypeRegistry.initialise();
        poiTypesRegistry.initialise(blockRegistry);
        villagerProfessionRegistry.initialise(poiTypesRegistry);

        // Create the Mod event listeners
        new ClientEventListener(modEventBus, blockEntityTypeRegistry, entityTypeRegistry);
        new LifecycleEventListener(modEventBus, decoratedPotPatternsRegistry, itemRegistry,menuTypeRegistry);
        new ModEventListener(modEventBus, itemRegistry);

        // Create the Forge event listeners.
        new EntityEventListener(forgeEventBus, modEventBus, entityTypeRegistry);
        new ForgeEventListener(forgeEventBus);
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
