package com.bokmcdok.butterflies.world.entity.animal;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.entity.PeacemakerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A Peacemaker Cow entity.
 */
public class PeacemakerCow extends Mob implements PeacemakerEntity {

    // Constants for Peacemaker Cow attributes.
    private static final double PEACEMAKER_COW_HEALTH = 60.0d;
    private static final double PEACEMAKER_COW_KNOCKBACK_RESISTANCE = 1.0d;
    private static final double PEACEMAKER_COW_SPEED = 0.1d;

    private final ItemRegistry itemRegistry;

    /**
     * Generates attributes for the Peacemaker Cow.
     * @return A builder containing the mob's attributes.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, PEACEMAKER_COW_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, PEACEMAKER_COW_SPEED)
                .add(Attributes.KNOCKBACK_RESISTANCE, PEACEMAKER_COW_KNOCKBACK_RESISTANCE);
    }

    /**
     * Construction
     * @param entityType The type of this entity.
     * @param level The current level.
     */
    public PeacemakerCow(ItemRegistry itemRegistry,
                         EntityType<? extends Mob> entityType,
                         Level level) {
        super(entityType, level);
        this.itemRegistry =  itemRegistry;
    }

    /**
     * Allow players to "milk" Peacemaker Honey from the Cow.
     * @param player The player interacting with the Cow.
     * @param interactionHand The hand being used for the interaction.
     * @return The result of the interaction.
     */
    @NotNull
    @Override
    public InteractionResult mobInteract(Player player,
                                         @NotNull InteractionHand interactionHand) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (!itemInHand.is(Items.GLASS_BOTTLE)) {
            return super.mobInteract(player, interactionHand);
        }

        Level level = level();
        if (level.isClientSide()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        Item peacemakerHoney = itemRegistry.getPeacemakerHoneyBottle().get();
        ItemStack result = new ItemStack(peacemakerHoney);

        itemInHand.shrink(1);
        if (itemInHand.isEmpty()) {
            player.setItemInHand(interactionHand, result);
        } else if (!player.getInventory().add(result)) {
            player.drop(result, false);
        }

        level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPosition());
        return InteractionResult.CONSUME;
    }
}
