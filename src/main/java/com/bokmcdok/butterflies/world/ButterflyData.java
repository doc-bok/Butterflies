package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.lang.EnumExtensions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
                            ButterflyType type,
                            Diurnality diurnality,
                            ExtraLandingBlocks extraLandingBlocks,
                            PlantEffect plantEffect,
                            ResourceLocation breedTarget,
                            EggMultiplier eggMultiplier) {

    // Represents the type of "butterfly"
    public enum ButterflyType {
        BUTTERFLY,
        MOTH,
        SPECIAL
    }

    // Represents where in the day/night cycle a butterfly is most active.
    @SuppressWarnings("unused")
    public enum Diurnality {
        DIURNAL,        // Active during the day
        NOCTURNAL,      // Active during the night
        CREPUSCULAR,    // Active during twilight
        CATHEMERAL      // Always active
    }

    // Some dimorphic butterflies will have different amounts of eggs.
    public enum EggMultiplier {
        NONE,
        NORMAL,
        DOUBLE
    }

    // Used to indicate any extra landing blocks that the butterflies can use.
    public enum ExtraLandingBlocks {
        NONE,
        HAY_BALE,
        LOGS,
        WOOL,
        FRUIT
    }

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
        LONG(2),
        IMMORTAL(3);

        private final int value;

        Lifespan(int value) {
            this.value = value;
        }

        public int getIndex() {
            return this.value;
        }
    }

    // The effect the butterfly has on plants.
    public enum PlantEffect {
        NONE,
        POLLINATE,
        CONSUME
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
        LARGE,
        HUGE
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
            24000 * 7,
            Integer.MAX_VALUE
    };

    //  Helper maps.
    private static final Map<String, Integer> ENTITY_ID_TO_INDEX_MAP = new HashMap<>();
    private static final Map<Integer, ButterflyData> BUTTERFLY_ENTRIES = new HashMap<>();

    private static int NUM_BUTTERFLIES;
    private static int NUM_MOTHS;

    /**
     * Get the overall lifespan as a simple enumeration
     * @return A representation of the lifespan.
     */
    public Lifespan getOverallLifeSpan() {
        if (butterflyLifespan == Integer.MAX_VALUE) {
            return Lifespan.IMMORTAL;
        }

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
                         ButterflyType type,
                         Diurnality diurnality,
                         ExtraLandingBlocks extraLandingBlocks,
                         PlantEffect plantEffect,
                         ResourceLocation breedTarget,
                         EggMultiplier eggMultiplier) {
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
        this.diurnality = diurnality;
        this.extraLandingBlocks = extraLandingBlocks;
        this.plantEffect = plantEffect;

        this.breedTarget = breedTarget;
        this.eggMultiplier = eggMultiplier;
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
                Diurnality diurnality = getEnumValue(object, Diurnality.class, "diurnality", Diurnality.DIURNAL);
                ExtraLandingBlocks extraLandingBlocks = getEnumValue(object, ExtraLandingBlocks.class, "extraLandingBlocks", ExtraLandingBlocks.NONE);
                PlantEffect plantEffect = getEnumValue(object, PlantEffect.class, "plantEffect", PlantEffect.NONE);

                String breedTarget = object.get("breedTarget").getAsString();
                EggMultiplier eggMultiplier = getEnumValue(object, EggMultiplier.class, "eggMultiplier", EggMultiplier.NORMAL);

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
                        type,
                        diurnality,
                        extraLandingBlocks,
                        plantEffect,
                        new ResourceLocation(ButterfliesMod.MODID, breedTarget),
                        eggMultiplier
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
        if (entry.type != ButterflyType.SPECIAL) {
            int total = 0;
            for (ButterflyData i : BUTTERFLY_ENTRIES.values()) {
                if (i.type == entry.type) {
                    ++total;
                }
            }

            if (entry.type == ButterflyType.BUTTERFLY) {
                NUM_BUTTERFLIES = total;
            } else if (entry.type == ButterflyType.MOTH) {
                NUM_MOTHS = total;
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
     * Accessor to help get butterfly data when needed.
     * @return A valid butterfly data entry.
     */
    public static ButterflyData getButterflyDataForEntity(LivingEntity entity) {
        String species = getSpeciesString(entity);
        ResourceLocation location = new ResourceLocation(ButterfliesMod.MODID, species);
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
                // don't lose work done before we realised it was a problem.
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
     * Returns the total number of butterfly species in the mod.
     * @return The total number of butterflies.
     */
    public static int getNumMothSpecies() {
        return NUM_MOTHS;
    }

    /**
     * Gets the resource location for the caterpillar item.
     * @return The resource location of the caterpillar item.
     */
    public ResourceLocation getCaterpillarItem() {
        if (this.entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, "caterpillar_" + this.entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg item.
     * @return The resource location of the butterfly egg.
     */
    public ResourceLocation getButterflyEggItem() {
        if (this.entityId != null) {
            return new ResourceLocation(ButterfliesMod.MODID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly entity.
     * @return The resource location of the butterfly.
     */
    public ResourceLocation getButterflyEntity() {
        return new ResourceLocation(ButterfliesMod.MODID, this.entityId);
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @return The resource location of the butterfly egg.
     */
    public ResourceLocation getButterflyEggEntity() {
        return new ResourceLocation(ButterfliesMod.MODID, this.entityId + "_egg");
    }

    /**
     * Gets the resource location for the caterpillar at the specified index.
     * @return The resource location of the caterpillar.
     */
    public ResourceLocation getCaterpillarEntity() {
        return new ResourceLocation(ButterfliesMod.MODID, this.entityId + "_caterpillar");
    }

    /**
     * Gets the resource location for the chrysalis at the specified index.
     * @return The resource location of the chrysalis.
     */
    public  ResourceLocation getChrysalisEntity() {
        return new ResourceLocation(ButterfliesMod.MODID, this.entityId + "_chrysalis");
    }

    /**
     * Returns the butterfly index of the butterfly's mate.
     * @return The index of the butterfly to try and mate with.
     */
    public int getMateButterflyIndex() {
        int mateIndex = getButterflyIndex(this.breedTarget);
        if (mateIndex < 0) {
            mateIndex = this.butterflyIndex;
        }

        return mateIndex;
    }

    /**
     * Gets the entity that a chrysalis will spawn. For dimorphic species there
     * is a 50/50 chance it will be male or female.
     * @param random A random source used to determine the sex of dimorphic species.
     * @return The entity ID of the butterfly that should spawn.
     */
    public ResourceLocation getMateButterflyEntity(RandomSource random) {
        int mateIndex = getMateButterflyIndex();
        if (random.nextInt() % 2 == 0) {
            ButterflyData entry = getEntry(mateIndex);
            if (entry != null) {
                return entry.getButterflyEntity();
            }
        }

        return this.getButterflyEntity();
    }

    /**
     * Gets the texture to use for a specific butterfly
     * @return The resource location of the texture to use.
     */
    public ResourceLocation getScrollTexture() {
        return new ResourceLocation("butterflies", "textures/gui/butterfly_scroll/" + this.entityId + ".png");
    }

    /**
     * Returns a multiplier for the sizes of eggs, caterpillars, and chrysalises.
     * @return A multiplier based on the butterfly size.
     */
    public float getSizeMultiplier() {
        switch (this.size) {
            case SMALL -> {
                return 0.7f;
            }
            case LARGE -> {
                return 1.28f;
            }
            case HUGE -> {
                return 1.5f;
            }
            default -> {
                return 1.0f;
            }
        }
    }

    /**
     * Check if the current block is a valid landing block.
     * @param blockState The block state to check.
     * @return TRUE if the butterfly can land on the block.
     */
    public boolean isValidLandingBlock(BlockState blockState) {
        if (blockState.is(BlockTags.LEAVES)) {
            return true;
        }

        // Handle extra block types
        return switch (extraLandingBlocks) {
            case HAY_BALE -> blockState.is(Blocks.HAY_BLOCK);
            case LOGS -> blockState.is(BlockTags.LOGS);
            case WOOL -> blockState.is(BlockTags.WOOL);
            case FRUIT -> blockState.is(Blocks.PUMPKIN) || blockState.is(Blocks.MELON);
            default -> false;
        };
    }
}
