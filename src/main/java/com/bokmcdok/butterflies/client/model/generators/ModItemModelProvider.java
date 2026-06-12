package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

/**
 * Generates Item Models for the mod.
 */
public class ModItemModelProvider extends ItemModelProvider {

    /**
     * Construction
     * @param output The pack to output the resources to.
     * @param existingFileHelper A helper containing the existing files.
     */
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ButterfliesMod.MOD_ID, existingFileHelper);
    }

    /**
     * Entry point.
     */
    @Override
    protected void registerModels() {
        for(int i = 0; i < ButterflyEntityTypeRegistry.BUTTERFLIES.size(); ++i) {
            registerBottledButterfly(i);
            registerButterflySpawnEggs(i);
        }
    }

    /**
     * Registers the bottled butterflies.
     */
    private void registerBottledButterfly(int index) {
        bottledButterflyItem(ItemRegistry.BOTTLED_BUTTERFLIES.get(index));
    }

    /**
     * Register all buttery-related spawn eggs.
     * @param index The butterfly index.
     */
    private void registerButterflySpawnEggs(int index) {
        final ResourceLocation parent = new ResourceLocation("handheld_rod");
        String path = Objects.requireNonNull(SpawnEggRegistry.BUTTERFLY_SPAWN_EGGS.get(index).getId()).getPath();
        String species = path.substring(20);

        singleTexture(path, parent, "layer0", new ResourceLocation(ButterfliesMod.MOD_ID, "item/spawn_egg/butterfly/" + species));

        path = Objects.requireNonNull(SpawnEggRegistry.CATERPILLAR_SPAWN_EGGS.get(index).getId()).getPath();
        singleTexture(path, parent, "layer0", new ResourceLocation(ButterfliesMod.MOD_ID, "item/spawn_egg/caterpillar/" + species));

        path = Objects.requireNonNull(SpawnEggRegistry.CHRYSALIS_SPAWN_EGGS.get(index).getId()).getPath();
        singleTexture(path, parent, "layer0", new ResourceLocation(ButterfliesMod.MOD_ID, "item/spawn_egg/chrysalis/" + species));

        path = Objects.requireNonNull(SpawnEggRegistry.EGG_SPAWN_EGGS.get(index).getId()).getPath();
        singleTexture(path, parent, "layer0", new ResourceLocation(ButterfliesMod.MOD_ID, "item/spawn_egg/egg/" + species));
    }

    /**
     * Registers a bottled butterfly item.
     * @param item The item to register.
     */
    private void bottledButterflyItem(RegistryObject<Item> item)
    {
        // The key should never be null.
        assert item.getKey() != null;

        ResourceLocation itemKey = item.getKey().location();
        getBuilder(itemKey.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld_rod"))
                .texture("layer0", new ResourceLocation(ButterfliesMod.MOD_ID, "item/bottled_butterfly/bottled_" + itemKey.getPath().substring(18)));
    }
}
