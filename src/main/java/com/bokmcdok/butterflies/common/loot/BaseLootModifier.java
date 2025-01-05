package com.bokmcdok.butterflies.common.loot;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.function.Supplier;

public abstract class BaseLootModifier extends LootModifier {

    // The item registry.
    protected final ItemRegistry itemRegistry;

    // The codec that is registered with Forge.
    private final Supplier<Codec<BaseLootModifier>> codec = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, this::create)));

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

    /**
     * Get the codec.
     * @return The codec.
     */
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return codec.get();
    }

    /**
     * Accessor for the codec.
     * @return The loot modifier's codec.
     */
    public Supplier<Codec<BaseLootModifier>> getCodec() {
        return codec;
    }

    /**
     * Helper method to create the loot modifier.
     * @param conditionsIn The conditions for this loot modifier.
     * @return A new loot modifier.
     */
    protected abstract BaseLootModifier create(LootItemCondition[] conditionsIn);
}
