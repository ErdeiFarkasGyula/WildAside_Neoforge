package net.farkas.wildaside.item.custom;

import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.block.custom.FallenHickoryLeavesBlock;
import net.farkas.wildaside.util.HickoryColour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HickoryLeafItem extends FuelItem {
    private final HickoryColour colour;

    public HickoryLeafItem(Item.Properties pProperties, int burnTime, HickoryColour colour) {
        super(pProperties, burnTime);
        this.colour = colour;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var level = pContext.getLevel();
        if (level.isClientSide) return InteractionResult.PASS;
        if (pContext.getHand() == InteractionHand.OFF_HAND) return InteractionResult.PASS;

        var pos = pContext.getClickedPos();
        var player = pContext.getPlayer();
        var hand = pContext.getHand();
        var blockstate = level.getBlockState(pos);

        if (blockstate.getBlock() instanceof FallenHickoryLeavesBlock) {
            return addLeaf(level, pos, player, hand);
        }
        else if (blockstate.isFaceSturdy(level, pos, Direction.UP)) {
            return placeLeaf(level, pos, player, hand);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult placeLeaf(Level level, BlockPos pos, Player player, InteractionHand hand) {
        BlockPos abovePos = pos.above();
        if (!level.isEmptyBlock(abovePos)) {
            if (level.getBlockState(abovePos).getBlock() instanceof FallenHickoryLeavesBlock) {
                return addLeaf(level, abovePos, player, hand);
            }
            return InteractionResult.PASS;
        }

        BlockState newState = ModBlocks.FALLEN_HICKORY_LEAVES.get().defaultBlockState()
                .setValue(FallenHickoryLeavesBlock.COLOUR, this.colour)
                .setValue(FallenHickoryLeavesBlock.FACING, player.getDirection().getOpposite());
        level.setBlock(abovePos, newState, 3);
        onSuccesfullPlacement(level, abovePos, player, hand);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult addLeaf(Level level, BlockPos pos, Player player, InteractionHand hand) {
        BlockState leaves = level.getBlockState(pos);

        if (this.colour != leaves.getValue(FallenHickoryLeavesBlock.COLOUR)) return InteractionResult.PASS;

        int count = leaves.getValue(FallenHickoryLeavesBlock.COUNT);
        if (count >= FallenHickoryLeavesBlock.MAX_COUNT) return InteractionResult.PASS;

        BlockState newState = leaves.setValue(FallenHickoryLeavesBlock.COUNT, count + 1);
        level.setBlock(pos, newState, 3);
        onSuccesfullPlacement(level, pos, player, hand);

        return InteractionResult.SUCCESS;
    }

    private void onSuccesfullPlacement(Level level, BlockPos pos, Player player, InteractionHand hand) {
        var item = player.getItemInHand(hand);

        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("block.big_dripleaf.place")), SoundSource.BLOCKS, 1, 1.1f);
        if (!player.isCreative()) {
            item.shrink(1);
        }
    }

    public HickoryColour getColour() {
        return this.colour;
    }
}
