package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.TagRegistry;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.raid.Raider;

/**
 * Holds event listeners for entities.
 */
public class MobSpawnEventListener {

    // The entity type registry.
    private final EntityTypeRegistry entityTypeRegistry;
    private final TagRegistry tagRegistry;

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public MobSpawnEventListener(IEventBus forgeEventBus,
                                 EntityTypeRegistry entityTypeRegistry,
                                 TagRegistry tagRegistry) {
        forgeEventBus.register(this);

        this.entityTypeRegistry = entityTypeRegistry;
        this.tagRegistry = tagRegistry;
    }

    /**
     * Handle mobs being replaced on spawn.
     * @param event The event context.
     */
    @SubscribeEvent
    private void onMobSpawn(FinalizeSpawnEvent event) {
        trySpawnButterflyGolem(event);
        trySpawnPeacemakerButterfly(event);
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    @SuppressWarnings({"deprecation", "OverrideOnly", "unchecked"})
    private void trySpawnButterflyGolem(FinalizeSpawnEvent event) {
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // 1 in 256 chance.
            if (ironGolem.getRandom().nextInt() % 256 == 1) {
                ServerLevelAccessor level = event.getLevel();
                EntityType<IronGolem> entityType = (EntityType<IronGolem>) entityTypeRegistry.getButterflyGolem().get();

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

    /**
     * Some Illagers and Villagers may be infected with Peacemaker Butterflies.
     * @param event The event context.
     */
    private void trySpawnPeacemakerButterfly(FinalizeSpawnEvent event) {

        // Peacemaker butterflies can be disabled via a config.
        if (!ButterfliesConfig.Common.enableHostileButterflies.get()) {
            return;
        }

        // Entities can't be born with butterflies.
        if (event.getSpawnType() == MobSpawnType.BREEDING) {
            return;
        }

        // Don't infest entities if they are already infested.
        Entity entity = event.getEntity();
        if (entity.getType().is(this.tagRegistry.getPeacemakerEntities())) {
            return;
        }

        // Handle Villagers being infected.
        if (entity instanceof Villager villager) {
            ServerLevelAccessor level = event.getLevel();
            if (villager.getRandom().nextInt(1000) < 17) {
                PeacemakerButterfly.possess(level, villager);
                event.setCanceled(true);
            }
        }

        // Handle Wandering Traders being infected.
        if (entity instanceof WanderingTrader wanderingTrader) {
            ServerLevelAccessor level = event.getLevel();
            if (wanderingTrader.getRandom().nextInt(1000) < 35) {
                PeacemakerButterfly.possess(level, wanderingTrader);
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

    /**
     * If a villager, illager, or witch is killed by a peacemaker butterfly
     * then it shouldn't drop loot.
     * @param event The drop event to cancel
     */
    @SubscribeEvent
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
