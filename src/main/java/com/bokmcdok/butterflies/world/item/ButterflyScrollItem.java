package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyScrollScreen;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ButterflyScrollItem extends Item implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static final String ADMIRAL_NAME = "butterfly_scroll_admiral";
    public static final String BUCKEYE_NAME = "butterfly_scroll_buckeye";
    public static final String CABBAGE_NAME = "butterfly_scroll_cabbage";
    public static final String CHALKHILL_NAME = "butterfly_scroll_chalkhill";
    public static final String CLIPPER_NAME = "butterfly_scroll_clipper";
    public static final String COMMON_NAME = "butterfly_scroll_common";
    public static final String EMPEROR_NAME = "butterfly_scroll_emperor";
    public static final String FORESTER_NAME = "butterfly_scroll_forester";
    public static final String GLASSWING_NAME = "butterfly_scroll_glasswing";
    public static final String HAIRSTREAK_NAME = "butterfly_scroll_hairstreak";
    public static final String HEATH_NAME = "butterfly_scroll_heath";
    public static final String LONGWING_NAME = "butterfly_scroll_longwing";
    public static final String MONARCH_NAME = "butterfly_scroll_monarch";
    public static final String MORPHO_NAME = "butterfly_scroll_morpho";
    public static final String RAINBOW_NAME = "butterfly_scroll_rainbow";
    public static final String SWALLOWTAIL_NAME = "butterfly_scroll_swallowtail";
    public static final String PEACOCK_NAME = "butterfly_scroll_peacock";

    //  TODO: Remove in future version.
    public static final String NAME = "item.butterflies.butterfly_scroll";

    //  The index of the butterfly species.
    private final int butterflyIndex;
    
    /**
     * Construction
     */
    public ButterflyScrollItem(int butterflyIndex) {
        super(new Item.Properties());

        this.butterflyIndex = butterflyIndex;
    }

    /**
     * Adds some helper text that tells us what butterfly is on the page (if any).
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

        MutableComponent newComponent = Component.translatable("tooltip.butterflies.scroll");
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);

        super.appendHoverText(stack, level, components, tooltipFlag);
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
     * Open the GUI when the item is used.
     * @param level The current level.
     * @param player The current player.
     * @param hand The hand holding the item.
     * @return The interaction result (always SUCCESS).
     */
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level,
                                                  Player player,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            CompoundTag tag = itemstack.getTag();
            if (tag != null && tag.contains(CompoundTagId.CUSTOM_MODEL_DATA)) {
                openScreen(tag.getInt(CompoundTagId.CUSTOM_MODEL_DATA));
            } else if (getButterflyIndex() >= 0) {
                openScreen(getButterflyIndex());
            } else {
                replaceWithPaper(player, hand, itemstack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    /**
     * Places the butterfly scroll on a block.
     * @param context Contains information about the block the user clicked on.
     * @return The result of the interaction.
     */
    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {

            BlockPos clickedPos = context.getClickedPos();
            Direction clickedFace = context.getClickedFace();
            BlockPos blockPos = clickedPos.relative(clickedFace);
            ItemStack itemInHand = context.getItemInHand();

            if (!this.mayPlace(player, clickedFace, itemInHand, blockPos)) {
                return InteractionResult.FAIL;
            } else {
                Level level = context.getLevel();
                int butterflyIndex = getButterflyIndex();
                CompoundTag tag = itemInHand.getTag();

                if (butterflyIndex < 0) {
                    if (tag != null && tag.contains(CompoundTagId.CUSTOM_MODEL_DATA)) {
                        butterflyIndex = tag.getInt(CompoundTagId.CUSTOM_MODEL_DATA);
                    }
                }

                if (butterflyIndex >= 0) {
                    ButterflyScroll butterflyScroll = new ButterflyScroll(level, blockPos, clickedFace);

                    butterflyScroll.setButterflyIndex(butterflyIndex);

                    if (butterflyScroll.survives()) {
                        if (!level.isClientSide) {
                            butterflyScroll.playPlacementSound();
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, butterflyScroll.position());
                            level.addFreshEntity(butterflyScroll);
                        }

                        itemInHand.shrink(1);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                } else {
                    replaceWithPaper(player, context.getHand(), itemInHand);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }

        return super.useOn(context);
    }

    /**
     * Determine whether a player can place an item.
     * @param player The player.
     * @param direction The direction the block is facing.
     * @param itemStack The item to place.
     * @param blockPos The position of the block.
     * @return Whether the item can be placed.
     */
    protected boolean mayPlace(Player player, Direction direction, ItemStack itemStack, BlockPos blockPos) {
        return !direction.getAxis().isVertical() && player.mayUseItemAt(blockPos, direction, itemStack);
    }

    /**
     * Open the screen. Kept separate, so it can be excluded from server builds.
     * @param butterflyIndex The index of the butterfly.
     */
    @OnlyIn(Dist.CLIENT)
    private void openScreen(int butterflyIndex) {
        Minecraft.getInstance().setScreen(new ButterflyScrollScreen(butterflyIndex));
    }

    /**
     * For replacing empty scrolls with paper.
     * @param player The interacting player.
     * @param hand The hand holding the scrolls.
     * @param stack The scroll item stack.
     */
    private void replaceWithPaper(@NotNull Player player,
                                  @NotNull InteractionHand hand,
                                  ItemStack stack) {
        player.setItemInHand(hand, new ItemStack(Items.PAPER, stack.getCount()));
        player.addItem(new ItemStack(Items.IRON_NUGGET, stack.getCount()));
    }
}
