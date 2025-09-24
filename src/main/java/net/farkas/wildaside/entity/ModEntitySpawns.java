package net.farkas.wildaside.entity;

import net.farkas.wildaside.entity.custom.vibrion.MucellithEntity;
import net.farkas.wildaside.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class ModEntitySpawns {
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.MUCELLITH.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModEntitySpawns::canSpawnMucellith,
                RegisterSpawnPlacementsEvent.Operation.OR
        );
    }

    private static boolean canSpawnMucellith(EntityType<MucellithEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        BlockState below = level.getBlockState(pos.below());
        if (!below.is(ModTags.Blocks.MUCELLITH_SPAWN_BLOCKS)) {
            return false;
        }

        int light = level.getMaxLocalRawBrightness(pos);
        return light <= 7;
    }
}