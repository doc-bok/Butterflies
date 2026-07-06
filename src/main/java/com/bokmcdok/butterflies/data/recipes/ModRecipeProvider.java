package com.bokmcdok.butterflies.data.recipes;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generates recipes and their unlock advancements.
 */
public class ModRecipeProvider extends RecipeProvider {

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

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput,
                      CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @NotNull
        @Override
        protected RecipeProvider createRecipeProvider(@NotNull HolderLookup.Provider registries,
                                                      @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @NotNull
        @Override
        public String getName() {
            return "Recipes";
        }
    }


    /**
     * Construction.
     * @param output The pack to output to.
     */
    public ModRecipeProvider(HolderLookup.Provider registries,
                             RecipeOutput output) {
        super(registries, output);
    }

    /**
     * Entry point.
     */
    @Override
    protected void buildRecipes() {
        HolderGetter<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);

        for(int i = 0; i < ItemRegistry.BOTTLED_BUTTERFLIES.size(); ++i) {
            ButterflyData data = ButterflyData.getEntry(i);
            if (data != null) {
                boolean isButterfly = data.type() == ButterflyData.ButterflyType.BUTTERFLY;
                String bottledFlyer = isButterfly ? "bottled_butterfly" : "bottled_moth";
                String bottledCrawler = isButterfly ? "bottled_caterpillar" : "bottled_larva";
                String scroll = isButterfly ? "butterfly_scroll" : "moth_scroll";

                addBottleRecipe(itemRegistry, output, ItemRegistry.BUTTERFLY_NETS, ItemRegistry.BOTTLED_BUTTERFLIES, bottledFlyer, i);
                addBottleRecipe(itemRegistry, output, ItemRegistry.CATERPILLARS, ItemRegistry.BOTTLED_CATERPILLARS, bottledCrawler, i);

                addScrollRecipe(itemRegistry, output, ItemRegistry.BUTTERFLY_NETS, "_from_net", scroll, i);
                addScrollRecipe(itemRegistry, output, ItemRegistry.BOTTLED_BUTTERFLIES, "_from_bottle", scroll, i);
            }
        }

        for(int i = 0; i < ORIGAMI_COLORS.length; ++i) {
            ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, ItemRegistry.BUTTERFLY_ORIGAMI.get(i).get())
                    .requires(ORIGAMI_COLORS[i])
                    .requires(Items.PAPER)
                    .unlockedBy(getHasName(ORIGAMI_COLORS[i]), has(ORIGAMI_COLORS[i]))
                    .save(output);
        }

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ItemRegistry.BUTTERFLY_BANNER_PATTERN.get())
                .pattern(" s ")
                .pattern("sss")
                .pattern(" s ")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.DECORATIONS, ItemRegistry.BUTTERFLY_FEEDER.get())
                .pattern("iii")
                .pattern("bsb")
                .pattern("b b")
                .define('i', Items.IRON_INGOT)
                .define('b', Items.BAMBOO)
                .define('s', Items.STRING)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.DECORATIONS, ItemRegistry.BUTTERFLY_MICROSCOPE.get())
                .pattern(" i ")
                .pattern(" / ")
                .pattern(" - ")
                .define('i', Items.SPYGLASS)
                .define('/', Items.STICK)
                .define('-', Items.STONE_SLAB)
                .unlockedBy(getHasName(Items.SPYGLASS), has(Items.SPYGLASS))
                .save(output);

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.TOOLS, ItemRegistry.EMPTY_BUTTERFLY_NET.get())
                .pattern("  /")
                .pattern(" /s")
                .pattern("/ss")
                .define('/', Items.STICK)
                .define('s', Items.STRING)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        int codlingIndex = ButterflyData.getButterflyIndex("codling");
        ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, ItemRegistry.CATERPILLARS.get(codlingIndex).get())
                .requires(ItemRegistry.INFESTED_APPLE.get())
                .unlockedBy(getHasName(ItemRegistry.INFESTED_APPLE.get()), has(ItemRegistry.INFESTED_APPLE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, ItemRegistry.INFESTED_APPLE.get())
                .requires(ItemRegistry.CATERPILLARS.get(codlingIndex).get())
                .requires(Items.APPLE)
                .unlockedBy(getHasName(ItemRegistry.CATERPILLARS.get(codlingIndex).get()), has(ItemRegistry.CATERPILLARS.get(codlingIndex).get()))
                .save(output);

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, Items.PAPER)
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(output, ButterfliesMod.MOD_ID + ":paper_from_silk");

        ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, Items.STRING)
                .pattern(" s ")
                .pattern("s s")
                .pattern(" s ")
                .define('s', ItemRegistry.SILK.get())
                .unlockedBy(getHasName(ItemRegistry.SILK.get()), has(ItemRegistry.SILK.get()))
                .save(output, ButterfliesMod.MOD_ID + ":string_from_silk");
    }

    /**
     * Adds a bottle recipe.
     * @param output The output data.
     * @param baseItems The base item for the recipe (e.g. net/caterpillar)
     * @param outputItems The list of items to take the output from.
     * @param groupName The group name.
     * @param butterflyIndex The butterfly index.
     */
    private void addBottleRecipe(HolderGetter<Item> itemRegistry,
                                 @NotNull RecipeOutput output,
                                 List<DeferredHolder<Item, Item>> baseItems,
                                 List<DeferredHolder<Item, Item>> outputItems,
                                 String groupName,
                                 int butterflyIndex) {
        Item base = baseItems.get(butterflyIndex).get();
        ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, outputItems.get(butterflyIndex).get())
                .group(groupName)
                .requires(base)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(getHasName(base), has(base))
                .save(output);
    }

    /**
     * Adds a bottle recipe.
     * @param output The output data.
     * @param baseItems The base item for the recipe (e.g. net/caterpillar)
     * @param keySuffix A suffix to append to the key.
     * @param groupName The group name.
     * @param butterflyIndex The butterfly index.
     */
    private void addScrollRecipe(HolderGetter<Item> itemRegistry,
                                 @NotNull RecipeOutput output,
                                 List<DeferredHolder<Item, Item>> baseItems,
                                 String keySuffix,
                                 String groupName,
                                 int butterflyIndex) {
        Item base = baseItems.get(butterflyIndex).get();
        Item result = ItemRegistry.BUTTERFLY_SCROLLS.get(butterflyIndex).get();
        ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, result)
                .group(groupName)
                .requires(base)
                .requires(Items.PAPER)
                .requires(Items.IRON_NUGGET)
                .unlockedBy(getHasName(base), has(base))
                .save(output, RecipeBuilder.getDefaultRecipeId(result) + keySuffix);
    }
}
