package com.bokmcdok.butterflies.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ButterfliesConfig {

    public static final ModConfigSpec COMMON_CONFIG;
    public static final ModConfigSpec SERVER_CONFIG;

    // Group common config values in a static inner class
    public static class Common {
        public static ModConfigSpec.DoubleValue doubleEggChance;
        public static ModConfigSpec.IntValue eggLimit;
        public static ModConfigSpec.IntValue maxDensity;
        public static ModConfigSpec.BooleanValue enableLifespan;
        public static ModConfigSpec.BooleanValue enablePollination;
        public static ModConfigSpec.BooleanValue enableHostileButterflies;
    }

    // Group server config values in a static inner class
    public static class Server {
        public static ModConfigSpec.BooleanValue debugInformation;
    }

    static {
        // Build Common Config
        ModConfigSpec.Builder commonBuilder = new ModConfigSpec.Builder();
        commonBuilder.comment("Common configs for the butterflies mod.");
        commonBuilder.push("butterfly_options");

        Common.doubleEggChance = commonBuilder
                .comment("Chance a butterfly has double eggs.")
                .defineInRange("double_egg_chance", 0.0625, 0, 1);
        Common.eggLimit = commonBuilder
                .comment("Eggs per butterfly.")
                .defineInRange("egg_limit", 1, 0, 1024);
        Common.maxDensity = commonBuilder
                .comment("Butterfly max density in a 32x32x32 area before breeding is disabled (0 ignores).")
                .defineInRange("max_density", 16, 0, 1024);
        Common.enableLifespan = commonBuilder
                .comment("If true, butterflies will die naturally.")
                .define("enable_lifespan", true);
        Common.enablePollination = commonBuilder
                .comment("If true, butterflies will pollinate flowers.")
                .define("enable_pollination", true);
        Common.enableHostileButterflies = commonBuilder
                .comment("If true, hostile butterflies will spawn.")
                .define("enable_hostile_butterflies", true);
        commonBuilder.pop();
        COMMON_CONFIG = commonBuilder.build();

        // Build Server Config
        ModConfigSpec.Builder serverBuilder = new ModConfigSpec.Builder();
        serverBuilder.comment("Server configs for the butterflies mod.");
        serverBuilder.push("butterfly_options");
        Server.debugInformation = serverBuilder
                .comment("If true, render debug info.")
                .define("debug_info", false);
        serverBuilder.pop();
        SERVER_CONFIG = serverBuilder.build();
    }
}
