package com.bokmcdok.butterflies.common.data;

import net.minecraft.advancements.Advancement;

/**
 * Holds a named advancement.
 * @param id The ID to use for localization.
 * @param builder The advancement builder.
 */
public record NamedAdvancement(String id,
                               Advancement.Builder builder) {
}
