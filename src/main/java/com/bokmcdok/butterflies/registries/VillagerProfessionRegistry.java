package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

/**
 * Register professions to be used by villagers.
 */
public class VillagerProfessionRegistry {

    // An instance of a deferred registry we use to register.
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS;

    // The lepidopterist profession.
    public static final RegistryObject<VillagerProfession> LEPIDOPTERIST;

    static {
        VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ButterfliesMod.MOD_ID);

        final String lepidopteristId = "lepidopterist";
        Predicate<Holder<PoiType>> jobSite = x -> x.get() == PoiTypeRegistry.LEPIDOPTERIST.get();
        LEPIDOPTERIST = VILLAGER_PROFESSIONS.register(lepidopteristId,
                () -> new VillagerProfession(
                        lepidopteristId,
                        jobSite,
                        jobSite,
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.FLOWERING_AZALEA_PLACE));
    }

    /**
     * Prevent construction.
     */
    private VillagerProfessionRegistry() {}
}
