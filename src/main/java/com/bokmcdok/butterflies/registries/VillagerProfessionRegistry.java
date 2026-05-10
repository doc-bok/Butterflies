package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register professions to be used by villagers.
 */
public class VillagerProfessionRegistry {

    // An instance of a deferred registry we use to register.
    public static final DeferredRegister<VillagerProfession> REGISTER;

    // The lepidopterist profession.
    public static final RegistryObject<VillagerProfession> LEPIDOPTERIST;

    static {
        REGISTER = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ButterfliesMod.MOD_ID);

        LEPIDOPTERIST = REGISTER.register("lepidopterist",
                () -> new VillagerProfession(
                        "lepidopterist",
                        x -> x.get() == PoiTypeRegistry.LEPIDOPTERIST.get(),
                        x -> x.get() == PoiTypeRegistry.LEPIDOPTERIST.get(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.FLOWERING_AZALEA_PLACE));
    }
}
