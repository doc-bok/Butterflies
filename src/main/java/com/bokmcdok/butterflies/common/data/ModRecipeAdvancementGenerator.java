package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Generates advancements for recipe unlocks.
 */
public class ModRecipeAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    private final static String CATEGORY_DECORATIONS = "decorations";
    private final static String CATEGORY_MISC = "misc";
    private final static String CATEGORY_TOOLS = "tools";

    /**
     * Entry Point.
     * @param registries A lookup for registries and their objects.
     * @param saver A consumer used to write advancements to a file.
     * @param existingFileHelper A helper used to find whether a file exists.
     */
    @Override
    public void generate(@NotNull HolderLookup.Provider registries,
                         @NotNull Consumer<AdvancementHolder> saver,
                         @NotNull ExistingFileHelper existingFileHelper) {

        addRecipeAdvancement(saver, CATEGORY_DECORATIONS, "butterfly_feeder", Items.IRON_INGOT);
        addRecipeAdvancement(saver, CATEGORY_DECORATIONS, "butterfly_microscope", Items.SPYGLASS);

        addRecipeAdvancement(saver, CATEGORY_MISC, "banner_pattern_butterfly", ItemRegistry.SILK.get());

        for(ButterflyData data : ButterflyData.getButterflyDataCollection()) {
            addRecipeAdvancement(saver, CATEGORY_MISC, "bottled_butterfly_" + data.entityId(), ItemRegistry.getButterflyNetFromIndex(data.butterflyIndex()).get());
            addRecipeAdvancement(saver, CATEGORY_MISC, "bottled_caterpillar_" + data.entityId(), ItemRegistry.CATERPILLARS.get(data.butterflyIndex()).get());
            addRecipeAdvancement(saver, CATEGORY_MISC, "butterfly_scroll_" + data.entityId(), ItemRegistry.getButterflyNetFromIndex(data.butterflyIndex()).get());
        }

        for(RegistryObject<Item> scroll : ItemRegistry.BUTTERFLY_ORIGAMI) {
            addRecipeAdvancement(saver, CATEGORY_MISC, Objects.requireNonNull(scroll.getId()).getPath(), Items.PAPER);
        }

        int codlingMothIndex = ButterflyData.getButterflyIndex("codling");

        addRecipeAdvancement(saver, CATEGORY_MISC, "codling_caterpillar", ItemRegistry.INFESTED_APPLE.get());
        addRecipeAdvancement(saver, CATEGORY_MISC, "infested_apple", ItemRegistry.CATERPILLARS.get(codlingMothIndex).get());
        addRecipeAdvancement(saver, CATEGORY_MISC, "paper_from_silk", ItemRegistry.SILK.get());
        addRecipeAdvancement(saver, CATEGORY_MISC, "string_from_silk", ItemRegistry.SILK.get());

        addRecipeAdvancement(saver, CATEGORY_TOOLS, "butterfly_net", Items.STRING);
    }

    /**
     * Add an advancement for a single recipe that requires a single item.
     * @param saver A consumer used to write advancements to a file.
     * @param category The category of the recipe.
     * @param recipeName The name of the recipe.
     * @param requiredItem The item required to unlock the recipe.
     */
    private void addRecipeAdvancement(@NotNull Consumer<AdvancementHolder> saver,
                                      String category,
                                      String recipeName,
                                      Item requiredItem) {
        ResourceLocation[] recipe =  new ResourceLocation[] { new ResourceLocation(ButterfliesMod.MOD_ID, recipeName) };
        Advancement.Builder.recipeAdvancement()
                .addCriterion("has_the_item", InventoryChangeTrigger.TriggerInstance.hasItems(requiredItem))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipe[0]))
                .requirements(AdvancementRequirements.Strategy.OR)
                .rewards(new AdvancementRewards(0, new ResourceLocation[0], recipe, CommandFunction.CacheableFunction.NONE))
                .save(saver, new ResourceLocation(ButterfliesMod.MOD_ID, "recipes/" + category + "/" + recipeName));
    }
}
