package com.bokmcdok.butterflies.common.loot;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Supplier;

public abstract class BaseLootModifier extends LootModifier {

    /**
     * Construction
     * @param conditionsIn The conditions needed for this loot modifier to apply.
     */
    public BaseLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }
}
