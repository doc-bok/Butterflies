package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register for the menu types.
 */
public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    private final DeferredRegister<MenuType<?>> deferredRegister;

    // The menus
    private RegistryObject<MenuType<ButterflyFeederMenu>> butterflyFeederMenu;
    private RegistryObject<MenuType<ButterflyMicroscopeMenu>> butterflyMicroscopeMenu;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public MenuTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.CONTAINERS, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the menu types.
     */
    public void initialise() {
        this.butterflyFeederMenu = deferredRegister.register("butterfly_feeder",
                        () -> new MenuType<>(this::createButterflyFeederMenu));

        this.butterflyMicroscopeMenu = deferredRegister.register("butterfly_microscope",
                        () -> new MenuType<>(this::createButterflyMicroscopeMenu));
    }

    /**
     * Get the butterfly feeder menu.
     * @return The menu type.
     */
    public RegistryObject<MenuType<ButterflyFeederMenu>> getButterflyFeederMenu() {
        return butterflyFeederMenu;
    }

    /**
     * Get the butterfly microscope menu.
     * @return The menu type.
     */
    public RegistryObject<MenuType<ButterflyMicroscopeMenu>> getButterflyMicroscopeMenu() {
        return butterflyMicroscopeMenu;
    }

    /**
     * Helper method for creating butterfly feeder menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @return A new menu instance.
     */
    private ButterflyFeederMenu createButterflyFeederMenu(int containerId,
                                                          Inventory playerInventory) {
        return new ButterflyFeederMenu(this.butterflyFeederMenu.get(), containerId, playerInventory);
    }

    /**
     * Helper method for creating butterfly feeder menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @return A new menu instance.
     */
    private ButterflyMicroscopeMenu createButterflyMicroscopeMenu(int containerId,
                                                                  Inventory playerInventory) {
        return new ButterflyMicroscopeMenu(this.butterflyMicroscopeMenu.get(), containerId, playerInventory);
    }
}
