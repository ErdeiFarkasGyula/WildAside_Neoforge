package net.farkas.wildaside;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = WildAside.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Integer> HICKORY_FOREST_WEIGHT = BUILDER
            .push("Biome weights")
            .comment("Spawn weight of the Hickory Forest biome (def: 3)")
            .define("hickory_forest_weight", 3);
    public static final ModConfigSpec.ConfigValue<Integer> GLOWING_HICKORY_FOREST_WEIGHT = BUILDER
            .comment("Spawn weight of the Glowing Hickory Forest biome (def: 1)")
            .define("glowing_hickory_forest_weight", 1);
    public static final ModConfigSpec.ConfigValue<Integer> VIBRION_HIVE_WEIGHT = BUILDER
            .comment("Spawn weight of the Vibrion Hive biome (def: 4)")
            .define("vibrion_hive_weight", 4);


//    public static final ForgeConfigSpec.ConfigValue<Integer> MUCELLITH_SPAWN_WEIGHT = BUILDER
//            .pop()
//            .push("Mucellith mob")
//            .comment("Spawn weight of the Mucellith mob in the Vibrion Hive biome")
//            .define("mucellith_spawn_weight", 15);
//    public static final ForgeConfigSpec.ConfigValue<Integer> MUCELLITH_SPAWN_MIN = BUILDER
//            .comment("Minimum number of Mucellith mobs in a group when spawning in the Vibrion Hive biome")
//            .define("mucellith_spawn_min", 2);
//    public static final ForgeConfigSpec.ConfigValue<Integer> MUCELLITH_SPAWN_MAX = BUILDER
//            .comment("Maximum number of Mucellith mobs in a group when spawning in the Vibrion Hive biome")
//            .define("mucellith_spawn_max", 3);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }
}
