package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Holds event listeners for entities.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEventListener {

    /**
     * On joining the world modify the cat's goals so it attacks butterflies.
     * @param event Information for the event.
     */
    @SubscribeEvent
    public static void onJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cat cat) {

            //  Add new butterfly target.
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(
                    cat, Butterfly.class, false, null));
        }
    }
}
