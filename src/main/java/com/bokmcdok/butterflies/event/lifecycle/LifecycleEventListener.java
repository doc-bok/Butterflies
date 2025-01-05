package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
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
    private final ItemRegistry itemRegistry;
    private final MenuTypeRegistry menuTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus,
                                  ItemRegistry itemRegistry,
                                  MenuTypeRegistry menuTypeRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        this.itemRegistry = itemRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
    }

    /**
     * Common setup event where we register brewing recipes and pottery sherd
     * patterns.
     * @param event The event class.
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        NetworkEventListener.BUTTERFLY_NETWORK_CHANNEL.addListener(NetworkEventListener::onButterflyCollectionPayload);

        // Brewing Monarch Butterflies into poison.
        int monarchIndex = Arrays.asList(ButterflySpeciesList.SPECIES).indexOf("monarch");
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(itemRegistry.getBottledButterflies().get(monarchIndex).get()),
                PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
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
