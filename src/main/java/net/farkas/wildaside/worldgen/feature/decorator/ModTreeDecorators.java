package net.farkas.wildaside.worldgen.feature.decorator;

import com.mojang.serialization.MapCodec;
import net.farkas.wildaside.WildAside;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> DECORATORS =
            DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, WildAside.MOD_ID);

    public static final Supplier<TreeDecoratorType<FallenLeavesDecorator>> FALLEN_LEAVES =
            DECORATORS.register("fallen_leaves", () -> new TreeDecoratorType<>(MapCodec.assumeMapUnsafe(FallenLeavesDecorator.CODEC)));

    public static void register(IEventBus bus) {
        DECORATORS.register(bus);
    }
}
