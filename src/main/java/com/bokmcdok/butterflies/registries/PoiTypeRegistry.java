package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

/**
 * Register POIs for use by the AI.
 */
public class PoiTypeRegistry {

    // An instance of a deferred registry we use to register.
    public static final DeferredRegister<PoiType> REGISTER;

    // The lepidopterist's job block.
    public static final RegistryObject<PoiType> LEPIDOPTERIST;

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.POI_TYPES, ButterfliesMod.MOD_ID);
        LEPIDOPTERIST = REGISTER.register("lepidopterist",
                () -> new PoiType(getBlockStates(BlockRegistry.BUTTERFLY_FEEDER), 1, 1));
    }

    /**
     * Helper method to get a set of block states.
     * @param block The block to get the block states for.
     * @return The set of block states.
     */
    private static Set<BlockState> getBlockStates(RegistryObject<Block> block) {
        return ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates());
    }

    // Prevent construction.
    private PoiTypeRegistry() {}
}
