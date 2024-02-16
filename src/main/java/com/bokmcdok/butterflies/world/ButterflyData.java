package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private static final Map<Integer, ButterflyData> BUTTERFLY_ENTRIES = new HashMap<>();

    // The index of the butterfly
    public final int butterflyIndex;

    // The butterfly species
    public final String entityId;

    // The size of the butterfly
    public final Size size;

    // The speed of the butterfly
    public final Speed speed;

    // How rare the butterfly is
    public final Rarity rarity;

    // The description of the butterfly's habitat
    public final Habitat habitat;

    // The lifespan of the caterpillar phase
    public final int caterpillarLifespan;

    // The lifespan of the chrysalis phase
    public final int chrysalisLifespan;

    // The lifespan of the butterfly phase
    public final int butterflyLifespan;

    /**
     * Get the overall lifespan as a simple enumeration
     * @return A representation of the lifespan.
     */
    public Lifespan getOverallLifeSpan() {
        int days = (caterpillarLifespan + chrysalisLifespan + butterflyLifespan) / 24000;
        if (days < 15) {
            return Lifespan.SHORT;
        } else if (days < 25) {
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
     */
    public ButterflyData(int butterflyIndex,
                          String entityId,
                          Size size,
                          Speed speed,
                          Rarity rarity,
                          Habitat habitat,
                          int caterpillarLifespan,
                          int chrysalisLifespan,
                          int butterflyLifespan) {
        this.butterflyIndex = butterflyIndex;
        this.entityId = entityId;
        this.size = size;
        this.speed = speed;
        this.rarity = rarity;
        this.habitat = habitat;

        this.caterpillarLifespan = caterpillarLifespan * 2;
        this.chrysalisLifespan = chrysalisLifespan;
        this.butterflyLifespan = butterflyLifespan * 2;
    }

    /**
     * Class to help serialize a butterfly entry.
     */
    public static class Serializer implements JsonDeserializer<ButterflyData> {

        /**
         * Deserializes a JSON object into a butterfly entry
         * @param json The Json data being deserialized
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

                String sizeStr = object.get("size").getAsString();
                Size size = Size.MEDIUM;
                if (Objects.equals(sizeStr, "small")) {
                    size = Size.SMALL;
                } else if (Objects.equals(sizeStr, "large")) {
                    size = Size.LARGE;
                }

                String speedStr = object.get("speed").getAsString();
                Speed speed = Speed.MODERATE;
                if (Objects.equals(speedStr, "fast")) {
                    speed = Speed.FAST;
                }

                String rarityStr = object.get("rarity").getAsString();
                Rarity rarity = Rarity.COMMON;
                if (Objects.equals(rarityStr, "uncommon")) {
                    rarity = Rarity.UNCOMMON;
                } else if (Objects.equals(rarityStr, "rare")) {
                    rarity = Rarity.RARE;
                }

                String habitatStr = object.get("habitat").getAsString();
                Habitat habitat = Habitat.PLAINS;
                if (Objects.equals(habitatStr, "forests")) {
                    habitat = Habitat.FORESTS;
                } else if (Objects.equals(habitatStr, "forests_and_plains")) {
                    habitat = Habitat.FORESTS_AND_PLAINS;
                } else if (Objects.equals(habitatStr, "jungles")) {
                    habitat = Habitat.JUNGLES;
                }

                JsonObject lifespan = object.get("lifespan").getAsJsonObject();

                String caterpillarStr = lifespan.get("caterpillar").getAsString();
                int caterpillarLifespan = LIFESPAN_MEDIUM;
                if (Objects.equals(caterpillarStr, "short")) {
                    caterpillarLifespan = LIFESPAN_SHORT;
                } else if (Objects.equals(caterpillarStr, "long")) {
                    caterpillarLifespan = LIFESPAN_LONG;
                }

                String chrysalisStr = lifespan.get("chrysalis").getAsString();
                int chrysalisLifespan = LIFESPAN_MEDIUM;
                if (Objects.equals(chrysalisStr, "short")) {
                    chrysalisLifespan = LIFESPAN_SHORT;
                } else if (Objects.equals(chrysalisStr, "long")) {
                    chrysalisLifespan = LIFESPAN_LONG;
                }

                String butterflyStr = lifespan.get("butterfly").getAsString();
                int butterflyLifespan = LIFESPAN_MEDIUM;
                if (Objects.equals(butterflyStr, "short")) {
                    butterflyLifespan = LIFESPAN_SHORT;
                } else if (Objects.equals(butterflyStr, "long")) {
                    butterflyLifespan = LIFESPAN_LONG;
                }

                entry = new ButterflyData(
                        index,
                        entityId,
                        size,
                        speed,
                        rarity,
                        habitat,
                        caterpillarLifespan,
                        chrysalisLifespan,
                        butterflyLifespan
                );
            }

            return entry;
        }
    }

    /**
     * Create new butterfly data.
     * @param entry The butterfly data.
     */
    public static void addButterfly(ButterflyData entry)
    {
        ENTITY_ID_TO_INDEX_MAP.put(entry.entityId, entry.butterflyIndex);
        BUTTERFLY_ENTRIES.put(entry.butterflyIndex, entry);
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
     *         found.
     */
    public static int getButterflyIndex(ResourceLocation location) {
        return entityIdToIndex(location.toString());
    }

    /**
     * Gets the resource location for the butterfly at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the butterfly.
     */
    public static ResourceLocation indexToButterflyEntity(int index) {
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
    public static ResourceLocation indexToButterflyEggItem(int index) {
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
    public static ResourceLocation indexToCaterpillarEntity(int index) {
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
    public static ResourceLocation indexToChrysalisEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_chrysalis");
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
            return new ResourceLocation(ButterfliesMod.MODID, "caterpillar_" + entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the caterpillar item at the specified index.
     * @param index The butterfly index.
     * @return The resource location of the caterpillar item.
     */
    public static ResourceLocation indexToBottledCaterpillarItem(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, "bottled_caterpillar_" + entityId);
        }

        return null;
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
}
