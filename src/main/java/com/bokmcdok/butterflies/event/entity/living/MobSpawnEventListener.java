package com.bokmcdok.butterflies.event.entity.living;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.config.ButterfliesConfig;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Holds event listeners for entities.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobSpawnEventListener {

    /**
     * Handle mobs being replaced on spawn.
     * @param event The event context.
     */
    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        trySpawnButterflyGolem(event);
        trySpawnPeacemakerButterfly(event);
    }

    /**
     * If a villager, illager, or witch is killed by a peacemaker butterfly
     * then it shouldn't drop loot.
     * @param event The drop event to cancel
     */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof PeacemakerButterfly) {
            LivingEntity killed = event.getEntity();
            if (killed instanceof Villager || killed instanceof AbstractIllager) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Occasionally replace an iron golem with a butterfly golem.
     * @param event The event context.
     */
    @SuppressWarnings({"deprecation", "OverrideOnly"})
    private static void trySpawnButterflyGolem(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getEntity();

            // Read probability from the configs.
            if (ironGolem.getRandom().nextInt(1000) < ButterfliesConfig.Common.butterflyGolemSpawnChance.get()) {
                ServerLevelAccessor level = event.getLevel();
                EntityType<IronGolem> entityType = EntityTypeRegistry.BUTTERFLY_GOLEM.get();

                if (ForgeEventFactory.canLivingConvert(ironGolem, entityType, (x) -> {
                })) {
                    IronGolem newMob = tryConvertMob(level, ironGolem, entityType, false);//ironGolem.convertTo(entityType, false);
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

                        if (level.addFreshEntity(newMob)) {
                            event.setCanceled(true);
                            event.setSpawnCancelled(true);
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
    private static void trySpawnPeacemakerButterfly(MobSpawnEvent.FinalizeSpawn event) {
        if (PeacemakerButterfly.tryPossess(event.getLevel(), event.getEntity(), event.getSpawnType())) {
            event.setCanceled(true);
            event.setSpawnCancelled(true);
        }
    }

    /**
     * Try and convert a mob from one to another without calling `addFreshEntity()`
     * @param level The current server level accessor.
     * @param from The entity to convert from.
     * @param to The entity type to convert to.
     * @param includeEquipment Whether to include equipment in the conversion.
     * @return The new mob, if one is created.
     * @param <T> The new mob's class.
     */
    public static <T extends Mob> T tryConvertMob(ServerLevelAccessor level,
                                                  Mob from,
                                                  EntityType<T> to,
                                                  boolean includeEquipment) {
        if (from.isRemoved()) {
            return null;
        } else {
            T converted = to.create(level.getLevel());
            if (converted == null) {
                return null;
            } else {
                converted.copyPosition(from);
                converted.setBaby(from.isBaby());
                converted.setNoAi(from.isNoAi());
                if (from.hasCustomName()) {
                    converted.setCustomName(from.getCustomName());
                    converted.setCustomNameVisible(from.isCustomNameVisible());
                }

                if (from.isPersistenceRequired()) {
                    converted.setPersistenceRequired();
                }

                converted.setInvulnerable(from.isInvulnerable());

                if (includeEquipment) {
                    converted.setCanPickUpLoot(from.canPickUpLoot());

                    for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                        ItemStack itemstack = from.getItemBySlot(equipmentslot);
                        if (!itemstack.isEmpty()) {
                            converted.setItemSlot(equipmentslot, itemstack.copyAndClear());
                            converted.setDropChance(equipmentslot, from.getEquipmentDropChance(equipmentslot));
                        }
                    }
                }

                if (from.isPassenger()) {
                    Entity entity = from.getVehicle();
                    from.stopRiding();
                    if (entity != null) {
                        converted.startRiding(entity, true);
                    }
                }

                from.discard();
                return converted;
            }
        }
    }
}
