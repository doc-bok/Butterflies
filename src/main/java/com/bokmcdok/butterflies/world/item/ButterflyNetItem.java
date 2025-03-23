package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.registries.DataComponentRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflySpeciesList;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An item that allows players to catch butterflies.
 */
public class ButterflyNetItem extends Item implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "butterfly_net_" + ButterflySpeciesList.SPECIES[butterflyIndex];
    }

    // The name this item is registered under.
    public static final String EMPTY_NAME = "butterfly_net";

    // The localisation string ID for this item.
    private static final String NAME = "item.butterflies.butterfly_net";

    // Reference to the data component registry.
    private final DataComponentRegistry dataComponentRegistry;

    // Reference to the item registry.
    private final ItemRegistry itemRegistry;

    // The index of the butterfly species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param properties The item's properties.
     * @param dataComponentRegistry The data component registry.
     * @param itemRegistry The item registry.
     * @param butterflyIndex The index of the butterfly species.
     */
    public ButterflyNetItem(Properties properties,
                            DataComponentRegistry dataComponentRegistry,
                            ItemRegistry itemRegistry,
                            int butterflyIndex) {
        super(properties);

        this.dataComponentRegistry = dataComponentRegistry;
        this.itemRegistry = itemRegistry;
        this.butterflyIndex = butterflyIndex;
    }

    /**
     * Adds some helper text that tells us what butterfly is in the net (if any).
     * @param stack The item stack.
     * @param context The context for the tooltip.
     * @param components The current text components.
     * @param tooltipFlag Is this a tooltip?
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull Item.TooltipContext context,
                                @NotNull List<Component> components,
                                @NotNull TooltipFlag tooltipFlag) {
        appendButterflyNameToHoverText(dataComponentRegistry, stack, components);

        String localisation = "tooltip.butterflies.release_butterfly";
        if (butterflyIndex < 0) {
            localisation = "tooltip.butterflies.butterfly_net";
        }

        MutableComponent newComponent = Component.translatable(localisation);
        Style style = newComponent.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                .withItalic(true);
        newComponent.setStyle(style);
        components.add(newComponent);

        super.appendHoverText(stack, context, components, tooltipFlag);
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
    @NotNull
    @Override
    public ItemStack getCraftingRemainder(@NotNull ItemStack itemStack) {
        return new ItemStack(itemRegistry.getEmptyButterflyNet().get());
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
     * If we left-click on a butterfly with an empty net, the player will catch the butterfly.
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return TRUE if the left-click action is consumed.
     */
    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack,
                                     @NotNull Player player,
                                     @NotNull Entity entity) {
        if (entity instanceof Butterfly butterfly) {
            DeferredHolder<Item, Item> item = itemRegistry.getButterflyNetFromIndex(butterfly.getButterflyIndex());

            if (item != null) {
                ItemStack newStack = new ItemStack(item.get(), 1);

                if (item != itemRegistry.getBurntButterflyNet()) {
                    entity.discard();
                }

                player.setItemInHand(InteractionHand.MAIN_HAND, newStack);
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

            ItemStack newStack = new ItemStack(itemRegistry.getEmptyButterflyNet().get(), 1);
            player.setItemInHand(hand, newStack);
            return InteractionResult.SUCCESS.heldItemTransformedTo(newStack);
        }

        return super.use(level, player, hand);
    }
}
