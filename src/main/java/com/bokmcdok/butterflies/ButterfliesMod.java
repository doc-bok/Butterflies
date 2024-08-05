package com.bokmcdok.butterflies;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.LootModifierRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Arrays;

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
        modEventBus.addListener(this::commonSetup);

        // Deferred registries.
        BlockRegistry.INSTANCE.register(modEventBus);
        BlockEntityTypeRegistry.INSTANCE.register(modEventBus);
        EntityTypeRegistry.INSTANCE.register(modEventBus);
        ItemRegistry.INSTANCE.register(modEventBus);
        LootModifierRegistry.INSTANCE.register(modEventBus);

        // Mod Config Settings
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ButterfliesConfig.SERVER_CONFIG);
    }

    /**
     * Common setup event where we register brewing recipes.
     * @param event The event class.
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        int monarchIndex = Arrays.asList(ButterflySpeciesList.SPECIES).indexOf("monarch");
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(ItemRegistry.CATERPILLAR_ITEMS.get(monarchIndex).get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
    }
}
