package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers any block entity types used by the mod.
 */
public class BlockEntityTypeRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<BlockEntityType<?>> INSTANCE =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ButterfliesMod.MOD_ID);
}
