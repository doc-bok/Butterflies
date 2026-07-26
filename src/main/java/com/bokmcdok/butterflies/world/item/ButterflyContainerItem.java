package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
    default void appendButterflyNameToHoverText(@NotNull ItemStack stack,
                                                @NotNull List<Component> components) {
        String translatable = "item.butterflies.empty";
        ResourceLocation entity = getContainedButterflyEntityId(stack);

        if (entity != null) {
            translatable = "entity." + entity.toString().replace(':', '.');
        }

        MutableComponent newComponent = new TranslatableComponent(translatable);
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);
    }

    /**
     * Helper method to get the entity from an item stack
     * @param stack The item stack.
     * @return The entity held in this item, if any.
     */
    default ResourceLocation getContainedButterflyEntityId(ItemStack stack) {
        ResourceLocation entity = null;

        //  TODO: Compound tags are checked for backwards compatibility. This
        //        code should be removed in a future version.
        if (stack != null) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains(CompoundTagId.ENTITY_ID)) {
                String entityId = tag.getString(CompoundTagId.ENTITY_ID);
                entity = new ResourceLocation(entityId);
            }
        }

        if (entity == null) {
            ButterflyData data = ButterflyRegistry.getEntry(getButterflyIndex());
            if (data != null) {
                entity = data.getButterflyEntity();
            }
        }

        return entity;
    }

    /**
     * Helper method that gets a release position for the Butterfly.
     * @param player The player that is releasing the butterfly.
     * @return A position just in front of the player.
     */
    default BlockPos getReleasePosition(Player player) {
        Vec3 look = player.getLookAngle().normalize();
        Vec3 pos = player.position().add(look.scale(1.5D)).add(0.0D, 1.0D, 0.0D);
        return new BlockPos(pos);
    }

    /**
     * Releaseses a butterfly just in front of the player's position.
     * @param level The current level.
     * @param player The player holding the net.
     * @param hand The player's hand.
     * @param itemLeftInHand The item that will be left in the player's hand.
     * @return The interaction result of trying to release a butterfly.
     */
    default InteractionResultHolder<ItemStack> releaseButterfly(@NotNull Level level,
                                                                @NotNull Player player,
                                                                @NotNull InteractionHand hand,
                                                                Item itemLeftInHand) {
        ItemStack stack = player.getItemInHand(hand);
        ResourceLocation entityId = getContainedButterflyEntityId(stack);

        if (entityId == null) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide) {
            BlockPos spawnPos = getReleasePosition(player);
            Butterfly.spawnFree(level, entityId, spawnPos);
            player.setItemInHand(hand, new ItemStack(itemLeftInHand));
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    /**
     * Provides a style for helper tooltips.
     * @param key The localization key.
     * @return A new component with the helper style applied.
     */
    default Component helperTooltip(String key) {
        return Component.translatable(key)
                .withStyle(style -> style
                        .withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                        .withItalic(true));
    }
}
