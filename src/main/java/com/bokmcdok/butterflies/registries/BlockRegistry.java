package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.block.BottledButterflyBlock;
import com.bokmcdok.butterflies.world.block.ButterflyLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers the blocks used by the mod.
 */
public class BlockRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<Block> INSTANCE =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ButterfliesMod.MODID);

    // The bottled butterfly block when it is in the world.
    public static final RegistryObject<Block> BOTTLED_BUTTERFLY_BLOCK =
            INSTANCE.register(BottledButterflyBlock.NAME,
            () -> new BottledButterflyBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .isRedstoneConductor(BlockRegistry::never)
                    .isSuffocating(BlockRegistry::never)
                    .isValidSpawn(BlockRegistry::never)
                    .isViewBlocking(BlockRegistry::never)
                    .noOcclusion()
                    .sound(SoundType.GLASS)
                    .strength(0.3F)));

    // Represent leaves that have butterfly eggs in them.
    public static final RegistryObject<Block> BUTTERFLY_OAK_LEAVES =
            INSTANCE.register("butterfly_oak_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_SPRUCE_LEAVES =
            INSTANCE.register("butterfly_spruce_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_BIRCH_LEAVES =
            INSTANCE.register("butterfly_birch_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_JUNGLE_LEAVES =
            INSTANCE.register("butterfly_jungle_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_ACACIA_LEAVES =
            INSTANCE.register("butterfly_acacia_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_DARK_OAK_LEAVES =
            INSTANCE.register("butterfly_dark_oak_leaves", () -> butterflyLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> BUTTERFLY_AZALEA_LEAVES =
            INSTANCE.register("butterfly_azalea_leaves", () -> butterflyLeaves(SoundType.AZALEA_LEAVES));

    public static final RegistryObject<Block> BUTTERFLY_FLOWERING_AZALEA_LEAVES =
            INSTANCE.register("butterfly_flowering_azalea_leaves", () -> butterflyLeaves(SoundType.AZALEA_LEAVES));

    //public static final Block CHERRY_LEAVES = register("cherry_leaves", new CherryLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(0.2F).randomTicks().sound(SoundType.CHERRY_LEAVES).noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never)));
    //public static final Block MANGROVE_LEAVES = register("mangrove_leaves", new MangroveLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never)));

    private static ButterflyLeavesBlock butterflyLeaves(SoundType p_152615_) {
        return new ButterflyLeavesBlock(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(p_152615_)
                .noOcclusion()
                .isValidSpawn(BlockRegistry::ocelotOrParrot)
                .isSuffocating(BlockRegistry::never)
                .isViewBlocking(BlockRegistry::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(BlockRegistry::never));
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @param entityType The entity type trying to spawn.
     * @return Always FALSE.
     */
    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    /**
     * Helper method for the "never" attribute.
     * @param blockState The current block state.
     * @param blockGetter Access to the block.
     * @param blockPos The block's position.
     * @return Always FALSE.
     */
    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState blockState,
                                          BlockGetter blockGetter,
                                          BlockPos position,
                                          EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }
}
