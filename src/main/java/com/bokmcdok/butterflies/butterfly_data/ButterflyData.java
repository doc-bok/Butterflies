package com.bokmcdok.butterflies.butterfly_data;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.regex.Pattern;

import static net.minecraft.world.level.Level.TICKS_PER_DAY;

/**
 * Helper for converting entity ID to index and vice versa.
 */
public final class ButterflyData { 
    private final int butterflyIndex;
    private final String entityId;
    private final ButterflySize size;
    private final ButterflySpeed speed;
    private final ButterflyRarity rarity;
    private final Set<ButterflyHabitat> habitats;
    private final int eggLifespan;
    private final int caterpillarLifespan;
    private final int chrysalisLifespan;
    private final int butterflyLifespan;
    private final ResourceLocation foodBlock;
    private final ResourceLocation foodItem;
    private final ButterflyType type;
    private final Diurnality diurnality;
    private final ExtraLandingBlocks extraLandingBlocks;
    private final PlantEffect plantEffect;
    private final EggMultiplier eggMultiplier;
    private final boolean caterpillarSounds;
    private final boolean butterflySounds;
    private final Set<ButterflyTrait> traits;
    private final String baseVariant;
    private final String coldVariant;
    private final String mateVariant;
    private final String warmVariant;
    private final String agedVariant;

    private final ResourceLocation butterflyEntity;
    private final ResourceLocation butterflyEggEntity;
    private final ResourceLocation caterpillarEntity;
    private final ResourceLocation chrysalisEntity;
    private final ResourceLocation caterpillarItem;
    private final ResourceLocation butterflyEggItem;
    private final ResourceLocation scrollTexture;

    private final ButterflyLifespan overallLifespan;

    private final float sizeMultiplier;

    public static final int IMMORTAL_LIFESPAN = Integer.MAX_VALUE;
    private static final Pattern ENTITY_ID_PATTERN = Pattern.compile("[a-z0-9._-]+");

