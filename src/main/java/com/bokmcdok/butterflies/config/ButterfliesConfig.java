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
        public static ModConfigSpec.IntValue villagerPossessionChance;
        public static ModConfigSpec.IntValue wanderingTraderPossessionChance;
        public static ModConfigSpec.IntValue illagerPossessionChance;
        public static ModConfigSpec.IntValue butterflyGolemSpawnChance;
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
        Common.villagerPossessionChance = commonBuilder
                .comment("Chance out of 1000 that Villagers are spawned with Peacemaker Butterflies. " +
                        "Requires hostile butterflies enabled.")
                .defineInRange("villager_possession_chance", 17, 0, 1000);
        Common.wanderingTraderPossessionChance = commonBuilder
                .comment("Chance out of 1000 that Wandering Traders are spawned with Peacemaker Butterflies. " +
                        "Requires hostile butterflies enabled.")
                .defineInRange("wandering_trader_possession_chance", 35, 0, 1000);
        Common.illagerPossessionChance = commonBuilder
                .comment("Chance out of 1000 that Illagers are spawned with Peacemaker Butterflies. " +
                        "Requires hostile butterflies enabled.")
                .defineInRange("illager_possession_chance", 50, 0, 1000);
        Common.butterflyGolemSpawnChance = commonBuilder
                .comment("Chance out of 1000 that a Butterfly Golem will spawn instead of an Iron Golem.")
                .defineInRange("butterfly_golem_spawn_chance", 4, 0, 1000);
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
