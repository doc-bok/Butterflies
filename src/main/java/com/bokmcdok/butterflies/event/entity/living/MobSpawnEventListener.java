package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Holds event listeners for entities.
 */
public class MobSpawnEventListener {

    // The entity type registry.
    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public MobSpawnEventListener(IEventBus forgeEventBus,
                                 EntityTypeRegistry entityTypeRegistry) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onMobSpawn);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    private void onMobSpawn(LivingSpawnEvent event) {

        // Only affect Iron Golems.
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // 1 in 256 chance.
            if (ironGolem.getRandom().nextInt() % 256 == 1) {
                LevelAccessor levelAccessor = event.getWorld();
                if (levelAccessor instanceof ServerLevelAccessor level) {
                    EntityType<IronGolem> entityType = entityTypeRegistry.getButterflyGolem().get();

                    if (ForgeEventFactory.canLivingConvert(ironGolem, entityType, (x) -> {
                    })) {
                        IronGolem newMob = ironGolem.convertTo(entityType, false);
                        if (newMob != null) {
                            newMob.finalizeSpawn(level,
                                    level.getCurrentDifficultyAt(newMob.blockPosition()),
                                    MobSpawnType.CONVERSION,
                                    null,
                                    null);

                            ForgeEventFactory.onLivingConvert(ironGolem, newMob);

                            if (!newMob.isSilent()) {
                                level.levelEvent(null, 1026, newMob.blockPosition(), 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
