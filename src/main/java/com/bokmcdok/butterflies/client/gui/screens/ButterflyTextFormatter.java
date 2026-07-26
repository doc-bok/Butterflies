package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyHabitat;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ButterflyTextFormatter {

    /**
     * Returns a collection of formatted components ready to render as text.
     * @param butterflyIndex The butterfly index.
     * @return Formatted, localized text.
     */
    public static FormattedText getFormattedButterflyData(int butterflyIndex) {
        ButterflyData entry = ButterflyRegistry.getEntry(butterflyIndex);
        if (entry != null) {
            //  Butterfly name
            MutableComponent component = Component.translatable("entity.butterflies." + entry.entityId());

            if (entry.type() == ButterflyType.SPECIAL) {
                component.withStyle(ChatFormatting.DARK_BLUE);
            }

            // Rarity
            component.append("\n\n");
            component.append(Component.translatable("gui.butterflies.rarity"));
            switch (entry.rarity()) {
                case RARE -> component.append(Component.translatable("gui.butterflies.rarity.rare"));
                case UNCOMMON -> component.append(Component.translatable("gui.butterflies.rarity.uncommon"));
                case COMMON -> component.append(Component.translatable("gui.butterflies.rarity.common"));
                default -> {
                }
            }

            // Size
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.size"));
            switch (entry.size()) {
                case TINY -> component.append(Component.translatable("gui.butterflies.size.tiny"));
                case SMALL -> component.append(Component.translatable("gui.butterflies.size.small"));
                case MEDIUM -> component.append(Component.translatable("gui.butterflies.size.medium"));
                case LARGE -> component.append(Component.translatable("gui.butterflies.size.large"));
                case HUGE -> component.append(Component.translatable("gui.butterflies.size.huge"));
                default -> {
                }
            }

            // Speed
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.speed"));
            switch (entry.speed()) {
                case SLOW -> component.append(Component.translatable("gui.butterflies.speed.slow"));
                case MODERATE -> component.append(Component.translatable("gui.butterflies.speed.moderate"));
                case FAST -> component.append(Component.translatable("gui.butterflies.speed.fast"));
                default -> {
                }
            }

            // Lifespan
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.lifespan"));
            switch (entry.getOverallLifeSpan()) {
                case SHORT -> component.append(Component.translatable("gui.butterflies.lifespan.short"));
                case MEDIUM -> component.append(Component.translatable("gui.butterflies.lifespan.average"));
                case LONG -> component.append(Component.translatable("gui.butterflies.lifespan.long"));
                case IMMORTAL -> component.append(Component.translatable("gui.butterflies.lifespan.immortal"));
                default -> {
                }
            }

            // Habitat
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.habitat"));

            // If there are no habitats we still need a string.
            if (entry.habitats().isEmpty()) {
                component.append(Component.translatable("gui.butterflies.habitat.none"));
            }

            // When this flag is true we add commas
            boolean comma = false;
            for (ButterflyHabitat habitat : entry.habitats()) {
                if (comma) {
                    component.append(Component.translatable("gui.butterflies.habitat.comma"));
                }

                switch (habitat) {
                    case FORESTS -> component.append(Component.translatable("gui.butterflies.habitat.forests"));
                    case HILLS -> component.append(Component.translatable("gui.butterflies.habitat.hills"));
                    case JUNGLES -> component.append(Component.translatable("gui.butterflies.habitat.jungles"));
                    case PLAINS -> component.append(Component.translatable("gui.butterflies.habitat.plains"));
                    case ICE -> component.append(Component.translatable("gui.butterflies.habitat.ice"));
                    case NETHER -> component.append(Component.translatable("gui.butterflies.habitat.nether"));
                    case PLATEAUS -> component.append(Component.translatable("gui.butterflies.habitat.plateaus"));
                    case SAVANNAS -> component.append(Component.translatable("gui.butterflies.habitat.savannas"));
                    case WETLANDS -> component.append(Component.translatable("gui.butterflies.habitat.wetlands"));
                    case VILLAGES -> component.append(Component.translatable("gui.butterflies.habitat.villages"));
                    case END -> component.append(Component.translatable("gui.butterflies.habitat.end"));
                    default -> {
                    }
                }

                // If there is more than one habitat, create a comma-separated list.
                comma = true;
            }

            // Preferred Flower
            component.append("\n");
            component.append(Component.translatable("gui.butterflies.preferred_flower"));

            Optional<Holder.Reference<Item>> preferredFlower = BuiltInRegistries.ITEM.get(entry.preferredFlower());
            if (preferredFlower.isPresent()) {
                Component description = preferredFlower.get().value().getName();
                component.append(description);
            }

            // Fact
            component.append("\n\n");
            component.append(Component.translatable("gui.butterflies.fact." + entry.entityId()));

            return component;
        }

        return null;
    }
}
