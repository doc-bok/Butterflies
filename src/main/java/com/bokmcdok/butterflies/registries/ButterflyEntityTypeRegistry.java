package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButterflyEntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> BUTTERFLY_ENTITY_TYPES;

    public static final List<RegistryObject<EntityType<? extends Butterfly>>> BUTTERFLIES;
    public static final List<RegistryObject<EntityType<ButterflyEgg>>> BUTTERFLY_EGGS;
    public static final List<RegistryObject<EntityType<Caterpillar>>> CATERPILLARS;
    public static final List<RegistryObject<EntityType<Chrysalis>>> CHRYSALISES;

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
        BUTTERFLY_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ButterfliesMod.MOD_ID);
        final int speciesCount = ButterflyInfo.SPECIES.length;

        List<RegistryObject<EntityType<? extends Butterfly>>> butterflies = new ArrayList<>(speciesCount);
        List<RegistryObject<EntityType<ButterflyEgg>>> butterflyEggs = new ArrayList<>(speciesCount);
        List<RegistryObject<EntityType<Caterpillar>>> caterpillars = new ArrayList<>(speciesCount);
        List<RegistryObject<EntityType<Chrysalis>>> chrysalises = new ArrayList<>(speciesCount);

        for (int i = 0; i < speciesCount; i++) {
            butterflies.add(registerButterfly(i));
            butterflyEggs.add(registerButterflyEgg(i));
            caterpillars.add(registerCaterpillar(i));
            chrysalises.add(registerChrysalis(i));
        }

        BUTTERFLIES = Collections.unmodifiableList(butterflies);
        BUTTERFLY_EGGS = Collections.unmodifiableList(butterflyEggs);
        CATERPILLARS = Collections.unmodifiableList(caterpillars);
        CHRYSALISES = Collections.unmodifiableList(chrysalises);
    }

    // Registration methods
    private static RegistryObject<EntityType<? extends Butterfly>> registerButterfly(int butterflyIndex) {
        String registryId = Butterfly.getRegistryId(butterflyIndex);
        EntityType.EntityFactory<Butterfly> entityFactory = getEntityFactory(butterflyIndex);

        return BUTTERFLY_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(entityFactory, BUTTERFLY_MOBS)
                        .sized(0.3f, 0.2f)
                        .clientTrackingRange(10)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<ButterflyEgg>> registerButterflyEgg(int butterflyIndex) {
        String registryId = ButterflyEgg.getRegistryId(butterflyIndex);
        return BUTTERFLY_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(ButterflyEgg::new, BUTTERFLY_MOBS)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<Caterpillar>> registerCaterpillar(int butterflyIndex) {
        String registryId = Caterpillar.getRegistryId(butterflyIndex);
        return BUTTERFLY_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(Caterpillar::new, BUTTERFLY_MOBS)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
    }

    private static RegistryObject<EntityType<Chrysalis>> registerChrysalis(int butterflyIndex) {
        String registryId = Chrysalis.getRegistryId(butterflyIndex);
        return BUTTERFLY_ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(Chrysalis::new, BUTTERFLY_MOBS)
                        .sized(0.1f, 0.1f)
                        .build(registryId));
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
     * Prevent construction.
     */
    private ButterflyEntityTypeRegistry() {}
}
