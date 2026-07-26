package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.DecoratedPotPatternsRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyInfo;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;

import java.util.Arrays;
import java.util.Map;

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
        //modEventBus.addListener(this::clientSetup);
        //modEventBus.addListener(this::commonSetup);
    }

    /**
     * Common setup event where we register brewing recipes and pottery sherd
     * patterns.
     * @param event The event class.
     */
    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {

        // Check for the poisonous trait
        for (int i = 0; i < ButterflyInfo.TRAITS.length; ++i) {
            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyTrait.POISONOUS)) {
                BrewingRecipeRegistry.addRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(ItemRegistry.BOTTLED_BUTTERFLIES.get(i).get()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
            }
        }

        // Butterfly Sherd Pattern.
        Map<Item, ResourceKey<String>> itemToPotTextureMap = Maps.newHashMap(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
        itemToPotTextureMap.put(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get(),
                                DecoratedPotPatternsRegistry.BUTTERFLY_POT_PATTERN.getKey());
        DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemToPotTextureMap;
    }

    /**
     * Register the screens with their respective menus.
     * @param event The client setup event.
     */
    @SubscribeEvent
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(MenuTypeRegistry.BUTTERFLY_FEEDER_MENU.get(), ButterflyFeederScreen::new)
        );

        event.enqueueWork(
                () -> MenuScreens.register(MenuTypeRegistry.BUTTERFLY_MICROSCOPE_MENU.get(), ButterflyMicroscopeScreen::new)
        );
    }
}
