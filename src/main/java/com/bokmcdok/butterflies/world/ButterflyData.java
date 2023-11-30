package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static void AddButterfly(int index, String species, Size size)
    {
        ENTITY_ID_TO_INDEX_MAP.put(species, index);
        INDEX_TO_ENTITY_ID_MAP.put(index, species);
        BUTTERFLY_SIZES.put(index, size);
    }

    static {
        AddButterfly(0, "admiral", Size.MEDIUM);
        AddButterfly(1, "buckeye", Size.MEDIUM);
        AddButterfly(2, "cabbage", Size.LARGE);
        AddButterfly(3, "chalkhill", Size.SMALL);
        AddButterfly(4, "clipper", Size.LARGE);
        AddButterfly(5, "common", Size.MEDIUM);
        AddButterfly(6, "emperor", Size.LARGE);
        AddButterfly(7, "forester", Size.MEDIUM);
        AddButterfly(8, "glasswing", Size.MEDIUM);
        AddButterfly(9, "hairstreak", Size.MEDIUM);
        AddButterfly(10, "heath", Size.SMALL);
        AddButterfly(11, "longwing", Size.SMALL);
        AddButterfly(12, "monarch", Size.MEDIUM);
        AddButterfly(13, "morpho", Size.LARGE);
        AddButterfly(14, "rainbow", Size.SMALL);
        AddButterfly(15, "swallowtail", Size.LARGE);
    }

    /**
     * Converts an Entity ID to an index.
     * @param entityId The entity ID to convert.
     * @return The index of said entity ID.
     */
    private static int EntityIdToIndex(String entityId) {
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
    public static int LocationToIndex(ResourceLocation location) {
        return EntityIdToIndex(location.toString());
    }

    /**
     * Gets the resource location for the butterfly at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly.
     */
    public static ResourceLocation IndexToButterflyLocation(int index) {
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
    public static ResourceLocation IndexToButterflyEggLocation(int index) {
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
    public static ResourceLocation IndexToCaterpillarLocation(int index) {
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
    public static ResourceLocation IndexToChrysalisLocation(int index) {
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return new ResourceLocation(ButterfliesMod.MODID, INDEX_TO_ENTITY_ID_MAP.get(index) + "_chrysalis");
        }

        return null;
    }

    public static Size GetSize(int index) {
        if (BUTTERFLY_SIZES.containsKey(index)) {
            return BUTTERFLY_SIZES.get(index);
        }

        return Size.MEDIUM;
    }

    public static Size GetSize(ResourceLocation location) {
        int index = LocationToIndex(location);
        return GetSize(index);
    }
}
