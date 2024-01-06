package com.bokmcdok.butterflies.event.entity.player;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.bokmcdok.butterflies.world.item.BottledButterflyItem;
import com.bokmcdok.butterflies.world.item.ButterflyBookItem;
import com.bokmcdok.butterflies.world.item.ButterflyContainerItem;
import com.bokmcdok.butterflies.world.item.ButterflyScrollItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.NameTagItem;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
                        index = ButterflyData.locationToIndex(location);
                    }

                    if (index >= 0) {
                        ButterflyContainerItem.setButterfly(craftingItem, index);

                        // Check for shift-clicked item.
                        Player player = event.getEntity();
                        if (player != null) {
                            Inventory inventory = player.getInventory();
                            for (int j = 0; j < inventory.getContainerSize(); ++j) {

                                ItemStack inventoryItem = inventory.getItem(j);
                                if (inventoryItem.getItem() instanceof BottledButterflyItem ||
                                        inventoryItem.getItem() instanceof ButterflyScrollItem) {

                                    CompoundTag inventoryItemTag = inventoryItem.getTag();
                                    if (inventoryItemTag == null || !inventoryItemTag.contains(CompoundTagId.ENTITY_ID)) {
                                        ButterflyContainerItem.setButterfly(inventoryItem, index);
                                    }
                                }
                            }
                        }

                        break;
                    }
                }
            }

        } else if (craftingItem.getItem() instanceof ButterflyBookItem) {
            Container craftingMatrix = event.getInventory();
            int index = -1;
            ItemStack oldBook = null;
            for (int i = 0; i < craftingMatrix.getContainerSize(); ++i) {

                // If there is a book then we are adding a page.
                ItemStack recipeItem = craftingMatrix.getItem(i);
                if (recipeItem.getItem() instanceof ButterflyBookItem) {
                    oldBook = recipeItem;
                }

                // Always use Entity ID for compatibility with butterfly net.
                CompoundTag tag = recipeItem.getTag();
                if (tag != null && tag.contains(CompoundTagId.ENTITY_ID)) {
                    ResourceLocation location = new ResourceLocation(tag.getString(CompoundTagId.ENTITY_ID));
                    index = ButterflyData.locationToIndex(location);
                    break;
                }
            }

            if (index >= 0) {
                ButterflyBookItem.addPage(oldBook, craftingItem, index);

                // Check for shift-clicked item.
                Player player = event.getEntity();
                if (player != null) {
                    Inventory inventory = player.getInventory();
                    for (int j = 0; j < inventory.getContainerSize(); ++j) {

                        ItemStack inventoryItem = inventory.getItem(j);
                        if (inventoryItem.getItem() instanceof ButterflyBookItem) {
                            ButterflyBookItem.addPage(oldBook, craftingItem, index);
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when a player interacts with an entity.
     * @param event The interaction event
     */
    @SubscribeEvent
    public static void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Cat cat) {
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack stack = player.getItemInHand(hand);
            Item item = stack.getItem();
            if (item instanceof NameTagItem) {
                String name = stack.getHoverName().getString();

                //  If the cat is named Snow, then change it to a white cat.
                if ("Snow".equals(name)) {
                    cat.setCatVariant(CatVariant.WHITE);
                }
            }
        }
    }
}
