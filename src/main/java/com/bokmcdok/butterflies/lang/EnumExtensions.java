package com.bokmcdok.butterflies.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Class with some methods to help with enumerations.
 */
public class EnumExtensions {

    /**
     * Convert a string to an enumerated value, ignoring case sensitivity.
     * @param enumeration The Enum class to use.
     * @param search The string to search for.
     * @return The value if found, otherwise the callback.
     * @param <T> The enumerated type.
     */
    @NotNull
    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                   String search)
            throws IllegalArgumentException {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }

        throw new IllegalArgumentException(search);
    }
}
