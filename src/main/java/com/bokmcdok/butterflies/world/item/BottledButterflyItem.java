package com.bokmcdok.butterflies.world.item;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyInfo;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyType;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a bottled butterfly held in a player's hand.
 */
public class BottledButterflyItem extends BlockItem implements ButterflyContainerItem {

    //  The name this item is registered under.
    public static String getRegistryId(int butterflyIndex) {
        return "bottled_butterfly_" + ButterflyInfo.SPECIES[butterflyIndex];
    }

    //  The localization strings.
    public static final String BOTTLED_BUTTERFLY_STRING = "block.butterflies.bottled_butterfly";
    public static final String BOTTLED_MOTH_STRING = "block.butterflies.bottled_moth";

    //  The index of the butterfly species.
    private final int butterflyIndex;

    /**
     * Construction
     * @param properties The properties to apply to the item.
     * @param block The block related to this item.
     * @param butterflyIndex The index of the butterfly species.
     */
    public BottledButterflyItem(Properties properties,
                                RegistryObject<Block> block,
                                int butterflyIndex) {
        super(block.get(), properties);

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
        appendButterflyNameToHoverText(stack, components);
        components.add(helperTooltip("tooltip.butterflies.release_butterfly"));
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
     * Overridden so we can use a single localization string for all instances.
     * @param itemStack The stack to get the name for.
     * @return The description ID, which is a reference to the localization
     *         string.
     */
    @NotNull
    @Override
    public Component getName(@NotNull ItemStack itemStack) {
        ButterflyData data = ButterflyRegistry.getEntry(butterflyIndex);
        if (data != null && data.type() == ButterflyType.MOTH) {
            return Component.translatable(BOTTLED_MOTH_STRING);
        } else {
            return Component.translatable(BOTTLED_BUTTERFLY_STRING);
        }
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
        return releaseButterfly(level, player, hand, Items.GLASS_BOTTLE);
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

            Player player = context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getItemInHand(context.getHand());
                ResourceLocation entity = getContainedButterflyEntityId(stack);

                if (entity != null) {
                    BlockPos position = context.getClickedPos();
                    Butterfly.spawnBottled(player.getLevel(), entity, position);
                }
            }
        }

        return result;
    }
}
