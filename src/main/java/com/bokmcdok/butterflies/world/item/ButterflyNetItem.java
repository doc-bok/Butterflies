package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.monster.PeacemakerButterfly;
import net.minecraft.network.chat.Component;
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

/**
 * An item that allows players to catch butterflies.
 */
public class ButterflyNetItem extends Item implements ButterflyContainerItem {

    private static final String EMPTY_NET_HELP_TEXT = "tooltip.butterflies.butterfly_net";
    private static final String FULL_NET_HELP_TEXT = "tooltip.butterflies.release_butterfly";

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "butterfly_net_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    // The name this item is registered under.
    public static final String EMPTY_NAME = "butterfly_net";

    // The localization string ID for this item.
    private static final String NAME = "item.butterflies.butterfly_net";

    // The index of the butterfly species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param butterflyIndex The index of the butterfly species.
     */
    public ButterflyNetItem(int butterflyIndex) {
        super(new Item.Properties().stacksTo(1));

        this.butterflyIndex = butterflyIndex;
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
        components.add(helperTooltip(butterflyIndex < 0 ? EMPTY_NET_HELP_TEXT : FULL_NET_HELP_TEXT));
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
     * Get the item that remains when we use this for crafting.
     * @param itemStack The current ItemStack.
     * @return An empty butterfly net.
     */
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.EMPTY_BUTTERFLY_NET.get());
    }

    /**
     * Overridden so we can use a single localization string for all instances.
     * @param itemStack The stack to get the name for.
     * @return The description ID, which is a reference to the localization
     *         string.
     */
    @NotNull
    @Override
    public Component getName(@NotNull ItemStack itemStack) {
        return Component.translatable(NAME);
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
     * @param target The entity being attacked
     * @return TRUE if the left-click action is consumed.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack,
                                     Player player,
                                     Entity target) {
        if (!isEmptyNet(stack)) {
            return false;
        }

        ItemStack capturedNet = createCapturedNet(target);
        if (capturedNet.isEmpty()) {
            return false;
        }

        if (!player.level().isClientSide) {
            if (!(target instanceof Butterfly && capturedNet.is(ItemRegistry.BURNT_BUTTERFLY_NET.get()))) {
                target.discard();
            }

            player.setItemInHand(InteractionHand.MAIN_HAND, capturedNet);
            player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);
        }

        return true;
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
    public InteractionResultHolder<ItemStack> use(@NotNull Level level,
                                                  @NotNull Player player,
                                                  @NotNull InteractionHand hand) {
        return releaseButterfly(level, player, hand, ItemRegistry.EMPTY_BUTTERFLY_NET.get());
    }

    /**
     * Checks to see if this is an empty net.
     * @param stack The item stack with the item.
     * @return True if this is an empty net.
     */
    private static boolean isEmptyNet(ItemStack stack) {
        return stack.is(ItemRegistry.EMPTY_BUTTERFLY_NET.get());
    }

    /**
     * Gets the Butterfly Net that contains the specified target.
     * @param target The entity to try and capture.
     * @return An instance of a Butterfly Net if the target is valid.
     */
    private static ItemStack createCapturedNet(Entity target) {
        if (target instanceof PeacemakerButterfly) {
            return new ItemStack(ItemRegistry.PEACEMAKER_BUTTERFLY_NET.get());
        }

        if (target instanceof Butterfly butterfly) {
            var item = ItemRegistry.getButterflyNetFromIndex(butterfly.getButterflyIndex());
            if (item != null) {
                return new ItemStack(item.get());
            }
        }

        return ItemStack.EMPTY;
    }
}
