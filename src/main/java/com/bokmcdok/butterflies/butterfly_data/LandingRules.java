package com.bokmcdok.butterflies.butterfly_data;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Holds the landing rules for a butterfly.
 */
public final class LandingRules {
    private final Set<Holder.Reference<Block>> landingBlocks;
    private final Set<TagKey<Block>> landingBlockTags;

    /**
     * Construction - pregenerate the landing rules
     * @param extraLandingBlocks The list of Resource Locations and Tags
     */
    public LandingRules(Set<String> extraLandingBlocks) {
        this.landingBlocks = new HashSet<>();
        this.landingBlockTags = new HashSet<>();
        this.landingBlockTags.add(BlockTags.LEAVES);

        for (String entry : extraLandingBlocks) {
            if (entry.startsWith("#")) {
                this.landingBlockTags.add(requireBlockTag(entry));
            } else {
                this.landingBlocks.add(requireBlock(entry));
            }
        }
    }

    /**
     * Check if the current block is a valid landing block.
     * @param state The block state to check.
     * @return TRUE if the butterfly can land on the block.
     */
    public boolean isValidLandingBlock(BlockState state) {
        for (TagKey<Block> tag : landingBlockTags) {
            if (state.is(tag)) {
                return true;
            }
        }

        for (Holder.Reference<Block> block : landingBlocks) {
            if (block != null && state.is(block)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Helper method to ensure Resource Locations are valid.
     * @param raw The raw string used to create a Resource Location.
     * @param fieldName The name of the field.
     * @return A valid Resource Location.
     */
    private static ResourceLocation requireResourceLocation(String raw,
                                                            String fieldName) {
        Objects.requireNonNull(raw, fieldName + " must not be null");

        String normalized = raw.strip();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        ResourceLocation id = ResourceLocation.tryParse(normalized);
        if (id == null) {
            throw new IllegalArgumentException(
                    fieldName + " has invalid ResourceLocation syntax: '" + raw + "'"
            );
        }

        return id;
    }

    /**
     * Helper method to ensure the Block is valid.
     * @param raw The raw string used to create a Resource Location.
     * @return A valid Block.
     */
    private static Holder.Reference<Block> requireBlock(String raw) {
        ResourceLocation id = requireResourceLocation(raw, "landing block");

        Optional<Holder.Reference<Block>> blockReference = BuiltInRegistries.BLOCK.get(id);
        if (blockReference.isEmpty()) {
            throw new IllegalArgumentException("Unknown landing block: '" + raw + "' (" + id + ")");
        }

        return blockReference.get();
    }

    /**
     * Helper method to ensure the Block Tag is valid.
     * @param raw The raw string used to create a Resource Location.
     * @return A valid Block Tag.
     */
    private static TagKey<Block> requireBlockTag(String raw) {
        Objects.requireNonNull(raw, "landing block tag must not be null");

        if (!raw.startsWith("#")) {
            throw new IllegalArgumentException(
                    "Landing block tag must start with '#': '" + raw + "'"
            );
        }

        ResourceLocation id = requireResourceLocation(raw.substring(1), "landing block tag");
        return TagKey.create(Registries.BLOCK, id);
    }
}
