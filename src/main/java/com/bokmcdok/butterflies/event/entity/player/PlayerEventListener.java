package com.bokmcdok.butterflies.event.entity.player;

import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Holds event listeners for player actions.
 */
public class PlayerEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public PlayerEventListener(IEventBus forgeEventBus) {
        forgeEventBus.register(this);
        forgeEventBus.addListener(this::onEntityInteractEvent);
    }

    /**
     * Called when a player interacts with an entity.
     * @param event The interaction event
     */
    private void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Cat cat &&
            event.getEntity() instanceof Player player) {
            InteractionHand hand = event.getHand();
            ItemStack stack = player.getItemInHand(hand);
            Item item = stack.getItem();
            if (item instanceof NameTagItem) {
                String name = stack.getHoverName().getString();

                //  If the cat is named Snow, then change it to a white cat.
                if ("Snow".equals(name)) {
                    cat.setCatType(Cat.TYPE_WHITE);
                }
            }
        }
    }
}
