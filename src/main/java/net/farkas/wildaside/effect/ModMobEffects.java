package net.farkas.wildaside.effect;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.effect.custom.Contamination;
import net.farkas.wildaside.effect.custom.Immunity;
import net.farkas.wildaside.effect.custom.Lifesteal;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, WildAside.MOD_ID);

    public static final Holder<MobEffect> CONTAMINATION = MOB_EFFECTS.register("contamination",
            () -> new Contamination(MobEffectCategory.HARMFUL, 0xefca73)
                    .addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "contamination_armor_reduction"), -0.10 , AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "contamination_damage_reduction"), -0.10 , AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.SPAWN_REINFORCEMENTS_CHANCE, ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, "contamination_reinforcement_boost"), 0.01, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> IMMUNITY = MOB_EFFECTS.register("immunity",
            () -> new Immunity(MobEffectCategory.BENEFICIAL, -3579177)
    );

    public static final Holder<MobEffect> LIFESTEAL = MOB_EFFECTS.register("lifesteal",
            () -> new Lifesteal(MobEffectCategory.BENEFICIAL, 11141120)
    );

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
