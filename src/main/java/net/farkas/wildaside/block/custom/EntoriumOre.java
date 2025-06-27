package net.farkas.wildaside.block.custom;

import net.farkas.wildaside.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.neoforged.neoforge.common.util.TriState;

public class EntoriumOre extends Block {
    public EntoriumOre(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.addParticle(ModParticles.ENTORIUM_PARTICLE.get(), pPos.getX() + Math.random(), pPos.getY() + 1, pPos.getZ() + Math.random(), 0, 0, 0);
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.TRUE;
    }
}
