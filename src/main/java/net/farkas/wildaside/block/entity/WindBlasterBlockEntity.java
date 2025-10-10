package net.farkas.wildaside.block.entity;

import net.farkas.wildaside.block.custom.vibrion.WindBlaster;
import net.farkas.wildaside.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WindBlasterBlockEntity extends BlockEntity {
    private static final int MAX_RANGE = 15;
    private static final double BASE_FORCE = 0.2D;

    public WindBlasterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIND_BLASTER.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public void tickServer(Level level, BlockPos origin, BlockState oldState) {
        if (!(level instanceof ServerLevel world)) return;

        Direction facing = getBlockState().getValue(WindBlaster.FACING);
        Direction back = facing.getOpposite();

        int rangeSignal = world.getSignal(worldPosition.relative(back), back);

        int strength = 1;
        for (Direction dir : Direction.values()) {
            if (dir == back) continue;
            strength = Math.max(strength, world.getSignal(worldPosition.relative(dir), dir));
        }

        if (rangeSignal <= 0) return;

        int range = Mth.clamp(rangeSignal, 1, MAX_RANGE);
        double force = BASE_FORCE * (strength / 15.0);

        RandomSource rand = world.random;
        SimpleParticleType particle = ModParticles.LIFESTEAL_PARTICLE.get();
        Vec3 start = Vec3.atCenterOf(worldPosition);
        double step = 0.1D;

        for (double traveled = 0.8D; traveled <= range; traveled += step) {
            Vec3 sample = start.add(facing.getStepX() * traveled, facing.getStepY() * traveled, facing.getStepZ() * traveled);
            BlockPos samplePos = BlockPos.containing(sample);
            BlockState state = world.getBlockState(samplePos);

            if (state.isCollisionShapeFullBlock(world, samplePos)) break;

            if (rand.nextFloat() < 0.3f + 0.02f * strength) {
                world.sendParticles(particle, sample.x, sample.y, sample.z, 1,
                        0.02 * facing.getStepX(), 0.02 * facing.getStepY(), 0.02 * facing.getStepZ(), 0.0);
            }

            double falloff = Math.exp(-0.25 * traveled);
            double appliedForce = force * falloff;

            AABB box = new AABB(samplePos);
            List<Entity> entities = world.getEntities((Entity) null, box, e -> true);
            for (Entity e : entities) {
                if (e instanceof ArmorStand) continue;
                if (isHeavyEntity(e)) continue;

                Vec3 velocity = e.getDeltaMovement();

                if (velocity.y < -0.08 && facing.getStepY() > 0) {
                    double fallSpeed = Math.abs(velocity.y);
                    double lift = Math.min(fallSpeed * 0.25 * (strength / 15.0), 0.12);
                    velocity = velocity.add(0.0, lift, 0.0);
                }

                Vec3 push = new Vec3(facing.getStepX(), facing.getStepY(), facing.getStepZ());
                velocity = velocity.add(push.scale(appliedForce));

                double maxSpeed = 1.5;
                double hor = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
                if (hor > maxSpeed) {
                    double scale = maxSpeed / hor;
                    velocity = new Vec3(velocity.x * scale, velocity.y, velocity.z * scale);
                }

                e.setDeltaMovement(velocity);
                e.hurtMarked = true;
            }
        }
    }

    private boolean isHeavyEntity(Entity e) {
        return (e instanceof IronGolem) || (e instanceof Ravager) || (e instanceof WitherBoss) || (e instanceof EnderDragon);
    }
}