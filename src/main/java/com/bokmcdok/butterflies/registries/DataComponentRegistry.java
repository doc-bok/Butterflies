package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class DataComponentRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS;

    // The list of pages in a butterfly book.
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> BUTTERFLY_BOOK_PAGES;

    // The entity ID of a butterfly.
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BUTTERFLY_ENTITY_ID;

    static {
        DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ButterfliesMod.MOD_ID);

        BUTTERFLY_BOOK_PAGES = DATA_COMPONENTS.register(
                "butterfly_book_pages",
                () -> DataComponentType.<List<Integer>>builder()
                        .persistent(Codec.INT.listOf())
                        .networkSynchronized(ByteBufCodecs.INT.apply(ByteBufCodecs.list()))
                        .cacheEncoding()
                        .build());

        BUTTERFLY_ENTITY_ID = DATA_COMPONENTS.register(
                "butterfly_entity_id",
                () -> DataComponentType.<String>builder()
                        .persistent(Codec.STRING)
                        .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                        .cacheEncoding()
                        .build());
    }

    /**
     * Prevent construction.
     */
    private DataComponentRegistry() {}
}
