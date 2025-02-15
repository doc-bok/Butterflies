package com.bokmcdok.butterflies.common.loot;

import com.bokmcdok.butterflies.registries.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Random;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

/**
 * A loot modifier to add treasure to some chests.
 */
public class ButterflyLootModifier extends BaseLootModifier {

    /**
     * Construction
     * @param conditionsIn The conditions needed for this loot modifier to apply.
     */
    public ButterflyLootModifier(ItemRegistry itemRegistry,
                                 LootItemCondition[] conditionsIn)
    {
        super(itemRegistry, conditionsIn);
    }

    /**
     * Apply the modification to the loot.
     * @param generatedLoot the list of ItemStacks that will be dropped, generated by loot tables
     * @param context the LootContext, identical to what is passed to loot tables
     * @return The potentially updated loot.
     */
    @NotNull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                              LootContext context) {
        Random random = context.getRandom();

        if (random.nextInt(32) == 1) {
            ItemStack stack = new ItemStack(itemRegistry.getZhuangziBook().get());
            generatedLoot.add(stack);
        }

        return generatedLoot;
    }

    /**
     * Helper method to create the loot modifier.
     * @param conditionsIn The conditions for this loot modifier.
     * @return A new loot modifier.
     */
    @Override
    protected BaseLootModifier create(LootItemCondition[] conditionsIn) {
        return new ButterflyLootModifier(itemRegistry, conditionsIn);
    }
}
