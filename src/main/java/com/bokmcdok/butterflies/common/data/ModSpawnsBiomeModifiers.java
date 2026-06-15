package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Adds biome modifiers for butterfly spawns.
 */
public class ModSpawnsBiomeModifiers {

    public static final ResourceKey<BiomeModifier> VILLAGE_DESERT_BUTTERFLIES = registerKey("village_desert_butterflies");
    public static final ResourceKey<BiomeModifier> VILLAGE_PLAINS_BUTTERFLIES = registerKey("village_plains_butterflies");
    public static final ResourceKey<BiomeModifier> VILLAGE_SAVANNA_BUTTERFLIES = registerKey("village_savanna_butterflies");
    public static final ResourceKey<BiomeModifier> VILLAGE_SNOWY_BUTTERFLIES = registerKey("village_snowy_butterflies");
    public static final ResourceKey<BiomeModifier> VILLAGE_TAIGA_BUTTERFLIES = registerKey("village_taiga_butterflies");

    private static final EnumMap<ButterflyData.Habitat, BiomeProfile> BIOME_PROFILES =
            new EnumMap<>(ButterflyData.Habitat.class);
    /**
     * Entry point.
     * @param context The context for the biome modifier.
     */
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        EnumMap<ButterflyData.Habitat, List<MobSpawnSettings.SpawnerData>> spawners =
                new EnumMap<>(ButterflyData.Habitat.class);
        for (ButterflyData.Habitat habitat : ButterflyData.Habitat.values()) {
            spawners.put(habitat, new ArrayList<>());
        }

        // Populate the data.
        for (ButterflyData data : ButterflyData.getButterflyDataCollection()) {
            for(ButterflyData.Habitat habitat : data.habitats()) {
                addButterflySpawn(spawners.get(habitat), data);
            }
        }

        // Add the biome spawn modifiers.
        for(ButterflyData.Habitat habitat : ButterflyData.Habitat.values()) {
            if(BIOME_PROFILES.containsKey(habitat)) {
                BiomeProfile profile = BIOME_PROFILES.get(habitat);
                registerSpawnModifier(context, biomes,
                        profile.key,
                        profile.biomeTag,
                        spawners.get(ButterflyData.Habitat.FORESTS));
            }
        }

