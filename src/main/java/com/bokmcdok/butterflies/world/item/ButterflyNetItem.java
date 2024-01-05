package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * An item that allows players to catch butterflies.
 */
public class ButterflyNetItem extends Item implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static final String NAME = "butterfly_net";

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
        appendButterflyNameToHoverText(stack, components);
        super.appendHoverText(stack, level, components, tooltipFlag);
    }

    /**
     * Get the item that remains when we use this for crafting.
     * @param itemStack The current ItemStack.
     * @return An empty butterfly net.
     */
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.BUTTERFLY_NET.get());
    }

    /**
     * Let Minecraft know we don't lose the item if we use it to craft.
     * @return Always TRUE.
     */
    @Override
    @SuppressWarnings("deprecation")
    public boolean hasCraftingRemainingItem() {
        return true;
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
            if (!tag.contains(CompoundTagId.CUSTOM_MODEL_DATA) ||
                !tag.contains(CompoundTagId.ENTITY_ID)) {

                tag.putInt(CompoundTagId.CUSTOM_MODEL_DATA,1);
                tag.putString(CompoundTagId.ENTITY_ID, Objects.requireNonNull(entity.getEncodeId()));
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
        if (tag.contains(CompoundTagId.ENTITY_ID)) {
            String entityId = tag.getString(CompoundTagId.ENTITY_ID);

            //  Move the target position slightly in front of the player
            Vec3 lookAngle = player.getLookAngle();
            BlockPos positionToSpawn = player.blockPosition().offset(
                        (int) lookAngle.x,
                        (int) lookAngle.y + 1,
                        (int) lookAngle.z);

            Butterfly.spawn(player.getLevel(), new ResourceLocation(entityId), positionToSpawn, false);
            tag.remove(CompoundTagId.CUSTOM_MODEL_DATA);
            tag.remove(CompoundTagId.ENTITY_ID);

            return InteractionResultHolder.success(stack);
        }

        return super.use(level, player, hand);
    }
}
