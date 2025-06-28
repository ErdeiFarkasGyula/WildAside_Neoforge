package net.farkas.wildaside.recipe;

import net.farkas.wildaside.WildAside;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, WildAside.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, WildAside.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BioengineeringWorkstationRecipe>> BIOENGINEERING_SERIALIZER =
            SERIALIZERS.register("bioengineering", BioengineeringWorkstationRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<BioengineeringWorkstationRecipe>> BIOENGINEERING_TYPE =
            TYPES.register("bioengineering", () -> new RecipeType<BioengineeringWorkstationRecipe>() {
                @Override
                public String toString() {
                    return "bioengineering";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}