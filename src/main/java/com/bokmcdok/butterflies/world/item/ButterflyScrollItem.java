package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyScrollScreen;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
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

    public static final String NAME = "butterfly_scroll";
    
    /**
     * Construction
     */
    public ButterflyScrollItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
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
        super.appendHoverText(stack, level, components, tooltipFlag);
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
                CompoundTag tag = itemInHand.getTag();
                if (tag != null && tag.contains(CompoundTagId.CUSTOM_MODEL_DATA)) {
                    ButterflyScroll butterflyScroll = new ButterflyScroll(level, blockPos, clickedFace);

                    butterflyScroll.setButterflyIndex(tag.getInt(CompoundTagId.CUSTOM_MODEL_DATA));

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
     * Open the screen. Kept separate so it can be excluded from server builds.
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
