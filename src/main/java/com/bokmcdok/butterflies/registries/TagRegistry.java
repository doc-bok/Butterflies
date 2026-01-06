package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BannerPattern;

/**
 * Holds all the tags used by the mod.
 */
public class TagRegistry {

    // The available tags for this mod.
    private final TagKey<BannerPattern> butterflyBannerPattern;
    private final TagKey<EntityType<?>> peacemakerEntities;

    /**
     * Create all the needed tags.
     */
    public TagRegistry() {
        this.butterflyBannerPattern = create(Registries.BANNER_PATTERN, "banner_pattern_butterfly");
        this.peacemakerEntities = create(Registries.ENTITY_TYPE, "peacemaker_entities");
    }

    /**
     * Accessor to the butterfly banner pattern tag key.
     * @return The tag key.
     */
    public TagKey<BannerPattern> getButterflyBannerPattern() {
        return butterflyBannerPattern;
    }

    /**
     * Accessor to the peacemaker entity tag key.
     * @return The tag key.
     */
    public TagKey<EntityType<?>> getPeacemakerEntities() {
        return peacemakerEntities;
    }

    /**
     * Create a new tag using the current mod's ID.
     * @param registry The registry to create a tag for.
     * @param tagName The name of the tag.
     * @return The new tag key.
     * @param <T> The type of the tag.
     */
    private <T> TagKey<T> create(ResourceKey<? extends Registry<T>> registry,
                                 String tagName) {
        return TagKey.create(
                registry,
                new ResourceLocation(ButterfliesMod.MOD_ID, tagName));
    }
}
