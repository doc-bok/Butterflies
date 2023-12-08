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

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, String> INDEX_TO_ENTITY_ID_MAP = new HashMap<>();
    private static final Map<Integer, Size> BUTTERFLY_SIZES = new HashMap<>();

    /**
     * Create new butterfly data.
     * @param index The butterfly index.
     * @param species The species of the butterfly. Used to generate resource
     *                locations.
     * @param size The size of the butterfly.
     */
    private static void addButterfly(int index, String species, Size size)
    {
        ENTITY_ID_TO_INDEX_MAP.put(species, index);
        INDEX_TO_ENTITY_ID_MAP.put(index, species);
        BUTTERFLY_SIZES.put(index, size);
    }

    static {
        addButterfly(0, "admiral", Size.MEDIUM);
        addButterfly(1, "buckeye", Size.MEDIUM);
        addButterfly(2, "cabbage", Size.MEDIUM);
        addButterfly(3, "chalkhill", Size.SMALL);
        addButterfly(4, "clipper", Size.LARGE);
        addButterfly(5, "common", Size.SMALL);
        addButterfly(6, "emperor", Size.MEDIUM);
        addButterfly(7, "forester", Size.SMALL);
        addButterfly(8, "glasswing", Size.MEDIUM);
        addButterfly(9, "hairstreak", Size.SMALL);
        addButterfly(10, "heath", Size.SMALL);
        addButterfly(11, "longwing", Size.MEDIUM);
        addButterfly(12, "monarch", Size.LARGE);
        addButterfly(13, "morpho", Size.LARGE);
        addButterfly(14, "rainbow", Size.SMALL);
        addButterfly(15, "swallowtail", Size.LARGE);
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
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return new ResourceLocation(ButterfliesMod.MODID, INDEX_TO_ENTITY_ID_MAP.get(index));
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly egg.
     */
    public static ResourceLocation indexToButterflyEggLocation(int index) {
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return new ResourceLocation(ButterfliesMod.MODID, INDEX_TO_ENTITY_ID_MAP.get(index) + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the caterpillar at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the caterpillar.
     */
    public static ResourceLocation indexToCaterpillarLocation(int index) {
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return new ResourceLocation(ButterfliesMod.MODID, INDEX_TO_ENTITY_ID_MAP.get(index) + "_caterpillar");
        }

        return null;
    }

    /**
     * Gets the resource location for the chrysalis at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the chrysalis.
     */
    public static ResourceLocation indexToChrysalisLocation(int index) {
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return new ResourceLocation(ButterfliesMod.MODID, INDEX_TO_ENTITY_ID_MAP.get(index) + "_chrysalis");
        }

        return null;
    }

    public static Size getSize(int index) {
        if (BUTTERFLY_SIZES.containsKey(index)) {
            return BUTTERFLY_SIZES.get(index);
        }

        return Size.MEDIUM;
    }

    public static Size getSize(ResourceLocation location) {
        int index = locationToIndex(location);
        return getSize(index);
    }
}
