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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WindBlasterBlockEntity extends BlockEntity {
    public enum Mode { CONSTANT, BURST }

    private int powerStored = 0;
    private Mode mode = Mode.CONSTANT;
    private int burstCooldown = 0;

    private static final int MAX_RANGE = 15;
    private static final double BASE_FORCE = 0.1D;
    private static final double BURST_MULT = 2.5D;
    private static final int BURST_COOLDOWN_TICKS = 40;

    public WindBlasterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIND_BLASTER.get(), pos, state);
    }

    public void toggleMode() {
        mode = (mode == Mode.CONSTANT) ? Mode.BURST : Mode.CONSTANT;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Mode")) this.mode = Mode.valueOf(tag.getString("Mode"));
        this.burstCooldown = tag.getInt("BurstCD");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Mode", mode.name());
        tag.putInt("BurstCD", burstCooldown);
    }

    public void tickServer(Level level, BlockPos origin, BlockState oldState) {
        if (!(level instanceof ServerLevel world)) return;

        int signal = world.getBestNeighborSignal(worldPosition);
        if (signal <= 0) {
            if (burstCooldown > 0) burstCooldown = Math.max(0, burstCooldown - 1);
            return;
        }

        Direction dir = getBlockState().getValue(WindBlaster.FACING);
        int range = Mth.clamp(signal, 1, MAX_RANGE);

        double baseForce = BASE_FORCE * (signal / 15.0);
        double force = baseForce;

        if (mode == Mode.BURST) {
            if (burstCooldown > 0) {
                force *= 0.25D;
            } else {
                force *= BURST_MULT;
                burstCooldown = BURST_COOLDOWN_TICKS;
            }
        }

        RandomSource rand = world.random;
        SimpleParticleType particle = ModParticles.LIFESTEAL_PARTICLE.get();
        Vec3 start = Vec3.atCenterOf(worldPosition);
        double step = 0.6D;

        for (double traveled = 0.8D; traveled <= range; traveled += step) {
            Vec3 sample = start.add(dir.getStepX() * traveled, dir.getStepY() * traveled, dir.getStepZ() * traveled);
            BlockPos samplePos = BlockPos.containing(sample);
            BlockState state = world.getBlockState(samplePos);

            if (state.isCollisionShapeFullBlock(world, samplePos)) break;

            interactWithBlock(state, samplePos, dir, world);

            if (rand.nextFloat() < 0.45f) {
                world.sendParticles(particle, sample.x, sample.y, sample.z, 1,
                        0.02 * dir.getStepX(), 0.02 * dir.getStepY(), 0.02 * dir.getStepZ(), 0.0);
            }

            double falloff = Math.exp(-0.25 * traveled);
            double appliedForce = force * falloff;

            AABB box = new AABB(samplePos);
            List<Entity> entities = world.getEntities((Entity) null, box, e -> true);
            for (Entity e : entities) {
                if (e instanceof ArmorStand || isHeavyEntity(e)) continue;

                Vec3 push = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ());
                Vec3 vel = e.getDeltaMovement();

                if (e instanceof LivingEntity le && le.getDeltaMovement().y < -0.05 && dir.getStepY() > 0) {
                    e.setDeltaMovement(vel.add(0.0, Math.abs(le.getDeltaMovement().y) * 0.8, 0.0));
                }

                Vec3 newVel = vel.add(push.scale(appliedForce));
                double maxSpeed = 1.5;
                double hor = Math.sqrt(newVel.x * newVel.x + newVel.z * newVel.z);
                if (hor > maxSpeed) {
                    double scale = maxSpeed / hor;
                    newVel = new Vec3(newVel.x * scale, newVel.y, newVel.z * scale);
                }

                e.setDeltaMovement(newVel);
                e.hurtMarked = true;

                if (e instanceof ItemEntity item) {
                    item.setPickUpDelay(20);
                }
            }
        }

        if (burstCooldown > 0) burstCooldown = Math.max(0, burstCooldown - 1);
    }

    private boolean isHeavyEntity(Entity e) {
        return (e instanceof IronGolem) || (e instanceof Ravager) || (e instanceof WitherBoss) || (e instanceof EnderDragon);
    }

    private void interactWithBlock(BlockState state, BlockPos pos, Direction windDir, ServerLevel world) {
        Block block = state.getBlock();

        if (block instanceof DoorBlock door) {
            boolean open = state.getValue(DoorBlock.OPEN);
            Direction facing = state.getValue(DoorBlock.FACING);

            if (windDir == facing && !open) {
                world.setBlock(pos, state.setValue(DoorBlock.OPEN, true), 3);
            } else if (windDir == facing.getOpposite() && open) {
                world.setBlock(pos, state.setValue(DoorBlock.OPEN, false), 3);
            }
        }

        if (block instanceof TrapDoorBlock trapdoor) {
            boolean open = state.getValue(TrapDoorBlock.OPEN);
            Direction facing = state.getValue(TrapDoorBlock.FACING);

            if (windDir == facing && !open) {
                world.setBlock(pos, state.setValue(TrapDoorBlock.OPEN, true), 3);
            } else if (windDir == facing.getOpposite() && open) {
                world.setBlock(pos, state.setValue(TrapDoorBlock.OPEN, false), 3);
            }
        }
    }
}
