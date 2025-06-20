package com.bokmcdok.butterflies.event.brewing;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.Arrays;
import java.util.Map;

/**
 * Events fired during the overall life cycle of the mod.
 */
public class BrewingEventListener {

    // Reference to the registries.
    private final ItemRegistry itemRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public BrewingEventListener(IEventBus modEventBus,
                                ItemRegistry itemRegistry) {
        modEventBus.register(this);

        this.itemRegistry = itemRegistry;
    }

    /**
     * Register any new potion mixes here.
     * @param event The event object.
     */
    @SubscribeEvent
    private void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {

        // Check for the poisonous trait
        for (int i = 0; i < ButterflyInfo.TRAITS.length; ++i) {
            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyData.Trait.POISONOUS)) {
                event.getBuilder().addMix(
                        Potions.AWKWARD,
                        itemRegistry.getBottledButterflies().get(i).get(),
                        Potions.POISON
                );
            }
        }
    }
}
