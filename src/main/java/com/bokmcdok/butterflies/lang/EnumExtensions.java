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
     * @param fallback The fallback value if we don't find the string.
     * @return The value if found, otherwise the callback.
     * @param <T> The enumerated type.
     */
    @NotNull
    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                   String search,
                                                   @NotNull T fallback) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }

        return fallback;
    }
}
