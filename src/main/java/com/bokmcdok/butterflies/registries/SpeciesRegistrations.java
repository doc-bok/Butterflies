package com.bokmcdok.butterflies.registries;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public record SpeciesRegistrations(
        List<DeferredHolder<Item, Item>> butterflyNets,
        List<DeferredHolder<Item, Item>> fireproofButterflyNets,
        List<DeferredHolder<Item, Item>> butterflyEggs,
        List<DeferredHolder<Item, Item>> caterpillars,
        List<DeferredHolder<Item, Item>> bottledButterflies,
        List<DeferredHolder<Item, Item>> bottledCaterpillars,
        List<DeferredHolder<Item, Item>> butterflyScrolls,
        int peacemakerIndex) {
}