    /**
     * Construction
     * @param butterflyIndex      The index of the butterfly
     * @param entityId            The butterfly species
     * @param size                The size of the butterfly
     * @param speed               The speed of the butterfly
     * @param rarity              How rare the butterfly is
     * @param habitats            A list of the butterflies habitats
     * @param eggLifespan         The lifespan of the egg phase
     * @param caterpillarLifespan The lifespan of the caterpillar phase
     * @param chrysalisLifespan   The lifespan of the chrysalis phase
     * @param butterflyLifespan   The lifespan of the butterfly phase
     * @param foodBlock           The block this butterfly considers food
     * @param foodItem            The item this butterfly considers food
     * @param type                The type of butterfly
     * @param diurnality          The sleeping pattern of the butterfly
     * @param extraLandingBlocks  The extra blocks the butterfly can land on
     * @param plantEffect         The effect the butterfly has on its food
     * @param eggMultiplier       Multiplies the amount of eggs the butterfly has
     * @param caterpillarSounds   The sounds the caterpillar makes
     * @param butterflySounds     The sounds the butterfly makes
     * @param traits              The traits of the butterfly
     * @param baseVariant         The base variant of the butterfly
     * @param coldVariant         The cold variant of the butterfly
     * @param mateVariant         The mate variant of the butterfly
     * @param warmVariant         The warm variant of the butterfly
     * @param agedVariant         The aged variant of the butterfly
     */
    public ButterflyData(int butterflyIndex,
                         String entityId,
                         ButterflySize size,
                         ButterflySpeed speed,
                         ButterflyRarity rarity,
                         Set<ButterflyHabitat> habitats,
                         int eggLifespan,
                         int caterpillarLifespan,
                         int chrysalisLifespan,
                         int butterflyLifespan,
                         ResourceLocation foodBlock,
                         ResourceLocation foodItem,
                         ButterflyType type,
                         Diurnality diurnality,
                         ExtraLandingBlocks extraLandingBlocks,
                         PlantEffect plantEffect,
                         EggMultiplier eggMultiplier,
                         boolean caterpillarSounds,
                         boolean butterflySounds,
                         Set<ButterflyTrait> traits,
                         String baseVariant,
                         String coldVariant,
                         String mateVariant,
                         String warmVariant,
                         String agedVariant) {
        this.butterflyIndex = validateIndex(butterflyIndex);

        this.entityId = requireValidEntityId(entityId);

        this.size = Objects.requireNonNull(size, "size must not be null");
        this.speed = Objects.requireNonNull(speed, "speed must not be null");
        this.rarity = Objects.requireNonNull(rarity, "rarity must not be null");
        this.foodBlock = Objects.requireNonNull(foodBlock, "foodBlock must not be null");
        this.foodItem = Objects.requireNonNull(foodItem, "foodItem must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.diurnality = Objects.requireNonNull(diurnality, "diurnality must not be null");
        this.extraLandingBlocks = Objects.requireNonNull(extraLandingBlocks, "extraLandingBlocks must not be null");
        this.plantEffect = Objects.requireNonNull(plantEffect, "plantEffect must not be null");
        this.eggMultiplier = Objects.requireNonNull(eggMultiplier, "eggMultiplier must not be null");

        validateLifeCycle(eggLifespan, caterpillarLifespan, chrysalisLifespan, butterflyLifespan);

        this.eggLifespan = validateLifespan(eggLifespan, "eggLifespan");
        this.caterpillarLifespan = validateLifespan(caterpillarLifespan, "caterpillarLifespan");
        this.chrysalisLifespan = validateLifespan(chrysalisLifespan, "chrysalisLifespan");
        this.butterflyLifespan = validateLifespan(butterflyLifespan, "butterflyLifespan");

        this.habitats = Set.copyOf(Objects.requireNonNull(
                habitats,
                "habitats must not be null"
        ));

        this.traits = Set.copyOf(Objects.requireNonNull(
                traits,
                "traits must not be null"
        ));

        this.baseVariant = normalizeVariant(baseVariant, entityId);
        this.coldVariant = normalizeVariant(coldVariant, entityId);
        this.mateVariant = normalizeVariant(mateVariant, entityId);
        this.warmVariant = normalizeVariant(warmVariant, entityId);
        this.agedVariant = normalizeVariant(agedVariant, entityId);

        this.caterpillarSounds = caterpillarSounds;
        this.butterflySounds = butterflySounds;

        this.butterflyEntity = item(entityId);
        this.butterflyEggEntity = entity("_egg");
        this.caterpillarEntity = entity("_caterpillar");
        this.chrysalisEntity = entity("_chrysalis");
        this.caterpillarItem = item("caterpillar_" + entityId);
        this.butterflyEggItem = entity("_egg");
        this.scrollTexture = item("textures/gui/butterfly_scroll/" + entityId + ".png");

        if (butterflyLifespan == IMMORTAL_LIFESPAN) {
            this.overallLifespan = ButterflyLifespan.IMMORTAL;
        } else {

            long totalTicks = (long) eggLifespan + caterpillarLifespan + chrysalisLifespan + butterflyLifespan;
            long days = totalTicks / TICKS_PER_DAY;
            if (days < 18) {
                this.overallLifespan = ButterflyLifespan.SHORT;
            } else if (days < 30) {
                this.overallLifespan = ButterflyLifespan.MEDIUM;
            } else {
                this.overallLifespan = ButterflyLifespan.LONG;
            }
        }

        this.sizeMultiplier = switch (size) {
            case TINY -> 0.5f;
            case SMALL -> 0.7f;
            case LARGE -> 1.28f;
            case HUGE -> 1.5f;
            default ->1.0f;
        };
    }

    /**
     * Check if a butterfly has a specific trait.
     * @param trait The trait we are looking for.
     * @return TRUE if the butterfly has the trait, FALSE otherwise.
     */
    public boolean hasTrait(ButterflyTrait trait) {
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
            case OBSIDIAN -> blockState.is(Blocks.OBSIDIAN);
            default -> false;
        };
    }

    //***
    // Accessor Methods
    //***

    public int butterflyIndex() {
        return butterflyIndex;
    }

    public String entityId() {
        return entityId;
    }

    public ButterflySize size() {
        return size;
    }

    public ButterflySpeed speed() {
        return speed;
    }

    public ButterflyRarity rarity() {
        return rarity;
    }

    public Set<ButterflyHabitat> habitats() {
        return habitats;
    }

    public int eggLifespan() {
        return eggLifespan;
    }

    public int caterpillarLifespan() {
        return caterpillarLifespan;
    }

    public int chrysalisLifespan() {
        return chrysalisLifespan;
    }

    public int butterflyLifespan() {
        return butterflyLifespan;
    }

    public ResourceLocation foodBlock() {
        return foodBlock;
    }

    public ResourceLocation foodItem() {
        return foodItem;
    }

    public ButterflyType type() {
        return type;
    }

    public Diurnality diurnality() {
        return diurnality;
    }

    public ExtraLandingBlocks extraLandingBlocks() {
        return extraLandingBlocks;
    }

