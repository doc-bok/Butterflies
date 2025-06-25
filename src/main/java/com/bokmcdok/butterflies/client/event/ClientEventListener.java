package com.bokmcdok.butterflies.client.event;

import com.bokmcdok.butterflies.client.model.*;
import com.bokmcdok.butterflies.client.renderer.blockentity.ButterflyFeederEntityRenderer;
import com.bokmcdok.butterflies.client.renderer.entity.*;
import com.bokmcdok.butterflies.registries.BlockEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.ButterflyInfo;
import com.bokmcdok.butterflies.world.entity.animal.Butterfly;
import com.bokmcdok.butterflies.world.entity.animal.ButterflyEgg;
import com.bokmcdok.butterflies.world.entity.animal.Caterpillar;
import com.bokmcdok.butterflies.world.entity.animal.Chrysalis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

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
    @SubscribeEvent
    private void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(CaterpillarModel.LAYER_LOCATION, CaterpillarModel::createBodyLayer);
        event.registerLayerDefinition(ChrysalisModel.LAYER_LOCATION, ChrysalisModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyEggModel.LAYER_LOCATION, ButterflyEggModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyScrollModel.LAYER_LOCATION, ButterflyScrollModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyGolemModel.LAYER_LOCATION, ButterflyGolemModel::createBodyLayer);
        event.registerLayerDefinition(HummingbirdMothModel.LAYER_LOCATION, HummingbirdMothModel::createBodyLayer);
    }

    /**
     * Register the renderers for our entities
     * @param event The event information
     */
    @SubscribeEvent
    @SuppressWarnings("unchecked")
    private void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(entityTypeRegistry.getButterflyScroll().get(), ButterflyScrollRenderer::new);
        // Register the butterfly renderers.
        List<DeferredHolder<EntityType<?>, EntityType<? extends Mob>>> butterflies = entityTypeRegistry.getButterflies();
        for (int i = 0; i < butterflies.size(); ++i) {

            // Get the renderer provider.
            EntityRendererProvider<Butterfly> rendererProvider = getButterflyEntityRendererProvider(i);

            // Register the selected renderer provider.
            event.registerEntityRenderer((EntityType<Butterfly>)butterflies.get(i).get(), rendererProvider);
        }

        for (DeferredHolder<EntityType<?>, EntityType<? extends Mob>> i : entityTypeRegistry.getCaterpillars()) {
            event.registerEntityRenderer((EntityType<Caterpillar>)i.get(), CaterpillarRenderer::new);
        }

        for (DeferredHolder<EntityType<?>, EntityType<Chrysalis>> i : entityTypeRegistry.getChrysalises()) {
            event.registerEntityRenderer(i.get(), ChrysalisRenderer::new);
        }

        for (DeferredHolder<EntityType<?>, EntityType<ButterflyEgg>> i : entityTypeRegistry.getButterflyEggs()) {
            event.registerEntityRenderer(i.get(), ButterflyEggRenderer::new);
        }

        event.registerEntityRenderer((EntityType<IronGolem>)entityTypeRegistry.getButterflyGolem().get(), ButterflyGolemRenderer::new);

        event.registerBlockEntityRenderer(blockEntityTypeRegistry.getButterflyFeeder().get(), ButterflyFeederEntityRenderer::new);
    }

    /**
     * Get the render provider for the specified butterfly index.
     * @param butterflyIndex The index of the butterfly.
     * @return The renderer provider to use.
     */
    private static @NotNull EntityRendererProvider<Butterfly> getButterflyEntityRendererProvider(int butterflyIndex) {
        EntityRendererProvider<Butterfly> rendererProvider = ButterflyRenderer::new;

        // Choose a different provider based on certain butterfly traits.
        List<ButterflyData.Trait> traits = Arrays.asList(ButterflyInfo.TRAITS[butterflyIndex]);
        if (traits.contains(ButterflyData.Trait.GLOW)) {
            rendererProvider =  GlowButterflyRenderer::new;
        } else if (traits.contains(ButterflyData.Trait.HUMMINGBIRD)){
            rendererProvider = HummingbirdMothRenderer::new;
        }
        return rendererProvider;
    }
}
