package com.bokmcdok.butterflies.butterfly_data;

import com.bokmcdok.butterflies.lang.EnumExtensions;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.zip.DataFormatException;

public class ButterflyDataLoader {

    // Constants representing the base life spans of each butterfly cycle.
    private static final int[] LIFESPAN = {
            24000 * 2,
            24000 * 4,
            24000 * 7,
            ButterflyData.IMMORTAL_LIFESPAN
    };

    /**
     * Load the butterfly data.
     * @param resourceManager The resource manager to use for loading.
     */
    public static void load(ResourceManager resourceManager) {
        ButterflyRegistry.reset();

        Gson gson = new GsonBuilder().registerTypeAdapter(ButterflyData.class, new Serializer()).create();

        // Get the butterfly JSON files
        Map<ResourceLocation, Resource> resourceMap =
                resourceManager.listResources("butterfly_data", (x) -> x.getPath().endsWith(".json"));

        // Parse each one and generate the data.
        for (ResourceLocation location : resourceMap.keySet()) {
            try {
                Resource resource = resourceMap.get(location);
                BufferedReader reader = resource.openAsReader();
                ButterflyData butterflyData = gson.fromJson(reader, ButterflyData.class);
                ButterflyRegistry.addButterfly(butterflyData);
            } catch (DataFormatException | IOException e) {
                LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Failed to load butterfly data.", e);
            }
        }
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
                int index = require(object, "index").getAsInt();
                String entityId = require(object, "entityId").getAsString();

                ButterflySize size = getEnumValue(object, ButterflySize.class, "size", ButterflySize.MEDIUM);
                ButterflySpeed speed = getEnumValue(object, ButterflySpeed.class, "speed", ButterflySpeed.MODERATE);
                ButterflyRarity rarity = getEnumValue(object, ButterflyRarity.class, "rarity", ButterflyRarity.COMMON);

                Set<ButterflyHabitat> habitats = getEnumCollection(object, ButterflyHabitat.class, "habitats");

                JsonObject lifespan = getOptionalObject(object, "lifespan");
                ButterflyLifespan eggLifespan = getEnumValue(lifespan, ButterflyLifespan.class, "egg", ButterflyLifespan.MEDIUM);
                ButterflyLifespan caterpillarLifespan = getEnumValue(lifespan, ButterflyLifespan.class, "caterpillar", ButterflyLifespan.MEDIUM);
                ButterflyLifespan chrysalisLifespan = getEnumValue(lifespan, ButterflyLifespan.class, "chrysalis", ButterflyLifespan.MEDIUM);
                ButterflyLifespan butterflyLifespan = getEnumValue(lifespan, ButterflyLifespan.class, "butterfly", ButterflyLifespan.MEDIUM);

                String foodBlock;
                String foodItem;

                // For backwards compatibility allow "foodSource" to be used.
                if (object.has("foodSource")) {
                    foodBlock = object.get("foodSource").getAsString();
                    foodItem = object.get("foodSource").getAsString();
                } else {
                    foodBlock = require(object, "foodBlock").getAsString();
                    foodItem = require(object, "foodItem").getAsString();
                }

                ButterflyType type = getEnumValue(object, ButterflyType.class, "type", ButterflyType.BUTTERFLY);
                Diurnality diurnality = getEnumValue(object, Diurnality.class, "diurnality", Diurnality.DIURNAL);
                Set<String> extraLandingBlocks = getStringCollection(object, "extraLandingBlocks");
                PlantEffect plantEffect = getEnumValue(object, PlantEffect.class, "plantEffect", PlantEffect.NONE);

                EggMultiplier eggMultiplier = getEnumValue(object, EggMultiplier.class, "eggMultiplier", EggMultiplier.NORMAL);

                JsonObject sounds = getOptionalObject(object, "sounds");
                boolean caterpillarSounds = false;
                boolean butterflySounds = false;

                JsonElement caterpillarElem = sounds.get("caterpillar");
                if (caterpillarElem != null && caterpillarElem.isJsonPrimitive()) {
                    caterpillarSounds = caterpillarElem.getAsBoolean();
                }

                JsonElement butterflyElem = sounds.get("butterfly");
                if (butterflyElem != null && butterflyElem.isJsonPrimitive()) {
                    butterflySounds = butterflyElem.getAsBoolean();
                }

                Set<ButterflyTrait> traits = getEnumCollection(object, ButterflyTrait.class, "traits");

                JsonElement variantElement = getOptionalObject(object, "variants");
                JsonObject variants = variantElement.getAsJsonObject();
                String baseVariant = getOptionalString(variants, "base");
                String coldVariant = getOptionalString(variants, "cold");
                String mateVariant = getOptionalString(variants, "mate");
                String warmVariant = getOptionalString(variants, "warm");
                String agedVariant = getOptionalString(variants, "aged");

                entry = new ButterflyData.Builder(
                        index,
                        entityId,
                        size,
                        speed,
                        rarity,
                        habitats,
                        LIFESPAN[eggLifespan.getIndex()],
                        LIFESPAN[caterpillarLifespan.getIndex()],
                        LIFESPAN[chrysalisLifespan.getIndex()],
                        LIFESPAN[butterflyLifespan.getIndex()] == ButterflyData.IMMORTAL_LIFESPAN ?
                                ButterflyData.IMMORTAL_LIFESPAN : LIFESPAN[butterflyLifespan.getIndex()] * 2,
                        ResourceLocation.tryParse(foodBlock),
                        ResourceLocation.tryParse(foodItem),
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
                        warmVariant,
                        agedVariant
                ).build();
            }

