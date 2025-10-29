package com.bokmcdok.butterflies.world;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.lang.EnumExtensions;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Helper for converting entity ID to index and vice versa.
 * @param butterflyIndex      The index of the butterfly
 * @param entityId            The butterfly species
 * @param size                The size of the butterfly
 * @param speed               The speed of the butterfly
 * @param rarity              How rare the butterfly is
 * @param habitats            A list of the butterflies habitats
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
                            List<Habitat> habitats,
                            int eggLifespan,
                            int caterpillarLifespan,
                            int chrysalisLifespan,
                            int butterflyLifespan,
                            ResourceLocation preferredFlower,
                            ButterflyType type,
                            Diurnality diurnality,
                            ExtraLandingBlocks extraLandingBlocks,
                            PlantEffect plantEffect,
                            EggMultiplier eggMultiplier,
                            boolean caterpillarSounds,
                            boolean butterflySounds,
                            List<Trait> traits,
                            String baseVariant,
                            String coldVariant,
                            String mateVariant,
                            String warmVariant) {

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
        HILLS,
        ICE,
        JUNGLES,
        NETHER,
        PLAINS,
        PLATEAUS,
        SAVANNAS,
        VILLAGES,
        WETLANDS
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
        TINY,
        SMALL,
        MEDIUM,
        LARGE,
        HUGE
    }

    // Represents the speed of a butterfly.
    public enum Speed {
        SLOW,
        MODERATE,
        FAST
    }

    // The various traits butterflies can have.
    public enum Trait {
        CATFRIEND,
        CHRISTMASSY,
        GLOW,
        HUMMINGBIRD,
        ICY,
        INEDIBLE,
        MIMICRY,
        MOTHWANDERER,
        POISONOUS,
        LAVA
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
                         List<Habitat> habitats,
                         int eggLifespan,
                         int caterpillarLifespan,
                         int chrysalisLifespan,
                         int butterflyLifespan,
                         ResourceLocation preferredFlower,
                         ButterflyType type,
                         Diurnality diurnality,
                         ExtraLandingBlocks extraLandingBlocks,
                         PlantEffect plantEffect,
                         EggMultiplier eggMultiplier,
                         boolean caterpillarSounds,
                         boolean butterflySounds,
                         List<Trait> traits,
                         String baseVariant,
                         String coldVariant,
                         String mateVariant,
                         String warmVariant) {
        this.butterflyIndex = butterflyIndex;
        this.entityId = entityId;
        this.size = size;
        this.speed = speed;
        this.rarity = rarity;
        this.habitats = habitats;

        this.eggLifespan = eggLifespan;
        this.caterpillarLifespan = caterpillarLifespan * 2;
        this.chrysalisLifespan = chrysalisLifespan;
        this.butterflyLifespan = butterflyLifespan == Integer.MAX_VALUE ? Integer.MAX_VALUE : butterflyLifespan * 2;

        this.preferredFlower = preferredFlower;
        
        this.type = type;
        this.diurnality = diurnality;
        this.extraLandingBlocks = extraLandingBlocks;
        this.plantEffect = plantEffect;

        this.eggMultiplier = eggMultiplier;

        this.caterpillarSounds = caterpillarSounds;
        this.butterflySounds = butterflySounds;

        this.traits = traits;

        this.baseVariant = baseVariant;
        this.coldVariant = coldVariant;
        this.mateVariant = mateVariant;
        this.warmVariant = warmVariant;
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
         * @throws IllegalArgumentException Unused
         */
        @Override
        public ButterflyData deserialize(JsonElement json,
                                         Type typeOfT,
                                         JsonDeserializationContext context) throws IllegalArgumentException {
            ButterflyData entry = null;

            if (json instanceof final JsonObject object) {
                int index = object.get("index").getAsInt();
                String entityId = object.get("entityId").getAsString();

                Size size = getEnumValue(object, Size.class, "size", Size.MEDIUM);
                Speed speed = getEnumValue(object, Speed.class, "speed", Speed.MODERATE);
                Rarity rarity = getEnumValue(object, Rarity.class, "rarity", Rarity.COMMON);

                List<Habitat> habitats = getEnumCollection(object, Habitat.class, "habitats");

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

                EggMultiplier eggMultiplier = getEnumValue(object, EggMultiplier.class, "eggMultiplier", EggMultiplier.NORMAL);

                JsonObject sounds = object.get("sounds").getAsJsonObject();
                boolean caterpillarSounds = sounds.get("caterpillar").getAsBoolean();
                boolean butterflySounds = sounds.get("butterfly").getAsBoolean();

                List<Trait> traits = getEnumCollection(object, Trait.class, "traits");

                String baseVariant = entityId;
                String coldVariant = entityId;
                String mateVariant = entityId;
                String warmVariant = entityId;
                JsonElement variantElement = object.get("variants");
                if (variantElement != null) {
                    JsonObject variants = variantElement.getAsJsonObject();

                    JsonElement baseElement = variants.get("base");
                    if (baseElement != null) {
                        baseVariant = baseElement.getAsString();
                    }

                    JsonElement coldElement = variants.get("cold");
                    if (coldElement != null) {
                        coldVariant = coldElement.getAsString();
                    }

                    JsonElement mateElement = variants.get("mate");
                    if (mateElement != null) {
                        mateVariant = mateElement.getAsString();
                    }

                    JsonElement warmElement = variants.get("warm");
                    if (warmElement != null) {
                        warmVariant = warmElement.getAsString();
                    }
                }

                entry = new ButterflyData(
                        index,
                        entityId,
                        size,
                        speed,
                        rarity,
                        habitats,
                        LIFESPAN[eggLifespan.getIndex()],
                        LIFESPAN[caterpillarLifespan.getIndex()],
                        LIFESPAN[chrysalisLifespan.getIndex()],
                        LIFESPAN[butterflyLifespan.getIndex()],
                        new ResourceLocation(preferredFlower),
                        type,
                        diurnality,
                        extraLandingBlocks,
                        plantEffect,
                        eggMultiplier,
                        caterpillarSounds,
                        butterflySounds,
                        traits,
                        baseVariant,
                        coldVariant,
                        mateVariant,
                        warmVariant
                );
            }

            return entry;
        }

        /**
         * Helper method for pulling out a collection of enumerated values.
         * @param object The JSON object to read the value from.
         * @param enumeration The enumerated type to extract.
         * @param key The key to look for.
         * @return A value of the enumerated type.
         * @param <T> (Inferred) The type of the enumeration.
         */
        private static <T extends Enum<?>> List<T> getEnumCollection(
                JsonObject object,
                Class<T> enumeration,
                String key
        ) {
            JsonArray jsonData = object.get(key).getAsJsonArray();
            List<T> result = new ArrayList<>();
            for (int i = 0; i < jsonData.size(); ++i) {
                try {
                    T value = EnumExtensions.searchEnum(enumeration, jsonData.get(i).getAsString());
                    result.add(value);
                } catch (IllegalArgumentException e) {

                    // The value specified is invalid, so make sure it's written to the log.
                    LogUtils.getLogger().error("Invalid [{}]([{}]) specified on [{}]",
                            key,
                            jsonData.get(i).getAsString(),
                            object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown");
                }
            }

            return result;
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
            // Check the key exists in the JSON object.
            JsonElement element = object.get(key);
            if (element == null) {
                LogUtils.getLogger().error("Element [{}] missing from [{}]",
                        key,
                        object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown");

                return fallback;
            }

            String value = element.getAsString();
            try {
                return EnumExtensions.searchEnum(enumeration, value);
            } catch (IllegalArgumentException e) {

                // The value specified is invalid, so make sure it's written to the log.
                LogUtils.getLogger().error("Invalid type specified on [{}] for [{}] of type [{}]:[{}]",
                        object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown",
                        key,
                        enumeration,
                        value);

                return fallback;
            }
        }
    }

    /**
     * Create new butterfly data.
     * @param entry The butterfly data.
     */
    public static void addButterfly(ButterflyData entry)
            throws DataFormatException {
        if (ENTITY_ID_TO_INDEX_MAP.containsKey(entry.entityId)) {
            String message = String.format("Butterfly Data Entry for entity [%s] already exists.", entry.entityId);
            throw new DataFormatException(message);
        }

        if (BUTTERFLY_ENTRIES.containsKey(entry.butterflyIndex)) {
            String message = String.format("Butterfly Data Entry for index [%d] already exists.", entry.butterflyIndex);
            throw new DataFormatException(message);
        }

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
     * Accessor to help get butterfly data when needed.
     * @return A valid butterfly data entry.
     */
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
     * Returns a collection of formatted components ready to render as text.
     * @param butterflyIndex The butterfly index.
     * @return Formatted, localised text.
     */
    public static FormattedText getFormattedButterflyData(int butterflyIndex) {
        ButterflyData entry = ButterflyData.getEntry(butterflyIndex);
        if (entry != null) {
            //  Butterfly name
            MutableComponent component = Component.translatable("entity.butterflies." + entry.entityId());

            if (entry.type() == ButterflyData.ButterflyType.SPECIAL) {
                component.withStyle(ChatFormatting.DARK_BLUE);
            }

            // Rarity
            component.append("\n\n");
            component.append(Component.translatable("gui.butterflies.rarity"));
            switch (entry.rarity()) {
                case RARE -> component.append(Component.translatable("gui.butterflies.rarity.rare"));
                case UNCOMMON -> component.append(Component.translatable("gui.butterflies.rarity.uncommon"));
                case COMMON -> component.append(Component.translatable("gui.butterflies.rarity.common"));
                default -> {
                }
            }

            // Size
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.size"));
            switch (entry.size()) {
                case TINY -> component.append(Component.translatable("gui.butterflies.size.tiny"));
                case SMALL -> component.append(Component.translatable("gui.butterflies.size.small"));
                case MEDIUM -> component.append(Component.translatable("gui.butterflies.size.medium"));
                case LARGE -> component.append(Component.translatable("gui.butterflies.size.large"));
                case HUGE -> component.append(Component.translatable("gui.butterflies.size.huge"));
                default -> {
                }
            }

            // Speed
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.speed"));
            switch (entry.speed()) {
                case SLOW -> component.append(Component.translatable("gui.butterflies.speed.slow"));
                case MODERATE -> component.append(Component.translatable("gui.butterflies.speed.moderate"));
                case FAST -> component.append(Component.translatable("gui.butterflies.speed.fast"));
                default -> {
                }
            }

            // Lifespan
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.lifespan"));
            switch (entry.getOverallLifeSpan()) {
                case SHORT -> component.append(Component.translatable("gui.butterflies.lifespan.short"));
                case MEDIUM -> component.append(Component.translatable("gui.butterflies.lifespan.average"));
                case LONG -> component.append(Component.translatable("gui.butterflies.lifespan.long"));
                case IMMORTAL -> component.append(Component.translatable("gui.butterflies.lifespan.immortal"));
                default -> {
                }
            }

            // Habitat
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.habitat"));

            // If there are no habitats we still need a string.
            if (entry.habitats().isEmpty()) {
                component.append(Component.translatable("gui.butterflies.habitat.none"));
            }

            // When this flag is true we add commas
            boolean comma = false;
            for (Habitat habitat : entry.habitats()) {
                if (comma) {
                    component.append(Component.translatable("gui.butterflies.habitat.comma"));
                }

                switch (habitat) {
                    case FORESTS -> component.append(Component.translatable("gui.butterflies.habitat.forests"));
                    case HILLS -> component.append(Component.translatable("gui.butterflies.habitat.hills"));
                    case JUNGLES -> component.append(Component.translatable("gui.butterflies.habitat.jungles"));
                    case PLAINS -> component.append(Component.translatable("gui.butterflies.habitat.plains"));
                    case ICE -> component.append(Component.translatable("gui.butterflies.habitat.ice"));
                    case NETHER -> component.append(Component.translatable("gui.butterflies.habitat.nether"));
                    case PLATEAUS -> component.append(Component.translatable("gui.butterflies.habitat.plateaus"));
                    case SAVANNAS -> component.append(Component.translatable("gui.butterflies.habitat.savannas"));
                    case WETLANDS -> component.append(Component.translatable("gui.butterflies.habitat.wetlands"));
                    case VILLAGES -> component.append(Component.translatable("gui.butterflies.habitat.villages"));
                    default -> {
                    }
                }

                // If there is more than one habitat, create a comma-separated list.
                comma = true;
            }

            // Preferred Flower
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.preferred_flower"));

            Item value = ForgeRegistries.ITEMS.getValue(entry.preferredFlower());
            if (value != null) {
                Component description = value.getDescription();
                component.append(description);
            }

            // Fact
            component.append("\n\n");
            component.append(Component.translatable("gui.butterflies.fact." + entry.entityId()));

            return component;
        }

        return null;
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
     * Load the butterfly data.
     * @param resourceManager The resource manager to use for loading.
     */
    public static void load(ResourceManager resourceManager) {
        ButterflyData.reset();

        Gson gson = new GsonBuilder().registerTypeAdapter(ButterflyData.class, new ButterflyData.Serializer()).create();

        // Get the butterfly JSON files
        Map<ResourceLocation, Resource> resourceMap =
                resourceManager.listResources("butterfly_data", (x) -> x.getPath().endsWith(".json"));

        // Parse each one and generate the data.
        for (ResourceLocation location : resourceMap.keySet()) {
            try {
                Resource resource = resourceMap.get(location);
                BufferedReader reader = resource.openAsReader();
                ButterflyData butterflyData = gson.fromJson(reader, ButterflyData.class);
                ButterflyData.addButterfly(butterflyData);
            } catch (DataFormatException | IOException e) {
                LogUtils.getLogger().error("Failed to load butterfly data.", e);
            }
        }
    }

    /**
     * Resets the butterfly data to its unloaded state.
     */
    public static void reset() {
        ButterflyData.BUTTERFLY_ENTRIES.clear();
        ButterflyData.ENTITY_ID_TO_INDEX_MAP.clear();
        ButterflyData.NUM_BUTTERFLIES = 0;
        ButterflyData.NUM_MOTHS = 0;
    }

    /**
     * Gets the resource location for the caterpillar item.
     * @return The resource location of the caterpillar item.
     */
    public ResourceLocation getCaterpillarItem() {
        if (this.entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, "caterpillar_" + this.entityId);
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly egg item.
     * @return The resource location of the butterfly egg.
     */
    public ResourceLocation getButterflyEggItem() {
        if (this.entityId != null) {
            return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + "_egg");
        }

        return null;
    }

    /**
     * Gets the resource location for the butterfly entity.
     * @return The resource location of the butterfly.
     */
    public ResourceLocation getButterflyEntity() {
        return new ResourceLocation(ButterfliesMod.MOD_ID, this.entityId);
    }

    /**
     * Gets the resource location for the butterfly egg at the specified index.
     * @return The resource location of the butterfly egg.
     */
    public ResourceLocation getButterflyEggEntity() {
        return new ResourceLocation(ButterfliesMod.MOD_ID, this.entityId + "_egg");
    }

    /**
     * Gets the resource location for the caterpillar at the specified index.
     * @return The resource location of the caterpillar.
     */
    public ResourceLocation getCaterpillarEntity() {
        return new ResourceLocation(ButterfliesMod.MOD_ID, this.entityId + "_caterpillar");
    }

    /**
     * Gets the resource location for the chrysalis at the specified index.
     * @return The resource location of the chrysalis.
     */
    public  ResourceLocation getChrysalisEntity() {
        return new ResourceLocation(ButterfliesMod.MOD_ID, this.entityId + "_chrysalis");
    }

    /**
     * Returns the butterfly index of the butterfly's base variant.
     * @return The index of the butterfly to try and mate with.
     */
    public int getBaseButterflyIndex() {
        int index = getButterflyIndex(this.baseVariant);
        if (index < 0) {
            index = this.butterflyIndex;
        }

        return index;
    }

    /**
     * Returns the butterfly index of the butterfly's cold variant.
     * @return The index of the butterfly to try and mate with.
     */
    public int getColdButterflyIndex() {
        return getButterflyIndex(this.coldVariant);
    }

    /**
     * Returns the butterfly index of the butterfly's mate.
     * @return The index of the butterfly to try and mate with.
     */
    public int getMateButterflyIndex() {
        return getButterflyIndex(this.mateVariant);
    }

    /**
     * Returns the butterfly index of the butterfly's warm variant.
     * @return The index of the butterfly to try and mate with.
     */
    public int getWarmButterflyIndex() {
        return getButterflyIndex(this.warmVariant);
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
            case TINY -> {
                return 0.5f;
            }
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
     * Check if a butterfly has a specific trait.
     * @param trait The trait we are looking for.
     * @return TRUE if the butterfly has the trait, FALSE otherwise.
     */
    public boolean hasTrait(Trait trait) {
        return traits.contains(trait);
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
