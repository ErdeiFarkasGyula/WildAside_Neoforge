package net.farkas.wildaside.block.entity;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.block.ModBlocks;
import net.farkas.wildaside.block.entity.sign.ModHangingSignBlockEntity;
import net.farkas.wildaside.block.entity.sign.ModSignBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, WildAside.MOD_ID);

    public static final Supplier<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register("mod_sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new,
                    ModBlocks.SUBSTILIUM_SIGN.get(), ModBlocks.SUBSTILIUM_WALL_SIGN.get(),
                    ModBlocks.HICKORY_SIGN.get(), ModBlocks.HICKORY_HANGING_SIGN.get()).build(null));

    public static final Supplier<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITIES.register("mod_hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                    ModBlocks.SUBSTILIUM_HANGING_SIGN.get(), ModBlocks.SUBSTILIUM_WALL_HANGING_SIGN.get(),
                    ModBlocks.HICKORY_HANGING_SIGN.get(),  ModBlocks.HICKORY_WALL_HANGING_SIGN.get()).build(null));

    public static final Supplier<BlockEntityType<BioengineeringWorkstationBlockEntity>> BIOENGINEERING_WORKSTATION =
            BLOCK_ENTITIES.register("bioengineering_workstation", () -> BlockEntityType.Builder.of(BioengineeringWorkstationBlockEntity::new,
                    ModBlocks.BIOENGINEERING_WORKSTATION.get()).build(null));

    public static final Supplier<BlockEntityType<NaturalSporeBlasterBlockEntity>> NATURAL_SPORE_BLASTER =
            BLOCK_ENTITIES.register("natural_spore_blaster",
                    () -> BlockEntityType.Builder.of(NaturalSporeBlasterBlockEntity::new, ModBlocks.NATURAL_SPORE_BLASTER.get()).build(null));

    public static final Supplier<BlockEntityType<SporeBlasterBlockEntity>> SPORE_BLASTER =
            BLOCK_ENTITIES.register("spore_blaster",
                    () -> BlockEntityType.Builder.of(SporeBlasterBlockEntity::new, ModBlocks.SPORE_BLASTER.get()).build(null));

    public static final Supplier<BlockEntityType<PotionBlasterBlockEntity>> POTION_BLASTER =
            BLOCK_ENTITIES.register("potion_blaster",
                    () -> BlockEntityType.Builder.of(PotionBlasterBlockEntity::new, ModBlocks.POTION_BLASTER.get()).build(null));

    public static final Supplier<BlockEntityType<WindBlasterBlockEntity>> WIND_BLASTER =
            BLOCK_ENTITIES.register("wind_blaster",
                    () -> BlockEntityType.Builder.of(WindBlasterBlockEntity::new, ModBlocks.POTION_BLASTER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
