package com.bokmcdok.butterflies.butterfly_data;

/**
 * Helper enum to determine a butterflies overall lifespan.
 */
public enum ButterflyLifespan {
    SHORT(0),
    MEDIUM(1),
    LONG(2),
    IMMORTAL(3);

    private final int value;

    ButterflyLifespan(int value) {
        this.value = value;
    }

    public int getIndex() {
        return this.value;
    }
}
