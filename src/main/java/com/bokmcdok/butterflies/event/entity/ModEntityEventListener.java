package com.bokmcdok.butterflies.event.entity;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * Holds event listeners for entities.
 */
public class ModEntityEventListener {

    // The entity type registry.
    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEntityEventListener(IEventBus modEventBus,
                                  EntityTypeRegistry entityTypeRegistry) {
        modEventBus.register(this);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Used to stop cats from attacking forester butterflies.
     * @param entity The entity the cat wants to target.
     * @return TRUE if the entity is any butterfly except for a Forester.
     */
    private static boolean isButterflyAttackableByCat(LivingEntity entity) {
        if (entity instanceof Butterfly butterfly) {
            return !Objects.equals(butterfly.getData().entityId(), "forester");
        }

        return false;
    }

    /**
     * Register the attributes for living entities
     */
    @SubscribeEvent
    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            event.put(i.get(), Butterfly.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            event.put(i.get(), Caterpillar.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.put(i.get(), Chrysalis.createAttributes().build());
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.put(i.get(), ButterflyEgg.createAttributes().build());
        }

        event.put(entityTypeRegistry.getButterflyGolem().get(), IronGolem.createAttributes().build());
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    @SubscribeEvent
    private void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        for (DeferredHolder<EntityType<?>, EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules,
                    SpawnPlacementRegisterEvent.Operation.AND);
        }

        event.register(entityTypeRegistry.getButterflyGolem().get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND);
    }
}