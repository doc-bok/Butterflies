package com.bokmcdok.butterflies.common.data;

import net.minecraft.advancements.AdvancementHolder;

/**
 * Holds an already built action.
 * @param definition The definition of the action.
 * @param oneHolder The single item advancement.
 */
public record BuiltAction(ActionDefinition definition,
                          AdvancementHolder oneHolder) {
}
