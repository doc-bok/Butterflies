package com.bokmcdok.butterflies.common.data;

import net.minecraft.advancements.Advancement;

/**
 * A definition for a specific advancement action.
 * @param oneId The ID of the single item action.
 * @param allId The ID of the all item action.
 * @param oneBuilder The builder for the single item action.
 * @param allBuilder The builder for the all item action.
 */
public record ActionDefinition(String oneId,
                               String allId,
                               Advancement.Builder oneBuilder,
                               Advancement.Builder allBuilder) {
}
