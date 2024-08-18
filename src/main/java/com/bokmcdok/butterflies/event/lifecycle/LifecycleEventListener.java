package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Arrays;

/**
 * Events fired during the overall life cycle of the mod.
 */
public class LifecycleEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus) {
        modEventBus.register(this);
        modEventBus.addListener(this::commonSetup);
    }

    /**
     * Common setup event where we register brewing recipes.
     * @param event The event class.
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        int monarchIndex = Arrays.asList(ButterflySpeciesList.SPECIES).indexOf("monarch");
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(ButterfliesMod.getItemRegistry().getBottledButterflies().get(monarchIndex).get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
    }
}
