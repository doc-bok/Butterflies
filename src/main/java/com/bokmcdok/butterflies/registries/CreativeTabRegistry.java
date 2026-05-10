package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * This class registers new creative tabs for the Butterflies Mod
 */
public class CreativeTabRegistry {

    // An instance of a deferred registry we use to register items.
    public static final DeferredRegister<CreativeModeTab> REGISTER;

    // The Butterfly Creative Tab
    public static RegistryObject<CreativeModeTab> BUTTERFLY_CREATIVE_TAB;

    static {
        REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ButterfliesMod.MOD_ID);
        BUTTERFLY_CREATIVE_TAB = REGISTER.register("butterfly_creative_tab",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.butterfly_tab"))
                        .icon(ItemRegistry.BUTTERFLY_BOOK.get()::getDefaultInstance)
                        .build()
        );
    }

    // Prevent construction.
    private CreativeTabRegistry() {}
}
