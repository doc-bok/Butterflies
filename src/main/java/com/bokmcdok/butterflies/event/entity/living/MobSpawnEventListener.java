package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.raid.Raider;
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
     * Handle mobs being replaced on spawn.
     * @param event The event context.
     */
    private void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        trySpawnButterflyGolem(event);
        trySpawnPeacemakerButterfly(event);
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    @SubscribeEvent
    @SuppressWarnings({"deprecation", "UnstableApiUsage", "OverrideOnly"})
    private void trySpawnButterflyGolem(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // 1 in 256 chance.
            if (ironGolem.getRandom().nextInt() % 256 == 1) {
                ServerLevelAccessor level = event.getLevel();
                EntityType<IronGolem> entityType = entityTypeRegistry.getButterflyGolem().get();

                if (EventHooks.canLivingConvert(ironGolem, entityType, (x) -> {})) {
                    IronGolem newMob = ironGolem.convertTo(entityType, false);
                    if (newMob != null) {
                        newMob.finalizeSpawn(level,
                                level.getCurrentDifficultyAt(newMob.blockPosition()),
                                MobSpawnType.CONVERSION,
                                null,
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

    /**
     * Some Illagers and Villagers may be infected with Peacemaker Butterflies.
     * @param event The event context.
     */
    private void trySpawnPeacemakerButterfly(MobSpawnEvent.FinalizeSpawn event) {

        // Peacemaker butterflies can be disabled via a config.
        if (ButterfliesConfig.Common.enableHostileButterflies.get()) {
            Entity entity = event.getEntity();

            // Handle Villagers being infected.
            if (entity instanceof Villager villager) {
                ServerLevelAccessor level = event.getLevel();
                if (villager.getRandom().nextInt(1000) < 17) {
                    PeacemakerButterfly.possess(level, villager);
                    event.setCanceled(true);
                }
            }

            // Handle raiders being infected.
            if (entity instanceof Raider raider) {
                ServerLevelAccessor level = event.getLevel();
                if (raider.getRandom().nextInt(100) < 5) {
                    PeacemakerButterfly.possess(level, raider);
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * If a villager, illager, or witch is killed by a peacemaker butterfly
     * then it shouldn't drop loot.
     * @param event The drop event to cancel
     */
    private void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof PeacemakerButterfly) {
            LivingEntity killed = event.getEntity();
            if (killed instanceof Villager ||
                    killed instanceof AbstractIllager ||
                    killed instanceof Witch) {
                event.setCanceled(true);
            }
        }
    }
}
