package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register for the menu types.
 */
public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    public static final DeferredRegister<MenuType<?>> MENU_TYPES;

    // The menus
    public static final RegistryObject<MenuType<ButterflyFeederMenu>> BUTTERFLY_FEEDER_MENU;
    public static final RegistryObject<MenuType<ButterflyMicroscopeMenu>> BUTTERFLY_MICROSCOPE_MENU;

    static {
        MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ButterfliesMod.MOD_ID);
        BUTTERFLY_FEEDER_MENU = MENU_TYPES.register("butterfly_feeder",
                () -> new MenuType<>(ButterflyFeederMenu::new));
        BUTTERFLY_MICROSCOPE_MENU = MENU_TYPES.register("butterfly_microscope",
                () -> new MenuType<>(ButterflyMicroscopeMenu::new));
    }

    /**
     * Prevent construction.
     */
    private MenuTypeRegistry() {}
}
