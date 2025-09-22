package net.farkas.wildaside.block.entity;

import net.farkas.wildaside.block.custom.vibrion.PotionBlaster;
import net.farkas.wildaside.screen.potion_blaster.PotionBlasterMenu;
import net.farkas.wildaside.util.AdvancementHandler;
import net.farkas.wildaside.util.BlasterUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class PotionBlasterBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private boolean shouldBreakNext;

    public static final int OUTPUT_1 = 9;

    public final ContainerData data;

    public int potionColour = 0;
    public int potionTicksLeft = 0;
    public int maxPotionTicks = 200;
    public int lastUsedSlot = -1;
    public ItemStack activePotion = ItemStack.EMPTY;
    public boolean shouldSelectNewPotion = true;

    public PotionBlasterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.POTION_BLASTER.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PotionBlasterBlockEntity.this.potionTicksLeft;
                    case 1 -> PotionBlasterBlockEntity.this.maxPotionTicks;
                    case 2 -> PotionBlasterBlockEntity.this.potionColour;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PotionBlasterBlockEntity.this.potionTicksLeft = pValue;
                    case 1 -> PotionBlasterBlockEntity.this.maxPotionTicks = pValue;
                    case 2 -> PotionBlasterBlockEntity.this.potionColour = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.wildaside.potion_blaster");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PotionBlasterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void shootPotionBeam(Direction direction, ServerLevel level, BlockPos pos) {
        if (activePotion.isEmpty()) return;

        Iterable<MobEffectInstance> effects = activePotion.get(DataComponents.POTION_CONTENTS).getAllEffects();
        this.potionColour = PotionContents.getColor(effects);
        int power = level.getBestNeighborSignal(pos);

        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

        float r = ((potionColour >> 16) & 0xFF) / 255.0f;
        float g = ((potionColour >> 8) & 0xFF) / 255.0f;
        float b = (potionColour & 0xFF) / 255.0f;

        DustParticleOptions particle = new DustParticleOptions(new Vector3f(r, g, b), 1f);
        RandomSource random = level.random;

        for (int i = 1; i <= power; i++) {
            if (shouldBreakNext) {
                shouldBreakNext = false;
                break;
            }

            BlockPos target = pos.relative(direction, i);
            if (level.getBlockState(target).isCollisionShapeFullBlock(level, target)) break;

            var nextBlock = level.getBlockState(target);
            var originBlock = level.getBlockState(this.getBlockPos());
            var axis = originBlock.getValue(PotionBlaster.FACING).getAxis();

            if (axis == Direction.Axis.Y) {
                if (nextBlock.getBlock() instanceof SlabBlock) {
                    break;
                }
                if (nextBlock.getBlock() instanceof TrapDoorBlock) {
                    if (!nextBlock.getValue(TrapDoorBlock.OPEN)) {
                        break;
                    }
                }
                if (nextBlock.getBlock() instanceof StairBlock) {
                    break;
                }
            }
            else
            if (axis == Direction.Axis.X) {
                if (nextBlock.getBlock() instanceof StairBlock) {
                    if (nextBlock.getValue(StairBlock.FACING).getAxis() == Direction.Axis.X) {
                        break;
                    }
                }
                if (nextBlock.getBlock() instanceof TrapDoorBlock) {
                    if (nextBlock.getValue(TrapDoorBlock.OPEN)) {
                        Direction facing = nextBlock.getValue(TrapDoorBlock.FACING);
                        if (facing.getAxis() == Direction.Axis.X) {
                            if (direction == facing) {
                                break;
                            } else {
                                shouldBreakNext = true;
                            }
                        }
                    }
                }
                if (nextBlock.getBlock() instanceof DoorBlock) {
                    var open = nextBlock.getValue(DoorBlock.OPEN);
                    Direction facing = nextBlock.getValue(DoorBlock.FACING);

                    if (!open) {
                        if (facing.getAxis() == Direction.Axis.X) {
                            if (BlasterUtils.axisToDirection(axis, direction.getStepX()) == facing) {
                                break;
                            } else {
                                shouldBreakNext = true;
                            }
                        }
                    } else {
                        if (facing.getAxis() != Direction.Axis.X) {
                            if (BlasterUtils.doorDirectionCheck(axis, direction.getStepX(), facing)) {
                                if (nextBlock.getValue(DoorBlock.HINGE) == DoorHingeSide.LEFT) {
                                    break;
                                }
                                shouldBreakNext = true;
                            } else {
                                if (nextBlock.getValue(DoorBlock.HINGE) == DoorHingeSide.LEFT) {
                                    shouldBreakNext = true;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else
            if (axis == Direction.Axis.Z) {
                if (nextBlock.getBlock() instanceof StairBlock) {
                    if (nextBlock.getValue(StairBlock.FACING).getAxis() == Direction.Axis.Z) {
                        break;
                    }
                }
                if (nextBlock.getBlock() instanceof TrapDoorBlock) {
                    if (nextBlock.getValue(TrapDoorBlock.OPEN)) {
                        Direction facing = nextBlock.getValue(TrapDoorBlock.FACING);
                        if (facing.getAxis() == Direction.Axis.Z) {
                            if (direction == facing) {
                                break;
                            } else {
                                shouldBreakNext = true;
                            }
                        }
                    }
                }
                if (nextBlock.getBlock() instanceof DoorBlock) {
                    var open = nextBlock.getValue(DoorBlock.OPEN);
                    Direction facing = nextBlock.getValue(DoorBlock.FACING);

                    if (!open) {
                        if (facing.getAxis() == Direction.Axis.Z) {
                            if (direction == facing) {
                                break;
                            } else {
                                shouldBreakNext = true;
                            }
                        }
                    } else {
                        if (facing.getAxis() != Direction.Axis.Z) {
                            if (BlasterUtils.doorDirectionCheck(axis, direction.getStepZ(), facing)) {
                                if (nextBlock.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT) {
                                    break;
                                }
                                shouldBreakNext = true;
                            } else {
                                if (nextBlock.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT) {
                                    shouldBreakNext = true;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            for (int k = 0; k < 2; k++) {
                double x = target.getX() + random.nextDouble();
                double y = target.getY() + random.nextDouble();
                double z = target.getZ() + random.nextDouble();

                level.sendParticles(particle,
                        x, y, z, 1, direction.getStepX(), direction.getStepY(), direction.getStepZ(), 0.1
                );
            }

            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(target));
            for (LivingEntity entity : targets) {
                for (MobEffectInstance effect : effects) {
                    entity.addEffect(new MobEffectInstance(
                            effect.getEffect(),
                            effect.getDuration() - (maxPotionTicks - potionTicksLeft),
                            effect.getAmplifier()
                    ));
                    level.sendParticles(particle,
                            entity.getX(), entity.getY(), entity.getZ(), 1, direction.getStepX(), direction.getStepY(), direction.getStepZ(), 0.1
                    );
                    if (entity instanceof ServerPlayer player) {
                        AdvancementHandler.givePlayerAdvancement(player, "brew_barrage");
                    }
                }
            }
        }
    }

    public void consumePotionBottle() {
        if (lastUsedSlot >= 0 && lastUsedSlot < 9) {
            ItemStack stack = itemHandler.getStackInSlot(lastUsedSlot);
            if (!stack.isEmpty()) {
                itemHandler.setStackInSlot(lastUsedSlot, stack);
            }
        }

        activePotion = ItemStack.EMPTY;
        potionTicksLeft = 0;

        lastUsedSlot = -1;

        setChanged();
    }

    public void selectNewPotion() {
        if (!activePotion.isEmpty() || potionTicksLeft > 0) return;

        List<Integer> validSlots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (i != lastUsedSlot && stack.getItem() instanceof PotionItem && !stack.isEmpty()) {
                validSlots.add(i);
            }
        }

        if (validSlots.isEmpty()) {
            activePotion = ItemStack.EMPTY;
            return;
        }

        int slot = validSlots.get(level.random.nextInt(validSlots.size()));
        ItemStack potionStack = itemHandler.getStackInSlot(slot);

        itemHandler.insertItem(OUTPUT_1, new ItemStack(Items.GLASS_BOTTLE), false);

        activePotion = potionStack.copy();
        setChanged();

        Iterable<MobEffectInstance> effectInstances = activePotion.get(DataComponents.POTION_CONTENTS).getAllEffects();
        List<MobEffectInstance> effects = StreamSupport.stream(effectInstances.spliterator(), false).toList();

        if (effects.isEmpty()) {
            maxPotionTicks = 200;
        } else {
            maxPotionTicks = effects.stream().mapToInt(MobEffectInstance::getDuration).max().orElse(200);
        }
        setChanged();

        potionTicksLeft = maxPotionTicks;

        potionStack.shrink(1);
        itemHandler.setStackInSlot(slot, potionStack);

        lastUsedSlot = slot;

    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("ticks_left", potionTicksLeft);
        pTag.putInt("max_ticks", maxPotionTicks);
        pTag.putInt("colour", potionColour);
        if (activePotion.isEmpty()) {
            pTag.remove("potion");
        } else {
            pTag.put("potion", activePotion.save(pRegistries));
        }

    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        potionTicksLeft = pTag.getInt("ticks_left");
        maxPotionTicks = pTag.getInt("max_ticks");
        potionColour = pTag.getInt("colour");
        if (pTag.contains("potion")) {
            activePotion = ItemStack.parseOptional(pRegistries, pTag.getCompound("potion"));
        } else {
            activePotion = ItemStack.EMPTY;
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof PotionBlasterBlockEntity be) {
            if (level.getBestNeighborSignal(pos) > 0) {
                int poweredSides = 0;
                for (Direction dir : Direction.values()) {
                    if (level.getSignal(pos.relative(dir), dir) > 0) {
                        poweredSides++;
                    }
                }

                if (poweredSides >= 2) {
                    clearActivePotion();
                    return;
                }

                if (potionTicksLeft <= 0 || activePotion.isEmpty()) {
                    if (shouldSelectNewPotion) {
                        selectNewPotion();
                    }
                }

                if (!activePotion.isEmpty()) {
                    shootPotionBeam(state.getValue(PotionBlaster.FACING), (ServerLevel)level, pos);
                    potionTicksLeft--;

                    if (potionTicksLeft <= 0) {
                        consumePotionBottle();
                    }
                }
            }
        }
    }

    public void clearActivePotion() {
        if (!activePotion.isEmpty()) {
            activePotion = ItemStack.EMPTY;
            potionTicksLeft = 0;
            lastUsedSlot = -1;
            shouldSelectNewPotion = true;
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }
}
