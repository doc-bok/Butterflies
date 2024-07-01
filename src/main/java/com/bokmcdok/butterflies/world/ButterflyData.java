package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.lang.EnumExtensions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper for converting entity ID to index and vice versa.
 * @param butterflyIndex      The index of the butterfly
 * @param entityId            The butterfly species
 * @param size                The size of the butterfly
 * @param speed               The speed of the butterfly
 * @param rarity              How rare the butterfly is
 * @param habitat             The description of the butterfly's habitat
 * @param eggLifespan         The lifespan of the caterpillar phase
 * @param caterpillarLifespan The lifespan of the caterpillar phase
 * @param chrysalisLifespan   The lifespan of the chrysalis phase
 * @param butterflyLifespan   The lifespan of the butterfly phase
 * @param preferredFlower     The flower this butterfly prefers
 */
public record ButterflyData(int butterflyIndex,
                            String entityId,
                            Size size,
                            Speed speed,
                            Rarity rarity,
                            Habitat habitat,
                            int eggLifespan,
                            int caterpillarLifespan,
                            int chrysalisLifespan,
                            int butterflyLifespan,
                            ResourceLocation preferredFlower,
                            ButterflyType type) {

    // Represents a butterflies preferred habitat.Note that like rarity, this
    // only affects the description. The biome modifiers will determine where
    // they will actually spawn.
    public enum Habitat {
        FORESTS,
        FORESTS_AND_PLAINS,
        ICE,
        JUNGLES,
        PLAINS
    }

    // Helper enum to determine a butterflies overall lifespan.
    public enum Lifespan {
        SHORT(0),
        MEDIUM(1),
        LONG(2);

        private final int value;

        Lifespan(int value) {
            this.value = value;
        }

        public int getIndex() {
            return this.value;
        }
    }

    // Represents the rarity of a butterfly. Note that this only affects the
    // description. The actual rarity is defined by biome modifiers.
    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE
    }

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
    
    public enum ButterflyType {
        BUTTERFLY,
        SPECIAL
    }

    // Constants representing the base life spans of each butterfly cycle.
    public static int[] LIFESPAN = {
            24000 * 2,
            24000 * 4,
            24000 * 7
    };

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, ButterflyData> BUTTERFLY_ENTRIES = new HashMap<>();

    private static int NUM_BUTTERFLIES;

    /**
     * Get the overall lifespan as a simple enumeration
     * @return A representation of the lifespan.
     */
    public Lifespan getOverallLifeSpan() {
        int days = (eggLifespan + caterpillarLifespan + chrysalisLifespan + butterflyLifespan) / 24000;
        if (days < 18) {
            return Lifespan.SHORT;
        } else if (days < 30) {
            return Lifespan.MEDIUM;
        } else {
            return Lifespan.LONG;
        }
    }

    /**
     * Construction
     * @param entityId            The id of the butterfly species.
     * @param size                The size of the butterfly.
     * @param speed               The speed of the butterfly.
     * @param rarity              The rarity of the butterfly.
     * @param caterpillarLifespan How long it remains in the caterpillar stage.
     * @param chrysalisLifespan   How long it takes for a chrysalis to hatch.
     * @param butterflyLifespan   How long it lives as a butterfly.
     * @param preferredFlower     The flower this butterfly prefers
     */
    public ButterflyData(int butterflyIndex,
                         String entityId,
                         Size size,
                         Speed speed,
                         Rarity rarity,
                         Habitat habitat,
                         int eggLifespan,
                         int caterpillarLifespan,
                         int chrysalisLifespan,
                         int butterflyLifespan,
                         ResourceLocation preferredFlower,
                         ButterflyType type) {
        this.butterflyIndex = butterflyIndex;
        this.entityId = entityId;
        this.size = size;
        this.speed = speed;
        this.rarity = rarity;
        this.habitat = habitat;

        this.eggLifespan = eggLifespan;
        this.caterpillarLifespan = caterpillarLifespan * 2;
        this.chrysalisLifespan = chrysalisLifespan;
        this.butterflyLifespan = butterflyLifespan * 2;

        this.preferredFlower = preferredFlower;
        
        this.type = type;
    }

    /**
     * Class to help serialize a butterfly entry.
     */
    public static class Serializer implements JsonDeserializer<ButterflyData> {

        /**
         * Deserializes a JSON object into a butterfly entry
         * @param json    The Json data being deserialized
         * @param typeOfT The type of the Object to deserialize to
         * @param context Language context (ignored)
         * @return A new butterfly entry
         * @throws JsonParseException Unused
         */
        @Override
        public ButterflyData deserialize(JsonElement json,
                                         Type typeOfT,
                                         JsonDeserializationContext context) throws JsonParseException {
            ButterflyData entry = null;

            if (json instanceof final JsonObject object) {
                int index = object.get("index").getAsInt();
                String entityId = object.get("entityId").getAsString();

                Size size = getEnumValue(object, Size.class, "size", Size.MEDIUM);
                Speed speed = getEnumValue(object, Speed.class, "speed", Speed.MODERATE);
                Rarity rarity = getEnumValue(object, Rarity.class, "rarity", Rarity.COMMON);
                Habitat habitat = getEnumValue(object, Habitat.class, "habitat", Habitat.PLAINS);

                JsonObject lifespan = object.get("lifespan").getAsJsonObject();
                Lifespan eggLifespan = getEnumValue(lifespan, Lifespan.class, "egg", Lifespan.MEDIUM);
                Lifespan caterpillarLifespan = getEnumValue(lifespan, Lifespan.class, "caterpillar", Lifespan.MEDIUM);
                Lifespan chrysalisLifespan = getEnumValue(lifespan, Lifespan.class, "chrysalis", Lifespan.MEDIUM);
                Lifespan butterflyLifespan = getEnumValue(lifespan, Lifespan.class, "butterfly", Lifespan.MEDIUM);

                String preferredFlower = object.get("preferredFlower").getAsString();

                ButterflyType type = getEnumValue(object, ButterflyType.class, "type", ButterflyType.BUTTERFLY);

                entry = new ButterflyData(
                        index,
                        entityId,
                        size,
                        speed,
                        rarity,
                        habitat,
                        LIFESPAN[eggLifespan.getIndex()],
                        LIFESPAN[caterpillarLifespan.getIndex()],
                        LIFESPAN[chrysalisLifespan.getIndex()],
                        LIFESPAN[butterflyLifespan.getIndex()],
                        new ResourceLocation(preferredFlower),
                        type
                );
            }

            return entry;
        }

        /**
         * Helper method for pulling out enumerated values.
         * @param object The JSON object to read the value from.
         * @param enumeration The enumerated type to extract.
         * @param key The key to look for.
         * @param fallback The fallback value if a value isn't found.
         * @return A value of the enumerated type.
         * @param <T> (Inferred) The type of the enumeration.
         */
        @NotNull
        private static <T extends Enum<?>> T getEnumValue(JsonObject object,
                                                          Class<T> enumeration,
                                                          String key,
                                                          T fallback) {
            String value = object.get(key).getAsString();
            return EnumExtensions.searchEnum(enumeration, value, fallback);
        }
    }

    /**
     * Create new butterfly data.
     * @param entry The butterfly data.
     */
    public static void addButterfly(ButterflyData entry) {
        ENTITY_ID_TO_INDEX_MAP.put(entry.entityId, entry.butterflyIndex);
        BUTTERFLY_ENTRIES.put(entry.butterflyIndex, entry);

        //  Recount the butterflies
        if (entry.type == ButterflyType.BUTTERFLY) {
            NUM_BUTTERFLIES = 0;
            for (ButterflyData i : BUTTERFLY_ENTRIES.values()) {
                if (i.type == ButterflyType.BUTTERFLY) {
                    ++NUM_BUTTERFLIES;
                }
            }
        }
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

        String[] components = species.split("_");
        for (String component : components) {
            if (ENTITY_ID_TO_INDEX_MAP.containsKey(component)) {
                return ENTITY_ID_TO_INDEX_MAP.get(component);
            }
        }

        return -1;
    }

    /**
     * Get all butterfly data. Used for network synchronisation.
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
        return entityIdToIndex(location.toString());
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
     * Gets the resource location for the butterfly at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly.
     */
    public static ResourceLocation indexToButterflyEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly egg.
     */
    public static ResourceLocation indexToButterflyEggEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly egg.
     */
    public static ResourceLocation indexToButterflyEggItem(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the texture to use for a specific butterfly
     * @param butterflyIndex The butterfly index.
     * @return The resource location of the texture to use.
     */
    public static ResourceLocation indexToButterflyScrollTexture(int butterflyIndex) {
        String entityId = indexToEntityId(butterflyIndex);
        return new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/" + entityId + ".png");
    }

    /**
     * Gets the resource location for the caterpillar at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the caterpillar.
     */
    public static ResourceLocation indexToCaterpillarEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + "_caterpillar");
        }

        return null;
    }

    /**
     * Gets the resource location for the caterpillar item at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the caterpillar item.
     */
    public static ResourceLocation indexToCaterpillarItem(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, "caterpillar_" + entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the chrysalis at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the chrysalis.
     */
    public static ResourceLocation indexToChrysalisEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + "_chrysalis");
        }

        return null;
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
}
