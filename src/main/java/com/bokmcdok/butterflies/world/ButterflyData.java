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

import static com.bokmcdok.butterflies.lang.EnumExtensions.searchEnum;

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
                            ResourceLocation preferredFlower) {

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

    // Represents a butterflies preferred habitat.Note that like rarity, this
    // only affects the description. The biome modifiers will determine where
    // they will actually spawn.
    public enum Habitat {
        FORESTS,
        FORESTS_AND_PLAINS,
        JUNGLES,
        PLAINS
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

    // Constants representing the base life spans of each butterfly cycle.
    public static int[] LIFESPAN = {
            24000 * 2,
            24000 * 4,
            24000 * 7
    };

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, ButterflyData> BUTTERFLY_ENTRIES = new HashMap<>();

    /**
     * Get the overall lifespan as a simple enumeration
     *
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
     *
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
                         ResourceLocation preferredFlower) {
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
    }

    /**
     * Class to help serialize a butterfly entry.
     */
    public static class Serializer implements JsonDeserializer<ButterflyData> {

        /**
         * Deserializes a JSON object into a butterfly entry
         *
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

                String sizeStr = object.get("size").getAsString();
                Size size = searchEnum(Size.class, sizeStr, Size.MEDIUM);

                String speedStr = object.get("speed").getAsString();
                Speed speed = searchEnum(Speed.class, speedStr, Speed.MODERATE);

                String rarityStr = object.get("rarity").getAsString();
                Rarity rarity = searchEnum(Rarity.class, rarityStr, Rarity.COMMON);

                String habitatStr = object.get("habitat").getAsString();
                Habitat habitat = searchEnum(Habitat.class, habitatStr, Habitat.PLAINS);

                JsonObject lifespan = object.get("lifespan").getAsJsonObject();

                String eggStr = lifespan.get("egg").getAsString();
                Lifespan eggLifespan = searchEnum(Lifespan.class, eggStr, Lifespan.MEDIUM);

                String caterpillarStr = lifespan.get("caterpillar").getAsString();
                Lifespan caterpillarLifespan = searchEnum(Lifespan.class, caterpillarStr, Lifespan.MEDIUM);

                String chrysalisStr = lifespan.get("chrysalis").getAsString();
                Lifespan chrysalisLifespan = searchEnum(Lifespan.class, chrysalisStr, Lifespan.MEDIUM);

                String butterflyStr = lifespan.get("butterfly").getAsString();
                Lifespan butterflyLifespan = searchEnum(Lifespan.class, butterflyStr, Lifespan.MEDIUM);

                String preferredFlower = object.get("preferredFlower").getAsString();

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
                        new ResourceLocation(preferredFlower)
                );
            }

            return entry;
        }
    }

    /**
     * Create new butterfly data.
     *
     * @param entry The butterfly data.
     */
    public static void addButterfly(ButterflyData entry) {
        ENTITY_ID_TO_INDEX_MAP.put(entry.entityId, entry.butterflyIndex);
        BUTTERFLY_ENTRIES.put(entry.butterflyIndex, entry);
    }

    /**
     * Converts an Entity ID to an index.
     *
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
     *
     * @return The butterfly entries as a collection.
     */
    public static Collection<ButterflyData> getButterflyDataCollection() {
        return BUTTERFLY_ENTRIES.values();
    }

    /**
     * Converts a resource location to a butterfly index.
     *
     * @param location The resource location to convert.
     * @return The butterfly index for the butterfly species, or -1 if not
     * found.
     */
    public static int getButterflyIndex(ResourceLocation location) {
        return entityIdToIndex(location.toString());
    }

    /**
     * Get butterfly data by index.
     *
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
     *
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
        return BUTTERFLY_ENTRIES.size();
    }

    /**
     * Gets the resource location for the butterfly at the specified index.
     *
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
     *
     * @param index The butterfly index.
     * @return The resource location of the butterfly egg.
     */
    public static ResourceLocation indexToButterflyEggEntity(int index) {
        String entityId = indexToEntityId(index);
        if (entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     *
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
     *
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
     * Gets the resource location for the caterpillar item at the specified index.
     *
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
     * Gets the resource location for the chrysalis at the specified index.
     *
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
     * Converts an index to an entity ID.
     *
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
