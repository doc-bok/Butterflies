package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.registries.CreativeTabRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles creative tab content building for Butterflies Mod
 */
public class ModEventListener {

    // References to the registries.
    private final CreativeTabRegistry creativeTabRegistry;
    private final ItemRegistry itemRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEventListener(@NotNull IEventBus modEventBus,
                            @NotNull CreativeTabRegistry creativeTabRegistry,
                            @NotNull ItemRegistry itemRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::onBuildCreativeModeTabContents);

        this.creativeTabRegistry = creativeTabRegistry;
        this.itemRegistry = itemRegistry;
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    public void onBuildCreativeModeTabContents(@NotNull BuildCreativeModeTabContentsEvent event) {

        if (!Objects.equals(event.getTabKey(), creativeTabRegistry.getButterflyCreativeTab().getKey())) {
            return;
        }

        // Nets
        event.accept(itemRegistry.getEmptyButterflyNet());
        itemRegistry.getButterflyNets().forEach(event::accept);
        event.accept(itemRegistry.getBurntButterflyNet());

        // Eggs
        itemRegistry.getButterflyEggs().forEach(event::accept);

        // Caterpillars
        itemRegistry.getCaterpillars().forEach(event::accept);

        // Bottles
        itemRegistry.getBottledButterflies().forEach(event::accept);
        itemRegistry.getBottledCaterpillars().forEach(event::accept);

        // Scrolls
        itemRegistry.getButterflyScrolls().forEach(event::accept);

        // Books
        event.accept(itemRegistry.getButterflyBook());
        event.accept(itemRegistry.getZhuangziBook());

        // Blocks
        event.accept(itemRegistry.getButterflyFeeder());
        event.accept(itemRegistry.getButterflyMicroscope());

        // Infested Apple
        event.accept(itemRegistry.getInfestedApple());

        // Silk
        event.accept(itemRegistry.getSilk());

        // Origami
        itemRegistry.getButterflyOrigami().forEach(event::accept);

        // Sherd
        event.accept(itemRegistry.getButterflyPotterySherd());

        // Banner Pattern
        event.accept(itemRegistry.getButterflyBannerPattern());

        // Spawn Eggs
        itemRegistry.getEggSpawnEggs().forEach(event::accept);
        itemRegistry.getCaterpillarSpawnEggs().forEach(event::accept);
        itemRegistry.getButterflySpawnEggs().forEach(event::accept);
        event.accept(itemRegistry.getButterflyGolemSpawnEgg());
    }
}
