package com.bokmcdok.butterflies.event.entity.player;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Holds event listeners for player actions.
 */
@Mod.EventBusSubscriber(modid = ButterfliesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventListener {

    /**
     * Transfer butterfly data when moving from a butterfly net to a bottle
     * @param event The event data
     */
    @SubscribeEvent
    public static void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        ItemStack craftingItem = event.getCrafting();
        if (craftingItem.getItem() == ItemRegistry.BOTTLED_BUTTERFLY.get()) {
            Container craftingMatrix = event.getInventory();
            for (int i = 0; i < craftingMatrix.getContainerSize(); ++i) {
                ItemStack recipeItem = craftingMatrix.getItem(i);
                if (recipeItem.getItem() == ItemRegistry.BUTTERFLY_NET.get()) {
                    CompoundTag tag = recipeItem.getOrCreateTag();
                    String entityId = tag.getString(CompoundTagId.ENTITY_ID);
                    BottledButterflyItem.setButterfly(craftingItem, entityId);
                    break;
                }
            }
        }
    }
}
