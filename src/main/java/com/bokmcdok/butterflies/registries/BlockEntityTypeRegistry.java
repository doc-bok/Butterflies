package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.entity.ButterflyFeederEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers block entity types.
 */
public class BlockEntityTypeRegistry {

    // Reference to the menu type registry.
    private MenuTypeRegistry menuTypeRegistry;

    // An instance of a deferred registry we use to register items.
    private final DeferredRegister<BlockEntityType<?>> deferredRegister;

    // The butterfly feeder entity.
    private DeferredHolder<BlockEntityType<?>, BlockEntityType<ButterflyFeederEntity>> butterflyFeeder;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public BlockEntityTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the block entities.
     * @param blockRegistry The block registry.
     * @param menuTypeRegistry The menu type registry.
     */
    public void initialise(BlockRegistry blockRegistry,
                           MenuTypeRegistry menuTypeRegistry) {

        this.menuTypeRegistry = menuTypeRegistry;

        //noinspection DataFlowIssue
        this.butterflyFeeder = this.deferredRegister.register("butterfly_feeder",
                () -> BlockEntityType.Builder.of(this::createButterflyFeeder,
                        blockRegistry.getButterflyFeeder().value()).build(null));

    }

    /**
     * Get the butterfly feeder.
     * @return The block entity type.
     */
    public DeferredHolder<BlockEntityType<?>, BlockEntityType<ButterflyFeederEntity>> getButterflyFeeder() {
        return butterflyFeeder;
    }

    /**
     * Create a butterfly feeder.
     * @param blockPos The position of the block.
     * @param blockState The block's state.
     * @return A new block entity.
     */
    private ButterflyFeederEntity createButterflyFeeder(BlockPos blockPos,
                                                        BlockState blockState) {
        return new ButterflyFeederEntity(menuTypeRegistry,
                butterflyFeeder.get(),
                blockPos,
                blockState);
    }
}
