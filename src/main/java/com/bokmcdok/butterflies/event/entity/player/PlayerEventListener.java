package com.bokmcdok.butterflies.event.entity.player;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
    }

    /**
     * Called when a player interacts with an entity.
     * @param event The interaction event
     */
    @SubscribeEvent
    private void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Cat cat) {
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack stack = player.getItemInHand(hand);
            Item item = stack.getItem();
            if (item instanceof NameTagItem) {
                String name = stack.getHoverName().getString();

                //  If the cat is named Snow, then change it to a white cat.
                if ("Snow".equals(name)) {
                    Holder<CatVariant> variant = BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.WHITE);
                    cat.setVariant(variant);
                }
            }
        }
    }
}
