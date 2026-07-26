package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientPayloadHandler;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import com.bokmcdok.butterflies.registries.CreativeTabRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
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
    @SubscribeEvent
    public void onBuildCreativeModeTabContents(@NotNull BuildCreativeModeTabContentsEvent event) {

        if (!Objects.equals(event.getTabKey(), CreativeTabRegistry.BUTTERFLY_CREATIVE_TAB.getKey())) {
            return;
        }

        // Nets
        event.accept(ItemRegistry.EMPTY_BUTTERFLY_NET.get());
        ItemRegistry.BUTTERFLY_NETS.forEach((x) -> event.accept(x.get()));
        event.accept(ItemRegistry.BURNT_BUTTERFLY_NET.get());
        event.accept(ItemRegistry.FIREPROOF_BUTTERFLY_NET.get());
        ItemRegistry.FIREPROOF_BUTTERFLY_NETS.forEach((x) -> event.accept(x.get()));

        // Eggs
        ItemRegistry.BUTTERFLY_EGGS.forEach((x) -> event.accept(x.get()));

        // Caterpillars
        ItemRegistry.CATERPILLARS.forEach((x) -> event.accept(x.get()));

        // Bottles
        ItemRegistry.BOTTLED_BUTTERFLIES.forEach((x) -> event.accept(x.get()));
        ItemRegistry.BOTTLED_CATERPILLARS.forEach((x) -> event.accept(x.get()));

        // Scrolls
        ItemRegistry.BUTTERFLY_SCROLLS.forEach((x) -> event.accept(x.get()));

        // Books
        event.accept(ItemRegistry.BUTTERFLY_BOOK.get());
        event.accept(ItemRegistry.ZHUANGZI_BOOK.get());

        // Blocks
        event.accept(ItemRegistry.BUTTERFLY_FEEDER.get());
        event.accept(ItemRegistry.BUTTERFLY_MICROSCOPE.get());

        // Infested Apple
        event.accept(ItemRegistry.INFESTED_APPLE.get());

        // Silk
        event.accept(ItemRegistry.SILK.get());

        // Origami
        ItemRegistry.BUTTERFLY_ORIGAMI.forEach((x) -> event.accept(x.get()));

        // Sherd
        event.accept(ItemRegistry.BUTTERFLY_POTTERY_SHERD.get());

        // Banner Pattern
        event.accept(ItemRegistry.BUTTERFLY_BANNER_PATTERN.get());

        // Peacemaker Honey
        event.accept(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get());

        // Spawn Eggs
        SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS.forEach((x) -> event.accept(x.get()));
        SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS.forEach((x) -> event.accept(x.get()));
        SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS.forEach((x) -> event.accept(x.get()));
        SpawnEggRegistry.EGG_SPAWN_EGGS.forEach((x) -> event.accept(x.get()));
        event.accept(SpawnEggRegistry.BUTTERFLY_GOLEM_SPAWN_EGG.get());

        // Peacemaker Spawn Eggs
        event.accept(SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_EVOKER_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_ILLUSIONER_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_PILLAGER_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_VILLAGER_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_VINDICATOR_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_WANDERING_TRADER_SPAWN_EGG.get());
        event.accept(SpawnEggRegistry.PEACEMAKER_WITCH_SPAWN_EGG.get());
    }
}
