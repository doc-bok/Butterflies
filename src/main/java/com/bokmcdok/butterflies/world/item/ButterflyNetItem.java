package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.entity.ambient.Butterfly;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ButterflyNetItem extends Item {

    public static final String NAME = "butterfly_net";
    public static final String ENTITY_ID = "EntityId";
    public static final String CUSTOM_MODEL_DATA = "CustomModelData";

    /**
     * Construction
     * @param properties The item properties.
     */
    public ButterflyNetItem(Properties properties) {
        super(properties);
    }

    /**
     * Adds some helper text that tells us what butterfly is in the net (if any).
     * @param stack The item stack.
     * @param level The current level.
     * @param components The current text components.
     * @param tooltipFlag Is this a tooltip?
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @Nullable Level level,
                                @NotNull List<Component> components,
                                @NotNull TooltipFlag tooltipFlag) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(ENTITY_ID)) {
            String translatable = "item." + tag.getString(ENTITY_ID).replace(':', '.');
            MutableComponent newComponent = Component.translatable(translatable);
            Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED)).withItalic(true);
            newComponent.setStyle(style);
            components.add(newComponent);
        }

        super.appendHoverText(stack, level, components, tooltipFlag);
    }

    /**
     * If we left-click on a butterfly with an empty net, the player will catch the butterfly.
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return TRUE if the left-click action is consumed.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof Butterfly) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains(CUSTOM_MODEL_DATA) ||
                !tag.contains(ENTITY_ID)) {

                tag.putInt(CUSTOM_MODEL_DATA,1);
                tag.putString(ENTITY_ID, Objects.requireNonNull(entity.getEncodeId()));
                entity.discard();

                player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);

                return true;
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    /**
     * Right-clicking with a full net will release the net.
     * @param level The current level.
     * @param player The player holding the net.
     * @param hand The player's hand.
     * @return The result of the action, if any.
     */
    @Override
    @NotNull
    public  InteractionResultHolder<ItemStack> use(@NotNull Level level,
                                                   @NotNull Player player,
                                                   @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("EntityId")) {
            String entityId = tag.getString("EntityId");
            Butterfly.release(player, entityId, player.blockPosition());
            tag.remove("CustomModelData");
            tag.remove("EntityId");

            return InteractionResultHolder.success(stack);
        }

        return super.use(level, player, hand);
    }
}