    public PlantEffect plantEffect() {
        return plantEffect;
    }

    public EggMultiplier eggMultiplier() {
        return eggMultiplier;
    }

    public boolean caterpillarSounds() {
        return caterpillarSounds;
    }

    public boolean butterflySounds() {
        return butterflySounds;
    }

    public Set<ButterflyTrait> traits() {
        return traits;
    }

    public String baseVariant() {
        return baseVariant;
    }

    public String coldVariant() {
        return coldVariant;
    }

    public String mateVariant() {
        return mateVariant;
    }

    public String warmVariant() {
        return warmVariant;
    }

    public ButterflyLifespan getOverallLifeSpan() {
        return overallLifespan;
    }

    public ResourceLocation getCaterpillarItem() {
        return caterpillarItem;
    }

    public ResourceLocation getButterflyEggItem() {
        return butterflyEggItem;
    }

    public ResourceLocation getButterflyEntity() {
        return butterflyEntity;
    }

    public ResourceLocation getButterflyEggEntity() {
        return butterflyEggEntity;
    }

    public ResourceLocation getCaterpillarEntity() {
        return caterpillarEntity;
    }

    public  ResourceLocation getChrysalisEntity() {
        return chrysalisEntity;
    }

    public int getAgedButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.agedVariant);
    }

    public int getBaseButterflyIndex() {
        int index = ButterflyRegistry.getButterflyIndex(this.baseVariant);
        if (index < 0) {
            index = this.butterflyIndex;
        }

        return index;
    }

    public int getColdButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.coldVariant);
    }

    public int getMateButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.mateVariant);
    }

    public int getWarmButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.warmVariant);
    }

    public ResourceLocation getScrollTexture() {
        return scrollTexture;
    }

    public float getSizeMultiplier() {
        return sizeMultiplier;
    }

    //***
    // Helper methods for ResourceLocations
    //***

    private ResourceLocation item(String path) {
        return new ResourceLocation(ButterfliesMod.MOD_ID, path);
    }

    private ResourceLocation entity(String suffix) {
        return new ResourceLocation(ButterfliesMod.MOD_ID, entityId + suffix
        );
    }

    //***
    // Validation methods
    //***

    private static String normalizeVariant(String variant,
                                           String fallbackEntityId) {
        if (variant == null) {
            return fallbackEntityId;
        }

        String normalized = variant.strip();

        if (normalized.isBlank()) {
            return fallbackEntityId;
        }

        if (!ENTITY_ID_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(
                    "Invalid butterfly variant ID: " + variant
            );
        }

        return normalized;
    }

    private static String requireNonBlank(String value) {
        Objects.requireNonNull(value, "entityId must not be null");

        String normalized = value.strip();

        if (normalized.isBlank()) {
            throw new IllegalArgumentException(
                    "entityId must not be blank"
            );
        }

        return normalized;
    }

    private static String requireValidEntityId(String entityId) {
        String normalized = requireNonBlank(entityId);

        if (!ENTITY_ID_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(
                    "entityId contains invalid characters: " + entityId
            );
        }

        return normalized;
    }

    private static int validateIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException(
                    "butterflyIndex must be >= 0, but was " + index
            );
        }

        return index;
    }

    private static int validateLifespan(int lifespan, String fieldName) {
        if (lifespan < 0) {
            throw new IllegalArgumentException(
                    fieldName + " must be >= 0 or IMMORTAL_LIFESPAN, but was " + lifespan
            );
        }

        return lifespan;
    }

    private static void validateLifeCycle(
            int eggLifespan,
            int caterpillarLifespan,
            int chrysalisLifespan,
            int butterflyLifespan
    ) {
        if (eggLifespan == IMMORTAL_LIFESPAN
                && caterpillarLifespan != IMMORTAL_LIFESPAN) {
            throw new IllegalArgumentException(
                    "An immortal egg phase cannot be followed by a finite caterpillar phase"
            );
        }

        if (caterpillarLifespan == IMMORTAL_LIFESPAN
                && chrysalisLifespan != IMMORTAL_LIFESPAN) {
            throw new IllegalArgumentException(
                    "An immortal caterpillar phase cannot be followed by a finite chrysalis phase"
            );
        }

        if (chrysalisLifespan == IMMORTAL_LIFESPAN
                && butterflyLifespan != IMMORTAL_LIFESPAN) {
            throw new IllegalArgumentException(
                    "An immortal chrysalis phase cannot be followed by a finite butterfly phase"
            );
        }
    }
}
