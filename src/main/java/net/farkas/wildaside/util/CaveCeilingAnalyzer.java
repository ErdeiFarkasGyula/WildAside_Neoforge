package net.farkas.wildaside.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaveCeilingAnalyzer {
    public static void visualizeCeilingSpace(ServerLevel level, BlockPos origin, int radius) {
        int bestSum = -1;
        BlockPos bestCenter = origin;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = origin.offset(dx, 0, dz);
                int dist = findCeilingDistance(level, pos);

                Vec3 particlePos = Vec3.atCenterOf(pos);
                for (int i = 0; i < dist; i++) {
                    level.sendParticles(ParticleTypes.END_ROD,
                            particlePos.x, particlePos.y + i, particlePos.z,
                            1, 0, 0, 0, 0);
                }

                int localSum = 0;
                for (int sx = -1; sx <= 1; sx++) {
                    for (int sz = -1; sz <= 1; sz++) {
                        BlockPos check = pos.offset(sx, 0, sz);
                        localSum += findCeilingDistance(level, check);
                    }
                }
                if (localSum > bestSum) {
                    bestSum = localSum;
                    bestCenter = pos;
                }
            }
        }

        int size = 3;
        for (int dx = -size / 2; dx <= size / 2; dx++) {
            for (int dz = -size / 2; dz <= size / 2; dz++) {
                BlockPos edge = bestCenter.offset(dx, 0, dz);
                if (Math.abs(dx) == size / 2 || Math.abs(dz) == size / 2) {
                    level.sendParticles(ParticleTypes.FLAME,
                            edge.getX() + 0.5, edge.getY() + 0.2, edge.getZ() + 0.5,
                            3, 0, 0, 0, 0.0);
                }
            }
        }
    }

    private static void highlightBestRegion(ServerLevel level, BlockPos center) {
        int size = 3;
        for (int dx = -size / 2; dx <= size / 2; dx++) {
            for (int dz = -size / 2; dz <= size / 2; dz++) {
                BlockPos edge = center.offset(dx, 0, dz);
                if (Math.abs(dx) == size / 2 || Math.abs(dz) == size / 2) {
                    level.sendParticles(ParticleTypes.FLAME,
                            edge.getX() + 0.5, edge.getY() + 0.2, edge.getZ() + 0.5,
                            3, 0, 0, 0, 0.0);
                }
            }
        }
    }

    public static int findCeilingDistance(LevelAccessor level, BlockPos pos) {
        int dist = 0;
        BlockPos.MutableBlockPos cursor = pos.mutable();

        cursor.move(0, 1, 0);
        while (cursor.getY() < level.getMaxBuildHeight()) {
            if (!level.isEmptyBlock(cursor)) break;
            dist++;
            cursor.move(0, 1, 0);
        }
        return dist;
    }

    public static Map<BlockPos, Integer> sampleClearanceGrid(LevelAccessor level, BlockPos origin, int radius) {
        Map<BlockPos, Integer> map = new HashMap<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) continue;
                BlockPos p = origin.offset(dx, 0, dz);
                int clearance = findCeilingDistance(level, p);
                map.put(p.immutable(), clearance);
            }
        }
        return map;
    }

    public static Vec3 calculateLeanDirection(LevelAccessor level, BlockPos origin, int radius) {
        Vec3 lean = Vec3.ZERO;
        List<Integer> heights = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) continue;
                BlockPos pos = origin.offset(dx, 0, dz);
                int clearance = findCeilingDistance(level, pos);
                lean = lean.add(dx * clearance, 0, dz * clearance);
                heights.add(clearance);
            }
        }

        if (heights.isEmpty()) return Vec3.ZERO;
        double mean = heights.stream().mapToInt(Integer::intValue).average().orElse(0);
        double variance = heights.stream().mapToDouble(h -> (h - mean) * (h - mean)).sum() / heights.size();

        Vec3 result = lean.normalize();
        double exaggeration = Math.min(variance / 6.0, 1.5); // scale up lean where contrast is strong
        return result.scale(exaggeration);
    }
}