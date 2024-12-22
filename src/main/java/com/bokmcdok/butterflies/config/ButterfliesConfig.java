package com.bokmcdok.butterflies.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Holds config options for the butterflies.
 */
public class ButterfliesConfig {
    public static final ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.DoubleValue doubleEggChance;
    public static ForgeConfigSpec.IntValue eggLimit;
    public static ForgeConfigSpec.IntValue maxDensity;
    public static ForgeConfigSpec.BooleanValue enableLifespan;
    public static ForgeConfigSpec.BooleanValue enablePollination;
    public static ForgeConfigSpec.BooleanValue debugInformation;

    /*
      Static initialisation for the configurations - they need to be loaded
      before anything else.
     */
    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupServerConfig(configBuilder);
        SERVER_CONFIG = configBuilder.build();
    }

    /**
     * Set up the server configuration.
     * @param builder The spec builder.
     */
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
                .comment("Defines how many butterflies can be in a 32x32x32 region" +
                        " before breeding is disabled. If set to zero, this is ignored.")
                .defineInRange("max_density", 16, 0, 1024);

        enableLifespan = builder
                .comment("If set to TRUE butterflies will die naturally.")
                .define("enable_lifespan", true);

        enablePollination = builder
                .comment("If set to TRUE butterflies will pollinate flowers and cause new ones to grow.")
                        .define("enable_pollination", true);

        debugInformation = builder
                .comment("If set to TRUE debug information will be rendered.")
                .define("debug_info", false);

        builder.pop();
    }
}
