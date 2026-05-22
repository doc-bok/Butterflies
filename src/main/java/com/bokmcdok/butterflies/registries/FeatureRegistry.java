package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.level.levelgen.feature.PeacemakerLair;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeatureRegistry {

    // An instance of a deferred registry we use to register blocks.
    public static final DeferredRegister<Feature<?>> FEATURES;

    // Peacemaker Lair
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PEACEMAKER_LAIR;

    static {
        FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ButterfliesMod.MOD_ID);

        PEACEMAKER_LAIR = FEATURES.register("peacemaker_lair", () -> new PeacemakerLair(NoneFeatureConfiguration.CODEC));
    }
}
