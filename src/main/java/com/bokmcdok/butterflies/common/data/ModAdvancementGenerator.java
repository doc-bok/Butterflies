package com.bokmcdok.butterflies.common.data;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyType;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.registries.PeacemakerEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.SpawnEggRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Generates all advancements for the mod.
 */
public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    // Definition of Special Catch Advancements.
    private static final List<SpecialCatchDefinition> SPECIAL_CATCHES;

    // Values for book predicates for Butterfly Book Advancements.
    private static final int BOOK_BUTTERFLIES = 1;
    private static final int BOOK_MOTHS = 2;
    private static final int BOOK_BOTH = 3;

    /**
     * Entry point.
     * @param registries A lookup for registries and their objects.
     * @param saver A consumer used to write advancements to a file.
     * @param existingFileHelper A helper used to find whether a file exists.
     */
    @Override
    public void generate(@NotNull HolderLookup.Provider registries,
                         @NotNull Consumer<Advancement> saver,
                         @NotNull ExistingFileHelper existingFileHelper) {
        Advancement root = createRoot(saver);
        createCollectionAdvancements(saver, root);
        createSpecialCatchAdvancements(saver, root);
        createPeacemakerAdvancements(saver);
        createMiscAdvancements(saver, root);
    }

    /**
     * Creates the root advancement.
     * @param saver A consumer used to write advancements to a file.
     * @return The new root advancement.
     */
    private Advancement createRoot(@NotNull Consumer<Advancement> saver) {
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
                .save(saver, butterflyLocation("root"));

    }

    /**
     * Creates all collection-based advancements.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createCollectionAdvancements(@NotNull Consumer<Advancement> saver,
                                              Advancement root) {

        int atlasMothIndex = ButterflyRegistry.getButterflyIndex("atlas");
        SpeciesAdvancementSet butterflyAdvancements = new SpeciesAdvancementSet(this, "butterfly", "butterflies", "caterpillar", "caterpillars", 0);
        SpeciesAdvancementSet mothAdvancements = new SpeciesAdvancementSet(this, "moth", "moths", "larva", "larvae", atlasMothIndex);

        Advancement.Builder createButterflyScrollBuilder = task(ItemRegistry.BUTTERFLY_SCROLLS.get(0).get(), "create_butterfly_scroll");

        for(int i = 0; i < ButterflyRegistry.getTotalNumSpecies(); ++i) {
            ButterflyData butterflyData = ButterflyRegistry.getEntry(i);
            if(butterflyData != null) {
                if (butterflyData.type() == ButterflyType.BUTTERFLY) {
                    butterflyAdvancements.addAllItemCriterion(i);

                } else if(butterflyData.type() == ButterflyType.MOTH) {
                    mothAdvancements.addAllItemCriterion(i);
                }

                addItemCriterion(createButterflyScrollBuilder, ItemRegistry.BUTTERFLY_SCROLLS.get(i));
            }
        }

        butterflyAdvancements.saveAll(saver, root);
        mothAdvancements.saveAll(saver, root);
        Advancement createButterflyScroll = save(saver, createButterflyScrollBuilder, butterflyAdvancements.getCatchOneAdvancement(), RequirementsStrategy.AND, "create_butterfly_scroll");

        ItemPredicate.Builder fullButterflyBook = butterflyBookWithModelData(BOOK_BUTTERFLIES);
        ItemPredicate.Builder fullMothBook = butterflyBookWithModelData(BOOK_MOTHS);
        ItemPredicate.Builder fullBothBook = butterflyBookWithModelData(BOOK_BOTH);

        Advancement.Builder fillButterflyBookBuilder = challenge(ItemRegistry.BUTTERFLY_BOOK.get(), "fill_butterfly_book", 200);
        fillButterflyBookBuilder.addCriterion("butterflies", InventoryChangeTrigger.TriggerInstance.hasItems(fullButterflyBook.build()));
        fillButterflyBookBuilder.addCriterion("both", InventoryChangeTrigger.TriggerInstance.hasItems(fullBothBook.build()));
        save(saver, fillButterflyBookBuilder, createButterflyScroll, RequirementsStrategy.OR, "fill_butterfly_book");

        Advancement.Builder fillMothBookBuilder = challenge(ItemRegistry.BUTTERFLY_BOOK.get(), "fill_moth_book", 200);
        fillMothBookBuilder.addCriterion("moths", InventoryChangeTrigger.TriggerInstance.hasItems(fullMothBook.build()));
        fillMothBookBuilder.addCriterion("both", InventoryChangeTrigger.TriggerInstance.hasItems(fullBothBook.build()));
        save(saver, fillMothBookBuilder, createButterflyScroll, RequirementsStrategy.OR, "fill_moth_book");
    }

    /**
     * Creates advancements for catching special butterflies.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createSpecialCatchAdvancements(@NotNull Consumer<Advancement> saver,
                                                Advancement root) {
        for (SpecialCatchDefinition definition : SPECIAL_CATCHES) {
            dualItemGoal(
                    saver,
                    definition.iconItem(),
                    definition.localization(),
                    definition.collectItem(),
                    definition.fireproofCollectItem(),
                    root,
                    definition.xpReward());
        }
    }

    /**
     * Creates all the Peacemaker Advancements.
     * @param saver A consumer used to write advancements to a file.
     */
    private void createPeacemakerAdvancements(@NotNull Consumer<Advancement> saver) {
        int peacemakerIndex = ButterflyRegistry.getButterflyIndex("peacemaker");

        // Root - What's Project Butterfly?
        Advancement.Builder rootBuilder = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(SpawnEggRegistry.PEACEMAKER_BUTTERFLY_SPAWN_EGG.get()),
                        createTitleString("peacemaker_root"),
                        createDescriptionString("peacemaker_root"),
                        new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.TASK,
                        false,  // Show Toast
                        false,  // Announce Chat
                        false)); // Hidden
        addKillPeacemakerEntityCriterion(rootBuilder);
        Advancement root = rootBuilder
                .requirements(RequirementsStrategy.OR)
                .save(saver, peacemakerLocation("root"));

        // Peace Catcher.
        Advancement.Builder peaceCatcherBuilder = task(ItemRegistry.BUTTERFLY_NETS.get(peacemakerIndex).get(), "peace_catcher");
        addItemCriterion(peaceCatcherBuilder, ItemRegistry.BUTTERFLY_NETS.get(peacemakerIndex));
        Advancement peaceCatcher = peaceCatcherBuilder
                .parent(root)
                .requirements(RequirementsStrategy.OR)
                .save(saver, peacemakerLocation("peace_catcher"));

        // Milk and Honey.
        Advancement.Builder milkAndHoneyBuilder = task(ItemRegistry.PEACEMAKER_HONEY_BOTTLE.get(), "milk_and_honey");
        addItemCriterion(milkAndHoneyBuilder, ItemRegistry.PEACEMAKER_HONEY_BOTTLE);
        milkAndHoneyBuilder
                .parent(root)
                .requirements(RequirementsStrategy.OR)
                .save(saver, peacemakerLocation("milk_and_honey"));

        // You Forgot to Tell Him About the Cow.
        Advancement.Builder cowBuilder = goal(SpawnEggRegistry.PEACEMAKER_COW_SPAWN_EGG.get(), "forgot_to_tell", 300);
        addKillPeacemakerEntityCriterion(cowBuilder);
        cowBuilder
                .parent(root)
                .requirements(RequirementsStrategy.AND)
                .save(saver, peacemakerLocation("forgot_to_tell"));

        // Oh. Project Butterfly.
        Advancement.Builder projectButterflyBuilder = task(ItemRegistry.BUTTERFLY_SCROLLS.get(peacemakerIndex).get(), "project_butterfly");
        addItemCriterion(projectButterflyBuilder, ItemRegistry.BUTTERFLY_SCROLLS.get(peacemakerIndex));
        projectButterflyBuilder
                .parent(peaceCatcher)
                .requirements(RequirementsStrategy.OR)
                .save(saver, peacemakerLocation("project_butterfly"));

        // Eek Stack Ik Ik.
        Advancement.Builder eekStackBuilder = task(ItemRegistry.BOTTLED_BUTTERFLIES.get(peacemakerIndex).get(), "eek_stack");
        addItemCriterion(eekStackBuilder, ItemRegistry.BOTTLED_BUTTERFLIES.get(peacemakerIndex));
        eekStackBuilder
                .parent(peaceCatcher)
                .requirements(RequirementsStrategy.OR)
                .save(saver, peacemakerLocation("eek_stack"));
    }

    /**
     * Creates advancements that don't fit in with other categories.
     * @param saver A consumer used to write advancements to a file.
     * @param root The root advancement in the tree.
     */
    private void createMiscAdvancements(@NotNull Consumer<Advancement> saver,
                                        Advancement root) {
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
     * Adds an item criterion to the specified builder.
     * @param builder The builder to modify.
     * @param item The item to add.
     */
    public void addItemCriterion(Advancement.Builder builder,
                                 RegistryObject<Item> item) {
        builder.addCriterion(
                Objects.requireNonNull(item.getKey()).location().getPath(),
                InventoryChangeTrigger.TriggerInstance.hasItems(item.get()));
    }

    /**
     * Adds kill entity criterion for all Peacemaker Entities.
     * @param builder The builder to modify.
     */
    private void addKillPeacemakerEntityCriterion(Advancement.Builder builder) {
        for (RegistryObject<?> entityType : PeacemakerEntityTypeRegistry.PEACEMAKER_ENTITIES) {
            builder.addCriterion(Objects.requireNonNull(entityType.getKey()).location().getPath(),
                    KilledTrigger.TriggerInstance.playerKilledEntity(
                            EntityPredicate.Builder.entity().of((EntityType<?>) entityType.get())));
        }
    }

    /**
     * Creates a basic advancement builder.
     *
     * @param iconItem     The item to use for an icon.
     * @param localization The ID of the localization string.
     * @param frame        The frame type to use.
     * @param xpReward     The XP reward for completing the advancement.
     * @param announceChat Should we announce to chat?
     * @param hidden       Should the advancement be hidden?
     * @return The new advancement builder.
     */
    private Advancement.Builder advancement(
            Item iconItem,
            String localization,
            FrameType frame,
            int xpReward,
            boolean announceChat,
            boolean hidden) {

        Advancement.Builder builder = Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(iconItem),
                        createTitleString(localization),
                        createDescriptionString(localization),
                        null,
                        frame,
                        true,
                        announceChat,
                        hidden
                ));

        if (xpReward > 0) {
            builder.rewards(new AdvancementRewards(
                    xpReward,
                    new ResourceLocation[0],
                    new ResourceLocation[0],
                    CommandFunction.CacheableFunction.NONE));
        }

        return builder;
    }

    /**
     * Creates a builder for a task advancement.
     * @param iconItem The item to use as an icon.
     * @param localization The string to use for localization.
     * @return A builder with the display setup.
     */
    public Advancement.Builder task(Item iconItem,
                                    String localization) {
        return advancement(iconItem, localization, FrameType.TASK, 0, true, false);
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
        return advancement(iconItem, localization, FrameType.GOAL, xpReward, false, true);
    }

    /**
     * Creates a builder for a challenge advancement.
     * @param iconItem The item to use as an icon.
     * @param localization The string to use for localization.
     * @return A builder with the display setup.
     */
    public Advancement.Builder challenge(Item iconItem,
                                         String localization,
                                         int xpReward) {
        return advancement(iconItem, localization, FrameType.CHALLENGE, xpReward, true, false);
    }

    /**
     * Helper to handle custom model data for Butterfly Book Advancements.
     * @param customModelData The value needed for the advancement.
     * @return The new item predicate builder.
     */
    private ItemPredicate.Builder butterflyBookWithModelData(int customModelData) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(CompoundTagId.CUSTOM_MODEL_DATA, customModelData);

        return ItemPredicate.Builder.item()
                .of(ItemRegistry.BUTTERFLY_BOOK.get())
                .hasNbt(tag);
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
     * Creates a resource location for a Butterfly Advancement.
     * @param localization The name of the advancement.
     * @return A new resource location.
     */
    private String butterflyLocation(String localization) {
        return ButterfliesMod.MOD_ID + ":butterfly/" + localization;
    }

    /**
     * Creates a resource location for a Peacemaker Advancement.
     * @param localization The name of the advancement.
     * @return A new resource location.
     */
    private String peacemakerLocation(String localization) {
        return ButterfliesMod.MOD_ID + ":peacemaker/" + localization;
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
     * Creates a goal for obtaining a single item.
     * @param saver        The holder to save an advancement.
     * @param iconItem     The item to use as an icon.
     * @param localization The string to use for localization.
     * @param collectItem  The item to collect.
     * @param parent       The goal's parent.
     * @param xpReward     The experience reward for completing the goal.
     */
    private void singleItemGoal(@NotNull Consumer<Advancement> saver,
                                Item iconItem,
                                String localization,
                                RegistryObject<Item> collectItem,
                                Advancement parent,
                                int xpReward) {
        Advancement.Builder builder = goal(iconItem, localization, xpReward);
        addItemCriterion(builder, collectItem);
        save(saver, builder, parent, localization);
    }

    /**
     * Creates a goal for obtaining one of two items.
     * @param saver        The holder to save an advancement.
     * @param iconItem     The item to use as an icon.
     * @param localization The string to use for localization.
     * @param collectItem1 The item to collect.
     * @param collectItem2 The item to collect.
     * @param parent       The goal's parent.
     * @param xpReward     The experience reward for completing the goal.
     */
    private void dualItemGoal(@NotNull Consumer<Advancement> saver,
                              Item iconItem,
                              String localization,
                              RegistryObject<Item> collectItem1,
                              RegistryObject<Item> collectItem2,
                              Advancement parent,
                              int xpReward) {
        if (collectItem1 == collectItem2) {
            singleItemGoal(saver, iconItem, localization, collectItem1, parent, xpReward);
            return;
        }

        Advancement.Builder builder = goal(iconItem, localization, xpReward);
        addItemCriterion(builder, collectItem1);
        addItemCriterion(builder, collectItem2);
        save(saver, builder, parent, RequirementsStrategy.OR, localization);
    }

    /**
     * Saves an advancement.
     * @param saver   The holder to save an advancement.
     * @param builder The advancement builder.
     * @param parent  The parent of the advancement.
     * @param name    The name of the advancement.
     */
    private void save(@NotNull Consumer<Advancement> saver,
                      Advancement.Builder builder,
                      Advancement parent,
                      String name) {
        save(saver, builder, parent, RequirementsStrategy.AND, name);
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
    public Advancement save(@NotNull Consumer<Advancement> saver,
                                  Advancement.Builder builder,
                                  Advancement parent,
                                  RequirementsStrategy strategy,
                                  String name) {
        return builder.parent(parent).requirements(strategy).save(saver, butterflyLocation(name));
    }

    static {
        SPECIAL_CATCHES  = List.of(
                new SpecialCatchDefinition("ice", "catch_ice_butterfly", 100, false, false),
                new SpecialCatchDefinition("lava", "catch_lava_butterfly", 100, true, false),
                new SpecialCatchDefinition("lava", "catch_lava_butterfly_for_real", 100, false, true),
                new SpecialCatchDefinition("light", "catch_light_butterfly", 100, false, false),
                new SpecialCatchDefinition("obsidian", "catch_obsidian_butterfly", 200, false, false)
        );
    }
}