        // Add the village biome modifiers.
        registerVillageSpawnModifiers(context, biomes, spawners.get(ButterflyData.Habitat.VILLAGES));
    }

    /**
     * Helper method to add spawn data.
     * @param spawnerData The spawner data to modify.
     * @param data The butterfly data to add.
     */
    private static void addButterflySpawn(List<MobSpawnSettings.SpawnerData> spawnerData,
                                          ButterflyData data) {
        SpawnProfile profile = getSpawnProfile(data.rarity());
        int butterflyIndex = data.butterflyIndex();

        spawnerData.add(new MobSpawnSettings.SpawnerData(
                ButterflyEntityTypeRegistry.BUTTERFLIES.get(butterflyIndex).get(),
                profile.weight, profile.minCount, profile.maxCount));

        if(data.getBaseButterflyIndex() == butterflyIndex) {
            spawnerData.add(new MobSpawnSettings.SpawnerData(
                    ButterflyEntityTypeRegistry.CATERPILLARS.get(butterflyIndex).get(),
                    profile.weight, profile.minCount, profile.maxCount));
            spawnerData.add(new MobSpawnSettings.SpawnerData(
                    ButterflyEntityTypeRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(),
                    profile.weight, profile.minCount, profile.maxCount));
            spawnerData.add(new MobSpawnSettings.SpawnerData(
                    ButterflyEntityTypeRegistry.CHRYSALISES.get(butterflyIndex).get(),
                    profile.weight, profile.minCount, profile.maxCount));
        }
    }

    /**
     * Helper to register a key for the spawner data.
     * @param name The key name.
     * @return A new ResourceKey.
     */
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ButterfliesMod.MOD_ID, name));
    }

    /**
     * Registers a new spawn modifier.
     * @param context The context for the biome modifier.
     * @param biomes The holder for the biomes.
     * @param key The key to use for the spawn modifier.
     * @param biomeTag The biome tag to modify.
     * @param spawnerData The spawns to add.
     */
    private static void registerSpawnModifier(
            BootstapContext<BiomeModifier> context,
            HolderGetter<Biome> biomes,
            ResourceKey<BiomeModifier> key,
            TagKey<Biome> biomeTag,
            List<MobSpawnSettings.SpawnerData> spawnerData) {

        context.register(key, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(biomeTag),
                spawnerData));
    }

    /**
     * Registers all village spawn modifiers.
     * @param context The context for the biome modifier.
     * @param biomes The holder for the biomes.
     * @param spawnerData The spawns to add.
     */
    private static void registerVillageSpawnModifiers(
            BootstapContext<BiomeModifier> context,
            HolderGetter<Biome> biomes,
            List<MobSpawnSettings.SpawnerData> spawnerData) {

        registerSpawnModifier(context, biomes, VILLAGE_DESERT_BUTTERFLIES,
                BiomeTags.HAS_VILLAGE_DESERT, spawnerData);
        registerSpawnModifier(context, biomes, VILLAGE_PLAINS_BUTTERFLIES,
                BiomeTags.HAS_VILLAGE_PLAINS, spawnerData);
        registerSpawnModifier(context, biomes, VILLAGE_SAVANNA_BUTTERFLIES,
                BiomeTags.HAS_VILLAGE_SAVANNA, spawnerData);
        registerSpawnModifier(context, biomes, VILLAGE_SNOWY_BUTTERFLIES,
                BiomeTags.HAS_VILLAGE_SNOWY, spawnerData);
        registerSpawnModifier(context, biomes, VILLAGE_TAIGA_BUTTERFLIES,
                BiomeTags.HAS_VILLAGE_TAIGA, spawnerData);
    }

    /**
     * Helper to get the spawn rates based on the Rarity attribute.
     * @param rarity The rarity of the butterfly.
     * @return The spawn rates.
     */
    private static SpawnProfile getSpawnProfile(ButterflyData.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> new SpawnProfile(100, 1, 4);
            case UNCOMMON -> new SpawnProfile(50, 1, 3);
            case RARE -> new SpawnProfile(20, 1, 2);
        };
    }

    private record SpawnProfile(int weight, int minCount, int maxCount) {}
    private record BiomeProfile(ResourceKey<BiomeModifier> key, TagKey<Biome> biomeTag) {}
    
    static {
        BIOME_PROFILES.put(ButterflyData.Habitat.FORESTS, new BiomeProfile(registerKey("forest_butterflies"), BiomeTags.IS_FOREST));
        BIOME_PROFILES.put(ButterflyData.Habitat.HILLS, new BiomeProfile(registerKey("hill_butterflies"), BiomeTags.IS_HILL));
        BIOME_PROFILES.put(ButterflyData.Habitat.ICE, new BiomeProfile(registerKey("ice_butterflies"), Tags.Biomes.IS_SNOWY));
        BIOME_PROFILES.put(ButterflyData.Habitat.JUNGLES, new BiomeProfile(registerKey("jungle_butterflies"), BiomeTags.IS_JUNGLE));
        BIOME_PROFILES.put(ButterflyData.Habitat.NETHER, new BiomeProfile(registerKey("nether_butterflies"), BiomeTags.IS_NETHER));
        BIOME_PROFILES.put(ButterflyData.Habitat.PLAINS, new BiomeProfile(registerKey("plains_butterflies"), Tags.Biomes.IS_PLAINS));
        BIOME_PROFILES.put(ButterflyData.Habitat.PLATEAUS, new BiomeProfile(registerKey("plateau_butterflies"), Tags.Biomes.IS_PLATEAU));
        BIOME_PROFILES.put(ButterflyData.Habitat.SAVANNAS, new BiomeProfile(registerKey("savanna_butterflies"), BiomeTags.IS_SAVANNA));
        BIOME_PROFILES.put(ButterflyData.Habitat.WETLANDS, new BiomeProfile(registerKey("wetlands_butterflies"), Tags.Biomes.IS_WET_OVERWORLD));
    }
}
