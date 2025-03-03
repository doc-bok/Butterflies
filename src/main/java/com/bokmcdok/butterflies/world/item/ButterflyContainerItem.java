package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.DataComponentRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Holds code common to all butterfly container items.
 */
public interface ButterflyContainerItem {

    /**
     * Get the index for the species of butterfly related to this item.
     * @return The butterfly index.
     */
    int getButterflyIndex();

    /**
     * Adds some helper text that tells us what butterfly is in the net (if any).
     * @param stack The item stack.
     * @param components The current text components.
     */
    default void appendButterflyNameToHoverText(DataComponentRegistry dataComponentRegistry,
                                                @NotNull ItemStack stack,
                                                @NotNull List<Component> components) {
        String translatable = "item.butterflies.empty";
        ResourceLocation entity = getButterflyEntity(dataComponentRegistry, stack);

        if (entity != null) {
            translatable = "entity." + entity.toString().replace(':', '.');
        }

        MutableComponent newComponent = Component.translatable(translatable);
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);
    }

    /**
     * Helper method to get the entity from an item stack
     * @param dataComponentRegistry The data component registry.
     * @param stack The item stack.
     * @return The entity held in this item, if any.
     */
    default ResourceLocation getButterflyEntity(DataComponentRegistry dataComponentRegistry,
                                                ItemStack stack) {
        ResourceLocation entity = null;

        //  TODO: Compound tags are checked for backwards compatibility. This
        //        code should be removed in a future version.
        if (stack != null) {
            String entityId = stack.get(dataComponentRegistry.getButterflyEntityId());
            if (entityId != null) {
                entity = ResourceLocation.withDefaultNamespace(entityId);
            }
        }

        if (entity == null) {
            ButterflyData data = ButterflyData.getEntry(getButterflyIndex());
            if (data != null) {
                entity = data.getButterflyEntity();
            }
        }

        return entity;
    }
}
