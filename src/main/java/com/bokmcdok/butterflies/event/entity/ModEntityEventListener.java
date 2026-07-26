package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.PeacemakerEntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.world.entity.*;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Holds event listeners for entities.
 */
public class ModEntityEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEntityEventListener(IEventBus modEventBus) {
        modEventBus.register(this);
    }

    /**
     * Used to stop cats from attacking forester butterflies.
     * @param entity The entity the cat wants to target.
     * @return TRUE if the entity is any butterfly except for a Forester.
     */
    private static boolean isButterflyAttackableByCat(LivingEntity entity) {
        if (entity instanceof Butterfly butterfly) {
            return butterfly.getData().hasTrait(ButterflyTrait.CATFRIEND);
        }

        return false;
    }

    /**
     * Register the attributes for living entities
     */
    @SubscribeEvent
    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            event.put(ButterflyEntityTypeRegistry.BUTTERFLIES.get(i).get(), Butterfly.createAttributes(i).build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : ButterflyEntityTypeRegistry.CATERPILLARS) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : ButterflyEntityTypeRegistry.CHRYSALISES) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : ButterflyEntityTypeRegistry.BUTTERFLY_EGGS) {
            event.put(i.get(), ButterflyEgg.createAttributes().build());
        }

        event.put(EntityTypeRegistry.BUTTERFLY_GOLEM.get(), IronGolem.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_BUTTERFLY.get(), PeacemakerButterfly.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_COW.get(), PeacemakerCow.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_EVOKER.get(), PeacemakerEvoker.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_ILLUSIONER.get(), PeacemakerIllusioner.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_PILLAGER.get(), PeacemakerPillager.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_VINDICATOR.get(), PeacemakerVindicator.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_VILLAGER.get(), PeacemakerVillager.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_WANDERING_TRADER.get(), PeacemakerWanderingTrader.createAttributes().build());
        event.put(PeacemakerEntityTypeRegistry.PEACEMAKER_WITCH.get(), PeacemakerWitch.createAttributes().build());
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    @SubscribeEvent
    private void onSpawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
        for (DeferredHolder<EntityType<?>, EntityType<Butterfly>> i : ButterflyEntityTypeRegistry.BUTTERFLIES) {
            event.register(i.get(),
                    SpawnPlacementTypes.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : ButterflyEntityTypeRegistry.CATERPILLARS) {
            event.register(i.get(),
                    SpawnPlacementTypes.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : ButterflyEntityTypeRegistry.CHRYSALISES) {
            event.register(i.get(),
                    SpawnPlacementTypes.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : ButterflyEntityTypeRegistry.BUTTERFLY_EGGS) {
            event.register(i.get(),
                    SpawnPlacementTypes.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }

        event.register(EntityTypeRegistry.BUTTERFLY_GOLEM.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.AND);
    }
}