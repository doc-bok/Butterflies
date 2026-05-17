package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButterflyEntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> BUTTERFLY_ENTITY_TYPES;

    public static final List<DeferredHolder<EntityType<?>, EntityType<Butterfly>>> BUTTERFLIES;
    public static final List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> BUTTERFLY_EGGS;
    public static final List<DeferredHolder<EntityType<?>, EntityType<Caterpillar>>> CATERPILLARS;
    public static final List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> CHRYSALISES;

    /**
     * The mob category for registering butterflies.
     */
    public static final MobCategory BUTTERFLY_MOBS = MobCategory.create(
            "BUTTERFLY_SPAWNS",
            "butterfly_spawns",
            30,
            true,
            true,
            128);

    static {
        BUTTERFLY_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);
        final int speciesCount = ButterflyInfo.SPECIES.length;

        List<DeferredHolder<EntityType<?>, EntityType<Butterfly>>> butterflies = new ArrayList<>(speciesCount);
        List<DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>>> butterflyEggs = new ArrayList<>(speciesCount);
        List<DeferredHolder<EntityType<?>, EntityType<Caterpillar>>> caterpillars = new ArrayList<>(speciesCount);
        List<DeferredHolder<EntityType<?>, EntityType<Chrysalis>>> chrysalises = new ArrayList<>(speciesCount);

        for (int i = 0; i < speciesCount; i++) {
            butterflies.add(registerButterflyEntity(Butterfly.getRegistryId(i), getEntityFactory(i), 0.3f, 0.2f));
            butterflyEggs.add(registerButterflyEntity(ButterflyEgg.getRegistryId(i), ButterflyEgg::new, 0.1f, 0.1f));
            caterpillars.add(registerButterflyEntity(Caterpillar.getRegistryId(i), Caterpillar::new, 0.1f, 0.1f));
            chrysalises.add(registerButterflyEntity(Chrysalis.getRegistryId(i), Chrysalis::new, 0.1f, 0.1f));
        }

        BUTTERFLIES = Collections.unmodifiableList(butterflies);
        BUTTERFLY_EGGS = Collections.unmodifiableList(butterflyEggs);
        CATERPILLARS = Collections.unmodifiableList(caterpillars);
        CHRYSALISES = Collections.unmodifiableList(chrysalises);
    }

    // Factory methods
    private static Butterfly createIceButterfly(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(entityType, level, ParticleTypes.ELECTRIC_SPARK);
    }

    private static Butterfly createLavaMoth(EntityType<? extends Butterfly> entityType, Level level) {
        return new ParticleButterfly(entityType, level, ParticleTypes.DRIPPING_DRIPSTONE_LAVA);
    }

    /**
     * Returns the appropriate entity factory based on butterfly traits.
     * @param butterflyIndex The index of the butterfly species.
     * @return The factory method for creating butterfly entities.
     */
    private static EntityType.@NotNull EntityFactory<Butterfly> getEntityFactory(int butterflyIndex) {
        ButterflyData.Trait[] traits = ButterflyInfo.TRAITS[butterflyIndex];

        for (ButterflyData.Trait trait : traits) {
            if (trait == ButterflyData.Trait.ICY) {
                return ButterflyEntityTypeRegistry::createIceButterfly;
            }
            if (trait == ButterflyData.Trait.LAVA) {
                return ButterflyEntityTypeRegistry::createLavaMoth;
            }
        }

        return Butterfly::new;
    }

    /**
     * Helper method to register a Butterfly entity.
     * @param registryId The ID of the entity.
     * @param factory The factory (constructor) to use.
     * @return The newly registered entity.
     * @param <T> The type of the entity.
     */
    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerButterflyEntity(String registryId,
                                                                                            EntityType.EntityFactory<T> factory,
                                                                                            float width,
                                                                                            float height) {
        return BUTTERFLY_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(factory, BUTTERFLY_MOBS)
                        .sized(width, height)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    /**
     * Prevent construction.
     */
    private ButterflyEntityTypeRegistry() {}
}
