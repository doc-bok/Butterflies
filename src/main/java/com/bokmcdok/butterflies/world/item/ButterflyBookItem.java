package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.client.gui.screens.ButterflyBookScreen;
import com.bokmcdok.butterflies.registries.DataComponentRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ButterflyBookItem extends Item {

    public static final String NAME = "butterfly_book";

    // The data component registry
    private final DataComponentRegistry dataComponentRegistry;

    /**
     * Add a new page to the butterfly book.
     * @param newBook The book being crafted.
     * @param index   The butterfly index.
     * @return True if a new page was added.
     */
    public static boolean addPage(DataComponentRegistry dataComponentRegistry,
                                  @NotNull ItemStack newBook,
                                  int index) {

        boolean result = false;

        List<Integer> newPages = newBook.getOrDefault(dataComponentRegistry.getButterflyBookPages(), new ArrayList<>());


        if (!newPages.contains(index)) {
            newPages.add(index);
            result = true;
        }

        // Calculate the actual number of butterflies in the book.
        int numButterflies = 0;
        int numMoths = 0;
        for (int i : newPages) {
            ButterflyData data = ButterflyData.getEntry(i);
            if (data != null) {
                if (data.type() == ButterflyData.ButterflyType.BUTTERFLY) {
                    numButterflies += 1;
                } else if (data.type() == ButterflyData.ButterflyType.MOTH) {
                    numMoths += 1;
                }
            }
        }

        newBook.set(dataComponentRegistry.getButterflyBookPages(), newPages);

        int customModelData = 0;
        if (numButterflies >= ButterflyData.getNumButterflySpecies()) {
            customModelData += 1;
        }

        if (numMoths >= ButterflyData.getNumMothSpecies()) {
            customModelData += 2;
        }

        newBook.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(customModelData));

        return result;
    }

    /**
     * Construction
     */
    public ButterflyBookItem(DataComponentRegistry dataComponentRegistry) {
        super(new Item.Properties().stacksTo(1));
        this.dataComponentRegistry = dataComponentRegistry;
    }

    /**
     * Adds some helper text that tells us what butterfly is in the net (if any).
     *
     * @param stack       The item stack.
     * @param context     The context for the tooltip.
     * @param components  The current text components.
     * @param tooltipFlag Is this a tooltip?
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull Item.TooltipContext context,
                                @NotNull List<Component> components,
                                @NotNull TooltipFlag tooltipFlag) {

        String localisation = "tooltip.butterflies.pages";

        List<Integer> pages = stack.getOrDefault(dataComponentRegistry.getButterflyBookPages(), new ArrayList<>());
        int numPages = 2 * pages.size();

        MutableComponent newComponent = Component.translatable(localisation, numPages);
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);

        super.appendHoverText(stack, context, components, tooltipFlag);
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
        ItemStack itemStack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            openScreen(itemStack);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    /**
     * Open the screen. Kept separate, so it can be excluded from server builds.
     * @param book The book to display.
     */
    @OnlyIn(Dist.CLIENT)
    private void openScreen(ItemStack book) {
        Minecraft.getInstance().setScreen(new ButterflyBookScreen(dataComponentRegistry, book));
    }
}
