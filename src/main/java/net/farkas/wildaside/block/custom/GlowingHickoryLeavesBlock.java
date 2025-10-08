package net.farkas.wildaside.block.custom;

import com.mojang.serialization.MapCodec;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.particle.ModParticles;
import net.farkas.wildaside.util.GlowingHickoryLightUtil;
import net.farkas.wildaside.util.HickoryColour;
import net.farkas.wildaside.util.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class GlowingHickoryLeavesBlock extends LeavesBlock {
    private static final int MIN_LIGHT = 0;
    private static final int MAX_LIGHT = 7;
    public static final IntegerProperty LIGHT = IntegerProperty.create("light", MIN_LIGHT, MAX_LIGHT);
    public static final BooleanProperty FIXED_LIGHTING = BooleanProperty.create("fixed_lighting");
    private final HickoryColour colour;

    public GlowingHickoryLeavesBlock(Properties pProperties, HickoryColour colour) {
        super(0.2f, pProperties.lightLevel(s -> s.getValue(LIGHT)));
        this.colour = colour;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 7)
                .setValue(PERSISTENT, false)
                .setValue(WATERLOGGED, false)
                .setValue(LIGHT, 0)
                .setValue(FIXED_LIGHTING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE, PERSISTENT, WATERLOGGED, LIGHT, FIXED_LIGHTING);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide()) {
            pLevel.scheduleTick(pPos, this, 0);
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide() || hand == InteractionHand.OFF_HAND || state.getValue(GlowingHickoryLeavesBlock.FIXED_LIGHTING))
            return InteractionResult.PASS;

        ItemStack playerItem = player.getItemInHand(hand);

        if (playerItem.getItem().equals(ModItems.VIBRION.get())) {
            level.setBlock(pos, state.setValue(GlowingHickoryLeavesBlock.FIXED_LIGHTING, true), 3);
            level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1);
            player.swing(hand);

            if (!player.isInvulnerable()) {
                playerItem.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);

        if (pState.getValue(GlowingHickoryLeavesBlock.FIXED_LIGHTING)) return;

        int time = (int)pLevel.dayTime();
        int currentLight = pState.getValue(LIGHT);
        int newLight = GlowingHickoryLightUtil.getLight(time, MIN_LIGHT, MAX_LIGHT);

        if (newLight != currentLight) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(LIGHT, newLight));
        }

        pLevel.scheduleTick(pPos, this, 100);

    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pLevel.getBlockState(pPos.below()).isFaceSturdy(pLevel, pPos.below(), Direction.UP)) return;

        SimpleParticleType particle = ModParticles.HICKORY_PARTICLES.get(colour).get();

        if (pRandom.nextFloat() < 0.025) {
            ParticleUtils.spawnHickoryParticles(pLevel, pPos, pRandom, particle);
        }
    }

    @Override
    protected void spawnFallingLeavesParticle(Level level, BlockPos blockPos, RandomSource randomSource) {

    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.025) {
            spawnNewFallenLeaves(pLevel, pPos, pRandom);
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private void spawnNewFallenLeaves(Level level, BlockPos pPos, RandomSource random) {
        if (level.isClientSide()) return;

        int x = pPos.getX() + random.nextIntBetweenInclusive(-2, 2);
        int z = pPos.getZ() + random.nextIntBetweenInclusive(-2, 2);


        int groundY = -100;
        for (int y = pPos.getY(); y >= -64; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
                BlockState aboveState = level.getBlockState(pos.above());
                if (aboveState.isAir() || aboveState.getBlock() instanceof FallenHickoryLeavesBlock) {
                    groundY = y;
                    break;
                }
                break;
            }
        }

        if (groundY > -64) {
            BlockPos target = new BlockPos(x, groundY + 1, z);
            BlockState state = level.getBlockState(target);
            Direction direction;
            int count = 1;

            if (state.getBlock() instanceof FallenHickoryLeavesBlock) {
                if (state.getValue(FallenHickoryLeavesBlock.COLOUR) != this.colour) return;
                count = java.lang.Math.min(level.getBlockState(target).getValue(FallenHickoryLeavesBlock.COUNT) + 1, 3);
                direction = level.getBlockState(target).getValue(FallenHickoryLeavesBlock.FACING);
            } else {
                direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            }

            BlockState leafSt = ModBlocks.FALLEN_HICKORY_LEAVES.get()
                    .defaultBlockState()
                    .setValue(FallenHickoryLeavesBlock.COUNT, count)
                    .setValue(FallenHickoryLeavesBlock.COLOUR, this.colour)
                    .setValue(FallenHickoryLeavesBlock.FACING, direction);

            level.setBlock(target, leafSt, 3);
        }
    }

    @Override
    public MapCodec<? extends LeavesBlock> codec() {
        return null;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }
}
