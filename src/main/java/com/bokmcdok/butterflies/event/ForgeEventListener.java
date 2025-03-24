package com.bokmcdok.butterflies.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.ArrayList;

/**
 * Listens for generic events on the Forge Bus.
 */
public class ForgeEventListener {

    /**
     * Construction
     * @param forgeEventBus The event bus to register with.
     */
    public ForgeEventListener(IEventBus forgeEventBus) {

        forgeEventBus.register(this);
    }

    /**
     * Add the lepidopterist's buildings to villages.
     * @param event The event we respond to in order to add the villages.
     */
    @SubscribeEvent
    private void onTagsUpdated(TagsUpdatedEvent event)
    {
        if(event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {

            // Plains
            addToPool(event.getLookupProvider(),
                    ResourceLocation.withDefaultNamespace("village/plains/houses"),
                    ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "village/plains/houses/plains_butterfly_house_1"),
                    4);

            // Savanna
            addToPool(event.getLookupProvider(),
                    ResourceLocation.withDefaultNamespace("village/savanna/houses"),
                    ResourceLocation.fromNamespaceAndPath("butterflies", "village/savanna/houses/savanna_butterfly_house_1"),
                    4);

            // Taiga
            addToPool(event.getLookupProvider(),
                    ResourceLocation.withDefaultNamespace("village/taiga/houses"),
                    ResourceLocation.fromNamespaceAndPath(ButterfliesMod.MOD_ID, "village/taiga/houses/taiga_butterfly_house_1"),
                    4);
        }
    }

    /**
     * Adds a structure to the specified pool.
     * @param registryAccess Access to the registry, provided by the event.
     * @param structurePool The RL of the pool to add to.
     * @param structureToAdd The RL of the structure to add.
     * @param weight The likelihood this building will appear.
     */
    private static void addToPool(HolderLookup.Provider registryAccess,
                                  ResourceLocation structurePool,
                                  ResourceLocation structureToAdd,
                                  int weight)
    {

        HolderLookup.RegistryLookup<StructureTemplatePool> registry = registryAccess.lookupOrThrow(Registries.TEMPLATE_POOL);
        ResourceKey<StructureTemplatePool> key = ResourceKey.create(Registries.TEMPLATE_POOL, structurePool);
        StructureTemplatePool pool = registry.getOrThrow(key).value();

        if(!(pool.rawTemplates instanceof ArrayList)) {
            pool.rawTemplates = new ArrayList<>(pool.rawTemplates);
        }

        SinglePoolElement addedElement = SinglePoolElement
                .single(structureToAdd.toString())
                .apply(StructureTemplatePool.Projection.RIGID);

        pool.rawTemplates.add(Pair.of(addedElement, weight));

        for (int i = 0; i < weight; ++i) {
            pool.templates.add(addedElement);
        }
    }
}
