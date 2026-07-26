package com.bokmcdok.butterflies.registries;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public record SpeciesRegistrations(
        List<RegistryObject<Item>> butterflyNets,
        List<RegistryObject<Item>> fireproofButterflyNets,
        List<RegistryObject<Item>> butterflyEggs,
        List<RegistryObject<Item>> caterpillars,
        List<RegistryObject<Item>> bottledButterflies,
        List<RegistryObject<Item>> bottledCaterpillars,
        List<RegistryObject<Item>> butterflyScrolls,
        int peacemakerIndex) {
}
