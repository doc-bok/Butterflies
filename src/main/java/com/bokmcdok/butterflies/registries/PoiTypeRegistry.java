package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Register POIs for use by the AI.
 */
public class PoiTypeRegistry {

    // An instance of a deferred registry we use to register points of interest.
    public static final DeferredRegister<PoiType> POI_TYPES;

    // The lepidopterist's job block.
    public static final DeferredHolder<PoiType, PoiType> LEPIDOPTERIST;

    static {
        POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ButterfliesMod.MOD_ID);

        LEPIDOPTERIST = POI_TYPES.register("lepidopterist", () ->
                new PoiType(ImmutableSet.copyOf(BlockRegistry.BUTTERFLY_FEEDER.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    /**
     * Prevent construction.
     */
    private PoiTypeRegistry() {}
}
