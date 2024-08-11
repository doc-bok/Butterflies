package com.bokmcdok.butterflies;

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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ButterfliesMod.MOD_ID)
public class ButterfliesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "butterflies";

    /**
     * Constructor - The main entry point for the mod.
     */
    public ButterfliesMod() {
        final IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        if (modEventBus != null) {
            // Register ourselves for server and other game events we are interested in
            // modEventBus.register(this);
            modEventBus.addListener(this::commonSetup);

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
