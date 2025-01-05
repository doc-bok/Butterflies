package com.bokmcdok.butterflies.event;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.Objects;

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
        forgeEventBus.addListener(this::onTagsUpdated);
    }

    /**
     * Add the lepidopterist's buildings to villages.
     * @param event The event we respond to in order to add the villages.
     */
    private void onTagsUpdated(TagsUpdatedEvent event)
    {
        if(event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) {

            // Plains
            addToPool(event.getRegistryAccess(),
                    new ResourceLocation("village/plains/houses"),
                    new ResourceLocation("butterflies", "village/plains/houses/plains_butterfly_house_1"),
                    3);

            // Savanna
            addToPool(event.getRegistryAccess(),
                    new ResourceLocation("village/savanna/houses"),
                    new ResourceLocation("butterflies", "village/savanna/houses/savanna_butterfly_house_1"),
                    2);

            // Taiga
            addToPool(event.getRegistryAccess(),
                    new ResourceLocation("village/taiga/houses"),
                    new ResourceLocation("butterflies", "village/taiga/houses/taiga_butterfly_house_1"),
                    2);
        }
    }

    /**
     * Adds a structure to the specified pool.
     * @param registryAccess Access to the registry, provided by the event.
     * @param structurePool The RL of the pool to add to.
     * @param structureToAdd The RL of the structure to add.
     * @param weight The likelihood this building will appear.
     */
    private static void addToPool(RegistryAccess registryAccess,
                                  ResourceLocation structurePool,
                                  ResourceLocation structureToAdd,
                                  int weight)
    {
        Registry<StructureTemplatePool> registry = registryAccess.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
        StructureTemplatePool pool = Objects.requireNonNull(registry.get(structurePool), structurePool.getPath());

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
