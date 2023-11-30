package com.bokmcdok.butterflies.event.entity.player;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyIds;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.item.ButterflyContainerItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
     * Transfer butterfly data when crafting items that contain a butterfly.
     * @param event The event data
     */
    @SubscribeEvent
    public static void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event) {

        ItemStack craftingItem = event.getCrafting();
        if (craftingItem.getItem() instanceof ButterflyContainerItem) {

            Container craftingMatrix = event.getInventory();
            for (int i = 0; i < craftingMatrix.getContainerSize(); ++i) {

                ItemStack recipeItem = craftingMatrix.getItem(i);
                CompoundTag tag = recipeItem.getTag();
                if (tag != null) {
                    int index = -1;

                    // Always use Entity ID for compatibility with butterfly net.
                    if (tag.contains(CompoundTagId.ENTITY_ID)) {
                        ResourceLocation location = new ResourceLocation(tag.getString(CompoundTagId.ENTITY_ID));
                        index = ButterflyIds.LocationToIndex(location);
                    }

                    if (index >= 0) {
                        ButterflyContainerItem.setButterfly(craftingItem, index);
                    }

                    break;
                }
            }
        }
    }
}
