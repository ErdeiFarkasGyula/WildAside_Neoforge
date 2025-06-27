package net.farkas.wildaside.block.custom.vibrion;

import net.farkas.wildaside.block.entity.NaturalSporeBlasterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NaturalSporeBlaster extends RotatedPillarBlock implements EntityBlock {
    public NaturalSporeBlaster(Properties props) {
        super(props);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, @Nullable Direction side) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(NaturalSporeBlaster.AXIS, pContext.getNearestLookingDirection().getOpposite().getAxis());
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean moved) {
        if (!world.isClientSide) world.scheduleTick(pos, this, 2);
        super.neighborChanged(state, world, pos, neighbor, neighborPos, moved);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved) {
        if (!world.isClientSide) world.scheduleTick(pos, this, 2);
        super.onPlace(state, world, pos, oldState, moved);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof NaturalSporeBlasterBlockEntity sbe) {
            sbe.tickServer();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NaturalSporeBlasterBlockEntity(pos, state);
    }
}
