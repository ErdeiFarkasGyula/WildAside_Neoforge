package net.farkas.wildaside.enchantment;

import com.mojang.serialization.MapCodec;
import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.enchantment.custom.ExtensiveResearchEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, WildAside.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> EXTENSIVE_RESEARCH =
            ENTITY_ENCHANTMENT_EFFECTS.register("extensive_research", () -> ExtensiveResearchEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
