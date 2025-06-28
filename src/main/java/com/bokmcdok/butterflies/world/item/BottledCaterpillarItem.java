package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
        return "bottled_caterpillar_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    //  The localisation strings.
    public static final String BOTTLED_CATERPILLAR_STRING = "block.butterflies.bottled_caterpillar";
    public static final String BOTTLED_LARVA_STRING = "block.butterflies.bottled_larva";

    // The butterfly index for this species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param block The block to place in the world.
     * @param butterflyIndex The butterfly index of the species.
     */
    public BottledCaterpillarItem(RegistryObject<Block> block,
                                  int butterflyIndex) {
        super(block.get(), new Item.Properties().stacksTo(1));

        this.butterflyIndex = butterflyIndex;
    }

    /**
     * Adds some helper text.
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
        ButterflyData data = ButterflyData.getEntry(this.butterflyIndex);
        if (data != null) {
            ResourceLocation caterpillarEntity = data.getCaterpillarEntity();
            String translatable = "entity." + caterpillarEntity.toString().replace(':', '.');

            MutableComponent speciesComponent = Component.translatable(translatable);
            Style speciesStyle = speciesComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                    .withItalic(true);
            speciesComponent.setStyle(speciesStyle);
            components.add(speciesComponent);

            MutableComponent tooltipComponent = Component.translatable("tooltip.butterflies.release_caterpillar");
            Style tooltipStyle = tooltipComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                    .withItalic(true);
            tooltipComponent.setStyle(tooltipStyle);
            components.add(tooltipComponent);

            super.appendHoverText(stack, level, components, tooltipFlag);
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
        ButterflyData data = ButterflyData.getEntry(butterflyIndex);
        if (data != null && data.type() == ButterflyData.ButterflyType.MOTH) {
            return Component.translatable(BOTTLED_LARVA_STRING);
        } else {
            return Component.translatable(BOTTLED_CATERPILLAR_STRING);
        }
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
            Item caterpillarItem = ForgeRegistries.ITEMS.getValue(location);
            if (caterpillarItem != null) {
                ItemStack caterpillarStack = new ItemStack(caterpillarItem, 1);
                player.addItem(caterpillarStack);
            }

            player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.fail(stack);
    }
}