            return entry;
        }

        /**
         * Helper method to get an optional object.
         * @param parent The parent object to get the value from.
         * @param key The key for the value.
         * @return A valid object.
         */
        private static JsonObject getOptionalObject(JsonObject parent, String key) {
            JsonElement e = parent.get(key);
            return e != null && e.isJsonObject() ? e.getAsJsonObject() : new JsonObject();
        }

        /**
         * Helper method to get an optional array.
         * @param parent The parent object to get the value from.
         * @param key The key for the value.
         * @return A valid array.
         */
        private static JsonArray getOptionalArray(JsonObject parent, String key) {
            JsonElement e = parent.get(key);
            return e != null && e.isJsonArray() ? e.getAsJsonArray() : new JsonArray();
        }

        /**
         * Helper method to get an optional string.
         * @param parent The parent object to get the value from.
         * @param key The key for the value.
         * @return A valid string.
         */
        private static String getOptionalString(JsonObject parent, String key) {
            JsonElement e = parent.get(key);
            return e != null && e.isJsonPrimitive() ? e.getAsString() : "";
        }

        /**
         * Helper method to enforce a required element.
         * @param parent The parent object to get the value from.
         * @param key The key for the value.
         * @return The required element, throws if it is missing.
         */
        private static JsonElement require(JsonObject parent, String key) {
            JsonElement e = parent.get(key);
            if (e == null || e.isJsonNull()) {
                String id = parent.has("entityId") ? parent.get("entityId").getAsString() : "unknown";
                throw new JsonParseException("Missing required field [" + key + "] for [" + id + "]");
            }
            return e;
        }

        /**
         * Helper method for pulling out a collection of enumerated values.
         * @param object The JSON object to read the value from.
         * @param enumeration The enumerated type to extract.
         * @param key The key to look for.
         * @return A value of the enumerated type.
         * @param <T> (Inferred) The type of the enumeration.
         */
        private static <T extends Enum<?>> Set<T> getEnumCollection(
                JsonObject object,
                Class<T> enumeration,
                String key
        ) {
            JsonArray jsonData = getOptionalArray(object, key);
            Set<T> result = new HashSet<>();
            for (int i = 0; i < jsonData.size(); ++i) {
                JsonElement element = jsonData.get(i);
                if (!element.isJsonPrimitive()) {
                    LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Non-primitive enum value for [{}] in [{}]", key,
                            object.has("entityId") ? object.get("entityId").getAsString() : "unknown");
                    continue;
                }

                try {
                    T value = EnumExtensions.searchEnum(enumeration, element.getAsString());
                    result.add(value);
                } catch (IllegalArgumentException e) {

                    // The value specified is invalid, so make sure it's written to the log.
                    LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Invalid [{}]([{}]) specified on [{}]",
                            key,
                            jsonData.get(i).getAsString(),
                            object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown");
                }
            }

            return result;
        }

        /**
         * Helper method for pulling out a collection of strings.
         * @param object The JSON object to read the value from.
         * @param key The key to look for.
         * @return A value of the enumerated type.
         */
        private static Set<String> getStringCollection(JsonObject object,
                                                       String key) {
            JsonArray jsonData = getOptionalArray(object, key);
            Set<String> result = new HashSet<>();
            for (int i = 0; i < jsonData.size(); ++i) {
                JsonElement element = jsonData.get(i);
                if (!element.isJsonPrimitive()) {
                    LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Non-primitive string value for [{}] in [{}]", key,
                            object.has("entityId") ? object.get("entityId").getAsString() : "unknown");
                    continue;
                }

                try {
                    String value = element.getAsString();
                    result.add(value);
                } catch (IllegalArgumentException e) {

                    // The value specified is invalid, so make sure it's written to the log.
                    LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Invalid [{}]([{}]) specified on [{}]",
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
            if (element == null || element.isJsonNull()) {
                LogUtils.getLogger().error("Element [{}] missing from [{}]",
                        key,
                        object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown");

                return fallback;
            }

            if (!element.isJsonPrimitive()) {
                LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Non-primitive value for [{}] in [{}], using fallback [{}]",
                        key,
                        object.has("entityId") ? object.get("entityId").getAsString() : "unknown",
                        fallback);
                return fallback;
            }

            String value = element.getAsString();
            try {
                return EnumExtensions.searchEnum(enumeration, value);
            } catch (IllegalArgumentException e) {

                // The value specified is invalid, so make sure it's written to the log.
                LogUtils.getLogger().error("[BUTTERFLY_DATA_LOADER] Invalid type specified on [{}] for [{}] of type [{}]:[{}]",
                        object.get("entityId") != null ? object.get("entityId").getAsString() : "unknown",
                        key,
                        enumeration,
                        value);

                return fallback;
            }
        }
    }

    /**
     * Stream codec for syncing butterfly data.
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, ButterflyData> STREAM_CODEC = new StreamCodec<>() {

        /**
         * Decode a data stream to an object.
         * @param buffer The message buffer.
         * @return A new butterfly object.
         */
        @NotNull
        @Override
        public ButterflyData decode(RegistryFriendlyByteBuf buffer) {
            return new ButterflyData.Builder(buffer.readInt(),
                    buffer.readUtf(),
                    buffer.readEnum(ButterflySize.class),
                    buffer.readEnum(ButterflySpeed.class),
                    buffer.readEnum(ButterflyRarity.class),
                    buffer.readEnumSet(ButterflyHabitat.class),
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readResourceLocation(),
                    buffer.readResourceLocation(),
                    buffer.readEnum(ButterflyType.class),
                    buffer.readEnum(Diurnality.class),
                    buffer.readCollection(FriendlyByteBuf.limitValue(HashSet::new, 4), FriendlyByteBuf::readUtf),
                    buffer.readEnum(PlantEffect.class),
                    buffer.readEnum(EggMultiplier.class),
                    buffer.readBoolean(),
                    buffer.readBoolean(),
                    buffer.readEnumSet(ButterflyTrait.class),
                    buffer.readUtf(),
                    buffer.readUtf(),
                    buffer.readUtf(),
                    buffer.readUtf(),
                    buffer.readUtf()).build();
        }

        /**
         * Encode some data to a buffer.
         * @param buffer The message buffer.
         * @param data The data to encode.
         */
        @Override
        public void encode(RegistryFriendlyByteBuf buffer,
                           ButterflyData data) {
            buffer.writeInt(data.butterflyIndex());
            buffer.writeUtf(data.speciesId().value());
            buffer.writeEnum(data.size());
            buffer.writeEnum(data.speed());
            buffer.writeEnum(data.rarity());
            buffer.writeEnumSet(data.habitats(), ButterflyHabitat.class);
            buffer.writeInt(data.eggLifespan());
            buffer.writeInt(data.caterpillarLifespan());
            buffer.writeInt(data.chrysalisLifespan());
            buffer.writeInt(data.butterflyLifespan());
            buffer.writeResourceLocation(data.foodBlock());
            buffer.writeResourceLocation(data.foodItem());
            buffer.writeEnum(data.type());
            buffer.writeEnum(data.diurnality());
            buffer.writeCollection(data.extraLandingBlocks(), FriendlyByteBuf::writeUtf);
            buffer.writeEnum(data.plantEffect());
            buffer.writeEnum(data.eggMultiplier());
            buffer.writeBoolean(data.caterpillarSounds());
            buffer.writeBoolean(data.butterflySounds());
            buffer.writeEnumSet(data.traits(), ButterflyTrait.class);
            buffer.writeUtf(data.baseVariant().value());
            buffer.writeUtf(data.coldVariant().value());
            buffer.writeUtf(data.mateVariant().value());
            buffer.writeUtf(data.warmVariant().value());
            buffer.writeUtf(data.agedVariant().value());
        }
    };
}
