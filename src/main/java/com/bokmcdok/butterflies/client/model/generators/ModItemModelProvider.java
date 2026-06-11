package com.bokmcdok.butterflies.client.model.generators;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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
        registerBottledButterflies();
    }

    /**
     * Registers the bottled butterflies.
     */
    private void registerBottledButterflies() {
        for (RegistryObject<Item> item :ItemRegistry.BOTTLED_BUTTERFLIES) {
            bottledButterflyItem(item);
        }
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
