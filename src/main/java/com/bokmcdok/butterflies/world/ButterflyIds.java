package com.bokmcdok.butterflies.world;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper for converting entity IDs to indexes and vice versa.
 */
public class ButterflyIds {

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, String> INDEX_TO_ENTITY_ID_MAP = new HashMap<>();

    static {
        ENTITY_ID_TO_INDEX_MAP.put("admiral", 0);
        ENTITY_ID_TO_INDEX_MAP.put("buckeye", 1);
        ENTITY_ID_TO_INDEX_MAP.put("cabbage", 2);
        ENTITY_ID_TO_INDEX_MAP.put("chalkhill", 3);
        ENTITY_ID_TO_INDEX_MAP.put("clipper", 4);
        ENTITY_ID_TO_INDEX_MAP.put("common", 5);
        ENTITY_ID_TO_INDEX_MAP.put("emperor", 6);
        ENTITY_ID_TO_INDEX_MAP.put("forester", 7);
        ENTITY_ID_TO_INDEX_MAP.put("glasswing", 8);
        ENTITY_ID_TO_INDEX_MAP.put("hairstreak", 9);
        ENTITY_ID_TO_INDEX_MAP.put("heath", 10);
        ENTITY_ID_TO_INDEX_MAP.put("longwing", 11);
        ENTITY_ID_TO_INDEX_MAP.put("monarch", 12);
        ENTITY_ID_TO_INDEX_MAP.put("morpho", 13);
        ENTITY_ID_TO_INDEX_MAP.put("rainbow", 14);
        ENTITY_ID_TO_INDEX_MAP.put("swallowtail", 15);

        INDEX_TO_ENTITY_ID_MAP.put(0, "admiral");
        INDEX_TO_ENTITY_ID_MAP.put(1, "buckeye");
        INDEX_TO_ENTITY_ID_MAP.put(2, "cabbage");
        INDEX_TO_ENTITY_ID_MAP.put(3, "chalkhill");
        INDEX_TO_ENTITY_ID_MAP.put(4, "clipper");
        INDEX_TO_ENTITY_ID_MAP.put(5, "common");
        INDEX_TO_ENTITY_ID_MAP.put(6, "emperor");
        INDEX_TO_ENTITY_ID_MAP.put(7, "forester");
        INDEX_TO_ENTITY_ID_MAP.put(8, "glasswing");
        INDEX_TO_ENTITY_ID_MAP.put(9, "hairstreak");
        INDEX_TO_ENTITY_ID_MAP.put(10, "heath");
        INDEX_TO_ENTITY_ID_MAP.put(11, "longwing");
        INDEX_TO_ENTITY_ID_MAP.put(12, "monarch");
        INDEX_TO_ENTITY_ID_MAP.put(13, "morpho");
        INDEX_TO_ENTITY_ID_MAP.put(14, "rainbow");
        INDEX_TO_ENTITY_ID_MAP.put(15, "swallowtail");
    }

    /**
     * Converts an Entity ID to an index.
     * @param entityId The entity ID to convert.
     * @return The index of said entity ID.
     */
    public static int EntityIdToIndex(String entityId) {
        if (entityId.contains(":")) {
            String[] splits = entityId.split(":");
            entityId = splits[1];
        }

        if (ENTITY_ID_TO_INDEX_MAP.containsKey(entityId)) {
            return ENTITY_ID_TO_INDEX_MAP.get(entityId);
        }

        return -1;
    }

    /**
     * Convert an index to an entity ID.
     * @param index The index to convert.
     * @return The entity ID at the index.
     */
    public static String IndexToEntityId(int index) {
        if (INDEX_TO_ENTITY_ID_MAP.containsKey(index)) {
            return INDEX_TO_ENTITY_ID_MAP.get(index);
        }

        return null;
    }
}
