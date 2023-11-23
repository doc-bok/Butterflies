package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Holds code common to all butterfly container items.
 */
public interface ButterflyContainerItem {

    /**
     * Adds some helper text that tells us what butterfly is in the net (if any).
     * @param stack The item stack.
     * @param components The current text components.
     */
    default void appendButterflyNameToHoverText(@NotNull ItemStack stack,
                                                @NotNull List<Component> components) {
        String translatable = "item.butterflies.empty";
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(CompoundTagId.ENTITY_ID)) {
            String entityId = tag.getString(CompoundTagId.ENTITY_ID);
            if (StringUtils.isNotBlank(entityId)) {
                translatable = "item." + entityId.replace(':', '.');
            }
        }

        MutableComponent newComponent = Component.translatable(translatable);
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);
    }
}
