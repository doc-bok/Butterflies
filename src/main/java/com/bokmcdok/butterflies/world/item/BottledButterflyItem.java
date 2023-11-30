package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.block.entity.ButterflyBlockEntity;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a bottled butterfly held in a player's hand.
 */
public class BottledButterflyItem extends BlockItem implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static final String NAME = "bottled_butterfly";

    /**
     * Construction
     * @param properties The properties of the item.
     */
    public BottledButterflyItem(Properties properties) {
        super(BlockRegistry.BOTTLED_BUTTERFLY_BLOCK.get(), properties);
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
     * Right-clicking with a full bottle will release the butterfly.
     * @param level The current level.
     * @param player The player holding the net.
     * @param hand The player's hand.
     * @return The result of the action, if any.
     */
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level level,
                                                  @NotNull Player player,
                                                  @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(CompoundTagId.ENTITY_ID)) {
            String entityId = tag.getString(CompoundTagId.ENTITY_ID);
            ResourceLocation location = new ResourceLocation(entityId);

            //  Move the target position slightly in front of the player
            Vec3 lookAngle = player.getLookAngle();
            BlockPos positionToSpawn = player.blockPosition().offset(
                    (int) lookAngle.x,
                    (int) lookAngle.y + 1,
                    (int) lookAngle.z);

            Butterfly.spawn(player.level(), location, positionToSpawn, false);
        }

        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));

        return InteractionResultHolder.success(stack);
    }

    /**
     * Placing the item will create an in-world bottle with a butterfly inside.
     * @param context The context in which the block is being placed.
     * @return The interaction result.
     */
    @Override
    @NotNull
    public InteractionResult place(@NotNull BlockPlaceContext context) {
        String entityId = null;
        Player player = context.getPlayer();
        if (player != null) {
            ItemStack stack = player.getItemInHand(context.getHand());
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains(CompoundTagId.ENTITY_ID)) {
                entityId = tag.getString(CompoundTagId.ENTITY_ID);
            }
        }

        InteractionResult result = super.place(context);

        if (result == InteractionResult.CONSUME && entityId != null) {
            Level level = context.getLevel();
            BlockPos position = context.getClickedPos();
            ResourceLocation location = new ResourceLocation(entityId);

            Butterfly.spawn(player.level(), location, position, true);

            BlockEntity blockEntity = level.getBlockEntity(position);
            if (blockEntity instanceof ButterflyBlockEntity butterflyBlockEntity) {
                butterflyBlockEntity.setEntityLocation(location);
            }
        }

        return result;
    }
}
