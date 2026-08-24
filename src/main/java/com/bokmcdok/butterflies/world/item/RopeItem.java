package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.entity.decoration.RopeKnotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * A class to represent a rope.
 */
public class RopeItem extends Item {

    /**
     * Construction.
     * @param properties The item's properties.
     */
    public RopeItem(Properties properties) {
        super(properties);
    }

    /**
     * Handles interactions with blocks and entities in the world.
     * @param useOnContext The context information for the use action.
     * @return The interaction result.
     */
    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        Player player = useOnContext.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }

        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (!isValidAnchor(blockState)) {
            return InteractionResult.PASS;
        }

        if(RopeKnotEntity.getRopeKnot(level, blockPos).isPresent()) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        RopeKnotEntity ropeKnot = RopeKnotEntity.createRopeKnot(level, blockPos);
        ropeKnot.playPlacementSound();

        useOnContext.getItemInHand().shrink(1);

        return InteractionResult.CONSUME;
    }

    /**
     * Returns true if a block is a valid anchor.
     * @param blockState The block state to check.
     * @return True if the block is a valid anchor.
     */
    private boolean isValidAnchor(BlockState blockState) {
        return blockState.is(BlockTags.FENCES);
    }
}
