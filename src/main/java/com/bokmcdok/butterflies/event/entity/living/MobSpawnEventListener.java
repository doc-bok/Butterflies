package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

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

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    @SubscribeEvent
    @SuppressWarnings({"deprecation", "OverrideOnly"})
    private void onMobSpawn(FinalizeSpawnEvent event) {

        // Only affect Iron Golems.
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // 1 in 256 chance.
            if (ironGolem.getRandom().nextInt() % 256 == 1) {
                ServerLevelAccessor level = event.getLevel();
                EntityType<IronGolem> entityType = entityTypeRegistry.getButterflyGolem().get();

                if (EventHooks.canLivingConvert(ironGolem, entityType, (x) -> {})) {
                    IronGolem newMob = ironGolem.convertTo(
                            entityType,
                            ConversionParams.single(ironGolem, true, true),
                            EntitySpawnReason.CONVERSION,
                            entity -> {});

                    if (newMob != null) {
                        newMob.finalizeSpawn(level,
                                level.getCurrentDifficultyAt(newMob.blockPosition()),
                                EntitySpawnReason.CONVERSION,
                                null);

                        EventHooks.onLivingConvert(ironGolem, newMob);

                        if (!newMob.isSilent()) {
                            level.levelEvent(null, 1026, newMob.blockPosition(), 0);
                        }
                    }
                }
            }
        }
    }
}
