package com.bokmcdok.butterflies.registries;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.decoration.ButterflyScroll;
import com.bokmcdok.butterflies.world.entity.monster.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.IronGolem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class registers all entity types used in the butterflies mod with Forge's Entity Type Registry.
 */
public class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES;

    public static final DeferredHolder<EntityType<?>, EntityType<IronGolem>> BUTTERFLY_GOLEM;
    public static final DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> BUTTERFLY_SCROLL; // TODO: Remove after migration, kept for backwards compatibility
    public static final List<DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>>> BUTTERFLY_SCROLLS;

    static {
        ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ButterfliesMod.MOD_ID);

        // Butterfly scrolls
        final int speciesCount = ButterflyInfo.SPECIES.length;
        List<DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>>> butterflyScrolls = new ArrayList<>(speciesCount);

        for (int i = 0; i < speciesCount; i++) {
            butterflyScrolls.add(registerButterflyScroll(i));
        }

        BUTTERFLY_SCROLLS = Collections.unmodifiableList(butterflyScrolls);

        // Register the single butterfly scroll separately (backwards compatibility)
        BUTTERFLY_SCROLL = ENTITY_TYPES.register(
                ButterflyScroll.NAME,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(butterflyScrollKey));


        BUTTERFLY_GOLEM = registerButterflyGolem();
    }

    private static DeferredHolder<EntityType<?>, EntityType<IronGolem>> registerButterflyGolem() {
        String registryId = "butterfly_golem";
        return ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(IronGolem::new, MobCategory.MISC)
                        .sized(1.4F, 2.7F)
                        .clientTrackingRange(10)
                        .build(resourceKey));
    }

    private static DeferredHolder<EntityType<?>, EntityType<ButterflyScroll>> registerButterflyScroll(int butterflyIndex) {
        String registryId = ButterflyScroll.getRegistryId(butterflyIndex);
        return ENTITY_TYPES.register(registryId,
                () -> EntityType.Builder.of(ButterflyScroll::create, MobCategory.MISC)
                        .sized(1.0f, 1.0f)
                        .build(resourceKey));
    }

    /**
     * Prevent construction.
     */
    private EntityTypeRegistry() {}
}
