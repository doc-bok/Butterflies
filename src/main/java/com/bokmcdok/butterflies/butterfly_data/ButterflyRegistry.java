package com.bokmcdok.butterflies.butterfly_data;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class ButterflyRegistry {

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, ButterflyData> BUTTERFLY_ENTRIES = new HashMap<>();

    private static int NUM_BUTTERFLIES;
    private static int NUM_MOTHS;

    /**
     * Create new butterfly data.
     * @param entry The butterfly data.
     */
    public static void addButterfly(ButterflyData entry)
            throws DataFormatException {
        if (ENTITY_ID_TO_INDEX_MAP.containsKey(entry.speciesId().value())) {
            String message = String.format("Butterfly Data Entry for entity [%s] already exists.", entry.speciesId());
            throw new DataFormatException(message);
        }

        if (BUTTERFLY_ENTRIES.containsKey(entry.butterflyIndex())) {
            String message = String.format("Butterfly Data Entry for index [%d] already exists.", entry.butterflyIndex());
            throw new DataFormatException(message);
        }

        ENTITY_ID_TO_INDEX_MAP.put(entry.speciesId().value(), entry.butterflyIndex());
        BUTTERFLY_ENTRIES.put(entry.butterflyIndex(), entry);

        //  Recount the butterflies
        if (entry.type() != ButterflyType.SPECIAL) {
            int total = 0;
            for (ButterflyData i : BUTTERFLY_ENTRIES.values()) {
                if (i.type() == entry.type()) {
                    ++total;
                }
            }

            if (entry.type() == ButterflyType.BUTTERFLY) {
                NUM_BUTTERFLIES = total;
            } else if (entry.type() == ButterflyType.MOTH) {
                NUM_MOTHS = total;
            }
        }
    }



    /**
     * Accessor to help get butterfly data when needed.
     * @return A valid butterfly data entry.
     */
    @SuppressWarnings("removal")
    public static ButterflyData getButterflyDataForEntity(LivingEntity entity) {
        String species = getSpeciesString(entity);
        ResourceLocation location = new ResourceLocation(ButterfliesMod.MOD_ID, species);
        return getEntry(location);
    }

    /**
     * Helper method to get the species string for creating resource locations.
     * @return A valid species string.
     */
    public static String getSpeciesString(LivingEntity entity) {
        String species = "undiscovered";
        String encodeId = entity.getEncodeId();
        if (encodeId != null) {
            String[] split = encodeId.split(":");
            if (split.length >= 2) {
                species = split[1];

                // Kind of hacky. We should avoid butterfly IDs with
                // underscores in the future. Making an exception here, so we
                // don't lose work done before we realized it was a problem.
                if (species.contains("domestic_silk")) {
                    return "domestic_silk";
                }

                split = species.split("_");
                if (split.length >=2) {
                    species = split[0];
                }
            }
        }

        return species;
    }

    /**
     * Get all butterfly data. Used for network synchronization.
     * @return The butterfly entries as a collection.
     */
    public static Collection<ButterflyData> getButterflyDataCollection() {
        return BUTTERFLY_ENTRIES.values();
    }

    /**
     * Converts a resource location to a butterfly index.
     * @param location The resource location to convert.
     * @return The butterfly index for the butterfly species, or -1 if not
     * found.
     */
    public static int getButterflyIndex(ResourceLocation location) {
        return getButterflyIndex(location.toString());
    }

    /**
     * Converts an Entity ID to an index.
     * @param entityId The entity ID to convert.
     * @return The index of said entity ID.
     */
    public static int getButterflyIndex(String entityId) {
        String species = entityId;
        if (species.contains(":")) {
            String[] splits = species.split(":");
            species = splits[1];
        }

        // Another workaround. In the future don't use underscores in butterfly
        // IDs.
        if (species.contains("domestic_silk")) {
            return ENTITY_ID_TO_INDEX_MAP.get("domestic_silk");
        }

        String[] components = species.split("_");
        for (String component : components) {
            if (ENTITY_ID_TO_INDEX_MAP.containsKey(component)) {
                return ENTITY_ID_TO_INDEX_MAP.get(component);
            }
        }

        return -1;
    }

    /**
     * Get butterfly data by index.
     * @param index The butterfly index.
     * @return The butterfly entry.
     */
    public static ButterflyData getEntry(int index) {
        if (BUTTERFLY_ENTRIES.containsKey(index)) {
            return BUTTERFLY_ENTRIES.get(index);
        }

        return null;
    }

    /**
     * Get butterfly data by resource location.
     * @param location The resource location of the butterfly.
     * @return The butterfly entry.
     */
    public static ButterflyData getEntry(ResourceLocation location) {
        int index = getButterflyIndex(location);
        return getEntry(index);
    }

    /**
     * Returns the total number of butterfly species in the mod.
     * @return The total number of butterflies.
     */
    public static int getNumButterflySpecies() {
        return NUM_BUTTERFLIES;
    }

    /**
     * Returns the total number of butterfly species in the mod.
     * @return The total number of butterflies.
     */
    public static int getNumMothSpecies() {
        return NUM_MOTHS;
    }

    /**
     * Returns the total number of all species in the mod.
     * @return The total number of all species.
     */
    public static int getTotalNumSpecies() {
        return BUTTERFLY_ENTRIES.size();
    }

    /**
     * Resets the butterfly data to its unloaded state.
     */
    public static void reset() {
        BUTTERFLY_ENTRIES.clear();
        ENTITY_ID_TO_INDEX_MAP.clear();
        NUM_BUTTERFLIES = 0;
        NUM_MOTHS = 0;
    }
}
