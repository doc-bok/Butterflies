package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientPayloadHandler;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.bokmcdok.butterflies.registries.CreativeTabRegistry;
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

        this.creativeTabRegistry = creativeTabRegistry;
        this.itemRegistry = itemRegistry;
    }

    /**
     * Registers items with the relevant creative tab
     * @param event The event information
     */
    @SubscribeEvent
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
        itemRegistry.getButterflySpawnEggs().forEach(event::accept);
        itemRegistry.getCaterpillarSpawnEggs().forEach(event::accept);
        event.accept(itemRegistry.getButterflyGolemSpawnEgg());
    }

    /**
     * Register a network payload namespace for our mod.
     * @param event The event fired when payload handlers are being registered.
     */
    @SubscribeEvent
    private void onRegisterPayloadHandler(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(ButterfliesMod.MOD_ID);
        registrar.play(ClientBoundButterflyDataPacket.ID, ClientBoundButterflyDataPacket::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handleButterflyData));
    }
}
