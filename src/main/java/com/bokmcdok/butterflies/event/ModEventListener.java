package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

/**
 * Listens for generic events on the Forge Bus.
 */
public class ModEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ModEventListener(IEventBus modEventBus) {

        modEventBus.register(this);
        modEventBus.addListener(this::onCommonSetup);
    }

    /**
     * Register entity spawn placements here
     * @param event The event information
     */
    private void onCommonSetup(FMLCommonSetupEvent event) {
        for (RegistryObject<EntityType<Butterfly>> i : ButterflyEntityTypeRegistry.BUTTERFLIES) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    Butterfly::checkButterflySpawnRules);
        }

        for (RegistryObject<EntityType<Caterpillar>> i : ButterflyEntityTypeRegistry.CATERPILLARS) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : ButterflyEntityTypeRegistry.CHRYSALISES) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : ButterflyEntityTypeRegistry.BUTTERFLY_EGGS) {
            SpawnPlacements.register(i.get(),
                    SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING,
                    DirectionalCreature::checkDirectionalSpawnRules);
        }
        
        SpawnPlacements.register(EntityTypeRegistry.BUTTERFLY_GOLEM.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules);
    }
}
