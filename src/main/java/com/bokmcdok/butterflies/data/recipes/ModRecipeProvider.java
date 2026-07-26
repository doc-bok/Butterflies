package com.bokmcdok.butterflies.data.recipes;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyType;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * Generates recipes and their unlock advancements.
 */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static final Item[] ORIGAMI_COLORS = new Item[] {
            Items.BLACK_DYE,
            Items.BLUE_DYE,
            Items.BROWN_DYE,
            Items.CYAN_DYE,
            Items.GRAY_DYE,
            Items.GREEN_DYE,
            Items.LIGHT_BLUE_DYE,
            Items.LIGHT_GRAY_DYE,
            Items.LIME_DYE,
            Items.MAGENTA_DYE,
            Items.ORANGE_DYE,
            Items.PINK_DYE,
            Items.PURPLE_DYE,
            Items.RED_DYE,
            Items.WHITE_DYE,
            Items.YELLOW_DYE
    };

    /**
     * Construction.
     * @param packOutput The pack to output to.
     */
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    /**
     * Entry point.
     * @param recipeOutput The recipe data to output to.
     */
    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> recipeOutput) {

        for(int i = 0; i < ItemRegistry.BOTTLED_BUTTERFLIES.size(); ++i) {
            ButterflyData data = ButterflyRegistry.getEntry(i);
            if (data != null) {
                boolean isButterfly = data.type() == ButterflyType.BUTTERFLY;
                String bottledFlyer = isButterfly ? "bottled_butterfly" : "bottled_moth";
                String bottledCrawler = isButterfly ? "bottled_caterpillar" : "bottled_larva";
                String scroll = isButterfly ? "butterfly_scroll" : "moth_scroll";

                addBottleRecipe(recipeOutput, ItemRegistry.BUTTERFLY_NETS, ItemRegistry.BOTTLED_BUTTERFLIES, "_from_net", bottledFlyer, i);
                addBottleRecipe(recipeOutput, ItemRegistry.FIREPROOF_BUTTERFLY_NETS, ItemRegistry.BOTTLED_BUTTERFLIES, "_from_fireproof", bottledFlyer, i);
                addBottleRecipe(recipeOutput, ItemRegistry.CATERPILLARS, ItemRegistry.BOTTLED_CATERPILLARS, "_from_caterpillar", bottledCrawler, i);

                addScrollRecipe(recipeOutput, ItemRegistry.BUTTERFLY_NETS, "_from_net", scroll, i);
                addScrollRecipe(recipeOutput, ItemRegistry.FIREPROOF_BUTTERFLY_NETS, "_from_fireproof", scroll, i);
                addScrollRecipe(recipeOutput, ItemRegistry.BOTTLED_BUTTERFLIES, "_from_bottle", scroll, i);
            }
        }

        for(int i = 0; i < ORIGAMI_COLORS.length; ++i) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BUTTERFLY_ORIGAMI.get(i).get())
                    .requires(ORIGAMI_COLORS[i])
                    .requires(Items.PAPER)
                    .unlockedBy(getHasName(ORIGAMI_COLORS[i]), has(ORIGAMI_COLORS[i]))
                    .save(recipeOutput);
        }

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BUTTERFLY_BANNER_PATTERN.get())
                .pattern(" s ")
                .pattern("sss")
                .pattern(" s ")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BUTTERFLY_FEEDER.get())
                .pattern("iii")
                .pattern("bsb")
                .pattern("b b")
                .define('i', Items.IRON_INGOT)
                .define('b', Items.BAMBOO)
                .define('s', Items.STRING)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BUTTERFLY_MICROSCOPE.get())
                .pattern(" i ")
                .pattern(" / ")
                .pattern(" - ")
                .define('i', Items.SPYGLASS)
                .define('/', Items.STICK)
                .define('-', Items.STONE_SLAB)
                .unlockedBy(getHasName(Items.SPYGLASS), has(Items.SPYGLASS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.EMPTY_BUTTERFLY_NET.get())
                .pattern("  /")
                .pattern(" /s")
                .pattern("/ss")
                .define('/', Items.STICK)
                .define('s', Items.STRING)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemRegistry.FIREPROOF_BUTTERFLY_NET.get())
                .requires(ItemRegistry.EMPTY_BUTTERFLY_NET.get())
                .requires(Items.MAGMA_CREAM)
                .unlockedBy(getHasName(Items.MAGMA_CREAM), has(Items.MAGMA_CREAM))
                .save(recipeOutput);

        int codlingIndex = ButterflyRegistry.getButterflyIndex("codling");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CATERPILLARS.get(codlingIndex).get())
                .requires(ItemRegistry.INFESTED_APPLE.get())
                .unlockedBy(getHasName(ItemRegistry.INFESTED_APPLE.get()), has(ItemRegistry.INFESTED_APPLE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.INFESTED_APPLE.get())
                .requires(ItemRegistry.CATERPILLARS.get(codlingIndex).get())
                .requires(Items.APPLE)
                .unlockedBy(getHasName(ItemRegistry.CATERPILLARS.get(codlingIndex).get()), has(ItemRegistry.CATERPILLARS.get(codlingIndex).get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAPER)
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(recipeOutput, new ResourceLocation(ButterfliesMod.MOD_ID, "paper_from_silk"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
                .pattern(" s ")
                .pattern("s s")
                .pattern(" s ")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(recipeOutput, new ResourceLocation(ButterfliesMod.MOD_ID, "string_from_silk"));
    }

    /**
     * Adds a bottle recipe.
     * @param recipeOutput The output data.
     * @param baseItems The base item for the recipe (e.g. net/caterpillar)
     * @param outputItems The list of items to take the output from.
     * @param groupName The group name.
     * @param butterflyIndex The butterfly index.
     */
    private void addBottleRecipe(@NotNull Consumer<FinishedRecipe> recipeOutput,
                                 List<RegistryObject<Item>> baseItems,
                                 List<RegistryObject<Item>> outputItems,
                                 String keySuffix,
                                 String groupName,
                                 int butterflyIndex) {
        Item base = baseItems.get(butterflyIndex).get();
        Item result = outputItems.get(butterflyIndex).get();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .group(groupName)
                .requires(base)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(getHasName(base), has(base))
                .save(recipeOutput, RecipeBuilder.getDefaultRecipeId(result) + keySuffix);
    }

    /**
     * Adds a bottle recipe.
     * @param recipeOutput The output data.
     * @param baseItems The base item for the recipe (e.g. net/caterpillar)
     * @param keySuffix A suffix to append to the key.
     * @param groupName The group name.
     * @param butterflyIndex The butterfly index.
     */
    private void addScrollRecipe(@NotNull Consumer<FinishedRecipe> recipeOutput,
                                 List<RegistryObject<Item>> baseItems,
                                 String keySuffix,
                                 String groupName,
                                 int butterflyIndex) {
        Item base = baseItems.get(butterflyIndex).get();
        Item result = ItemRegistry.BUTTERFLY_SCROLLS.get(butterflyIndex).get();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .group(groupName)
                .requires(base)
                .requires(Items.PAPER)
                .requires(Items.IRON_NUGGET)
                .unlockedBy(getHasName(base), has(base))
                .save(recipeOutput, RecipeBuilder.getDefaultRecipeId(result) + keySuffix);
    }
}
