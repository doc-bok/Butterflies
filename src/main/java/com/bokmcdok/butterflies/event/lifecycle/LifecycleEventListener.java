package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.DecoratedPotPatternsRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

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
        ResourceKey<DecoratedPotPattern> key = decoratedPotPatternsRegistry.getButterflyPotPattern().getKey();
        if (key != null) {
            ImmutableMap.Builder<Item, ResourceKey<DecoratedPotPattern>> itemsToPot = new ImmutableMap.Builder<>();
            itemsToPot.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
            itemsToPot.put(itemRegistry.getButterflyPotterySherd().get(), key);
            DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemsToPot.build();
        }
    }

    /**
     * Register the screens with their respective menus.
     * @param event The client setup event.
     */
    @SubscribeEvent
    private void clientSetup(RegisterMenuScreensEvent event) {
        event.register(this.menuTypeRegistry.getButterflyFeederMenu().get(), ButterflyFeederScreen::new);
        event.register(this.menuTypeRegistry.getButterflyMicroscopeMenu().get(), ButterflyMicroscopeScreen::new);
    }
}
