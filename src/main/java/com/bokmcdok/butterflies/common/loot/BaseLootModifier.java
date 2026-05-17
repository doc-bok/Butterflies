package com.bokmcdok.butterflies.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Supplier;

public abstract class BaseLootModifier extends LootModifier {
    // The codec that is registered with Forge.
    private final Supplier<Codec<BaseLootModifier>> codec = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, this::create)));

    /**
     * Construction
     * @param conditionsIn The conditions needed for this loot modifier to apply.
     */
    public BaseLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }
}
