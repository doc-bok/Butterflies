package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.DecoratedPotPatternsRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

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

        this.decoratedPotPatternsRegistry = decoratedPotPatternsRegistry;
        this.itemRegistry = itemRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
    }

    /**
     * Common setup event where we register brewing recipes and pottery sherd
     * patterns.
     * @param event The event class.
     */
    @SubscribeEvent
    private void commonSetup(FMLCommonSetupEvent event) {

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
    @SubscribeEvent
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(this.menuTypeRegistry.getButterflyFeederMenu().get(), ButterflyFeederScreen::new)
        );

        event.enqueueWork(
                () -> MenuScreens.register(this.menuTypeRegistry.getButterflyMicroscopeMenu().get(), ButterflyMicroscopeScreen::new)
        );
    }
}
