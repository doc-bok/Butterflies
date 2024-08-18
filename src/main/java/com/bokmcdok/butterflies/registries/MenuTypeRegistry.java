package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    private final DeferredRegister<MenuType<?>> deferredRegister;

    // The butterfly feeder menu
    private final RegistryObject<MenuType<ButterflyFeederMenu>> butterflyFeederMenu;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public MenuTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);

        this.butterflyFeederMenu = deferredRegister.register("butterfly_feeder",
                        () -> new MenuType<>(this::createButterflyFeederMenu, FeatureFlags.DEFAULT_FLAGS));
    }

    /**
     * Helper method for creating butterfly feeder menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @return A new menu instance.
     */
    private ButterflyFeederMenu createButterflyFeederMenu(int containerId,
                                                          Inventory playerInventory) {
        return new ButterflyFeederMenu(butterflyFeederMenu.get(), containerId, playerInventory);
    }
}
