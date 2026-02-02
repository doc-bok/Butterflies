package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientBoundButterflyDataPacket;
import com.bokmcdok.butterflies.network.protocol.common.custom.ClientPayloadHandler;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
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
        event.accept(itemRegistry.getEmptyButterflyNet().get());
        itemRegistry.getButterflyNets().forEach((x) -> event.accept(x.get()));
        event.accept(itemRegistry.getBurntButterflyNet().get());

        // Eggs
        itemRegistry.getButterflyEggs().forEach((x) -> event.accept(x.get()));

        // Caterpillars
        itemRegistry.getCaterpillars().forEach((x) -> event.accept(x.get()));

        // Bottles
        itemRegistry.getBottledButterflies().forEach((x) -> event.accept(x.get()));
        itemRegistry.getBottledCaterpillars().forEach((x) -> event.accept(x.get()));

        // Scrolls
        itemRegistry.getButterflyScrolls().forEach((x) -> event.accept(x.get()));

        // Books
        event.accept(itemRegistry.getButterflyBook().get());
        event.accept(itemRegistry.getZhuangziBook().get());

        // Blocks
        event.accept(itemRegistry.getButterflyFeeder().get());
        event.accept(itemRegistry.getButterflyMicroscope().get());

        // Infested Apple
        event.accept(itemRegistry.getInfestedApple().get());

        // Silk
        event.accept(itemRegistry.getSilk().get());

        // Origami
        itemRegistry.getButterflyOrigami().forEach((x) -> event.accept(x.get()));

        // Sherd
        event.accept(itemRegistry.getButterflyPotterySherd().get());

        // Banner Pattern
        event.accept(itemRegistry.getButterflyBannerPattern().get());

        // Spawn Eggs
        itemRegistry.getEggSpawnEggs().forEach((x) -> event.accept(x.get()));
        itemRegistry.getCaterpillarSpawnEggs().forEach((x) -> event.accept(x.get()));
        itemRegistry.getChrysalisSpawnEggs().forEach((x) -> event.accept(x.get()));
        itemRegistry.getButterflySpawnEggs().forEach((x) -> event.accept(x.get()));
        event.accept(itemRegistry.getButterflyGolemSpawnEgg().get());

        // Peacemaker Spawn Eggs
        event.accept(itemRegistry.getPeacemakerButterflySpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerEvokerSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerIllusionerSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerPillagerSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerVillagerSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerVindicatorSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerWanderingTraderSpawnEgg().get());
        event.accept(itemRegistry.getPeacemakerWitchSpawnEgg().get());
    }

    /**
     * Register a network payload namespace for our mod.
     * @param event The event fired when payload handlers are being registered.
     */
    @SubscribeEvent
    private void onRegisterPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ButterfliesMod.MOD_ID);
        registrar.playToClient(ClientBoundButterflyDataPacket.TYPE_PAYLOAD,
                ClientBoundButterflyDataPacket.STREAM_CODEC,
                ClientPayloadHandler::handleButterflyData);
    }
}
