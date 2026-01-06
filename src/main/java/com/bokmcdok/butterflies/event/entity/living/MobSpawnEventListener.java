package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
        forgeEventBus.addListener(this::onLivingDrops);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Handle mobs being replaced on spawn.
     * @param event The event context.
     */
    private void onMobSpawn(LivingSpawnEvent event) {
        trySpawnButterflyGolem(event);
        trySpawnPeacemakerButterfly(event);
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    private void trySpawnButterflyGolem(LivingSpawnEvent event) {
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // 1 in 256 chance.
            if (ironGolem.getRandom().nextInt() % 256 == 1) {
                LevelAccessor levelAccessor = event.getLevel();
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

    /**
     * Some Illagers and Villagers may be infected with Peacemaker Butterflies.
     * @param event The event context.
     */
    private void trySpawnPeacemakerButterfly(LivingSpawnEvent event) {

        // Peacemaker butterflies can be disabled via a config.
        if (ButterfliesConfig.Common.enableHostileButterflies.get()) {
            Entity entity = event.getEntity();

            // Handle Villagers being infected.
            if (entity instanceof Villager villager) {
                LevelAccessor levelAccessor = event.getLevel();
                if (levelAccessor instanceof ServerLevelAccessor level) {
                    if (villager.getRandom().nextInt(1000) < 17) {
                        PeacemakerButterfly.possess(level, villager);
                        event.setCanceled(true);
                    }
                }
            }

            // Handle raiders being infected.
            if (entity instanceof Raider raider) {
                LevelAccessor levelAccessor = event.getLevel();
                if (levelAccessor instanceof ServerLevelAccessor level) {
                    if (raider.getRandom().nextInt(100) < 5) {
                        PeacemakerButterfly.possess(level, raider);
                        event.setCanceled(true);
                    }
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
