package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class DataComponentRegistry {

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<DataComponentType<?>> deferredRegister;

    // The list of pages in a butterfly book.
    private DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> butterflyBookPages;

    // The entity ID of a butterfly.
    private DeferredHolder<DataComponentType<?>, DataComponentType<String>> butterflyEntityId;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public DataComponentRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Initialise the data components.
     */
    public void initialise() {
        butterflyBookPages = deferredRegister.register(
                "butterfly_book_pages",
                () -> DataComponentType.<List<Integer>>builder()
                        .persistent(Codec.INT.listOf())
                        .networkSynchronized(ByteBufCodecs.INT.apply(ByteBufCodecs.list()))
                        .cacheEncoding()
                        .build());

        butterflyEntityId = deferredRegister.register(
                "butterfly_entity_id",
                () -> DataComponentType.<String>builder()
                        .persistent(Codec.STRING)
                        .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                        .cacheEncoding()
                        .build());
    }

    /**
     * Accessor for butterfly book pages.
     * @return The data component type.
     */
    public DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> getButterflyBookPages() {
        return butterflyBookPages;
    }

    /**
     * Accessor for butterfly entity ID
     * @return The data component type.
     */
    public DeferredHolder<DataComponentType<?>, DataComponentType<String>> getButterflyEntityId() {
        return butterflyEntityId;
    }
}
