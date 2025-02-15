package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register professions to be used by villagers.
 */
public class VillagerProfessionRegistry {

    // An instance of a deferred registry we use to register.
    private final DeferredRegister<VillagerProfession> deferredRegister;

    // The lepidopterist profession.
    private RegistryObject<VillagerProfession> lepidopterist;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public VillagerProfessionRegistry(IEventBus modEventBus) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ButterfliesMod.MOD_ID);
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
                        poiTypeRegistry.getLepidopterist().get(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.FLOWERING_AZALEA_PLACE));
    }

    /**
     * Accessor to the lepidopterist profession.
     * @return The POI Type.
     */
    public RegistryObject<VillagerProfession> getLepidopterist() {
        return lepidopterist;
    }
}
