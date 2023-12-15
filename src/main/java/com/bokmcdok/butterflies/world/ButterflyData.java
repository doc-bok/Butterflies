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

    // Represents the speed of a butterfly.
    public enum Speed {
        MODERATE,
        FAST
    }

    // Represents the rarity of a butterfly. Note that this only affects the
    // description. The actual rarity is defined by biome modifiers.
    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE
    }

    // Represents a butterflies preferred habitat.Note that like rarity, this
    // only affects the description. The biome modifiers will determine where
    // they will actually spawn.
    public enum Habitat {
        FORESTS,
        FORESTS_AND_PLAINS,
        JUNGLES,
        PLAINS
    }

    // Helper enum to determine a butterflies overall lifespan.
    public enum Lifespan {
        SHORT,
        MEDIUM,
        LONG
    }

    // Constants representing the base life spans of each butterfly cycle.
    public static int LIFESPAN_SHORT = 24000 * 2;
    public static int LIFESPAN_MEDIUM = 24000 * 4;
    public static int LIFESPAN_LONG = 24000 * 7;

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, Entry> BUTTERFLY_ENTRIES = new HashMap<>();

    /**
     * Class to hold all the data for a specific butterfly.
     */
    public static class Entry {
        public final String entityId;
        public final Size size;
        public final Speed speed;
        public final Rarity rarity;
        public final Habitat habitat;

        public final int caterpillarLifespan;
        public final int chrysalisLifespan;
        public final int butterflyLifespan;

        /**
         * Get the overall lifespan as a simple enumeration
         * @return A representation of the lifespan.
         */
        public Lifespan getOverallLifeSpan() {
            int days = (caterpillarLifespan + chrysalisLifespan + butterflyLifespan) / 24000;
            if (days < 15) {
                return Lifespan.SHORT;
            } else if(days < 25) {
                return Lifespan.MEDIUM;
            } else {
                return Lifespan.LONG;
            }
        }

        /**
         * Construction
         * @param entityId The id of the butterfly species.
         * @param size The size of the butterfly.
         * @param speed The speed of the butterfly.
         * @param rarity The rarity of the butterfly.
         * @param caterpillarLifespan How long it remains in the caterpillar stage.
         * @param chrysalisLifespan How long it takes for a chrysalis to hatch.
         * @param butterflyLifespan How long it lives as a butterfly.
         */
        private Entry(String entityId,
                      Size size,
                      Speed speed,
                      Rarity rarity,
                      Habitat habitat,
                      int caterpillarLifespan,
                      int chrysalisLifespan,
                      int butterflyLifespan) {
            this.entityId = entityId;
            this.size = size;
            this.speed = speed;
            this.rarity = rarity;
            this.habitat = habitat;

            this.caterpillarLifespan = caterpillarLifespan * 2;
            this.chrysalisLifespan = chrysalisLifespan;
            this.butterflyLifespan = butterflyLifespan * 2;
        }
    }

    /**
     * Create new butterfly data.
     * @param index The butterfly index.
     * @param species The species of the butterfly. Used to generate resource
     *                locations.
     * @param size The size of the butterfly.
     */
    private static void addButterfly(int index,
                                     String species,
                                     Size size,
                                     Speed speed,
                                     Rarity rarity,
                                     Habitat habitat,
                                     int caterpillarLifespan,
                                     int chrysalisLifespan,
                                     int butterflyLifespan)
    {
        ENTITY_ID_TO_INDEX_MAP.put(species, index);
        BUTTERFLY_ENTRIES.put(index, new Entry(species,
                                               size,
                                               speed,
                                               rarity,
                                               habitat,
                                               caterpillarLifespan,
                                               chrysalisLifespan,
                                               butterflyLifespan));
    }

    static {
        addButterfly(0, "admiral", Size.MEDIUM, Speed.MODERATE, Rarity.COMMON, Habitat.FORESTS,
                LIFESPAN_SHORT, LIFESPAN_MEDIUM, LIFESPAN_MEDIUM);
        addButterfly(1, "buckeye", Size.MEDIUM, Speed.MODERATE, Rarity.COMMON, Habitat.PLAINS,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_MEDIUM);
        addButterfly(2, "cabbage", Size.MEDIUM, Speed.MODERATE, Rarity.COMMON, Habitat.PLAINS,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_SHORT);
        addButterfly(3, "chalkhill", Size.SMALL, Speed.FAST, Rarity.COMMON, Habitat.PLAINS,
                LIFESPAN_MEDIUM, LIFESPAN_MEDIUM, LIFESPAN_MEDIUM);
        addButterfly(4, "clipper", Size.LARGE, Speed.FAST, Rarity.RARE, Habitat.FORESTS,
                LIFESPAN_MEDIUM, LIFESPAN_LONG, LIFESPAN_MEDIUM);
        addButterfly(5, "common", Size.SMALL, Speed.MODERATE, Rarity.COMMON, Habitat.PLAINS,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_MEDIUM);
        addButterfly(6, "emperor", Size.MEDIUM, Speed.MODERATE, Rarity.COMMON, Habitat.FORESTS,
                LIFESPAN_MEDIUM, LIFESPAN_SHORT, LIFESPAN_MEDIUM);
        addButterfly(7, "forester", Size.SMALL, Speed.MODERATE, Rarity.RARE, Habitat.FORESTS,
                LIFESPAN_LONG, LIFESPAN_MEDIUM, LIFESPAN_MEDIUM);
        addButterfly(8, "glasswing", Size.MEDIUM, Speed.MODERATE, Rarity.UNCOMMON, Habitat.JUNGLES,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_LONG);
        addButterfly(9, "hairstreak", Size.SMALL, Speed.MODERATE, Rarity.COMMON, Habitat.FORESTS,
                LIFESPAN_SHORT, LIFESPAN_MEDIUM, LIFESPAN_SHORT);
        addButterfly(10, "heath", Size.SMALL, Speed.MODERATE, Rarity.RARE, Habitat.PLAINS,
                LIFESPAN_LONG, LIFESPAN_MEDIUM, LIFESPAN_LONG);
        addButterfly(11, "longwing", Size.MEDIUM, Speed.MODERATE, Rarity.COMMON, Habitat.FORESTS,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_LONG);
        addButterfly(12, "monarch", Size.LARGE, Speed.MODERATE, Rarity.COMMON, Habitat.PLAINS,
                LIFESPAN_SHORT, LIFESPAN_SHORT, LIFESPAN_MEDIUM);
        addButterfly(13, "morpho", Size.LARGE, Speed.MODERATE, Rarity.RARE, Habitat.JUNGLES,
                LIFESPAN_MEDIUM, LIFESPAN_SHORT, LIFESPAN_MEDIUM);
        addButterfly(14, "rainbow", Size.SMALL, Speed.FAST, Rarity.UNCOMMON, Habitat.FORESTS_AND_PLAINS,
                LIFESPAN_MEDIUM, LIFESPAN_MEDIUM, LIFESPAN_MEDIUM);
        addButterfly(15, "swallowtail", Size.LARGE, Speed.MODERATE, Rarity.COMMON, Habitat.FORESTS_AND_PLAINS,
                LIFESPAN_SHORT, LIFESPAN_MEDIUM, LIFESPAN_SHORT);
    }

    /**
     * Converts an Entity ID to an index.
     * @param entityId The entity ID to convert.
     * @return The index of said entity ID.
     */
    private static int entityIdToIndex(String entityId) {
        String species = entityId;
        if (species.contains(":")) {
            String[] splits = species.split(":");
            species = splits[1];
        }

        if (species.contains("_")) {
            String[] splits = species.split("_");
            species = splits[0];
        }

        if (ENTITY_ID_TO_INDEX_MAP.containsKey(species)) {
            return ENTITY_ID_TO_INDEX_MAP.get(species);
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
     * Get butterfly data by index.
     * @param index The butterfly index.
     * @return The butterfly entry.
     */
    public static Entry getEntry(int index) {
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
    public static Entry getEntry(ResourceLocation location) {
        int index = locationToIndex(location);
        return getEntry(index);
    }
}
