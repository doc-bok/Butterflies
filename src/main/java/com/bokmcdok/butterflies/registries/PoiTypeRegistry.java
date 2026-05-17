package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register POIs for use by the AI.
 */
public class PoiTypeRegistry {

    // An instance of a deferred registry we use to register points of interest.
    public static final DeferredRegister<PoiType> POI_TYPES;

    // The lepidopterist's job block.
    public static final RegistryObject<PoiType> LEPIDOPTERIST;

    static {
        POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ButterfliesMod.MOD_ID);

        LEPIDOPTERIST = POI_TYPES.register("lepidopterist", () ->
                new PoiType(ImmutableSet.copyOf(BlockRegistry.BUTTERFLY_FEEDER.get().getStateDefinition().getPossibleStates()), 1, 1));
    }

    /**
     * Prevent construction.
     */
    private PoiTypeRegistry() {}
}
