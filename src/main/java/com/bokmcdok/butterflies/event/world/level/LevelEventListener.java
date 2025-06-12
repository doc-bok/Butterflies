package com.bokmcdok.butterflies.event.world.level;

import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
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
import java.util.Objects;

/**
 * Listens for level-based events on the Forge Bus.
 */
public class LevelEventListener {

    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public LevelEventListener(IEventBus forgeEventBus,
                              EntityTypeRegistry entityTypeRegistry) {

        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onBiomeLoading);

        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Add the spawns for each butterfly.
     * @param event The event we respond to in order to add the villages.
     */
    private void onBiomeLoading(BiomeLoadingEvent event)
    {
        List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs = entityTypeRegistry.getButterflyEggs();
        List<RegistryObject<EntityType<Caterpillar>>> caterpillars = entityTypeRegistry.getCaterpillars();
        List<RegistryObject<EntityType<Chrysalis>>> chrysalises = entityTypeRegistry.getChrysalises();
        List<RegistryObject<EntityType<? extends Butterfly>>> butterflies = entityTypeRegistry.getButterflies();

        for (int i = 0; i < butterflies.size(); ++i) {

            // Set the weights based on rarity.
            int weight = 12;
            int maximum = 4;
            
            if (ButterflySpeciesList.RARITIES[i] == ButterflyData.Rarity.UNCOMMON) {
                weight = 8;
                maximum = 3;
            } else if (ButterflySpeciesList.RARITIES[i] == ButterflyData.Rarity.RARE) {
                weight = 4;
                maximum = 2;
            }

            // If the butterfly is in this habitat then add them to the spawn list.
            for (ButterflyData.Habitat habitat : ButterflySpeciesList.HABITATS[i]) {
                switch (habitat) {
                    case FORESTS:
                        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
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
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case PLAINS:
                        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
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
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case HILLS:
                        if (event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS) ||
                                event.getCategory().equals(Biome.BiomeCategory.MOUNTAIN)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case PLATEAUS:
                        if (event.getCategory().equals(Biome.BiomeCategory.MESA)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case SAVANNAS:
                        if (event.getCategory().equals(Biome.BiomeCategory.SAVANNA)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    case WETLANDS:
                        if (event.getCategory().equals(Biome.BiomeCategory.SWAMP) ||
                                event.getCategory().equals(Biome.BiomeCategory.RIVER)) {
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflyEggs.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(caterpillars.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(chrysalises.get(i).get(), weight, 1, maximum));
                            event.getSpawns().addSpawn(MobCategory.CREATURE,
                                    new MobSpawnSettings.SpawnerData(butterflies.get(i).get(), weight, 1, maximum));
                        }

                        break;

                    default:
                        break;
                }
            }
        }

    }
}
