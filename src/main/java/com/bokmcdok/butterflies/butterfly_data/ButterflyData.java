package com.bokmcdok.butterflies.butterfly_data;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

/**
 * Helper for converting entity ID to index and vice versa.
 */
public final class ButterflyData { 
    private final int butterflyIndex;
    private final SpeciesId speciesId;
    private final ButterflySize size;
    private final ButterflySpeed speed;
    private final ButterflyRarity rarity;
    private final EnumSet<ButterflyHabitat> habitats;
    private final ResourceLocation foodBlock;
    private final ResourceLocation foodItem;
    private final ButterflyType type;
    private final Diurnality diurnality;
    private final Set<String> extraLandingBlocks;
    private final PlantEffect plantEffect;
    private final EggMultiplier eggMultiplier;
    private final boolean caterpillarSounds;
    private final boolean butterflySounds;
    private final EnumSet<ButterflyTrait> traits;

    private final LifecycleData lifecycle;
    private final VariantSet variants;
    private final LandingRules landingRules;

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

    /**
     * Construction
     * @param builder The Butterfly Data Builder
     */
    public ButterflyData(Builder builder) {
        this.butterflyIndex = validateIndex(builder.butterflyIndex);

        this.speciesId = Objects.requireNonNull(builder.speciesId, "entityId must not be null");

        this.size = Objects.requireNonNull(builder.size, "size must not be null");
        this.speed = Objects.requireNonNull(builder.speed, "speed must not be null");
        this.rarity = Objects.requireNonNull(builder.rarity, "rarity must not be null");
        this.foodBlock = Objects.requireNonNull(builder.foodBlock, "foodBlock must not be null");
        this.foodItem = Objects.requireNonNull(builder.foodItem, "foodItem must not be null");
        this.type = Objects.requireNonNull(builder.type, "type must not be null");
        this.diurnality = Objects.requireNonNull(builder.diurnality, "diurnality must not be null");

        this.extraLandingBlocks = Set.copyOf(Objects.requireNonNull(
                builder.extraLandingBlocks,
                "extraLandingBlocks must not be null"
        ));

        this.plantEffect = Objects.requireNonNull(builder.plantEffect, "plantEffect must not be null");
        this.eggMultiplier = Objects.requireNonNull(builder.eggMultiplier, "eggMultiplier must not be null");

        validateLifeCycle(builder.eggLifespan,
                builder.caterpillarLifespan,
                builder.chrysalisLifespan,
                builder.butterflyLifespan);

        Objects.requireNonNull(builder.habitats, "habitats must not be null" );
        this.habitats = builder.habitats.isEmpty()
                ? EnumSet.noneOf(ButterflyHabitat.class)
                : EnumSet.copyOf(builder.habitats);

        Objects.requireNonNull(builder.traits, "traits must not be null");
        this.traits = builder.traits.isEmpty()
                ? EnumSet.noneOf(ButterflyTrait.class)
                : EnumSet.copyOf(builder.traits);

        this.caterpillarSounds = builder.caterpillarSounds;
        this.butterflySounds = builder.butterflySounds;

        this.lifecycle = new LifecycleData(
                builder.eggLifespan,
                builder.caterpillarLifespan,
                builder.chrysalisLifespan,
                builder.butterflyLifespan
        );

        this.overallLifespan = lifecycle.overallLifespan();

        this.variants = new VariantSet(
                builder.baseVariant,
                builder.coldVariant,
                builder.mateVariant,
                builder.warmVariant,
                builder.agedVariant
        );

        this.landingRules = new LandingRules(Set.copyOf(
                Objects.requireNonNull(builder.extraLandingBlocks, "extraLandingBlocks")
        ));

        this.butterflyEntity = item(builder.speciesId.value());
        this.butterflyEggEntity = entity("_egg");
        this.caterpillarEntity = entity("_caterpillar");
        this.chrysalisEntity = entity("_chrysalis");
        this.caterpillarItem = item("caterpillar_" + builder.speciesId.value());
        this.butterflyEggItem = entity("_egg");
        this.scrollTexture = item("textures/gui/butterfly_scroll/" + builder.speciesId.value() + ".png");

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
        return landingRules.isValidLandingBlock(blockState);
    }

    //***
    // Accessor Methods
    //***

    public int butterflyIndex() {
        return butterflyIndex;
    }

