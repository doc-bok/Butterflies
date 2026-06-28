package com.bokmcdok.butterflies.data.loot;

import com.bokmcdok.butterflies.ButterfliesMod;
import com.bokmcdok.butterflies.registries.ButterflyEntityTypeRegistry;
import com.bokmcdok.butterflies.registries.EntityTypeRegistry;
import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.bokmcdok.butterflies.world.ButterflyData;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Adds loot tables for entities.
 */
public class ModEntityLootSubProvider extends EntityLoot {
    private static final List<String> SILK_SPECIES = List.of(
            "atlas_chrysalis",
            "carpet_chrysalis",
            "domestic_silk_chrysalis",
            "oak-silk_chrysalis");

    /**
     * Entry point.
     */
    @Override
    public void addTables() {
        SILK_SPECIES.stream()
                .map(ButterflyData::getButterflyIndex)
                .forEach(this::addChrysalisSilkLoot);

        add(EntityTypeRegistry.BUTTERFLY_GOLEM.get(), createButterflyGolemLoot());
    }

    /**
     * Checks if an entity can have a loot table. Overridden to support Butterfly Golems.
     * @param entityType The entity type.
     * @return True if the entity can have a loot table.
     */
    @Override
    protected boolean isNonLiving(@NotNull EntityType<?> entityType) {
        return super.isNonLiving(entityType) && !entityType.equals(EntityTypeRegistry.BUTTERFLY_GOLEM.get());
    }

    /**
     * Get the entities that need loot tables.
     * @return The vanilla entities.
     */
    @NotNull
    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        Stream<EntityType<?>> silkChrysalisesStream = StreamSupport.stream(ForgeRegistries.ENTITY_TYPES.spliterator(), false)
                .filter(entry ->
                        Optional.ofNullable(ForgeRegistries.ENTITY_TYPES.getKey(entry))
                                .filter(key -> key.getNamespace().equals(ButterfliesMod.MOD_ID)
                                        && StringUtils.equalsAny(key.getPath(), SILK_SPECIES.toArray(new String[0])))
                                .isPresent()
                );

        return Stream.concat(silkChrysalisesStream, Stream.of(EntityTypeRegistry.BUTTERFLY_GOLEM.get())).toList();
    }

    /**
     * Adds a silk drop to a chrysalis.
     * @param butterflyIndex The butterfly index.
     */
    private void addChrysalisSilkLoot(int butterflyIndex) {
        add(ButterflyEntityTypeRegistry.CHRYSALISES.get(butterflyIndex).get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(ItemRegistry.SILK.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));
    }

    /**
     * Creates loot for a butterfly golem.
     * @return The golem's Loot Table Builder.
     */
    private LootTable.Builder createButterflyGolemLoot() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.POPPY)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0f, 5.0f)))));
    }
}
