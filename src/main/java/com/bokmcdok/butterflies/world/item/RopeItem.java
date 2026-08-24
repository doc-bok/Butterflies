package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.entity.decoration.RopeKnotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class to represent a rope.
 */
public class RopeItem extends Item {
    public static final double SEARCH_RADIUS = 7.0d;

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
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (isValidAnchor(blockState)) {
            Player player = useOnContext.getPlayer();
            if (!level.isClientSide && player != null) {
                attachLeashedMobsToAnchor(player, level, blockPos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    /**
     * Binds mobs to the rope.
     * @param player The player holding the rope.
     * @param level The current level.
     * @param blockPos The block's position.
     */
    public static void attachLeashedMobsToAnchor(Player player,
                                                 Level level,
                                                 BlockPos blockPos) {
        RopeKnotEntity ropeKnot = null;
        boolean attachedAny = false;

        for(Mob mob : getNearbyMobs(level, blockPos)) {
            if (mob.getLeashHolder() == player) {
                if (ropeKnot == null) {
                    ropeKnot = RopeKnotEntity.getOrCreateKnot(level, blockPos);
                    ropeKnot.playPlacementSound();
                }

                mob.setLeashedTo(ropeKnot, true);
                attachedAny = true;
            }
        }

        if (attachedAny) {
            level.gameEvent(GameEvent.BLOCK_ATTACH, blockPos, GameEvent.Context.of(player));
        }
    }

    /**
     * Returns true if a block is a valid anchor.
     * @param blockState The block state to check.
     * @return True if the block is a valid anchor.
     */
    private boolean isValidAnchor(BlockState blockState) {
        return blockState.is(BlockTags.FENCES);
    }

    /**
     * Find any nearby mobs.
     * @return A list of mobs.
     */
    private static List<Mob> getNearbyMobs(Level level,
                                           BlockPos blockPos) {
        AABB searchBox = new AABB(
                blockPos.getX() - SEARCH_RADIUS, blockPos.getY() - SEARCH_RADIUS, blockPos.getZ() - SEARCH_RADIUS,
                blockPos.getX() + SEARCH_RADIUS, blockPos.getY() + SEARCH_RADIUS, blockPos.getZ() + SEARCH_RADIUS
        );

        return level.getEntitiesOfClass(Mob.class, searchBox);
    }
}