    public SpeciesId speciesId() {
        return speciesId;
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

    public EnumSet<ButterflyHabitat> habitats() {
        return habitats;
    }

    public int eggLifespan() {
        return lifecycle.eggLifespan();
    }

    public int caterpillarLifespan() {
        return lifecycle.caterpillarLifespan();
    }

    public int chrysalisLifespan() {
        return lifecycle.chrysalisLifespan();
    }

    public int butterflyLifespan() {
        return lifecycle.butterflyLifespan();
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

    public Set<String> extraLandingBlocks() {
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

    public EnumSet<ButterflyTrait> traits() {
        return traits;
    }

    public SpeciesId baseVariant() {
        return variants.baseVariant();
    }

    public SpeciesId coldVariant() {
        return variants.coldVariant();
    }

    public SpeciesId mateVariant() {
        return variants.mateVariant();
    }

    public SpeciesId warmVariant() {
        return variants.warmVariant();
    }

    public SpeciesId agedVariant() {
        return variants.agedVariant();
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
        return ButterflyRegistry.getButterflyIndex(variants.agedVariant().value());
    }

    public int getBaseButterflyIndex() {
        int index = ButterflyRegistry.getButterflyIndex(variants.baseVariant().value());
        if (index < 0) {
            index = this.butterflyIndex;
        }

        return index;
    }

    public int getColdButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(variants.coldVariant().value());
    }

    public int getMateButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(variants.mateVariant().value());
    }

    public int getWarmButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(variants.warmVariant().value());
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
        return new ResourceLocation(ButterfliesMod.MOD_ID, speciesId.withSuffix(suffix));
    }

    //***
    // Validation methods
    //***

    private static int validateIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException(
                    "butterflyIndex must be >= 0, but was " + index
            );
        }

        return index;
    }

    static void validateLifeCycle(
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

    /**
     * A helper class to build ButterflyData.
     */
    public static final class Builder {

        private final int butterflyIndex;
        private final SpeciesId speciesId;
        private final ButterflySize size;
        private final ButterflySpeed speed;
        private final ButterflyRarity rarity;
        private final ResourceLocation foodBlock;
        private final ResourceLocation foodItem;
        private final ButterflyType type;
        private final Diurnality diurnality;
        private final PlantEffect plantEffect;
        private final EggMultiplier eggMultiplier;

        private final Set<ButterflyHabitat> habitats;
        private final Set<String> extraLandingBlocks;
        private final Set<ButterflyTrait> traits;
        private final boolean caterpillarSounds;
        private final boolean butterflySounds;

        // Lifespan
        private final int eggLifespan;
        private final int caterpillarLifespan;
        private final int chrysalisLifespan;
        private final int butterflyLifespan;

        // Variants (default to entityId)
        private final SpeciesId baseVariant;
        private final SpeciesId coldVariant;
        private final SpeciesId mateVariant;
        private final SpeciesId warmVariant;
        private final SpeciesId agedVariant;

        /**
         * Construction.
         * @param butterflyIndex      The index of the butterfly
         * @param speciesId           The butterfly species
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
        public Builder(int butterflyIndex,
                       String speciesId,
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
                       Set<String> extraLandingBlocks,
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
            this.butterflyIndex = butterflyIndex;
            this.speciesId = new SpeciesId(speciesId);
            this.size = size;
            this.speed = speed;
            this.rarity = rarity;
            this.foodBlock = foodBlock;
            this.foodItem = foodItem;
            this.type = type;
            this.diurnality = diurnality;
            this.plantEffect = plantEffect;
            this.eggMultiplier = eggMultiplier;

            // Default variants to speciesId; will be normalized in constructor
            this.baseVariant = new SpeciesId(normalizeVariant(baseVariant, speciesId));
            this.coldVariant = new SpeciesId(normalizeVariant(coldVariant, speciesId));
            this.mateVariant = new SpeciesId(normalizeVariant(mateVariant, speciesId));
            this.warmVariant = new SpeciesId(normalizeVariant(warmVariant, speciesId));
            this.agedVariant = new SpeciesId(normalizeVariant(agedVariant, speciesId));

            this.habitats = habitats;

            this.eggLifespan = eggLifespan;
            this.caterpillarLifespan = caterpillarLifespan;
            this.chrysalisLifespan = chrysalisLifespan;
            this.butterflyLifespan = butterflyLifespan;

            this.extraLandingBlocks = extraLandingBlocks;

            this.caterpillarSounds = caterpillarSounds;
            this.butterflySounds = butterflySounds;
            this.traits = traits;
        }

        /**
         * Builds a new ButterflyData.
         * @return The ButterflyData object.
         */
        public ButterflyData build() {
            return new ButterflyData(this);
        }

        /**
         * Helper to ensure variant strings are valid.
         * @param variant The ID of the variant.
         * @param fallback The fallback if the variant ID is invalid.
         * @return A valid variant string.
         */
        private static String normalizeVariant(String variant,
                                               String fallback) {
            return variant == null || variant.isBlank() ? fallback : variant;
        }
    }
}
