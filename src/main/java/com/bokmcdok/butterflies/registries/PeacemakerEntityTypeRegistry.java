package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PeacemakerEntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> PEACEMAKER_ENTITY_TYPES;

    public static final RegistryObject<EntityType<PeacemakerButterfly>> PEACEMAKER_BUTTERFLY;
    public static final RegistryObject<EntityType<PeacemakerEvoker>> PEACEMAKER_EVOKER;
    public static final RegistryObject<EntityType<PeacemakerIllusioner>> PEACEMAKER_ILLUSIONER;
    public static final RegistryObject<EntityType<PeacemakerPillager>> PEACEMAKER_PILLAGER;
    public static final RegistryObject<EntityType<PeacemakerVillager>> PEACEMAKER_VILLAGER;
    public static final RegistryObject<EntityType<PeacemakerVindicator>> PEACEMAKER_VINDICATOR;
    public static final RegistryObject<EntityType<PeacemakerWanderingTrader>> PEACEMAKER_WANDERING_TRADER;
    public static final RegistryObject<EntityType<PeacemakerWitch>> PEACEMAKER_WITCH;

    static {
        PEACEMAKER_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);
        
        PEACEMAKER_BUTTERFLY = registerPeacemakerEntity("peacemaker_butterfly", PeacemakerButterfly::new);
        PEACEMAKER_EVOKER = registerPeacemakerEntity("peacemaker_evoker", PeacemakerEvoker::new);
        PEACEMAKER_ILLUSIONER = registerPeacemakerEntity("peacemaker_illusioner", PeacemakerIllusioner::new);
        PEACEMAKER_PILLAGER = registerPeacemakerEntity("peacemaker_pillager", PeacemakerPillager::new);
        PEACEMAKER_VILLAGER = registerPeacemakerEntity("peacemaker_villager", PeacemakerVillager::new);
        PEACEMAKER_VINDICATOR = registerPeacemakerEntity("peacemaker_vindicator", PeacemakerVindicator::new);
        PEACEMAKER_WANDERING_TRADER = registerPeacemakerEntity("peacemaker_wandering_trader", PeacemakerWanderingTrader::new);
        PEACEMAKER_WITCH = registerPeacemakerEntity("peacemaker_witch", PeacemakerWitch::new);
    }

    /**
     * Helper method to register a Peacemaker entity.
     * @param registryId The ID of the entity.
     * @param factory The factory (constructor) to use.
     * @return The newly registered entity.
     * @param <T> The type of the entity.
     */
    private static <T extends Entity> RegistryObject<EntityType<T>> registerPeacemakerEntity(String registryId,
                                                                                             EntityType.EntityFactory<T> factory) {
        return PEACEMAKER_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
                        .sized(0.6F, 1.95F)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    /**
     * Prevent construction.
     */
    private PeacemakerEntityTypeRegistry() {}
}
