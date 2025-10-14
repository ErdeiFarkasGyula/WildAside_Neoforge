package net.farkas.wildaside.block.custom;

import net.farkas.wildaside.item.ModItems;
import net.farkas.wildaside.util.GlowingHickoryLightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowingHickorySaplingBlock extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    private static final int MIN_LIGHT = 0;
    private static final int MAX_LIGHT = 7;
    public static final IntegerProperty LIGHT = IntegerProperty.create("light", MIN_LIGHT, MAX_LIGHT);
    public static final BooleanProperty FIXED_LIGHTING = BooleanProperty.create("fixed_lighting");

    public GlowingHickorySaplingBlock(TreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties.lightLevel(s -> s.getValue(LIGHT)));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(STAGE, 0)
                .setValue(LIGHT, 0)
                .setValue(FIXED_LIGHTING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE, LIGHT, FIXED_LIGHTING);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            pLevel.scheduleTick(pPos, this, 0);
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.isClientSide || pHand == InteractionHand.OFF_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        var playerItem = pPlayer.getItemInHand(pHand);

        if (playerItem.getItem().equals(ModItems.VIBRION.get())) {
            if (pState.getValue(GlowingHickoryLeavesBlock.FIXED_LIGHTING)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            pLevel.setBlock(pPos, pState.setValue(GlowingHickorySaplingBlock.FIXED_LIGHTING, true), 3);
            pLevel.playSound(null, pPos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1, 1);
            pPlayer.swing(pHand);

            if (!pPlayer.isInvulnerable()) {
                playerItem.shrink(1);
            }

            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if (pLevel.isClientSide() || pState.getValue(GlowingHickorySaplingBlock.FIXED_LIGHTING)) return;

        int time = (int)pLevel.dayTime();
        int currentLight = pLevel.getBlockState(pPos).getValue(LIGHT);
        int newLight = GlowingHickoryLightUtil.getLight(time, MIN_LIGHT, MAX_LIGHT);

        if (newLight != currentLight) {
            pLevel.setBlockAndUpdate(pPos, pLevel.getBlockState(pPos).setValue(LIGHT, newLight));
        }

        pLevel.scheduleTick(pPos, this, 100);

    }
}
