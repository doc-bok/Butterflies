package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.registries.DecoratedPotPatternsRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Arrays;
import java.util.Map;

/**
 * Events fired during the overall life cycle of the mod.
 */
public class LifecycleEventListener {

    // Reference to the registries.
    private final DecoratedPotPatternsRegistry decoratedPotPatternsRegistry;
    private final ItemRegistry itemRegistry;
    private final MenuTypeRegistry menuTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus,
                                  DecoratedPotPatternsRegistry decoratedPotPatternsRegistry,
                                  ItemRegistry itemRegistry,
                                  MenuTypeRegistry menuTypeRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        this.decoratedPotPatternsRegistry = decoratedPotPatternsRegistry;
        this.itemRegistry = itemRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
    }

    /**
     * Common setup event where we register brewing recipes.
     * @param event The event class.
     */
    private void commonSetup(FMLCommonSetupEvent event) {

        // Brewing Monarch Butterflies into poison.
        int monarchIndex = Arrays.asList(ButterflySpeciesList.SPECIES).indexOf("monarch");
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(itemRegistry.getBottledButterflies().get(monarchIndex).get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));

        // Butterfly Sherd Pattern.
        Map<Item, ResourceKey<String>> itemToPotTextureMap = Maps.newHashMap(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        itemToPotTextureMap.put(itemRegistry.getButterflyPotterySherd().get(),
                                decoratedPotPatternsRegistry.getButterflyPotPattern().getKey());
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemToPotTextureMap;
    }

    /**
     * Register the screens with their respective menus.
     * @param event The client setup event.
     */
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(this.menuTypeRegistry.getButterflyFeederMenu().get(), ButterflyFeederScreen::new)
        );
    }
}
