package com.bokmcdok.butterflies.common.loot;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A loot modifier to add treasure to some chests.
 */
public class OakLeavesLootModifier extends LootModifier {

    // The item registry.
    private final ItemRegistry itemRegistry;

    // The codec that is registered with Forge.
    private final Supplier<Codec<OakLeavesLootModifier>> codec = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, this::createOakLeavesLootModifier)));

    /**
     * Construction
     * @param conditionsIn The conditions needed for this loot modifier to apply.
     */
    public OakLeavesLootModifier(ItemRegistry itemRegistry,
                                 LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
        this.itemRegistry = itemRegistry;
    }

    /**
     * Apply the modification to the loot.
     * @param generatedLoot the list of ItemStacks that will be dropped, generated by loot tables
     * @param context the LootContext, identical to what is passed to loot tables
     * @return The potentially updated loot.
     */
    @NotNull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();

        if (random.nextInt(400) == 1) {
            ItemStack stack = new ItemStack(itemRegistry.getInfestedApple().get());
            generatedLoot.add(stack);
        }

        return generatedLoot;
    }

    /**
     * Get the codec.
     * @return The codec.
     */
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return codec.get();
    }

    private OakLeavesLootModifier createOakLeavesLootModifier(LootItemCondition[] conditionsIn) {
        return new OakLeavesLootModifier(itemRegistry, conditionsIn);
    }

    public Supplier<Codec<OakLeavesLootModifier>> getCodec() {
        return codec;
    }
}
