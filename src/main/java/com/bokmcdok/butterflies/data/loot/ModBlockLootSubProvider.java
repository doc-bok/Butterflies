package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Provides loot tables for blocks.
 */
public class ModBlockLootSubProvider extends BlockLootSubProvider {

    /**
     * Construction.
     */
    public ModBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    /**
     * Generate loot tables for all blocks in the mod.
     */
    @Override
    protected void generate() {
        for(DeferredHolder<Block, Block> bottledButterfly : BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS) {
            dropSelf(bottledButterfly.get());
        }

        for(DeferredHolder<Block, Block> bottledCaterpillar : BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS) {
            dropSelf(bottledCaterpillar.get());
        }

        for(DeferredHolder<Block, Block> flowerBud : BlockRegistry.FLOWER_BUDS) {
            add(flowerBud.get(), noDrop());
        }

        dropSelf(BlockRegistry.BUTTERFLY_FEEDER.get());
        dropSelf(BlockRegistry.BUTTERFLY_MICROSCOPE.get());

        for(DeferredHolder<Block, Block> origami : BlockRegistry.BUTTERFLY_ORIGAMI) {
            dropSelf(origami.get());
        }
    }

    /**
     * Get the already known blocks.
     * @return The vanilla blocks.
     */
    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map((x) -> (Block)x.get())::iterator;
    }
}
