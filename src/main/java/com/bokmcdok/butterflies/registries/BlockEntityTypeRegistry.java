package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers block entity types.
 */
public class BlockEntityTypeRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;

    // The block entities.
    public static final RegistryObject<BlockEntityType<ButterflyFeederEntity>> BUTTERFLY_FEEDER;

    static {
        BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ButterfliesMod.MOD_ID);
        BUTTERFLY_FEEDER = BLOCK_ENTITY_TYPES.register("butterfly_feeder",
                () -> BlockEntityType.Builder.of(ButterflyFeederEntity::new,
                        BlockRegistry.BUTTERFLY_FEEDER.get()).build(null));
    }

    /**
     * Prevent construction.
     */
    private BlockEntityTypeRegistry() {}
}
