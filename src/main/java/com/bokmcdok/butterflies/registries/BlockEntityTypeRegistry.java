package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers block entity types.
 */
public class BlockEntityTypeRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<BlockEntityType<?>> REGISTER;

    // The block entities.
    public static RegistryObject<BlockEntityType<ButterflyFeederEntity>> BUTTERFLY_FEEDER;

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ButterfliesMod.MOD_ID);
        BUTTERFLY_FEEDER = REGISTER.register("butterfly_feeder",
                () -> BlockEntityType.Builder.of(BlockEntityTypeRegistry::createButterflyFeeder,
                        BlockRegistry.BUTTERFLY_FEEDER.get()).build(null));
    }

    /**
     * Create a butterfly feeder.
     * @param blockPos The position of the block.
     * @param blockState The block's state.
     * @return A new block entity.
     */
    private static ButterflyFeederEntity createButterflyFeeder(BlockPos blockPos,
                                                        BlockState blockState) {
        return new ButterflyFeederEntity(BUTTERFLY_FEEDER.get(), blockPos, blockState);
    }
}
