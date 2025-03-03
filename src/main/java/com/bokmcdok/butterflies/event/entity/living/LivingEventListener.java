package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Listens for events involving living entities.
 */
public class LivingEventListener {

    /**
     * Construction
     *
     * @param forgeEventBus The event bus to register with.
     */
    public LivingEventListener(IEventBus forgeEventBus) {
        forgeEventBus.register(this);
    }

    /**
     * Fired when a living entity dies.
     * @param event The event data.
     */
    @SubscribeEvent
    private void onLivingDeath(LivingDeathEvent event) {
        Level level = event.getEntity().level();

        // Only do this on the server.
        if (!level.isClientSide()) {

            // Detect when a villager dies.
            if (event.getEntity() instanceof Villager villager) {

                // 1 in 256 chance.
                if (villager.getRandom().nextInt() % 256 == 1) {

                    // Create a light butterfly.
                    ResourceLocation location = ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "light");
                    Butterfly.spawn(level, location, villager.getOnPos(), false);
                }
            }
        }
    }
}
