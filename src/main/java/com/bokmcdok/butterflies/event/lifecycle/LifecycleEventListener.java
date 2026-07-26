package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyInfo;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Arrays;

/**
 * Events fired during the overall life cycle of the mod.
 */
public class LifecycleEventListener {

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus) {
        modEventBus.register(this);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
    }

    /**
     * Common setup event where we register brewing recipes and pottery sherd
     * patterns.
     * @param event The event class.
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        NetworkEventListener.BUTTERFLY_NETWORK_CHANNEL.addListener(NetworkEventListener::onButterflyCollectionPayload);

        // Check for the poisonous trait
        for (int i = 0; i < ButterflyInfo.TRAITS.length; ++i) {
            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyTrait.POISONOUS)) {
                BrewingRecipeRegistry.addRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(ItemRegistry.BOTTLED_BUTTERFLIES.get(i).get()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
            }
        }
    }

    /**
     * Register the screens with their respective menus.
     * @param event The client setup event.
     */
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(MenuTypeRegistry.BUTTERFLY_FEEDER_MENU.get(), ButterflyFeederScreen::new)
        );

        event.enqueueWork(
                () -> MenuScreens.register(MenuTypeRegistry.BUTTERFLY_MICROSCOPE_MENU.get(), ButterflyMicroscopeScreen::new)
        );

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ALLIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZURE_BLUET_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BLUE_ORCHID_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CORNFLOWER_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DANDELION_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LILY_OF_THE_VALLEY_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ORANGE_TULIP_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.OXEYE_DAISY_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.PINK_TULIP_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POPPY_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RED_TULIP_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WHITE_TULIP_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WITHER_ROSE_BUD.get(), RenderType.cutout());
    }
}
