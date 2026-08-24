package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.registries.CreativeTabRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles creative tab content building for Butterflies Mod
 */
public class ModEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEventListener(@NotNull IEventBus modEventBus) {
        modEventBus.register(this);
        modEventBus.addListener(this::onBuildCreativeModeTabContents);
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    public void onBuildCreativeModeTabContents(@NotNull BuildCreativeModeTabContentsEvent event) {

        if (!Objects.equals(event.getTabKey(), CreativeTabRegistry.BUTTERFLY_CREATIVE_TAB.getKey())) {
            return;
        }

        // Nets
        event.accept(ItemRegistry.EMPTY_BUTTERFLY_NET);
        ItemRegistry.BUTTERFLY_NETS.forEach(event::accept);
        event.accept(ItemRegistry.BURNT_BUTTERFLY_NET);
        event.accept(ItemRegistry.FIREPROOF_BUTTERFLY_NET);
        ItemRegistry.FIREPROOF_BUTTERFLY_NETS.forEach(event::accept);

        // Eggs
        ItemRegistry.BUTTERFLY_EGGS.forEach(event::accept);

        // Caterpillars
        ItemRegistry.CATERPILLARS.forEach(event::accept);

        // Bottles
        ItemRegistry.BOTTLED_BUTTERFLIES.forEach(event::accept);
        ItemRegistry.BOTTLED_CATERPILLARS.forEach(event::accept);

        // Scrolls
        ItemRegistry.BUTTERFLY_SCROLLS.forEach(event::accept);

        // Books
        event.accept(ItemRegistry.BUTTERFLY_BOOK);
        event.accept(ItemRegistry.ZHUANGZI_BOOK);

        // Blocks
        event.accept(ItemRegistry.BUTTERFLY_FEEDER);
        event.accept(ItemRegistry.BUTTERFLY_MICROSCOPE);

        // Infested Apple
        event.accept(ItemRegistry.INFESTED_APPLE);

        // Silk
        event.accept(ItemRegistry.SILK);

        // Origami
        ItemRegistry.BUTTERFLY_ORIGAMI.forEach(event::accept);

        // Sherd
        event.accept(ItemRegistry.BUTTERFLY_POTTERY_SHERD);

        // Banner Pattern
        event.accept(ItemRegistry.BUTTERFLY_BANNER_PATTERN);

        // Peacemaker Honey
        event.accept(ItemRegistry.PEACEMAKER_HONEY_BOTTLE);

        // Rope
        event.accept(ItemRegistry.ROPE);

        // Spawn Eggs
        SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS.forEach(event::accept);
        SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS.forEach(event::accept);
        SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS.forEach(event::accept);
        SpawnEggRegistry.EGG_SPAWN_EGGS.forEach(event::accept);
        event.accept(SpawnEggRegistry.BUTTERFLY_GOLEM_SPAWN_EGG);

        // Peacemaker Spawn Eggs
        event.accept(SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_EVOKER_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_ILLUSIONER_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_PILLAGER_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_VILLAGER_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_VINDICATOR_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_WANDERING_TRADER_SPAWN_EGG);
        event.accept(SpawnEggRegistry.PEACEMAKER_WITCH_SPAWN_EGG);
    }
}
