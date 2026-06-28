package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Provides loot tables for blocks.
 */
public class ModBlockLootSubProvider extends BlockLoot {

    /**
     * Generate loot tables for all blocks in the mod.
     */
    @Override
    protected void addTables() {
        for(RegistryObject<Block> bottledButterfly : BlockRegistry.BOTTLED_BUTTERFLY_BLOCKS) {
            dropSelf(bottledButterfly.get());
        }

        for(RegistryObject<Block> bottledCaterpillar : BlockRegistry.BOTTLED_CATERPILLAR_BLOCKS) {
            dropSelf(bottledCaterpillar.get());
        }

        for(RegistryObject<Block> flowerBud : BlockRegistry.FLOWER_BUDS) {
            add(flowerBud.get(), noDrop());
        }

        dropSelf(BlockRegistry.BUTTERFLY_FEEDER.get());
        dropSelf(BlockRegistry.BUTTERFLY_MICROSCOPE.get());

        for(RegistryObject<Block> origami : BlockRegistry.BUTTERFLY_ORIGAMI) {
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
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
