package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

/**
 * Listens for generic events on the Forge Bus.
 */
public class ModEventListener {

    // The entity type registry.
    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEventListener(IEventBus modEventBus,
                            EntityTypeRegistry entityTypeRegistry) {

        modEventBus.register(this);
        modEventBus.addListener(this::onCommonSetup);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    private void onCommonSetup(FMLCommonSetupEvent event) {
        for (RegistryObject<EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules);
        }

        for (RegistryObject<EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }
    }
}
