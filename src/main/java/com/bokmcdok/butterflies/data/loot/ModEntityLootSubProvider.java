package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyTrait;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Adds loot tables for entities.
 */
public class ModEntityLootSubProvider extends EntityLoot {

    private final Set<EntityType<?>> knownEntityTypes;
    private final Set<EntityType<?>> silkChrysalises;

    /**
     * Construction.
     */
    public ModEntityLootSubProvider() {
        this.silkChrysalises = ButterflyRegistry.getButterflyDataCollection().stream()
                .filter(data -> data.hasTrait(ButterflyTrait.SILK))
                .map(data -> ButterflyEntityTypeRegistry.CHRYSALISES.get(data.butterflyIndex()).get())
                .collect(Collectors.toUnmodifiableSet());

        this.knownEntityTypes = Stream.concat(
                silkChrysalises.stream(),
                Stream.of(EntityTypeRegistry.BUTTERFLY_GOLEM.get())
        ).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Entry point.
     */
    @Override
    public void addTables() {
        silkChrysalises.forEach(this::addChrysalisSilkLoot);
        add(EntityTypeRegistry.BUTTERFLY_GOLEM.get(), createButterflyGolemLoot());
    }

    /**
     * Checks if an entity can have a loot table. Overridden to support Butterfly Golems.
     * @param entityType The entity type.
     * @return True if the entity can have a loot table.
     */
    @Override
    protected boolean isNonLiving(@NotNull EntityType<?> entityType) {
        return !knownEntityTypes.contains(entityType) && super.isNonLiving(entityType);
    }

    /**
     * Get the entities that need loot tables.
     * @return The vanilla entities.
     */
    @NotNull
    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return knownEntityTypes.stream().toList();
    }

    /**
     * Adds a silk drop to a chrysalis.
     * @param entityType The entity to add a loot table for.
     */
    private void addChrysalisSilkLoot(EntityType<?> entityType) {
        add(entityType, singleRollCountedLoot(
                ItemRegistry.SILK.get(),
                UniformGenerator.between(1.0f, 3.0f),
                LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))
        ));
    }

    /**
     * Creates loot for a butterfly golem.
     * @return The golem's Loot Table Builder.
     */
    private LootTable.Builder createButterflyGolemLoot() {
        return LootTable.lootTable()
                .withPool(singleRollPool(Items.POPPY, UniformGenerator.between(0.0f, 2.0f)))
                .withPool(singleRollPool(Items.IRON_INGOT, UniformGenerator.between(3.0f, 5.0f)));
    }

    /**
     * Creates a single roll for counted loot.
     * @param item The item to roll for.
     * @param count The count for the loot.
     * @param functions Any functions that can be applied to the roll.
     * @return A single roll for a loot table.
     */
    private LootTable.Builder singleRollCountedLoot(ItemLike item,
                                                    NumberProvider count,
                                                    LootItemFunction.Builder... functions) {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(count)));

        for (LootItemFunction.Builder function : functions) {
            pool.apply(function);
        }

        return LootTable.lootTable().withPool(pool);
    }


    /**
     * Creates a single roll for a loot table.
     * @param item The item to roll for.
     * @param count The count for the loot.
     * @return A single roll for a loot table.
     */
    private LootPool.Builder singleRollPool(ItemLike item,
                                            NumberProvider count) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(count)));
    }
}
