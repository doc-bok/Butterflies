package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Register for the menu types.
 */
public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    private final DeferredRegister<MenuType<?>> deferredRegister;

    // The butterfly feeder menu
    private DeferredHolder<MenuType<?>, MenuType<ButterflyFeederMenu>> butterflyFeederMenu;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public MenuTypeRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.MENU, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the menu types.
     */
    public void initialise() {
        this.butterflyFeederMenu = deferredRegister.register("butterfly_feeder",
                        () -> new MenuType<>(this::createButterflyFeederMenu, FeatureFlags.DEFAULT_FLAGS));
    }

    /**
     * Get the butterfly feeder menu.
     * @return The menu type.
     */
    public DeferredHolder<MenuType<?>, MenuType<ButterflyFeederMenu>> getButterflyFeederMenu() {
        return butterflyFeederMenu;
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
}
