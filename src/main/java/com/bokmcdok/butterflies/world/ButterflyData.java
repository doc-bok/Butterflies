package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper for converting entity ID to index and vice versa.
 */
public class ButterflyData {

    //  Represents the possible sizes of the butterflies.
    public enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    public enum Speed {
        MODERATE,
        FAST
    }

    private static class ButterflyEntry {
        public ButterflyEntry(String entityId, Size size, Speed speed) {
            this.entityId = entityId;
            this.size = size;
            this.speed = speed;
        }

        public String entityId;
        public Size size;
        public Speed speed;
    }

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, ButterflyEntry> BUTTERFLY_ENTRIES = new HashMap<>();

    /**
     * Create new butterfly data.
     * @param index The butterfly index.
     * @param species The species of the butterfly. Used to generate resource
     *                locations.
     * @param size The size of the butterfly.
     */
    private static void addButterfly(int index, String species, Size size, Speed speed)
    {
        ENTITY_ID_TO_INDEX_MAP.put(species, index);
        BUTTERFLY_ENTRIES.put(index, new ButterflyEntry(species, size, speed));
    }

    static {
        addButterfly(0, "admiral", Size.MEDIUM, Speed.MODERATE);
        addButterfly(1, "buckeye", Size.MEDIUM, Speed.MODERATE);
        addButterfly(2, "cabbage", Size.MEDIUM, Speed.MODERATE);
        addButterfly(3, "chalkhill", Size.SMALL, Speed.FAST);
        addButterfly(4, "clipper", Size.LARGE, Speed.FAST);
        addButterfly(5, "common", Size.SMALL, Speed.MODERATE);
        addButterfly(6, "emperor", Size.MEDIUM, Speed.MODERATE);
        addButterfly(7, "forester", Size.SMALL, Speed.MODERATE);
        addButterfly(8, "glasswing", Size.MEDIUM, Speed.MODERATE);
        addButterfly(9, "hairstreak", Size.SMALL, Speed.MODERATE);
        addButterfly(10, "heath", Size.SMALL, Speed.MODERATE);
        addButterfly(11, "longwing", Size.MEDIUM, Speed.MODERATE);
        addButterfly(12, "monarch", Size.LARGE, Speed.MODERATE);
        addButterfly(13, "morpho", Size.LARGE, Speed.MODERATE);
        addButterfly(14, "rainbow", Size.SMALL, Speed.FAST);
        addButterfly(15, "swallowtail", Size.LARGE, Speed.MODERATE);
    }

    /**
     * Converts an Entity ID to an index.
     * @param entityId The entity ID to convert.
     * @return The index of said entity ID.
     */
    private static int entityIdToIndex(String entityId) {
        if (entityId.contains(":")) {
            String[] splits = entityId.split(":");
            entityId = splits[1];
        }

        if (entityId.contains("_")) {
            String[] splits = entityId.split("_");
            entityId = splits[0];
        }

        if (ENTITY_ID_TO_INDEX_MAP.containsKey(entityId)) {
            return ENTITY_ID_TO_INDEX_MAP.get(entityId);
        }

        return -1;
    }

    /**
     * Converts an index to an entity ID.
     * @param index The index to convert to an entity ID.
     * @return The entity ID string.
     */
    private static String indexToEntityId(int index) {
        if (BUTTERFLY_ENTRIES.containsKey(index)) {
            return BUTTERFLY_ENTRIES.get(index).entityId;
        }

        return null;
    }

    /**
     * Converts a resource location to a butterfly index.
     * @param location The resource location to convert.
     * @return The butterfly index for the butterfly species, or -1 if not
     *         found.
     */
    public static int locationToIndex(ResourceLocation location) {
        return entityIdToIndex(location.toString());
    }

    /**
     * Gets the resource location for the butterfly at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly.
     */
    public static ResourceLocation indexToButterflyLocation(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly egg.
     */
    public static ResourceLocation indexToButterflyEggLocation(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the caterpillar at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the caterpillar.
     */
    public static ResourceLocation indexToCaterpillarLocation(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_caterpillar");
        }

        return null;
    }

    /**
     * Gets the resource location for the chrysalis at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the chrysalis.
     */
    public static ResourceLocation indexToChrysalisLocation(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_chrysalis");
        }

        return null;
    }

    /**
     * Get the size of the butterfly by index.
     * @param index The butterfly index.
     * @return The size of the butterfly.
     */
    public static Size getSize(int index) {
        if (BUTTERFLY_ENTRIES.containsKey(index)) {
            return BUTTERFLY_ENTRIES.get(index).size;
        }

        return Size.MEDIUM;
    }

    /**
     * Get the size of the butterfly by its resource location.
     * @param location The resource location of the butterfly.
     * @return The size of the butterfly.
     */
    public static Size getSize(ResourceLocation location) {
        int index = locationToIndex(location);
        return getSize(index);
    }

    /**
     * Get the speed of the butterfly by index.
     * @param index The butterfly index.
     * @return The speed of the butterfly.
     */
    public static Speed getSpeed(int index) {
        if (BUTTERFLY_ENTRIES.containsKey(index)) {
            return BUTTERFLY_ENTRIES.get(index).speed;
        }

        return Speed.MODERATE;
    }

    /**
     * Get the speed of the butterfly by its resource location.
     * @param location The resource location of the butterfly.
     * @return The speed of the butterfly.
     */
    public static Speed getSpeed(ResourceLocation location) {
        int index = locationToIndex(location);
        return getSpeed(index);
    }
}
