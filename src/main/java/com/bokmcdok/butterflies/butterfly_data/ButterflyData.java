package com.bokmcdok.butterflies.butterfly_data;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

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
public record ButterflyData(int butterflyIndex,
                            String entityId,
                            ButterflySize size,
                            ButterflySpeed speed,
                            ButterflyRarity rarity,
                            List<ButterflyHabitat> habitats,
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
                            List<ButterflyTrait> traits,
                            String baseVariant,
                            String coldVariant,
                            String mateVariant,
                            String warmVariant,
                            String agedVariant) {

    /**
     * Construction
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
                         List<ButterflyHabitat> habitats,
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
                         List<ButterflyTrait> traits,
                         String baseVariant,
                         String coldVariant,
                         String mateVariant,
                         String warmVariant,
                         String agedVariant) {
        Objects.requireNonNull(entityId, "entityId must not be null");
        Objects.requireNonNull(size, "size must not be null");
        Objects.requireNonNull(speed, "speed must not be null");
        Objects.requireNonNull(rarity, "rarity must not be null");
        Objects.requireNonNull(habitats, "habitats must not be null");
        Objects.requireNonNull(foodBlock, "foodBlock must not be null");
        Objects.requireNonNull(foodItem, "foodItem must not be null");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(diurnality, "diurnality must not be null");
        Objects.requireNonNull(extraLandingBlocks, "extraLandingBlocks must not be null");
        Objects.requireNonNull(plantEffect, "plantEffect must not be null");
        Objects.requireNonNull(eggMultiplier, "eggMultiplier must not be null");
        Objects.requireNonNull(traits, "traits must not be null");

        this.butterflyIndex = butterflyIndex;
        this.entityId = entityId;
        this.size = size;
        this.speed = speed;
        this.rarity = rarity;

        this.habitats = List.copyOf(habitats);
        this.traits = List.copyOf(traits);

        this.eggLifespan = eggLifespan;
        this.caterpillarLifespan = caterpillarLifespan;
        this.chrysalisLifespan = chrysalisLifespan;
        this.butterflyLifespan = butterflyLifespan;

        this.foodBlock = foodBlock;
        this.foodItem = foodItem;

        this.type = type;
        this.diurnality = diurnality;
        this.extraLandingBlocks = extraLandingBlocks;
        this.plantEffect = plantEffect;

        this.eggMultiplier = eggMultiplier;

        this.caterpillarSounds = caterpillarSounds;
        this.butterflySounds = butterflySounds;

        this.baseVariant = normalizeVariant(baseVariant, entityId);
        this.coldVariant = normalizeVariant(coldVariant, entityId);
        this.mateVariant = normalizeVariant(mateVariant, entityId);
        this.warmVariant = normalizeVariant(warmVariant, entityId);
        this.agedVariant = normalizeVariant(agedVariant, entityId);
    }

    /**
     * Helper method to ensure variants aren't null.
     * @param variant The variant to use.
     * @param fallbackEntityId The fallback Entity if the variant is invalid.
     * @return A valid Entity ID.
     */
    private String normalizeVariant(String variant, String fallbackEntityId) {
        if (variant == null || variant.isEmpty()) {
            return fallbackEntityId;
        }
        return variant;
    }

    /**
     * Get the overall lifespan as a simple enumeration
     * @return A representation of the lifespan.
     */
    public ButterflyLifespan getOverallLifeSpan() {
        if (butterflyLifespan == Integer.MAX_VALUE) {
            return ButterflyLifespan.IMMORTAL;
        }

        int days = (eggLifespan + caterpillarLifespan + chrysalisLifespan + butterflyLifespan) / 24000;
        if (days < 18) {
            return ButterflyLifespan.SHORT;
        } else if (days < 30) {
            return ButterflyLifespan.MEDIUM;
        } else {
            return ButterflyLifespan.LONG;
        }
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
     * Returns the butterfly index of the butterfly's aged variant.
     * @return The index of the butterfly to age into.
     */
    public int getAgedButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.agedVariant);
    }

    /**
     * Returns the butterfly index of the butterfly's base variant.
     * @return The index of the butterfly to try and mate with.
     */
    public int getBaseButterflyIndex() {
        int index = ButterflyRegistry.getButterflyIndex(this.baseVariant);
        if (index < 0) {
            index = this.butterflyIndex;
        }

        return index;
    }

    /**
     * Returns the butterfly index of the butterfly's cold variant.
     * @return The index of the cold variant of the butterfly.
     */
    public int getColdButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.coldVariant);
    }

    /**
     * Returns the butterfly index of the butterfly's mate.
     * @return The index of the butterfly to try and mate with.
     */
    public int getMateButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.mateVariant);
    }

    /**
     * Returns the butterfly index of the butterfly's warm variant.
     * @return The index of the butterfly to try and mate with.
     */
    public int getWarmButterflyIndex() {
        return ButterflyRegistry.getButterflyIndex(this.warmVariant);
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
}
