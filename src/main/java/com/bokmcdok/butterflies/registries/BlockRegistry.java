package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.BottledCaterpillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers the blocks used by the mod.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Block> INSTANCE =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ButterfliesMod.MODID);

    // Bottled Caterpillars
    private static RegistryObject<Block> registerBottledButterfly(int butterflyIndex) {
        return INSTANCE.register(BottledButterflyBlock.getRegistryId(butterflyIndex), BottledButterflyBlock::new);
    }

    public static final List<RegistryObject<Block>> BOTTLED_BUTTERFLY_BLOCKS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledButterfly(i));
            }
        }
    };

    // Bottled Caterpillars
    private static RegistryObject<Block> registerBottledCaterpillar(int butterflyIndex) {
        return INSTANCE.register(BottledCaterpillarBlock.getRegistryId(butterflyIndex), BlockRegistry::bottledCaterpillarBlock);
    }

    public static final List<RegistryObject<Block>> BOTTLED_CATERPILLAR_BLOCKS = new ArrayList<>() {
        {
            for (int i = 0; i < ButterflySpeciesList.SPECIES.length; ++i) {
                add(registerBottledCaterpillar(i));
            }
        }
    };

    private static BottledCaterpillarBlock bottledCaterpillarBlock() {
        return new BottledCaterpillarBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                .isRedstoneConductor(BlockRegistry::never)
                .isSuffocating(BlockRegistry::never)
                .isValidSpawn(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.3F));
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @param entityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @return Always FALSE.
     */
    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
