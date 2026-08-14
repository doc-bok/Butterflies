package com.bokmcdok.butterflies.butterfly_data;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

public record SpeciesId(String value) {
    private static final Pattern PATTERN = Pattern.compile("[a-z0-9._-]+");

    public SpeciesId {
        Objects.requireNonNull(value, "speciesId must not be null");
        value = value.strip();

        if (value.isBlank()) {
            throw new IllegalArgumentException("speciesId must not be blank");
        }

        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid speciesId: " + value);
        }
    }

    public static SpeciesId of(String raw) {
        return new SpeciesId(raw);
    }

    public String withSuffix(String suffix) {
        return value + suffix;
    }

    @NotNull
    @Override
    public String toString() {
        return value;
    }
}
