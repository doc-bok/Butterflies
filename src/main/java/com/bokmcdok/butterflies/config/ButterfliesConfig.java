package com.bokmcdok.butterflies.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ButterfliesConfig {
    public static final ModConfigSpec SERVER_CONFIG;

    public static ModConfigSpec.DoubleValue doubleEggChance;
    public static ModConfigSpec.IntValue eggLimit;
    public static ModConfigSpec.IntValue maxDensity;
    public static ModConfigSpec.BooleanValue enableLifespan;
    public static ModConfigSpec.BooleanValue enablePollination;

    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        setupServerConfig(configBuilder);
        SERVER_CONFIG = configBuilder.build();
    }

    private static void setupServerConfig(ModConfigSpec.Builder builder) {
        builder.comment("This category holds configs for the butterflies mod.");
        builder.push("Butterfly Options");

        doubleEggChance = builder
                .comment("Defines the chance a butterfly has double the eggs.")
                .defineInRange("double_egg_chance", 0.0625, 0, 1);

        eggLimit = builder
                .comment("Defines how many eggs each butterfly can lay.")
                .defineInRange("egg_limit", 1, 0, 1024);

        maxDensity = builder
                .comment("Defines how many butterflies can be in a 32x32x32 region" +
                        " before breeding is disabled. If set to zero, this is ignored.")
                .defineInRange("max_density", 16, 0, 1024);

        enableLifespan = builder
                .comment("If set to TRUE butterflies will die naturally.")
                .define("enable_lifespan", true);

        enablePollination = builder
                .comment("If set to TRUE butterflies will pollinate flowers and cause new ones to grow.")
                        .define("enable_pollination", true);

        builder.pop();
    }
}
