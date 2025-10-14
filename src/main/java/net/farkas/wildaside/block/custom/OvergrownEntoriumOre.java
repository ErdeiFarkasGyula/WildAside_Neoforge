package net.farkas.wildaside.block.custom;

import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.isClientSide || pHand == InteractionHand.OFF_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        var playerItem = pPlayer.getItemInHand(pHand);
        if (playerItem.getItem() == Items.SHEARS) {
            BlockState newBlock = ModBlocks.ENTORIUM_ORE.get().defaultBlockState();

            pLevel.setBlock(pPos, newBlock, 3);
            pLevel.playSound(null, pPos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1, 1);
            pPlayer.swing(pHand);

            if (!pPlayer.isInvulnerable()) {
                playerItem.hurtAndBreak(1, pPlayer, pStack.getEquipmentSlot());
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.5) {
            pLevel.addParticle(ModParticles.ENTORIUM_PARTICLE.get(), pPos.getX() + pRandom.nextFloat(), pPos.getY() + 1, pPos.getZ() + pRandom.nextFloat(), 0, 0, 0);
            super.animateTick(pState, pLevel, pPos, pRandom);
        }
    }
}
