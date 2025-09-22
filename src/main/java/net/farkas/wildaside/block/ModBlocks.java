package net.farkas.wildaside.block;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.custom.*;
import net.farkas.wildaside.block.custom.sign.ModHangingSignBlock;
import net.farkas.wildaside.block.custom.sign.ModStandingSignBlock;
import net.farkas.wildaside.block.custom.sign.ModWallHangingSignBlock;
import net.farkas.wildaside.block.custom.sign.ModWallSignBlock;
import net.farkas.wildaside.block.custom.vibrion.*;
import net.farkas.wildaside.block.custom.vibrion.hanging_vines.HangingVibrionVines;
import net.farkas.wildaside.block.custom.vibrion.hanging_vines.HangingVibrionVinesPlant;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.util.HickoryColour;
import net.farkas.wildaside.util.ModWoodTypes;
import net.farkas.wildaside.worldgen.feature.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WildAside.MOD_ID);

    //VIBRION
    public static final DeferredBlock<Block> VIBRION_BLOCK = registerBlock("vibrion_block",
            () -> new BouncyExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(2F, 1F)
                    .lightLevel(l -> 9)));

    public static final DeferredBlock<Block> COMPRESSED_VIBRION_BLOCK = registerBlock("compressed_vibrion_block",
            () -> new DropExperienceBlock(UniformInt.of(0, 0), BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_BLOCK.get())
                    .lightLevel(s -> 15)));

    public static final DeferredBlock<Block> VIBRION_GEL = registerBlock("vibrion_gel",
            () -> new VibrionGel(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.HONEY_BLOCK)
                    .strength(0.1F, 0F)
                    .noCollission()
                    .noOcclusion()
                    .isRedstoneConductor((bs, br, bp) -> false)
                    .speedFactor(0.2f)
                    .jumpFactor(0.6f)
                    .noOcclusion()));

    public static final DeferredBlock<Block> LIT_VIBRION_GEL = registerBlock("lit_vibrion_gel",
            () -> new VibrionGel(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_GEL.get()).lightLevel(s -> 5)));

    public static final DeferredBlock<Block> VIBRION_GLASS = registerBlock("vibrion_glass",
            () -> new VibrionGlass(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.GLASS)
                    .strength(0.4F, 0.3F)
                    .noOcclusion()
                    .isRedstoneConductor((bs, br, bp) -> false)
                    .instrument(NoteBlockInstrument.HAT)));

    public static final DeferredBlock<Block> LIT_VIBRION_GLASS = registerBlock("lit_vibrion_glass",
            () -> new VibrionGlass(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_GLASS.get()).lightLevel(s -> 5)));

    public static final DeferredBlock<Block> VIBRION_GLASS_PANE = registerBlock("vibrion_glass_pane",
            () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_GLASS.get())));

    public static final DeferredBlock<Block> LIT_VIBRION_GLASS_PANE = registerBlock("lit_vibrion_glass_pane",
            () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_GLASS_PANE.get()).lightLevel(s -> 5)));

    public static final DeferredBlock<Block> VIBRION_GROWTH = registerBlock("vibrion_growth",
            () -> new FlowerBlock(MobEffects.POISON, 5, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.ROOTS)
                    .lightLevel(s -> 3)
                    .noCollission()
                    .noOcclusion()
                    .replaceable()
                    .instabreak()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> POTTED_VIBRION_GROWTH = BLOCKS.register("potted_vibrion_growth",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.VIBRION_GROWTH, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_CRIMSON_ROOTS)
                    .mapColor(MapColor.COLOR_YELLOW)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> VIBRION_SPOREHOLDER = registerBlock("vibrion_sporeholder",
            () -> new Sporeholder(ModTreeGrowers.SUBSTILIUM_MUSHROOM, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.FUNGUS)
                    .lightLevel(l -> 5)
                    .noCollission()
                    .noOcclusion()
                    .strength(1.5f, 2f)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> HANGING_VIBRION_VINES = registerBlock("hanging_vibrion_vines",
            () -> new HangingVibrionVines(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.WEEPING_VINES)
                    .lightLevel(l -> 5)
                    .noCollission()
                    .noOcclusion()
                    .replaceable()
                    .isRedstoneConductor((bs, br, bp) -> false)
                    .strength(0.3f, 0.5f)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> HANGING_VIBRION_VINES_PLANT = registerBlock("hanging_vibrion_vines_plant",
            () -> new HangingVibrionVinesPlant(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HANGING_VIBRION_VINES.get())));

    public static final DeferredBlock<Block> SPORE_AIR = registerBlock("spore_air",
            () -> new SporeAir(BlockBehaviour.Properties.ofFullCopy(Blocks.AIR).mapColor(MapColor.COLOR_YELLOW).noLootTable()));

    //ENTORIUM
    public static final DeferredBlock<Block> ENTORIUM_SHROOM = registerBlock("entorium_shroom",
            () -> new HugeMushroomBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.WART_BLOCK)
                    .strength(1.2F, 1.5F)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> OVERGROWN_ENTORIUM_ORE = registerBlock("overgrown_entorium_ore",
            () -> new OvergrownEntoriumOre(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .sound(SoundType.NETHER_ORE)
                    .strength(3.5f, 12)));

    public static final DeferredBlock<Block> ENTORIUM_ORE = registerBlock("entorium_ore",
            () -> new EntoriumOre(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.NETHER_ORE)
                    .strength(3, 12)));

    public static final DeferredBlock<Block> SUBSTILIUM_COAL_ORE = registerBlock("substilium_coal_ore",
            () -> new SubstiliumSoil(UniformInt.of(0, 3), BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_COPPER_ORE = registerBlock("substilium_copper_ore",
            () -> new SubstiliumSoil(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_LAPIS_ORE = registerBlock("substilium_lapis_ore",
            () -> new SubstiliumSoil(UniformInt.of(2, 6), BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_IRON_ORE = registerBlock("substilium_iron_ore",
            () -> new SubstiliumSoil(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_GOLD_ORE = registerBlock("substilium_gold_ore",
            () -> new SubstiliumSoil(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_REDSTONE_ORE = registerBlock("substilium_redstone_ore",
            () -> new SubstiliumRedstoneOre(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_DIAMOND_ORE = registerBlock("substilium_diamond_ore",
            () -> new SubstiliumSoil(UniformInt.of(3, 8), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).sound(SoundType.ROOTED_DIRT)));
    public static final DeferredBlock<Block> SUBSTILIUM_EMERALD_ORE = registerBlock("substilium_emerald_ore",
            () -> new SubstiliumSoil(UniformInt.of(3, 8), BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_ORE).sound(SoundType.ROOTED_DIRT)));


    //SUBSTILIUM
    public static final DeferredBlock<Block> SUBSTILIUM_SOIL = registerBlock("substilium_soil",
            () -> new SubstiliumSoil(UniformInt.of(0, 0), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(1, 2)));

    public static final DeferredBlock<Block> COMPRESSED_SUBSTILIUM_SOIL = registerBlock("compressed_substilium_soil",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(2, 12)) {
                @Override
                public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
                    return TriState.TRUE;
                }
            });

    public static final DeferredBlock<Block> NATURAL_SPORE_BLASTER = registerBlock("natural_spore_blaster",
            () -> new NaturalSporeBlaster(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_SOIL.get())
                    .noOcclusion().strength(6f, 12f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> SPORE_BLASTER = registerBlock("spore_blaster",
            () -> new SporeBlaster(BlockBehaviour.Properties.ofFullCopy(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get())
                    .noOcclusion().strength(6f, 12f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> POTION_BLASTER = registerBlock("potion_blaster",
            () -> new PotionBlaster(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SPORE_BLASTER.get())));


    public static final DeferredBlock<Block> SMOOTH_SUBSTILIUM_SOIL = registerBlock("smooth_substilium_soil",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get())));

    public static final DeferredBlock<Block> CHISELED_SUBSTILIUM_SOIL = registerBlock("chiseled_substilium_soil",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TILES = registerBlock("substilium_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get())));

    public static final DeferredBlock<Block> CRACKED_SUBSTILIUM_TILES = registerBlock("cracked_substilium_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.COMPRESSED_SUBSTILIUM_SOIL.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TILE_STAIRS = registerBlock("substilium_tile_stairs",
            () -> new StairBlock(ModBlocks.SUBSTILIUM_TILES.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_TILES.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TILE_SLAB = registerBlock("substilium_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_TILES.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TILE_BUTTON = registerBlock("substilium_tile_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .sound(SoundType.DEEPSLATE)
                    .strength(1, 12)));

    public static final DeferredBlock<Block> SUBSTILIUM_TILE_PRESSURE_PLATE = registerBlock("substilium_tile_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_TILE_BUTTON.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TILE_WALLS = registerBlock("substilium_tile_walls",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_TILES.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_STEM = registerBlock("substilium_stem",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .sound(SoundType.STEM)
                    .strength(2, 2)));

    public static final DeferredBlock<Block> STRIPPED_SUBSTILIUM_STEM = registerBlock("stripped_substilium_stem",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_STEM.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_WOOD = registerBlock("substilium_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_STEM.get())));

    public static final DeferredBlock<Block> STRIPPED_SUBSTILIUM_WOOD = registerBlock("stripped_substilium_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_WOOD.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_PLANKS = registerBlock("substilium_planks",
            () -> new ModFlammableBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_STEM.get()).sound(SoundType.WOOD), 10, 5));

    public static final DeferredBlock<Block> SUBSTILIUM_STAIRS = registerBlock("substilium_stairs",
            () -> new StairBlock(ModBlocks.SUBSTILIUM_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_SLAB = registerBlock("substilium_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_BUTTON = registerBlock("substilium_button",
            () -> new ButtonBlock(BlockSetType.OAK, 5, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).strength(1, 1)));

    public static final DeferredBlock<Block> SUBSTILIUM_PRESSURE_PLATE = registerBlock("substilium_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_BUTTON.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_FENCE = registerBlock("substilium_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_FENCE_GATE = registerBlock("substilium_fence_gate",
            () -> new FenceGateBlock(Optional.of(ModWoodTypes.SUBSTILIUM), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()),
                    Optional.of(SoundEvents.FENCE_GATE_OPEN), Optional.of(SoundEvents.FENCE_GATE_CLOSE)));

    public static final DeferredBlock<Block> SUBSTILIUM_DOOR = registerBlock("substilium_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get())));

    public static final DeferredBlock<Block> SUBSTILIUM_TRAPDOOR = registerBlock("substilium_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).noOcclusion()));

    public static final DeferredBlock<Block> SUBSTILIUM_SIGN = BLOCKS.register("substilium_sign",
            () -> new ModStandingSignBlock(ModWoodTypes.SUBSTILIUM, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).strength(1).forceSolidOn().noCollission()));
    public static final DeferredBlock<Block> SUBSTILIUM_WALL_SIGN = BLOCKS.register("substilium_wall_sign",
            () -> new ModWallSignBlock(ModWoodTypes.SUBSTILIUM, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).strength(1).forceSolidOn().noCollission()));

    public static final DeferredBlock<Block> SUBSTILIUM_HANGING_SIGN = BLOCKS.register("substilium_hanging_sign",
            () -> new ModHangingSignBlock(ModWoodTypes.SUBSTILIUM, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).strength(1).forceSolidOn().noCollission()));
    public static final DeferredBlock<Block> SUBSTILIUM_WALL_HANGING_SIGN = BLOCKS.register("substilium_hanging_wall_sign",
            () -> new ModWallHangingSignBlock(ModWoodTypes.SUBSTILIUM, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SUBSTILIUM_PLANKS.get()).strength(1).forceSolidOn().noCollission()));

    public static final DeferredBlock<Block> SUBSTILIUM_SPROUTS = registerBlock("substilium_sprouts",
            () -> new FlowerBlock(MobEffects.CONFUSION, 5, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VIBRION_GROWTH.get())
                    .mapColor(MapColor.COLOR_CYAN)
                    .lightLevel(l -> 0)));

    public static final DeferredBlock<Block> BIOENGINEERING_WORKSTATION = registerBlock("bioengineering_workstation",
            () -> new BioengineeringWorkstation(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));


    //HICKORY
    public static final DeferredBlock<Block> HICKORY_LOG = registerBlock("hickory_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .strength(2.5f, 4)));

    public static final DeferredBlock<Block> HICKORY_WOOD = registerBlock("hickory_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_LOG.get())));

    public static final DeferredBlock<Block> STRIPPED_HICKORY_LOG = registerBlock("stripped_hickory_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_LOG.get())));

    public static final DeferredBlock<Block> STRIPPED_HICKORY_WOOD = registerBlock("stripped_hickory_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_LOG.get())));

    public static final DeferredBlock<Block> HICKORY_PLANKS = registerBlock("hickory_planks",
            () -> new ModFlammableBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_LOG.get()), 10, 5));

    public static final DeferredBlock<Block> HICKORY_STAIRS = registerBlock("hickory_stairs",
            () -> new StairBlock(ModBlocks.HICKORY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get())));

    public static final DeferredBlock<Block> HICKORY_SLAB = registerBlock("hickory_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get())));

    public static final DeferredBlock<Block> HICKORY_BUTTON = registerBlock("hickory_button",
            () -> new ButtonBlock(BlockSetType.OAK, 5, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).strength(1, 1)));

    public static final DeferredBlock<Block> HICKORY_PRESSURE_PLATE = registerBlock("hickory_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_BUTTON.get())));

    public static final DeferredBlock<Block> HICKORY_FENCE = registerBlock("hickory_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get())));

    public static final DeferredBlock<Block> HICKORY_FENCE_GATE = registerBlock("hickory_fence_gate",
            () -> new FenceGateBlock(Optional.of(ModWoodTypes.HICKORY), BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()),
                    Optional.of(SoundEvents.FENCE_GATE_OPEN), Optional.of(SoundEvents.FENCE_GATE_CLOSE)));

    public static final DeferredBlock<Block> HICKORY_DOOR = registerBlock("hickory_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get())));

    public static final DeferredBlock<Block> HICKORY_TRAPDOOR = registerBlock("hickory_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).noOcclusion()));

    public static final DeferredBlock<Block> HICKORY_SIGN = BLOCKS.register("hickory_sign",
            () -> new ModStandingSignBlock(ModWoodTypes.HICKORY, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).strength(1).forceSolidOn().noCollission()));
    public static final DeferredBlock<Block> HICKORY_WALL_SIGN = BLOCKS.register("hickory_wall_sign",
            () -> new ModWallSignBlock(ModWoodTypes.HICKORY, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).strength(1).forceSolidOn().noCollission()));

    public static final DeferredBlock<Block> HICKORY_HANGING_SIGN = BLOCKS.register("hickory_hanging_sign",
            () -> new ModHangingSignBlock(ModWoodTypes.HICKORY, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).strength(1).forceSolidOn().noCollission()));
    public static final DeferredBlock<Block> HICKORY_WALL_HANGING_SIGN = BLOCKS.register("hickory_hanging_wall_sign",
            () -> new ModWallHangingSignBlock(ModWoodTypes.HICKORY, BlockBehaviour.Properties.ofFullCopy(ModBlocks.HICKORY_PLANKS.get()).strength(1).forceSolidOn().noCollission()));

    public static final DeferredBlock<Block> SPOTTED_WINTERGREEN = registerBlock("spotted_wintergreen",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)));
    public static final DeferredBlock<Block> PINKSTER_FLOWER = registerBlock("pinkster_flower",
            () -> new FlowerBlock(MobEffects.CONFUSION, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)));


    public static final DeferredBlock<Block> HICKORY_LEAVES = registerBlock("hickory_leaves",
            () -> new HickoryLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> RED_GLOWING_HICKORY_LEAVES = registerBlock("red_glowing_hickory_leaves",
            () -> new GlowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), HickoryColour.RED_GLOWING));
    public static final DeferredBlock<Block> BROWN_GLOWING_HICKORY_LEAVES = registerBlock("brown_glowing_hickory_leaves",
            () -> new GlowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), HickoryColour.BROWN_GLOWING));
    public static final DeferredBlock<Block> YELLOW_GLOWING_HICKORY_LEAVES = registerBlock("yellow_glowing_hickory_leaves",
            () -> new GlowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), HickoryColour.YELLOW_GLOWING));
    public static final DeferredBlock<Block> GREEN_GLOWING_HICKORY_LEAVES = registerBlock("green_glowing_hickory_leaves",
            () -> new GlowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), HickoryColour.GREEN_GLOWING));

    public static final EnumMap<HickoryColour, DeferredBlock<Block>> HICKORY_LEAVES_BLOCKS = new EnumMap<>(HickoryColour.class);

    static {
        HICKORY_LEAVES_BLOCKS.put(HickoryColour.HICKORY, HICKORY_LEAVES);
        HICKORY_LEAVES_BLOCKS.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_LEAVES);
        HICKORY_LEAVES_BLOCKS.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_LEAVES);
        HICKORY_LEAVES_BLOCKS.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_LEAVES);
        HICKORY_LEAVES_BLOCKS.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_LEAVES);
    }

    public static final DeferredBlock<Block> FALLEN_HICKORY_LEAVES = registerBlock("fallen_hickory_leaves",
            () -> new FallenHickoryLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .noCollission()
                    .noOcclusion()
                    .instabreak()
                    .replaceable()
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> HICKORY_SAPLING = registerBlock("hickory_sapling",
            () -> new SaplingBlock(ModTreeGrowers.HICKORY_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> RED_GLOWING_HICKORY_SAPLING = registerBlock("red_glowing_hickory_sapling",
            () -> new GlowingSaplingBlock(ModTreeGrowers.RED_GLOWING_HICKORY_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> BROWN_GLOWING_HICKORY_SAPLING = registerBlock("brown_glowing_hickory_sapling",
            () -> new GlowingSaplingBlock(ModTreeGrowers.BROWN_GLOWING_HICKORY_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(ModBlocks.RED_GLOWING_HICKORY_SAPLING.get())));
    public static final DeferredBlock<Block> YELLOW_GLOWING_HICKORY_SAPLING = registerBlock("yellow_glowing_hickory_sapling",
            () -> new GlowingSaplingBlock(ModTreeGrowers.YELLOW_GLOWING_HICKORY_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(ModBlocks.RED_GLOWING_HICKORY_SAPLING.get())));
    public static final DeferredBlock<Block> GREEN_GLOWING_HICKORY_SAPLING = registerBlock("green_glowing_hickory_sapling",
            () -> new GlowingSaplingBlock(ModTreeGrowers.GREEN_GLOWING_HICKORY_TREE_GROWER, BlockBehaviour.Properties.ofFullCopy(ModBlocks.RED_GLOWING_HICKORY_SAPLING.get())));

    public static final EnumMap<HickoryColour, DeferredBlock<Block>> HICKORY_SAPLINGS = new EnumMap<>(HickoryColour.class);
    static {
        HICKORY_SAPLINGS.put(HickoryColour.HICKORY, HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_SAPLING);
        HICKORY_SAPLINGS.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_SAPLING);
    }

    public static final DeferredBlock<Block> HICKORY_ROOT_BUSH = registerBlock("hickory_root_bush",
            () -> new RootBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).strength(0.5f)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> blockObj = BLOCKS.register(name, block);
        registerBlockItem(name, blockObj);
        return blockObj;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithStackLimit(String name, Supplier<T> block, int stackLimit) {
        DeferredBlock<T> blockObj = BLOCKS.register(name, block);
        registerBlockItemWithStackLimit(name, blockObj, stackLimit);
        return blockObj;
    }

    private static <T extends Block> void registerBlockItemWithStackLimit(String name, DeferredBlock<T> block, int stackLimit) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(stackLimit)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}