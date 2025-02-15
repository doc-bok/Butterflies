package com.bokmcdok.butterflies.common.loot;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Supplier;

public abstract class BaseLootModifier extends LootModifier {

    // The item registry.
    protected final ItemRegistry itemRegistry;

    /**
     * Construction
     * @param conditionsIn The conditions needed for this loot modifier to apply.
     */
    public BaseLootModifier(ItemRegistry itemRegistry,
                            LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
        this.itemRegistry = itemRegistry;
    }
}
