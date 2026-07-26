package com.bokmcdok.butterflies.common.data;

import net.minecraft.advancements.Advancement;

/**
 * Holds an already built action.
 * @param definition The definition of the action.
 * @param oneHolder The single item advancement.
 */
public record BuiltAction(ActionDefinition definition,
                          Advancement oneHolder) {
}
