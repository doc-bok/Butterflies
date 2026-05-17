package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.entity.animal.PeacemakerCow;
import com.bokmcdok.butterflies.world.entity.monster.*;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerVillager;
import com.bokmcdok.butterflies.world.entity.npc.PeacemakerWanderingTrader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PeacemakerEntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> PEACEMAKER_ENTITY_TYPES;

    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerButterfly>> PEACEMAKER_BUTTERFLY;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerCow>> PEACEMAKER_COW;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerEvoker>> PEACEMAKER_EVOKER;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerIllusioner>> PEACEMAKER_ILLUSIONER;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerPillager>> PEACEMAKER_PILLAGER;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerVillager>> PEACEMAKER_VILLAGER;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerVindicator>> PEACEMAKER_VINDICATOR;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerWanderingTrader>> PEACEMAKER_WANDERING_TRADER;
    public static final DeferredHolder<EntityType<?>, EntityType<PeacemakerWitch>> PEACEMAKER_WITCH;

    static {
        PEACEMAKER_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);

        PEACEMAKER_BUTTERFLY = registerPeacemakerEntity("peacemaker_butterfly", PeacemakerButterfly::new, 0.6F, 1.95F);
        PEACEMAKER_COW = registerPeacemakerEntity("peacemaker_cow", PeacemakerCow::new, 4.5f, 2.5f);
        PEACEMAKER_EVOKER = registerPeacemakerEntity("peacemaker_evoker", PeacemakerEvoker::new, 0.6F, 1.95F);
        PEACEMAKER_ILLUSIONER = registerPeacemakerEntity("peacemaker_illusioner", PeacemakerIllusioner::new, 0.6F, 1.95F);
        PEACEMAKER_PILLAGER = registerPeacemakerEntity("peacemaker_pillager", PeacemakerPillager::new, 0.6F, 1.95F);
        PEACEMAKER_VILLAGER = registerPeacemakerEntity("peacemaker_villager", PeacemakerVillager::new, 0.6F, 1.95F);
        PEACEMAKER_VINDICATOR = registerPeacemakerEntity("peacemaker_vindicator", PeacemakerVindicator::new, 0.6F, 1.95F);
        PEACEMAKER_WANDERING_TRADER = registerPeacemakerEntity("peacemaker_wandering_trader", PeacemakerWanderingTrader::new, 0.6F, 1.95F);
        PEACEMAKER_WITCH = registerPeacemakerEntity("peacemaker_witch", PeacemakerWitch::new, 0.6F, 1.95F);
    }

    /**
     * Helper method to register a Peacemaker entity.
     * @param registryId The ID of the entity.
     * @param factory The factory (constructor) to use.
     * @return The newly registered entity.
     * @param <T> The type of the entity.
     */
    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerPeacemakerEntity(String registryId,
                                                                                             EntityType.EntityFactory<T> factory,
                                                                                             float width,
                                                                                             float height) {
        return PEACEMAKER_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(factory, MobCategory.MONSTER)
                        .sized(width, height)
                        .clientTrackingRange(8)
                        .build(registryId));
    }

    /**
     * Prevent construction.
     */
    private PeacemakerEntityTypeRegistry() {}
}
