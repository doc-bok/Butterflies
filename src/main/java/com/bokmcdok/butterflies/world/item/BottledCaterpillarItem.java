package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.world.ButterflyData;
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
    public static final String ADMIRAL_NAME = "bottled_caterpillar_admiral";
    public static final String BUCKEYE_NAME = "bottled_caterpillar_buckeye";
    public static final String CABBAGE_NAME = "bottled_caterpillar_cabbage";
    public static final String CHALKHILL_NAME = "bottled_caterpillar_chalkhill";
    public static final String CLIPPER_NAME = "bottled_caterpillar_clipper";
    public static final String COMMON_NAME = "bottled_caterpillar_common";
    public static final String EMPEROR_NAME = "bottled_caterpillar_emperor";
    public static final String FORESTER_NAME = "bottled_caterpillar_forester";
    public static final String GLASSWING_NAME = "bottled_caterpillar_glasswing";
    public static final String HAIRSTREAK_NAME = "bottled_caterpillar_hairstreak";
    public static final String HEATH_NAME = "bottled_caterpillar_heath";
    public static final String LONGWING_NAME = "bottled_caterpillar_longwing";
    public static final String MONARCH_NAME = "bottled_caterpillar_monarch";
    public static final String MORPHO_NAME = "bottled_caterpillar_morpho";
    public static final String RAINBOW_NAME = "bottled_caterpillar_rainbow";
    public static final String SWALLOWTAIL_NAME = "bottled_caterpillar_swallowtail";
    public static final String PEACOCK_NAME = "bottled_caterpillar_peacock";

    private static final String NAME = "block.butterflies.bottled_caterpillar";

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
        ResourceLocation caterpillarEntity = ButterflyData.indexToCaterpillarEntity(this.butterflyIndex);
        if (caterpillarEntity != null) {
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
            Caterpillar.spawn((ServerLevel)context.getLevel(),
                    ButterflyData.indexToCaterpillarEntity(this.butterflyIndex),
                    context.getClickedPos(),
                    Direction.DOWN, true);
        }

        return result;
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
        ResourceLocation location = ButterflyData.indexToCaterpillarItem(this.butterflyIndex);
        Item caterpillarItem = ForgeRegistries.ITEMS.getValue(location);
        if (caterpillarItem != null) {
            ItemStack caterpillarStack = new ItemStack(caterpillarItem, 1);
            player.addItem(caterpillarStack);
        }

        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));

        return InteractionResultHolder.success(stack);
    }
}
