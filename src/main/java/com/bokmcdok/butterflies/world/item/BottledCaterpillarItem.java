package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a bottled butterfly held in a player's hand.
 */
public class BottledCaterpillarItem extends BlockItem {

    //  The name this item is registered under.
    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "bottled_caterpillar_" + ButterflySpeciesList.SPECIES[butterflyIndex];
    }

    private static final String NAME = "block.butterflies.bottled_caterpillar";

    // The butterfly index for this species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param block The block to place in the world.
     * @param butterflyIndex The butterfly index of the species.
     */
    public BottledCaterpillarItem(DeferredHolder<Block, Block> block,
                                  int butterflyIndex) {
        super(block.get(), new Item.Properties().stacksTo(1));

        this.butterflyIndex = butterflyIndex;
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
        ButterflyData data = ButterflyData.getEntry(this.butterflyIndex);
        if (data != null) {
            ResourceLocation caterpillarEntity = data.getCaterpillarEntity();
            String translatable = "entity." + caterpillarEntity.toString().replace(':', '.');

            MutableComponent speciesComponent = Component.translatable(translatable);
            Style speciesStyle = speciesComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                    .withItalic(true);
            speciesComponent.setStyle(speciesStyle);
            tooltipComponents.add(speciesComponent);

            MutableComponent tooltipComponent = Component.translatable("tooltip.butterflies.release_caterpillar");
            Style tooltipStyle = tooltipComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                    .withItalic(true);
            tooltipComponent.setStyle(tooltipStyle);
            tooltipComponents.add(tooltipComponent);

            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
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
     * Placing the item will create an in-world bottle with a butterfly inside.
     * @param context The context in which the block is being placed.
     * @return The interaction result.
     */
    @Override
    @NotNull
    public InteractionResult place(@NotNull BlockPlaceContext context) {

        InteractionResult result = super.place(context);
        if (result == InteractionResult.CONSUME) {
            ButterflyData data = ButterflyData.getEntry(this.butterflyIndex);
            if (data != null) {
                Caterpillar.spawn((ServerLevel) context.getLevel(),
                        data.getCaterpillarEntity(),
                        context.getClickedPos(),
                        Direction.DOWN, true);
            }
        }

        return result;
    }

    /**
     * Right-clicking with a full bottle will release the caterpillar.
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
        ButterflyData data = ButterflyData.getEntry(this.butterflyIndex);
        if (data != null) {
            ResourceLocation location = data.getCaterpillarItem();
            Item caterpillarItem = BuiltInRegistries.ITEM.get(location);
            ItemStack caterpillarStack = new ItemStack(caterpillarItem, 1);
            player.addItem(caterpillarStack);
        }

        return InteractionResultHolder.fail(stack);
    }
}
