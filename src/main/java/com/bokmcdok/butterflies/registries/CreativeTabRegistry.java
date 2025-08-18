package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;


/**
 * This class registers new creative tabs for the Butterflies Mod
 */
public class CreativeTabRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<CreativeModeTab> deferredRegister;

    // The Butterfly Creative Tab
    private RegistryObject<CreativeModeTab> butterflyCreativeTab;


    /**
     * Constructs and registers the creative tab registry.
     * @param modEventBus The event bus to register with. Must not be null.
     */
    public CreativeTabRegistry(@NotNull IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Registers the creative tab. Must only be called once.
     * @param itemRegistry Registry containing mod items. Must not be null.
     * @throws IllegalStateException if called more than once
     */
    public void initialise(@NotNull ItemRegistry itemRegistry) {
        if (butterflyCreativeTab != null) {
            throw new IllegalStateException("Creative tabs have already been initialised");
        }

        this.butterflyCreativeTab = deferredRegister.register("butterfly_creative_tab",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.butterfly_tab"))
                        .icon(itemRegistry.getButterflyBook().get()::getDefaultInstance)
                        .build()
        );
    }

    /**
     * @return The butterfly creative tab. Never null after initialise().
     * @throws IllegalStateException if called before initialise().
     */
    @NotNull
    public RegistryObject<CreativeModeTab> getButterflyCreativeTab() {
        if (butterflyCreativeTab == null) {
            throw new IllegalStateException("Creative tab has not been initialised. Call initialise() first.");
        }

        return butterflyCreativeTab;
    }
}
