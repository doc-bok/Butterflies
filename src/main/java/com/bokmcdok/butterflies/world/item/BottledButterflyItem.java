package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.DataComponentRegistry;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a bottled butterfly held in a player's hand.
 */
public class BottledButterflyItem extends BlockItem implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "bottled_butterfly_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    private static final String NAME = "block.butterflies.bottled_butterfly";

    // The data component registry
    private final DataComponentRegistry dataComponentRegistry;

    //  The index of the butterfly species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param properties The item's properties.
     * @param dataComponentRegistry The data component registry.
     * @param block The block related to this item.
     * @param butterflyIndex The index of the butterfly species.
     */
    public BottledButterflyItem(Properties properties,
                                DataComponentRegistry dataComponentRegistry,
                                DeferredHolder<Block, Block> block,
                                int butterflyIndex) {
        super(block.get(), properties);

        this.butterflyIndex = butterflyIndex;
        this.dataComponentRegistry = dataComponentRegistry;
    }

    /**
     * Adds some helper text.
     * @param stack The item stack.
     * @param context The context of the tooltip.
     * @param tooltipComponents The current text components.
     * @param tooltipFlag Is this a tooltip?
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull Item.TooltipContext context,
                                @NotNull List<Component> tooltipComponents,
                                @NotNull TooltipFlag tooltipFlag) {
        appendButterflyNameToHoverText(dataComponentRegistry, stack, tooltipComponents);

        MutableComponent newComponent = Component.translatable("tooltip.butterflies.release_butterfly");
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                .withItalic(true);
        newComponent.setStyle(style);
        tooltipComponents.add(newComponent);

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    /**
     * Get the butterfly index.
     * @return The butterfly index.
     */
    @Override
    public int getButterflyIndex() {
        return this.butterflyIndex;
    }

    /**
     * Overridden so we can use a single localisation string for all instances.
     * @param itemStack The stack to get the name for.
     * @return The description ID, which is a reference to the localisation
     *         string.
     */
    @NotNull
    @Override
    public Component getName(@NotNull ItemStack itemStack) {
        return Component.translatable(NAME);
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
    public InteractionResult use(@NotNull Level level,
                                 @NotNull Player player,
                                 @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        ResourceLocation entity = getButterflyEntity(dataComponentRegistry, stack);
        if (entity != null) {

            //  Move the target position slightly in front of the player
            Vec3 lookAngle = player.getLookAngle();
            BlockPos positionToSpawn = player.blockPosition().offset(
                    (int) lookAngle.x,
                    (int) lookAngle.y + 1,
                    (int) lookAngle.z);

            Butterfly.spawn(player.level(), entity, positionToSpawn, false);
        }

        ItemStack newItemStack = new ItemStack(Items.GLASS_BOTTLE);
        player.setItemInHand(hand, newItemStack);
        return InteractionResult.SUCCESS.heldItemTransformedTo(newItemStack);
    }

    /**
     * Placing the item will create an in-world bottle with a butterfly inside.
     * @param context The context in which the block is being placed.
     * @return The interaction result.
     */
    @Override
    @NotNull
    public InteractionResult place(@NotNull BlockPlaceContext context) {

        InteractionResult result = super.place(context);
        if (result == InteractionResult.SUCCESS) {

            Player player = context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getItemInHand(context.getHand());
                ResourceLocation entity = getButterflyEntity(dataComponentRegistry,stack);

                if (entity != null) {
                    BlockPos position = context.getClickedPos();
                    Butterfly.spawn(player.level(), entity, position, true);
                }
            }
        }

        return result;
    }
}
