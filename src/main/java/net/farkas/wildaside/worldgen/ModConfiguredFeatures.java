package net.farkas.wildaside.worldgen;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.util.HickoryColour;
import net.farkas.wildaside.worldgen.feature.ModFeatures;
import net.farkas.wildaside.worldgen.feature.decorator.FallenLeavesDecorator;
import net.farkas.wildaside.worldgen.feature.tree.hickory.HickoryTreeFoliagePlacer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import java.util.EnumMap;
import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDLIKE_SUBSTILIUM_MUSHROOM = registerKey("redlike_substilium_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWNLIKE_SUBSTILIUM_MUSHROOM = registerKey("brownlike_substilium_mushroom");

    public static final ResourceKey<ConfiguredFeature<?, ?>> VIBRION_GROWTH = registerKey("vibrion_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VIBRION_SPOREHOLDER = registerKey("vibrion_sporeholder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_SPROUTS = registerKey("substilium_sprouts");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_VIBRION_VINES = registerKey("hanging_vibrion_vines");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_VIBRION_GEL = registerKey("hanging_vibrion_gel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_LIT_VIBRION_GEL = registerKey("hanging_lit_vibrion_gel");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERGROWN_ENTORIUM_ORE = registerKey("overgrown_entorium_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_COAL_ORE = registerKey("substilium_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_COPPER_ORE_SMALL = registerKey("substilium_copper_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_COPPER_ORE_LARGE = registerKey("substilium_copper_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_LAPIS_ORE = registerKey("substilium_lapis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_LAPIS_ORE_BURIED = registerKey("substilium_lapis_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_IRON_ORE = registerKey("substilium_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_IRON_ORE_SMALL = registerKey("substilium_iron_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_GOLD_ORE = registerKey("substilium_gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_GOLD_ORE_BURIED = registerKey("substilium_gold_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_REDSTONE_ORE = registerKey("substilium_redstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_DIAMOND_ORE_SMALL = registerKey("substilium_diamond_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_DIAMOND_ORE_LARGE = registerKey("substilium_diamond_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_DIAMOND_ORE_BURIED = registerKey("substilium_diamond_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUBSTILIUM_EMERALD_ORE = registerKey("substilium_emerald_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> COMPRESSED_SUBSTILIUM_SOIL = registerKey("compressed_substilium_soil");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NATURAL_SPORE_BLASTER = registerKey("natural_spore_blaster");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SPOTTED_EVERGREEN = registerKey("spotted_evergreen");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINKSTER_FLOWER = registerKey("pinkster_flower");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HICKORY_TREE = registerKey("hickory_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_GLOWING_HICKORY_TREE = registerKey("red_glowing_hickory_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_GLOWING_HICKORY_TREE = registerKey("brown_glowing_hickory_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_GLOWING_HICKORY_TREE = registerKey("yellow_glowing_hickory_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_GLOWING_HICKORY_TREE = registerKey("green_glowing_hickory_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HICKORY_SAPLING = registerKey("hickory_sapling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_GLOWING_HICKORY_SAPLING = registerKey("red_glowing_hickory_sapling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_GLOWING_HICKORY_SAPLING = registerKey("brown_glowing_hickory_sapling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_GLOWING_HICKORY_SAPLING = registerKey("yellow_glowing_hickory_sapling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_GLOWING_HICKORY_SAPLING = registerKey("green_glowing_hickory_sapling");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HICKORY_BUSH = registerKey("hickory_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_HICKORY_BUSH = registerKey("glowing_hickory_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_HICKORY_TREE = registerKey("fallen_hickory_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PODZOL_VEIN = registerKey("podzol_vein");


    public static final EnumMap<HickoryColour, ResourceKey<ConfiguredFeature<?, ?>>> HICKORY_TREES = new EnumMap<>(HickoryColour.class);
    static {
        HICKORY_TREES.put(HickoryColour.HICKORY, HICKORY_TREE);
        HICKORY_TREES.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_TREE);
        HICKORY_TREES.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_TREE);
        HICKORY_TREES.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_TREE);
        HICKORY_TREES.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_TREE);
    }

    public static final EnumMap<HickoryColour, ResourceKey<ConfiguredFeature<?, ?>>> HICKORY_SAPLINGS = new EnumMap<>(HickoryColour.class);
    static {
        HICKORY_SAPLINGS.put(HickoryColour.HICKORY, HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_SAPLING );
        HICKORY_SAPLINGS.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_SAPLING);
    }


    static final BeehiveDecorator glowingBeehive = new BeehiveDecorator(0.015f);
    static final List hickoryDecorator = List.of(new BeehiveDecorator(0.01f), new FallenLeavesDecorator(0.075f, HickoryColour.HICKORY));
    static final List redGlowingHickoryDecorator = List.of(glowingBeehive, new FallenLeavesDecorator(0.075f, HickoryColour.RED_GLOWING));
    static final List brownGlowingHickoryDecorator = List.of(glowingBeehive, new FallenLeavesDecorator(0.075f, HickoryColour.BROWN_GLOWING));
    static final List yellowGlowingHickoryDecorator = List.of(glowingBeehive, new FallenLeavesDecorator(0.075f, HickoryColour.YELLOW_GLOWING));
    static final List greenGlowingHickoryDecorator = List.of(glowingBeehive, new FallenLeavesDecorator(0.075f, HickoryColour.GREEN_GLOWING));

    public static final EnumMap<HickoryColour, List> DECORATORS = new EnumMap<>(HickoryColour.class);
    static {
        DECORATORS.put(HickoryColour.HICKORY, hickoryDecorator);
        DECORATORS.put(HickoryColour.RED_GLOWING, redGlowingHickoryDecorator);
        DECORATORS.put(HickoryColour.BROWN_GLOWING, brownGlowingHickoryDecorator);
        DECORATORS.put(HickoryColour.YELLOW_GLOWING, yellowGlowingHickoryDecorator);
        DECORATORS.put(HickoryColour.GREEN_GLOWING, greenGlowingHickoryDecorator);
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest substilium_soil_replace = new BlockMatchTest(ModBlocks.SUBSTILIUM_SOIL.get());
        RuleTest grass_replace = new BlockMatchTest(Blocks.GRASS_BLOCK);

        List<OreConfiguration.TargetBlockState> overgrown_entorium_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.OVERGROWN_ENTORIUM_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> compressed_substilium_soil = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> substilium_coal_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_COAL_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_copper_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_COPPER_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_lapis_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_LAPIS_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_iron_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_IRON_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_gold_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_GOLD_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_redstone_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_REDSTONE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_diamond_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_DIAMOND_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> substilium_emerald_ore = List.of(OreConfiguration.target(substilium_soil_replace, ModBlocks.SUBSTILIUM_EMERALD_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> podzol_vein = List.of(OreConfiguration.target(grass_replace, Blocks.PODZOL.defaultBlockState()));

        register(context, OVERGROWN_ENTORIUM_ORE, Feature.ORE, new OreConfiguration(overgrown_entorium_ore, 16));

        register(context, SUBSTILIUM_COAL_ORE, Feature.ORE, new OreConfiguration(substilium_coal_ore, 17, 0.5f));
        register(context, SUBSTILIUM_COPPER_ORE_SMALL, Feature.ORE, new OreConfiguration(substilium_copper_ore, 10));
        register(context, SUBSTILIUM_COPPER_ORE_LARGE, Feature.ORE, new OreConfiguration(substilium_copper_ore, 20));
        register(context, SUBSTILIUM_LAPIS_ORE, Feature.ORE, new OreConfiguration(substilium_lapis_ore, 7));
        register(context, SUBSTILIUM_LAPIS_ORE_BURIED, Feature.ORE, new OreConfiguration(substilium_lapis_ore, 7, 1f));
        register(context, SUBSTILIUM_IRON_ORE, Feature.ORE, new OreConfiguration(substilium_iron_ore, 9));
        register(context, SUBSTILIUM_IRON_ORE_SMALL, Feature.ORE, new OreConfiguration(substilium_iron_ore, 4));
        register(context, SUBSTILIUM_GOLD_ORE, Feature.ORE, new OreConfiguration(substilium_gold_ore, 16));
        register(context, SUBSTILIUM_GOLD_ORE_BURIED, Feature.ORE, new OreConfiguration(substilium_gold_ore, 9));
        register(context, SUBSTILIUM_REDSTONE_ORE, Feature.ORE, new OreConfiguration(substilium_redstone_ore, 9, 0.5f));
        register(context, SUBSTILIUM_DIAMOND_ORE_SMALL, Feature.ORE, new OreConfiguration(substilium_diamond_ore, 4, 0.5f));
        register(context, SUBSTILIUM_DIAMOND_ORE_LARGE, Feature.ORE, new OreConfiguration(substilium_diamond_ore, 12, 0.7f));
        register(context, SUBSTILIUM_DIAMOND_ORE_BURIED, Feature.ORE, new OreConfiguration(substilium_diamond_ore, 8, 1f));
        register(context, SUBSTILIUM_EMERALD_ORE, Feature.ORE, new OreConfiguration(substilium_emerald_ore, 3));

        register(context, COMPRESSED_SUBSTILIUM_SOIL, Feature.ORE, new OreConfiguration(compressed_substilium_soil, 36));

        register(context, NATURAL_SPORE_BLASTER, ModFeatures.NATURAL_SPORE_BLASTER_FEATURE.get(), new ReplaceBlockConfiguration(ModBlocks.SUBSTILIUM_SOIL.get().defaultBlockState(), ModBlocks.NATURAL_SPORE_BLASTER.get().defaultBlockState()));

        register(context, VIBRION_GROWTH, Feature.FLOWER,
                new RandomPatchConfiguration(32, 8, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.VIBRION_GROWTH.get())))));
        register(context, VIBRION_SPOREHOLDER, Feature.FLOWER,
                new RandomPatchConfiguration(32, 8, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.VIBRION_SPOREHOLDER.get())))));
        register(context, SUBSTILIUM_SPROUTS, Feature.FLOWER,
                new RandomPatchConfiguration(32, 8, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SUBSTILIUM_SPROUTS.get())))));

        BlockStateProvider vines_plant = BlockStateProvider.simple(ModBlocks.HANGING_VIBRION_VINES_PLANT.get());
        BlockStateProvider vines = BlockStateProvider.simple(ModBlocks.HANGING_VIBRION_VINES.get());

        register(context, HANGING_VIBRION_VINES, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 25), 3)
                        .add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 7).build()), vines_plant),
                BlockColumnConfiguration.layer(ConstantInt.of(1), vines)), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));

