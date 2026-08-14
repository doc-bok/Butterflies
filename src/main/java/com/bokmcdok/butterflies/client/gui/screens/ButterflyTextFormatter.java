package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.butterfly_data.ButterflyData;
import com.bokmcdok.butterflies.butterfly_data.ButterflyHabitat;
import com.bokmcdok.butterflies.butterfly_data.ButterflyRegistry;
import com.bokmcdok.butterflies.butterfly_data.ButterflyType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

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
            MutableComponent component = new TranslatableComponent("entity.butterflies." + entry.speciesId());

            if (entry.type() == ButterflyType.SPECIAL) {
                component.withStyle(ChatFormatting.DARK_BLUE);
            }

            // Rarity
            component.append("\n\n");
            component.append(new TranslatableComponent("gui.butterflies.rarity"));
            switch (entry.rarity()) {
                case RARE -> component.append(new TranslatableComponent("gui.butterflies.rarity.rare"));
                case UNCOMMON -> component.append(new TranslatableComponent("gui.butterflies.rarity.uncommon"));
                case COMMON -> component.append(new TranslatableComponent("gui.butterflies.rarity.common"));
                default -> {
                }
            }

            // Size
            component.append("\n");
            component.append(new TranslatableComponent("gui.butterflies.size"));
            switch (entry.size()) {
                case TINY -> component.append(new TranslatableComponent("gui.butterflies.size.tiny"));
                case SMALL -> component.append(new TranslatableComponent("gui.butterflies.size.small"));
                case MEDIUM -> component.append(new TranslatableComponent("gui.butterflies.size.medium"));
                case LARGE -> component.append(new TranslatableComponent("gui.butterflies.size.large"));
                case HUGE -> component.append(new TranslatableComponent("gui.butterflies.size.huge"));
                default -> {
                }
            }

            // Speed
            component.append("\n");
            component.append(new TranslatableComponent("gui.butterflies.speed"));
            switch (entry.speed()) {
                case SLOW -> component.append(new TranslatableComponent("gui.butterflies.speed.slow"));
                case MODERATE -> component.append(new TranslatableComponent("gui.butterflies.speed.moderate"));
                case FAST -> component.append(new TranslatableComponent("gui.butterflies.speed.fast"));
                default -> {
                }
            }

            // Lifespan
            component.append("\n");
            component.append(new TranslatableComponent("gui.butterflies.lifespan"));
            switch (entry.getOverallLifeSpan()) {
                case SHORT -> component.append(new TranslatableComponent("gui.butterflies.lifespan.short"));
                case MEDIUM -> component.append(new TranslatableComponent("gui.butterflies.lifespan.average"));
                case LONG -> component.append(new TranslatableComponent("gui.butterflies.lifespan.long"));
                case IMMORTAL -> component.append(new TranslatableComponent("gui.butterflies.lifespan.immortal"));
                default -> {
                }
            }

            // Habitat
            component.append("\n");
            component.append(new TranslatableComponent("gui.butterflies.habitat"));

            // If there are no habitats we still need a string.
            if (entry.habitats().isEmpty()) {
                component.append(new TranslatableComponent("gui.butterflies.habitat.none"));
            }

            // When this flag is true we add commas
            boolean comma = false;
            for (ButterflyHabitat habitat : entry.habitats()) {
                if (comma) {
                    component.append(new TranslatableComponent("gui.butterflies.habitat.comma"));
                }

                switch (habitat) {
                    case FORESTS -> component.append(new TranslatableComponent("gui.butterflies.habitat.forests"));
                    case HILLS -> component.append(new TranslatableComponent("gui.butterflies.habitat.hills"));
                    case JUNGLES -> component.append(new TranslatableComponent("gui.butterflies.habitat.jungles"));
                    case PLAINS -> component.append(new TranslatableComponent("gui.butterflies.habitat.plains"));
                    case ICE -> component.append(new TranslatableComponent("gui.butterflies.habitat.ice"));
                    case NETHER -> component.append(new TranslatableComponent("gui.butterflies.habitat.nether"));
                    case PLATEAUS -> component.append(new TranslatableComponent("gui.butterflies.habitat.plateaus"));
                    case SAVANNAS -> component.append(new TranslatableComponent("gui.butterflies.habitat.savannas"));
                    case WETLANDS -> component.append(new TranslatableComponent("gui.butterflies.habitat.wetlands"));
                    case VILLAGES -> component.append(new TranslatableComponent("gui.butterflies.habitat.villages"));
                    case END -> component.append(new TranslatableComponent("gui.butterflies.habitat.end"));
                    default -> {
                    }
                }

                // If there is more than one habitat, create a comma-separated list.
                comma = true;
            }

            // Preferred Flower
            component.append("\n");
            component.append(new TranslatableComponent("gui.butterflies.preferred_flower"));

            Item value = ForgeRegistries.ITEMS.getValue(entry.foodItem());
            if (value != null) {
                Component description = value.getDescription();
                component.append(description);
            }

            // Fact
            component.append("\n\n");
            component.append(new TranslatableComponent("gui.butterflies.fact." + entry.speciesId()));

            return component;
        }

        return null;
    }
}
