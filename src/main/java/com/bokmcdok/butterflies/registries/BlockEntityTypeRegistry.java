package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.entity.ButterflyBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers any block entity types used by the mod.
 */
public class BlockEntityTypeRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<BlockEntityType<?>> INSTANCE =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ButterfliesMod.MODID);

    // The block entity for a bottled butterfly.
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<ButterflyBlockEntity>> BUTTERFLY_BLOCK =
            INSTANCE.register(ButterflyBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(ButterflyBlockEntity::new,
                                                     BlockRegistry.BOTTLED_BUTTERFLY_BLOCK.get()).build(null));
}
