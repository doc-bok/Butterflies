package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers block entity types.
 */
public class BlockEntityTypeRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;

    // The block entities.
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ButterflyFeederEntity>> BUTTERFLY_FEEDER;

    static {
        BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ButterfliesMod.MOD_ID);

        //noinspection DataFlowIssue
        BUTTERFLY_FEEDER = BLOCK_ENTITY_TYPES.register("butterfly_feeder",
                () -> BlockEntityType.Builder.of(ButterflyFeederEntity::new,
                        BlockRegistry.BUTTERFLY_FEEDER.get()).build(null));
    }

    /**
     * Prevent construction.
     */
    private BlockEntityTypeRegistry() {}
}