//        register(context, HANGING_VIBRION_GEL, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(
//                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 3)
//                                .add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 7).build()),
//                BlockStateProvider.simple(ModBlocks.VIBRION_GEL.get()))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
//
//        register(context, HANGING_LIT_VIBRION_GEL, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(
//                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 3)
//                        .add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 7).build()),
//                BlockStateProvider.simple(ModBlocks.LIT_VIBRION_GEL.get()))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));

        register(context, HANGING_VIBRION_GEL, ModFeatures.HANGING_STRING.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.VIBRION_GEL.get())));
        register(context, HANGING_LIT_VIBRION_GEL, ModFeatures.HANGING_STRING.get(),
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.LIT_VIBRION_GEL.get())));

        register(context, REDLIKE_SUBSTILIUM_MUSHROOM, ModFeatures.REDLIKE_SUBSTILIUM_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(ModBlocks.ENTORIUM_SHROOM.get()), BlockStateProvider.simple(ModBlocks.SUBSTILIUM_STEM.get()), 2));
        register(context, BROWNLIKE_SUBSTILIUM_MUSHROOM, ModFeatures.BROWNLIKE_SUBSTILIUM_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(ModBlocks.ENTORIUM_SHROOM.get()), BlockStateProvider.simple(ModBlocks.SUBSTILIUM_STEM.get()), 3));

        register(context, SPOTTED_EVERGREEN, Feature.FLOWER,
                new RandomPatchConfiguration(32, 16, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SPOTTED_WINTERGREEN.get())))));

        register(context, PINKSTER_FLOWER, Feature.FLOWER,
                new RandomPatchConfiguration(32, 16, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PINKSTER_FLOWER.get())))));

        for (HickoryColour colour : HickoryColour.values()) {
            registerHickoryTree(context, colour);
            registerHickorySapling(context, colour);
        }

        register(context, HICKORY_BUSH, ModFeatures.HICKORY_BUSH.get(), new NoneFeatureConfiguration());
        register(context, GLOWING_HICKORY_BUSH, ModFeatures.GLOWING_HICKORY_BUSH.get(), new NoneFeatureConfiguration());
        register(context, FALLEN_HICKORY_TREE, ModFeatures.FALLEN_HICKORY_TREE.get(), new NoneFeatureConfiguration());

        register(context, PODZOL_VEIN, Feature.ORE, new OreConfiguration(podzol_vein, 32));
    }

    private static void registerHickoryTree(BootstrapContext<ConfiguredFeature<?, ?>> context, HickoryColour colour) {
        register(context, HICKORY_TREES.get(colour),Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.HICKORY_LOG.get()),
                new StraightTrunkPlacer(18, 0, 6),
                BlockStateProvider.simple(ModBlocks.HICKORY_LEAVES_BLOCKS.get(colour).get()),
                new HickoryTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 14),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(DECORATORS.get(colour)).build()
        );
    }

    private static void registerHickorySapling(BootstrapContext<ConfiguredFeature<?, ?>> context, HickoryColour colour) {
        int count = colour == HickoryColour.HICKORY ? 32 : 16;
        register(context, HICKORY_SAPLINGS.get(colour), Feature.FLOWER,
                new RandomPatchConfiguration(count, 16, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.HICKORY_SAPLINGS.get(colour).get())))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}