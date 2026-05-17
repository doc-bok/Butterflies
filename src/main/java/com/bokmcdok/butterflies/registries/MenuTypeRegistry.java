package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.inventory.ButterflyFeederMenu;
import com.bokmcdok.butterflies.world.inventory.ButterflyMicroscopeMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Register for the menu types.
 */
public class MenuTypeRegistry {

    // An instance of a deferred registry we use to register menus.
    public static final DeferredRegister<MenuType<?>> MENU_TYPES;

    // The menus
    public static final DeferredHolder<MenuType<?>, MenuType<ButterflyFeederMenu>> BUTTERFLY_FEEDER_MENU;
    public static final DeferredHolder<MenuType<?>, MenuType<ButterflyMicroscopeMenu>> BUTTERFLY_MICROSCOPE_MENU;

    static {
        MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, ButterfliesMod.MOD_ID);
        BUTTERFLY_FEEDER_MENU = MENU_TYPES.register("butterfly_feeder",
                () -> new MenuType<>(ButterflyFeederMenu::new, FeatureFlags.DEFAULT_FLAGS));
        BUTTERFLY_MICROSCOPE_MENU = MENU_TYPES.register("butterfly_microscope",
                () -> new MenuType<>(ButterflyMicroscopeMenu::new, FeatureFlags.DEFAULT_FLAGS));
    }

    /**
     * Prevent construction.
     */
    private MenuTypeRegistry() {}
}
