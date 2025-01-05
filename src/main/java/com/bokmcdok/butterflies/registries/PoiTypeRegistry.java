package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

/**
 * Register POIs for use by the AI.
 */
public class PoiTypeRegistry {

    // An instance of a deferred registry we use to register.
    private final DeferredRegister<PoiType> deferredRegister;

    // The lepidopterist's job block.
    private RegistryObject<PoiType> lepidopterist;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public PoiTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.POI_TYPES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the POI types.
     * @param blockRegistry The block registry.
     */
    public void initialise(BlockRegistry blockRegistry) {
        lepidopterist =deferredRegister.register("lepidopterist",
                () -> new PoiType(getBlockStates(blockRegistry.getButterflyFeeder()), 1, 1));
    }

    /**
     * Accessor to the lepidopterist POI.
     * @return The POI Type.
     */
    public RegistryObject<PoiType> getLepidopterist() {
        return lepidopterist;
    }

    /**
     * Helper method to get a set of block states.
     * @param block The block to get the block states for.
     * @return The set of block states.
     */
    private Set<BlockState> getBlockStates(RegistryObject<Block> block) {
        return ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates());
    }
}
