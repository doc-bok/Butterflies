package com.bokmcdok.butterflies.event.world.level;

import com.bokmcdok.butterflies.butterfly_data.ButterflyHabitat;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRarity;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * Listens for level-based events on the Forge Bus.
 */
public class LevelEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public LevelEventListener(IEventBus forgeEventBus) {

        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onBiomeLoading);
    }

    /**
     * Add the spawns for each butterfly.
     * @param event The event used to add the spawns.
     */
    private void onBiomeLoading(BiomeLoadingEvent event)
    {
        List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs = ButterflyEntityTypeRegistry.BUTTERFLY_EGGS;
        List<RegistryObject<EntityType<Caterpillar>>> caterpillars = ButterflyEntityTypeRegistry.CATERPILLARS;
        List<RegistryObject<EntityType<Chrysalis>>> chrysalises = ButterflyEntityTypeRegistry.CHRYSALISES;
        List<RegistryObject<EntityType<Butterfly>>> butterflies = ButterflyEntityTypeRegistry.BUTTERFLIES;

        for (int i = 0; i < butterflies.size(); ++i) {

            // Set the weights based on rarity.
            int weight = 12;
            int maximum = 4;
            
            if (ButterflyInfo.RARITIES[i] == ButterflyRarity.UNCOMMON) {
                weight = 8;
                maximum = 3;
            } else if (ButterflyInfo.RARITIES[i] == ButterflyRarity.RARE) {
                weight = 4;
                maximum = 2;
            }

            // If the butterfly is in this habitat then add them to the spawn list.
            for (ButterflyHabitat habitat : ButterflyInfo.HABITATS[i]) {
                switch (habitat) {
                    case FORESTS:
                        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case ICE:
                        if (event.getCategory().equals(Biome.BiomeCategory.ICY)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case JUNGLES:
                        if (event.getCategory().equals(Biome.BiomeCategory.JUNGLE)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case PLAINS:
                        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case NETHER:
                        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case VILLAGES:
                        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS) ||
                                event.getCategory().equals(Biome.BiomeCategory.DESERT) ||
                                event.getCategory().equals(Biome.BiomeCategory.FOREST) ||
                                event.getCategory().equals(Biome.BiomeCategory.SAVANNA)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case HILLS:
                        if (event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS) ||
                                event.getCategory().equals(Biome.BiomeCategory.MOUNTAIN)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case PLATEAUS:
                        if (event.getCategory().equals(Biome.BiomeCategory.MESA)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case SAVANNAS:
                        if (event.getCategory().equals(Biome.BiomeCategory.SAVANNA)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case WETLANDS:
                        if (event.getCategory().equals(Biome.BiomeCategory.SWAMP) ||
                                event.getCategory().equals(Biome.BiomeCategory.RIVER)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                        break;

                    case END:
                        if (event.getCategory().equals(Biome.BiomeCategory.THEEND)) {
                            addSpawns(event, butterflyEggs.get(i), caterpillars.get(i), chrysalises.get(i), butterflies.get(i), weight, maximum);
                        }

                    default:
                        break;
                }
            }
        }
    }

    /**
     * Add the spawns for a particular butterfly species.
     * @param event The Biome Loading Event, used to add spawns.
     * @param butterflyEgg The butterfly egg entity.
     * @param caterpillar The caterpillar entity.
     * @param chrysalis The chrysalis entity.
     * @param butterfly The butterfly entity.
     * @param weight The weight of the spawns.
     * @param maximum The maximum number in a single spawn.
     */
    private void addSpawns(BiomeLoadingEvent event,
                           RegistryObject<EntityType<ButterflyEgg>> butterflyEgg,
                           RegistryObject<EntityType<Caterpillar>> caterpillar,
                           RegistryObject<EntityType<Chrysalis>> chrysalis,
                           RegistryObject<EntityType<Butterfly>> butterfly,
                           int weight,
                           int maximum) {
        event.getSpawns().addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(butterflyEgg.get(), weight, 1, maximum));
        event.getSpawns().addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(caterpillar.get(), weight, 1, maximum));
        event.getSpawns().addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(chrysalis.get(), weight, 1, maximum));
        event.getSpawns().addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(butterfly.get(), weight, 1, maximum));
    }
}
