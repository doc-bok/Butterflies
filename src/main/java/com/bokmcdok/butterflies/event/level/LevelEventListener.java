package com.bokmcdok.butterflies.event.level;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.decoration.AbstractRopeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

/**
 * Class to listen for level-based events.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventListener {

    /**
     * Check if there is a neighboring rope to notify.
     * @param event The event that triggered this call.
     */
    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        LevelAccessor level = event.getLevel();
        BlockPos ropePos = event.getPos().above();

        AbstractRopeEntity rope = AbstractRopeEntity.getRopeAt(level, ropePos);
        if (rope != null) {
            rope.updateBlockConnections();
        }
    }
}
