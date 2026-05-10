package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register for the menu types.
 */
public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    public static final DeferredRegister<MenuType<?>> REGISTER;

    // The menus
    public static final RegistryObject<MenuType<ButterflyFeederMenu>> BUTTERFLY_FEEDER_MENU;
    public static final RegistryObject<MenuType<ButterflyMicroscopeMenu>> BUTTERFLY_MICROSCOPE_MENU;

    /**
     * Helper method for creating butterfly feeder menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @return A new menu instance.
     */
    private static ButterflyFeederMenu createButterflyFeederMenu(int containerId,
                                                                 Inventory playerInventory) {
        return new ButterflyFeederMenu(BUTTERFLY_FEEDER_MENU.get(), containerId, playerInventory);
    }

    /**
     * Helper method for creating butterfly feeder menu.
     * @param containerId The ID of the container.
     * @param playerInventory The player's inventory.
     * @return A new menu instance.
     */
    private static ButterflyMicroscopeMenu createButterflyMicroscopeMenu(int containerId,
                                                                         Inventory playerInventory) {
        return new ButterflyMicroscopeMenu(BUTTERFLY_MICROSCOPE_MENU.get(), containerId, playerInventory);
    }

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ButterfliesMod.MOD_ID);
        BUTTERFLY_FEEDER_MENU = REGISTER.register("butterfly_feeder",
                () -> new MenuType<>(MenuTypeRegistry::createButterflyFeederMenu, FeatureFlags.DEFAULT_FLAGS));
        BUTTERFLY_MICROSCOPE_MENU = REGISTER.register("butterfly_microscope",
                () -> new MenuType<>(MenuTypeRegistry::createButterflyMicroscopeMenu, FeatureFlags.DEFAULT_FLAGS));
    }

    // Prevent construction.
    private MenuTypeRegistry() {}
}
