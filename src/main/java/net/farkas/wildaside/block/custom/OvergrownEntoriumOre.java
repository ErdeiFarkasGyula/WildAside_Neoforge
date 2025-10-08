package net.farkas.wildaside.block.custom;

import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OvergrownEntoriumOre extends EntoriumOre {
    public OvergrownEntoriumOre(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide() || hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;

        var playerItem = player.getItemInHand(hand);
        if (playerItem.getItem() == Items.SHEARS) {
            BlockState newBlock = ModBlocks.ENTORIUM_ORE.get().defaultBlockState();

            level.setBlock(pos, newBlock, 3);
            level.playSound(null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1, 1);
            player.swing(hand);

            if (!player.isInvulnerable()) {
                playerItem.hurtAndBreak(1, player, stack.getEquipmentSlot());
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.5) {
            pLevel.addParticle(ModParticles.ENTORIUM_PARTICLE.get(), pPos.getX() + pRandom.nextFloat(), pPos.getY() + 1, pPos.getZ() + pRandom.nextFloat(), 0, 0, 0);
            super.animateTick(pState, pLevel, pPos, pRandom);
        }
    }
}
