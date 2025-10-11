package net.farkas.wildaside.worldgen.feature;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.worldgen.feature.configuration.LargeMushroomConfiguration;
import net.farkas.wildaside.worldgen.feature.custom.*;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, WildAside.MOD_ID);

    public static final Supplier<UpdateReplaceSingleBlockFeature> TICK_REPLACE_SINGLE_BLOCK = FEATURES.register("tick_replace_single_block",
            () -> new UpdateReplaceSingleBlockFeature(ReplaceBlockConfiguration.CODEC));

    public static final Supplier<NaturalSporeBlasterFeature> NATURAL_SPORE_BLASTER_FEATURE = FEATURES.register("natural_spore_blaster_feature",
            () -> new NaturalSporeBlasterFeature(ReplaceBlockConfiguration.CODEC));
    public static final Supplier<LargeMushroomFeature> LARGE_SUBSTILIUM_MUSHROOM = FEATURES.register("large_substilium_mushroom",
            () -> new LargeMushroomFeature(LargeMushroomConfiguration.CODEC));
    public static final Supplier<HangingStringFeature> HANGING_STRING = FEATURES.register("hanging_string",
            () -> new HangingStringFeature(SimpleBlockConfiguration.CODEC));

    public static final Supplier<HickoryBushFeature> HICKORY_BUSH = FEATURES.register("hickory_bush",
            () -> new HickoryBushFeature(SimpleBlockConfiguration.CODEC));
    public static final Supplier<FallenHickoryTreeFeature> FALLEN_HICKORY_TREE = FEATURES.register("fallen_hickory_tree",
            () -> new FallenHickoryTreeFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}