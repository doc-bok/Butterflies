//package com.bokmcdok.butterflies.event.world;
//
//import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
//import com.bokmcdok.butterflies.world.ButterflyData;
//import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
//import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
//import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
//import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraft.world.level.biome.MobSpawnSettings;
//import net.minecraftforge.common.world.ForgeBiomeModifiers;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.registries.RegistryObject;
//
//public class WorldEventListener {
//
//    /**
//     * Naturally spawn the mobs in biomes.
//     * @param event The event.
//     */
//    @SubscribeEvent
//    public static void onBiomeLoading(BiomeLoadingEvent event) {
//        for (RegistryObject<EntityType<? extends Butterfly>> i : EntityTypeRegistry.BUTTERFLY_ENTITIES) {
//            addSpawnModifiers(event, i.getId(), i.get());
//        }
//
//        for (RegistryObject<EntityType<Caterpillar>> i : EntityTypeRegistry.CATERPILLAR_ENTITIES) {
//            addSpawnModifiers(event, i.getId(), i.get());
//        }
//
//        for (RegistryObject<EntityType<Chrysalis>> i : EntityTypeRegistry.CHRYSALIS_ENTITIES) {
//            addSpawnModifiers(event, i.getId(), i.get());
//        }
//
//        for (RegistryObject<EntityType<ButterflyEgg>> i : EntityTypeRegistry.BUTTERFLY_EGG_ENTITIES) {
//            addSpawnModifiers(event, i.getId(), i.get());
//        }
//    }
//
//    /**
//     * Add an entity to a spawn location.
//     * @param event The biome loading event.
//     * @param id The resource location of the entity.
//     * @param entityType The type of entity.
//     */
//    private static void addSpawnModifiers(BiomeLoadingEvent event,
//                                          ResourceLocation id,
//                                          EntityType<?> entityType) {
//
//        ButterflyData data = ButterflyData.getEntry(id);
//
//        if (shouldSpawnHere(event, data)) {
//            // Get the weights.
//            int weight = 4;
//            int maximum = 4;
//            switch (data.rarity()) {
//                case COMMON:
//                    break;
//
//                case UNCOMMON:
//                    weight = 2;
//                    maximum = 3;
//                    break;
//
//                case RARE:
//                    weight = 1;
//                    maximum = 2;
//            }
//
//            ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn()
//
//            event.getSpawns().getSpawner(MobCategory.CREATURE)
//                    .add(new MobSpawnSettings.SpawnerData(entityType, weight, 1, maximum));
//        }
//    }
//
//    /**
//     * Determine if the entity should spawn in this biome.
//     * @param event The loading event for the current biome.
//     * @param data The butterfly data.
//     * @return TRUE if the entity can spawn here.
//     */
//    private static boolean shouldSpawnHere(BiomeLoadingEvent event, ButterflyData data) {
//        boolean shouldSpawnHere = false;
//        switch (data.habitat()) {
//            case ICE -> shouldSpawnHere = event.getClimate().temperature < 0.1;
//            case PLAINS -> shouldSpawnHere = event.getCategory() == Biome.BiomeCategory.PLAINS;
//            case FORESTS -> shouldSpawnHere = event.getCategory() == Biome.BiomeCategory.FOREST;
//            case JUNGLES -> shouldSpawnHere = event.getCategory() == Biome.BiomeCategory.JUNGLE;
//            case FORESTS_AND_PLAINS -> shouldSpawnHere = event.getCategory() == Biome.BiomeCategory.PLAINS ||
//                    event.getCategory() == Biome.BiomeCategory.FOREST;
//        }
//        return shouldSpawnHere;
//    }
//}
