package com.bokmcdok.butterflies.client.event;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.client.model.*;
import com.bokmcdok.butterflies.client.renderer.blockentity.ButterflyFeederEntityRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.*;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Listens for client only events.
 */
@OnlyIn(Dist.CLIENT)
public class ClientEventListener {

    // Registries.
    private final BlockEntityTypeRegistry blockEntityTypeRegistry;
    private final EntityTypeRegistry entityTypeRegistry;

    /**
     * Construction
     * @param modEventBus The event bus to register with.
     */
    public ClientEventListener(IEventBus modEventBus,
                               BlockEntityTypeRegistry blockEntityTypeRegistry,
                               EntityTypeRegistry entityTypeRegistry) {
        modEventBus.register(this);
        modEventBus.addListener(this::onRegisterLayerDefinitions);
        modEventBus.addListener(this::onRegisterRenderers);

        this.blockEntityTypeRegistry = blockEntityTypeRegistry;
        this.entityTypeRegistry = entityTypeRegistry;
    }

    /**
     * Registers models to be used for rendering
     * @param event The event information
     */
    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(CaterpillarModel.LAYER_LOCATION, CaterpillarModel::createBodyLayer);
        event.registerLayerDefinition(ChrysalisModel.LAYER_LOCATION, ChrysalisModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyEggModel.LAYER_LOCATION, ButterflyEggModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyScrollModel.LAYER_LOCATION, ButterflyScrollModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyGolemModel.LAYER_LOCATION, ButterflyGolemModel::createBodyLayer);
    }

    /**
     * Register the renderers for our entities
     * @param event The event information
     */
    private void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(entityTypeRegistry.getButterflyScroll().get(), ButterflyScrollRenderer::new);

        for (RegistryObject<EntityType<? extends Butterfly>> i : entityTypeRegistry.getButterflies()) {
            if (i.getId().compareTo(new ResourceLocation(ButterfliesMod.MOD_ID, "ice")) == 0 ||
                    i.getId().compareTo(new ResourceLocation(ButterfliesMod.MOD_ID, "lava")) == 0 ||
                    i.getId().compareTo(new ResourceLocation(ButterfliesMod.MOD_ID, "light")) == 0) {
                event.registerEntityRenderer(i.get(), GlowButterflyRenderer::new);
            } else {
                event.registerEntityRenderer(i.get(), ButterflyRenderer::new);
            }
        }

        for (RegistryObject<EntityType<Caterpillar>> i : entityTypeRegistry.getCaterpillars()) {
            event.registerEntityRenderer(i.get(), CaterpillarRenderer::new);
        }

        for (RegistryObject<EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.registerEntityRenderer(i.get(), ChrysalisRenderer::new);
        }

        for (RegistryObject<EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.registerEntityRenderer(i.get(), ButterflyEggRenderer::new);
        }

        event.registerEntityRenderer(entityTypeRegistry.getButterflyGolem().get(), ButterflyGolemRenderer::new);

        event.registerBlockEntityRenderer(blockEntityTypeRegistry.getButterflyFeeder().get(), ButterflyFeederEntityRenderer::new);
    }
}
