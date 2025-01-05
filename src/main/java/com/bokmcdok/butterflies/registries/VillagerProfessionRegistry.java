package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Register professions to be used by villagers.
 */
public class VillagerProfessionRegistry {

    // An instance of a deferred registry we use to register.
    private final DeferredRegister<VillagerProfession> deferredRegister;

    // The lepidopterist profession.
    private DeferredHolder<VillagerProfession, VillagerProfession> lepidopterist;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public VillagerProfessionRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, ButterfliesMod.MOD_ID);
        this.deferredRegister.register(modEventBus);
    }

    /**
     * Register the professions.
     * @param poiTypeRegistry The POI Type registry.
     */
    public void initialise(PoiTypeRegistry poiTypeRegistry) {
        lepidopterist = deferredRegister.register("lepidopterist",
                () -> new VillagerProfession(
                        "lepidopterist",
                        x -> x.value() == poiTypeRegistry.getLepidopterist().get(),
                        x -> x.value() == poiTypeRegistry.getLepidopterist().get(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.FLOWERING_AZALEA_PLACE));
    }

    /**
     * Accessor to the lepidopterist profession.
     * @return The POI Type.
     */
    public DeferredHolder<VillagerProfession, VillagerProfession> getLepidopterist() {
        return lepidopterist;
    }
}
