package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Generates all advancements for the mod.
 */
public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    /**
     * Entry point.
     * @param registries A lookup for registries and their objects.
     * @param saver A consumer used to write advancements to a file.
     * @param existingFileHelper A helper used to find whether a file exists.
     */
    @Override
    public void generate(@NotNull HolderLookup.Provider registries,
                         @NotNull Consumer<AdvancementHolder> saver,
                         @NotNull ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = createRoot(saver);
        createCollectionAdvancements(saver, root);
        createSpecialCatchAdvancements(saver, root);
        createMiscAdvancements(saver, root);
    }

    /**
     * Adds an item criterion to the specified builder.
     * @param builder The builder to modify.
     * @param item The item to add.
     */
    private void addItemCriterion(Advancement.Builder builder,
                                  RegistryObject<Item> item) {
        builder.addCriterion(
                Objects.requireNonNull(item.getKey()).location().getPath(),
                InventoryChangeTrigger.TriggerInstance.hasItems(item.get()));
    }

    /**
     * Creates a builder for a challenge advancement.
     * @param iconItem The item to use as an icon.
     * @param localization The string to use for localization.
     * @return A builder with the display setup.
     */
    private Advancement.Builder challenge(Item iconItem,
                                          String localization,
                                          int xpReward) {
        return Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(iconItem),
                        createTitleString(localization),
                        createDescriptionString(localization),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        false))
                .rewards(new AdvancementRewards(xpReward, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.NONE));
    }

    /**
     * Creates all collection-based advancements.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createCollectionAdvancements(@NotNull Consumer<AdvancementHolder> saver,
                                              AdvancementHolder root) {

        int atlasMothIndex = ButterflyData.getButterflyIndex("atlas");
        SpeciesAdvancementSet butterflyAdvancements = new SpeciesAdvancementSet("butterfly", "butterflies", "caterpillar", "caterpillars", 0);
        SpeciesAdvancementSet mothAdvancements = new SpeciesAdvancementSet("moth", "moths", "larva", "larvae", atlasMothIndex);

        Advancement.Builder createButterflyScrollBuilder = task(ItemRegistry.BUTTERFLY_SCROLLS.get(0).get(), "create_butterfly_scroll");

        for(int i = 0; i < ButterflyData.getTotalNumSpecies(); ++i) {
            ButterflyData butterflyData = ButterflyData.getEntry(i);
            if(butterflyData != null) {
                if (butterflyData.type() == ButterflyData.ButterflyType.BUTTERFLY) {
                    butterflyAdvancements.addAllItemCriterion(i);

                } else if(butterflyData.type() == ButterflyData.ButterflyType.MOTH) {
                    mothAdvancements.addAllItemCriterion(i);
                }

                addItemCriterion(createButterflyScrollBuilder, ItemRegistry.BUTTERFLY_SCROLLS.get(i));
            }
        }

        butterflyAdvancements.saveAll(saver, root);
        mothAdvancements.saveAll(saver, root);
        AdvancementHolder createButterflyScroll = save(saver, createButterflyScrollBuilder, butterflyAdvancements.catchOneAdvancement, AdvancementRequirements.Strategy.AND, "create_butterfly_scroll");

        CompoundTag fullButterflyBookTag = new CompoundTag();
        fullButterflyBookTag.putInt(CompoundTagId.CUSTOM_MODEL_DATA, 1);
        ItemPredicate.Builder fullButterflyBook = ItemPredicate.Builder.item().of(ItemRegistry.BUTTERFLY_BOOK.get()).hasNbt(fullButterflyBookTag);

        CompoundTag fullMothBookTag = new CompoundTag();
        fullMothBookTag.putInt(CompoundTagId.CUSTOM_MODEL_DATA, 2);
        ItemPredicate.Builder fullMothBook = ItemPredicate.Builder.item().of(ItemRegistry.BUTTERFLY_BOOK.get()).hasNbt(fullMothBookTag);

        CompoundTag fullBothBookTag = new CompoundTag();
        fullBothBookTag.putInt(CompoundTagId.CUSTOM_MODEL_DATA, 3);
        ItemPredicate.Builder fullBothBook = ItemPredicate.Builder.item().of(ItemRegistry.BUTTERFLY_BOOK.get()).hasNbt(fullBothBookTag);

        Advancement.Builder fillButterflyBookBuilder = challenge(ItemRegistry.BUTTERFLY_BOOK.get(), "fill_butterfly_book", 200);
        fillButterflyBookBuilder.addCriterion("butterflies", InventoryChangeTrigger.TriggerInstance.hasItems(fullButterflyBook));
        fillButterflyBookBuilder.addCriterion("both", InventoryChangeTrigger.TriggerInstance.hasItems(fullBothBook));
        save(saver, fillButterflyBookBuilder, createButterflyScroll, AdvancementRequirements.Strategy.OR, "fill_butterfly_book");

        Advancement.Builder fillMothBookBuilder = challenge(ItemRegistry.BUTTERFLY_BOOK.get(), "fill_moth_book", 200);
        fillMothBookBuilder.addCriterion("moths", InventoryChangeTrigger.TriggerInstance.hasItems(fullMothBook));
        fillMothBookBuilder.addCriterion("both", InventoryChangeTrigger.TriggerInstance.hasItems(fullBothBook));
        save(saver, fillMothBookBuilder, createButterflyScroll, AdvancementRequirements.Strategy.OR, "fill_moth_book");
    }

    /**
     * Creates a localization component for a description string.
     * @param localization The name of the advancement.
     * @return A new mutable component.
     */
    private MutableComponent createDescriptionString(String localization) {
        return Component.translatable("advancements.butterfly." + localization + ".description");
    }

    /**
     * Creates a resource location for an advancement.
     * @param localization The name of the advancement.
     * @return A new resource location.
     */
    private ResourceLocation createLocation(String localization) {
        return new ResourceLocation(ButterfliesMod.MOD_ID, "butterfly/" + localization);
    }

    /**
     * Creates advancements that don't fit in with other categories.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createMiscAdvancements(@NotNull Consumer<AdvancementHolder> saver,
                                        AdvancementHolder root) {
        Advancement.Builder builder = task(ItemRegistry.SILK.get(), "farm_silk");
        addItemCriterion(builder, ItemRegistry.SILK);
        save(saver, builder, root, "farm_silk");

        singleItemGoal(saver,
                ItemRegistry.ZHUANGZI_BOOK.get(),
                "find_zhuangzi",
                ItemRegistry.ZHUANGZI_BOOK,
                root,
                150);
    }

    /**
     * Creates the root advancement.
     * @param saver A consumer used to write advancements to a file.
     * @return The new root advancement.
     */
    private AdvancementHolder createRoot(@NotNull Consumer<AdvancementHolder> saver) {
        return Advancement.Builder.advancement()
            .display(new DisplayInfo(new ItemStack(ItemRegistry.EMPTY_BUTTERFLY_NET.get()),
                    createTitleString("root"),
                    createDescriptionString("root"),
                    new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK,
                    false,  // Show Toast
                    false,  // Announce Chat
                    false)) // Hidden
            .addCriterion("butterfly_net",
                    InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.EMPTY_BUTTERFLY_NET.get()))
            .save(saver, createLocation("root"));

    }

    /**
     * Creates advancements for catching special butterflies.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createSpecialCatchAdvancements(@NotNull Consumer<AdvancementHolder> saver,
                                                AdvancementHolder root) {

        int iceButterflyIndex = ButterflyData.getButterflyIndex("ice");
        singleItemGoal(saver,
                ItemRegistry.BUTTERFLY_SCROLLS.get(iceButterflyIndex).get(),
                "catch_ice_butterfly",
                ItemRegistry.BUTTERFLY_NETS.get(iceButterflyIndex),
                root,
                100);

        singleItemGoal(saver,
                ItemRegistry.BURNT_BUTTERFLY_NET.get(),
                "catch_lava_butterfly",
                ItemRegistry.BURNT_BUTTERFLY_NET,
                root,
                100);

        int lightButterflyIndex = ButterflyData.getButterflyIndex("light");
        singleItemGoal(saver,
                ItemRegistry.BUTTERFLY_SCROLLS.get(lightButterflyIndex).get(),
                "catch_light_butterfly",
                ItemRegistry.BUTTERFLY_NETS.get(lightButterflyIndex),
                root,
                100);

        int peacemakerButterflyIndex = ButterflyData.getButterflyIndex("peacemaker");
        singleItemGoal(saver,
                ItemRegistry.BUTTERFLY_SCROLLS.get(peacemakerButterflyIndex).get(),
                "catch_peacemaker_butterfly",
                ItemRegistry.BUTTERFLY_NETS.get(peacemakerButterflyIndex),
                root,
                200);
    }

    /**
     * Creates a localization component for a title string.
     * @param localization The name of the advancement.
     * @return A new mutable component.
     */
    private MutableComponent createTitleString(String localization) {
        return Component.translatable("advancements.butterfly." + localization + ".title");
    }

    /**
     * Creates a builder for a goal advancement.
     * @param iconItem The item to use as an icon.
     * @param localization The string to use for localization.
     * @return A builder with the display setup.
     */
    private Advancement.Builder goal(Item iconItem,
                                     String localization,
                                     int xpReward) {
        return Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(iconItem),
                        createTitleString(localization),
                        createDescriptionString(localization),
                        null,
                        FrameType.GOAL,
                        true,
                        false,
                        true))
                .rewards(new AdvancementRewards(xpReward, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.NONE));
    }

    /**
     * Creates a goal for obtaining a single item.
     * @param saver        The holder to save an advancement.
     * @param iconItem     The item to use as an icon.
     * @param localization The string to use for localization.
     * @param collectItem  The item to collect.
     * @param parent       The goal's parent.
     * @param xpReward     The experience reward for completing the goal.
     */
    private void singleItemGoal(@NotNull Consumer<AdvancementHolder> saver,
                                Item iconItem,
                                String localization,
                                RegistryObject<Item> collectItem,
                                AdvancementHolder parent,
                                int xpReward) {
        Advancement.Builder builder = goal(iconItem, localization.substring(1), xpReward);
        addItemCriterion(builder, collectItem);
        save(saver, builder, parent, localization);
    }

    /**
     * Saves an advancement.
     * @param saver   The holder to save an advancement.
     * @param builder The advancement builder.
     * @param parent  The parent of the advancement.
     * @param name    The name of the advancement.
     */
    private void save(@NotNull Consumer<AdvancementHolder> saver,
                      Advancement.Builder builder,
                      AdvancementHolder parent,
                      String name) {
        save(saver, builder, parent, AdvancementRequirements.Strategy.AND, name);
    }

    /**
     * Saves an advancement.
     * @param saver The holder to save an advancement.
     * @param builder The advancement builder.
     * @param parent The parent of the advancement.
     * @param strategy The strategy for aggregating requirements.
     * @param name The name of the advancement.
     * @return The completed advancement.
     */
    private AdvancementHolder save(@NotNull Consumer<AdvancementHolder> saver,
                                   Advancement.Builder builder,
                                   AdvancementHolder parent,
                                   AdvancementRequirements.Strategy strategy,
                                   String name) {
        return builder.parent(parent).requirements(strategy).save(saver, createLocation(name));
    }

    /**
     * Creates a builder for a task advancement.
     * @param iconItem The item to use as an icon.
     * @param localization The string to use for localization.
     * @return A builder with the display setup.
     */
    private Advancement.Builder task(Item iconItem,
                                     String localization) {
        return Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(iconItem),
                        createTitleString(localization),
                        createDescriptionString(localization),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false));
    }

    /**
     * Helper to hold a set of species advancements.
     */
    private class SpeciesAdvancementSet {
        private final Pair<String, Advancement.Builder> catchOne;
        private final Pair<String, Advancement.Builder> catchAll;
        private final Pair<String, Advancement.Builder> findEggOne;
        private final Pair<String, Advancement.Builder> findCrawlerOne;
        private final Pair<String, Advancement.Builder> findEggAll;
        private final Pair<String, Advancement.Builder> findCrawlerAll;
        private final Pair<String, Advancement.Builder> bottleOne;
        private final Pair<String, Advancement.Builder> bottleCrawlerOne;
        private final Pair<String, Advancement.Builder> bottleAll;
        private final Pair<String, Advancement.Builder> bottleCrawlerAll;

        // Used by the scroll advancement.
        private AdvancementHolder catchOneAdvancement;

        /**
         * Construction.
         * @param flyer The string to use for flyer advancements.
         * @param flyerPlural The string to use for "all" flyer advancements.
         * @param crawler The string to use for crawler advancements.
         * @param crawlerPlural The string to use for "all" crawler advancements.
         * @param butterflyIndex The butterfly index, used to generate icons.
         */
        private SpeciesAdvancementSet(String flyer,
                                      String flyerPlural,
                                      String crawler,
                                      String crawlerPlural,
                                      int butterflyIndex) {
            catchOne = new Pair<>("catch_" + flyer, task(ItemRegistry.EMPTY_BUTTERFLY_NET.get(), "catch_" + flyer));
            catchAll = new Pair<>("catch_all_" + flyerPlural, challenge(ItemRegistry.BUTTERFLY_NETS.get(butterflyIndex).get(), "catch_all_" + flyerPlural, 100));
            findEggOne = new Pair<>("find_" + flyer + "_egg", task(ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(), "find_" + flyer + "_egg"));
            findCrawlerOne = new Pair<>("find_" + crawler,task(ItemRegistry.CATERPILLARS.get(butterflyIndex).get(), "find_" + crawler));
            findEggAll = new Pair<>("find_all_" + flyer + "_eggs", challenge(ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(), "find_all_" + flyer + "_eggs", 100));
            findCrawlerAll = new Pair<>("find_all_" + crawlerPlural, challenge(ItemRegistry.CATERPILLARS.get(butterflyIndex).get(), "find_all_" + crawlerPlural, 100));
            bottleOne = new Pair<>("bottle_" + flyer, task(Items.GLASS_BOTTLE, "bottle_" + flyer));
            bottleCrawlerOne = new Pair<>("bottle_" + crawler,task(Items.GLASS_BOTTLE, "bottle_" + crawler));
            bottleAll = new Pair<>("bottle_all_" + flyerPlural, challenge(ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex).get(), "bottle_all_" + flyerPlural, 150));
            bottleCrawlerAll = new Pair<>("bottle_all_" + crawlerPlural, challenge(ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex).get(), "bottle_all_" + crawlerPlural, 150));
        }

        /**
         * Add all item criterion for a specific butterfly index.
         * @param butterflyIndex The Butterfly Index.
         */
        private void addAllItemCriterion(int butterflyIndex) {
            addItemCriterion(catchOne.getSecond(), ItemRegistry.BUTTERFLY_NETS.get(butterflyIndex));
            addItemCriterion(catchAll.getSecond(), ItemRegistry.BUTTERFLY_NETS.get(butterflyIndex));

            addItemCriterion(findEggOne.getSecond(), ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex));
            addItemCriterion(findCrawlerOne.getSecond(), ItemRegistry.CATERPILLARS.get(butterflyIndex));

            addItemCriterion(findEggAll.getSecond(), ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex));
            addItemCriterion(findCrawlerAll.getSecond(), ItemRegistry.CATERPILLARS.get(butterflyIndex));

            addItemCriterion(bottleOne.getSecond(), ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex));
            addItemCriterion(bottleCrawlerOne.getSecond(), ItemRegistry.BOTTLED_CATERPILLARS.get(butterflyIndex));

            addItemCriterion(bottleAll.getSecond(), ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex));
            addItemCriterion(bottleCrawlerAll.getSecond(), ItemRegistry.BOTTLED_CATERPILLARS.get(butterflyIndex));
        }

        /**
         * Save all the advancements.
         * @param saver A consumer used to write advancements to a file.
         * @param root The root advancement.
         */
        private void saveAll(@NotNull Consumer<AdvancementHolder> saver,
                             AdvancementHolder root) {

            catchOneAdvancement = save(saver, catchOne.getSecond(), root, AdvancementRequirements.Strategy.OR, catchOne.getFirst());
            save(saver, catchAll.getSecond(), catchOneAdvancement, AdvancementRequirements.Strategy.AND, catchAll.getFirst());

            AdvancementHolder findEggOneAdvancement = save(saver, findEggOne.getSecond(), root, AdvancementRequirements.Strategy.OR, findEggOne.getFirst());
            AdvancementHolder findCrawlerOneAdvancement = save(saver, findCrawlerOne.getSecond(), root, AdvancementRequirements.Strategy.OR, findCrawlerOne.getFirst());

            save(saver, findEggAll.getSecond(), findEggOneAdvancement, AdvancementRequirements.Strategy.AND, findEggAll.getFirst());
            save(saver, findCrawlerAll.getSecond(), findCrawlerOneAdvancement, AdvancementRequirements.Strategy.AND, findCrawlerAll.getFirst());

            AdvancementHolder bottleOneAdvancement = save(saver, bottleOne.getSecond(), catchOneAdvancement, AdvancementRequirements.Strategy.OR, bottleOne.getFirst());
            AdvancementHolder bottleCrawlerAdvancement = save(saver, bottleCrawlerOne.getSecond(), findCrawlerOneAdvancement, AdvancementRequirements.Strategy.OR, bottleCrawlerOne.getFirst());

            save(saver, bottleAll.getSecond(), bottleOneAdvancement, AdvancementRequirements.Strategy.AND, bottleAll.getFirst());
            save(saver, bottleCrawlerAll.getSecond(), bottleCrawlerAdvancement, AdvancementRequirements.Strategy.AND, bottleCrawlerAll.getFirst());
        }
    }
}
