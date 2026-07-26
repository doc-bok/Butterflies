package com.bokmcdok.butterflies.common.data;


import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Consumer;

/**
 * Helper to hold a set of species advancements.
 */
public class SpeciesAdvancementSet {
    private final ModAdvancementGenerator generator;
    private final EnumMap<ActionType, ActionDefinition> actions = new EnumMap<>(ActionType.class);

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
    public SpeciesAdvancementSet(ModAdvancementGenerator generator,
                                  String flyer,
                                  String flyerPlural,
                                  String crawler,
                                  String crawlerPlural,
                                  int butterflyIndex) {

        this.generator = generator;
        actions.put(ActionType.CATCH, new ActionDefinition(
                "catch_" + flyer,
                "catch_all_" + flyerPlural,
                this.generator.task(ItemRegistry.EMPTY_BUTTERFLY_NET.get(), "catch_" + flyer),
                this.generator.challenge(ItemRegistry.BUTTERFLY_NETS.get(butterflyIndex).get(), "catch_all_" + flyerPlural, 100)
        ));

        actions.put(ActionType.FIND_EGG, new ActionDefinition(
                "find_" + flyer + "_egg",
                "find_all_" + flyer + "_eggs",
                this.generator.task(ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(), "find_" + flyer + "_egg"),
                this.generator.challenge(ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex).get(), "find_all_" + flyer + "_eggs", 100)
        ));

        actions.put(ActionType.FIND_CRAWLER, new ActionDefinition(
                "find_" + crawler,
                "find_all_" + crawlerPlural,
                this.generator.task(ItemRegistry.CATERPILLARS.get(butterflyIndex).get(), "find_" + crawler),
                this.generator.challenge(ItemRegistry.CATERPILLARS.get(butterflyIndex).get(), "find_all_" + crawlerPlural, 100)
        ));

        actions.put(ActionType.BOTTLE, new ActionDefinition(
                "bottle_" + flyer,
                "bottle_all_" + flyerPlural,
                this.generator.task(Items.GLASS_BOTTLE, "bottle_" + flyer),
                this.generator.challenge(ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex).get(), "bottle_all_" + flyerPlural, 150)
        ));

        actions.put(ActionType.BOTTLE_CRAWLER, new ActionDefinition(
                "bottle_" + crawler,
                "bottle_all_" + crawlerPlural,
                this.generator.task(Items.GLASS_BOTTLE, "bottle_" + crawler),
                this.generator.challenge(ItemRegistry.BOTTLED_CATERPILLARS.get(butterflyIndex).get(), "bottle_all_" + crawlerPlural, 150)
        ));
    }

    /**
     * Add all item criterion for a specific butterfly index.
     * @param butterflyIndex The Butterfly Index.
     */
    public void addAllItemCriterion(int butterflyIndex) {
        addCriterion(ActionType.CATCH,
                ItemRegistry.BUTTERFLY_NETS.get(butterflyIndex));

        addCriterion(ActionType.FIND_EGG, ItemRegistry.BUTTERFLY_EGGS.get(butterflyIndex));
        addCriterion(ActionType.FIND_CRAWLER, ItemRegistry.CATERPILLARS.get(butterflyIndex));
        addCriterion(ActionType.BOTTLE, ItemRegistry.BOTTLED_BUTTERFLIES.get(butterflyIndex));
        addCriterion(ActionType.BOTTLE_CRAWLER, ItemRegistry.BOTTLED_CATERPILLARS.get(butterflyIndex));
    }

    /**
     * Accessor for Catch One Advancement.
     * @return The Advancement Holder.
     */
    public AdvancementHolder getCatchOneAdvancement() {
        return catchOneAdvancement;
    }

    private void addCriterion(ActionType type,
                              DeferredHolder<Item, Item> item) {
        ActionDefinition def = actions.get(type);
        generator.addItemCriterion(def.oneBuilder(), item);
        generator.addItemCriterion(def.allBuilder(), item);
    }

    /**
     * Save all the advancements.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement.
     */
    public void saveAll(@NotNull Consumer<AdvancementHolder> saver,
                        AdvancementHolder root) {

        BuiltAction catchAction = savePair(saver, root, ActionType.CATCH);
        savePair(saver, root, ActionType.FIND_EGG);
        BuiltAction crawlerAction = savePair(saver, root, ActionType.FIND_CRAWLER);

        catchOneAdvancement = catchAction.oneHolder();

        savePair(saver, catchAction.oneHolder(), ActionType.BOTTLE);
        savePair(saver, crawlerAction.oneHolder(), ActionType.BOTTLE_CRAWLER);
    }

    /**
     * Saves a pair of advancements, one for a single species, the second
     * for all species.
     * @param saver A consumer used to write advancements to a file.
     * @param parent The parent advancement.
     * @return Advancement holder for the single species advancement.
     */
    private BuiltAction savePair(@NotNull Consumer<AdvancementHolder> saver,
                                 AdvancementHolder parent,
                                 ActionType type) {
        ActionDefinition def = actions.get(type);

        AdvancementHolder oneHolder = generator.save(
                saver,
                def.oneBuilder(),
                parent,
                AdvancementRequirements.Strategy.OR,
                def.oneId()
        );

        generator.save(
                saver,
                def.allBuilder(),
                oneHolder,
                AdvancementRequirements.Strategy.AND,
                def.allId()
        );

        return new BuiltAction(def, oneHolder);
    }
}