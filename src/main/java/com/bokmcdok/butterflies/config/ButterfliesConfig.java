package com.bokmcdok.butterflies.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ButterfliesConfig {
    public static final ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.DoubleValue doubleEggChance;
    public static ForgeConfigSpec.IntValue eggLimit;
    public static ForgeConfigSpec.IntValue maxDensity;
    public static ForgeConfigSpec.BooleanValue enableLifespan;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupServerConfig(configBuilder);
        SERVER_CONFIG = configBuilder.build();
    }

    private static void setupServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("This category holds configs for the butterflies mod.");
        builder.push("Butterfly Options");

        doubleEggChance = builder
                .comment("Defines the chance a butterfly has double the eggs.")
                .defineInRange("double_egg_chance", 0.0625, 0, 1);

        eggLimit = builder
                .comment("Defines how many eggs each butterfly can lay.")
                .defineInRange("egg_limit", 1, 0, 1024);

        maxDensity = builder
                .comment("Defines how many butterflies can be in a 32x32 region" +
                        " before breeding is disabled. If set to zero, this is disabled")
                .defineInRange("max_density", 16, 0, 1024);

        enableLifespan = builder
                .comment("If set to TRUE butterflies will die naturally.")
                .define("enable_lifespan", true);

        builder.pop();
    }
}
