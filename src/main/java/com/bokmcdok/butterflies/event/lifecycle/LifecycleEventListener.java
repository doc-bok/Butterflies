package com.bokmcdok.butterflies.event.lifecycle;

import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyFeederScreen;
import com.bokmcdok.butterflies.event.network.NetworkEventListener;
import com.bokmcdok.butterflies.client.gui.screens.inventory.ButterflyMicroscopeScreen;
import com.bokmcdok.butterflies.registries.BlockRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.MenuTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
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

    // Reference to the registries.
    private final BlockRegistry blockRegistry;
    private final ItemRegistry itemRegistry;
    private final MenuTypeRegistry menuTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public LifecycleEventListener(IEventBus modEventBus,
                                  BlockRegistry blockRegistry,
                                  ItemRegistry itemRegistry,
                                  MenuTypeRegistry menuTypeRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        this.blockRegistry = blockRegistry;
        this.itemRegistry = itemRegistry;
        this.menuTypeRegistry = menuTypeRegistry;
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
            if (Arrays.asList(ButterflyInfo.TRAITS[i]).contains(ButterflyData.Trait.POISONOUS)) {
                BrewingRecipeRegistry.addRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(itemRegistry.getBottledButterflies().get(i).get()),
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
                () -> MenuScreens.register(this.menuTypeRegistry.getButterflyFeederMenu().get(), ButterflyFeederScreen::new)
        );

        event.enqueueWork(
                () -> MenuScreens.register(this.menuTypeRegistry.getButterflyMicroscopeMenu().get(), ButterflyMicroscopeScreen::new)
        );

        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getAlliumBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getAzureBluetBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getBlueOrchidBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getCornflowerBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getDandelionBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getLilyOfTheValleyBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getOrangeTulipBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getOxeyeDaisyBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getPinkTulipBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getPoppyBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getRedTulipBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getWhiteTulipBud().get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(blockRegistry.getWitherRoseBud().get(), RenderType.cutout());
    }
}
