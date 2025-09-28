package net.farkas.wildaside.potion;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.effect.ModMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, WildAside.MOD_ID);

//    public static final Holder<Potion> CONTAMINATION_POTION = POTIONS.register("contamination_potion",
//            () -> new Potion(new MobEffectInstance(ModMobEffects.CONTAMINATION.getDelegate(), 400, 0)));
//    public static final Holder<Potion> CONTAMINATION_POTION_2 = POTIONS.register("contamination_potion_2",
//            () -> new Potion(new MobEffectInstance(ModMobEffects.CONTAMINATION.getDelegate(), 400, 1)));
//
//    public static final Holder<Potion> IMMUNITY_POTION = POTIONS.register("immunity_potion",
//            () -> new Potion(new MobEffectInstance(ModMobEffects.IMMUNITY.getDelegate(), 400, 0)));
//    public static final Holder<Potion> IMMUNITY_POTION_2 = POTIONS.register("immunity_potion_2",
//            () -> new Potion(new MobEffectInstance(ModMobEffects.IMMUNITY.getDelegate(), 400, 1)));

    public static final Holder<Potion> LIFESTEAL_POTION = POTIONS.register("lifesteal_potion",
            () -> new Potion(new MobEffectInstance(ModMobEffects.LIFESTEAL.getDelegate(), 1800, 0)));
    public static final Holder<Potion> LIFESTEAL_POTION_2 = POTIONS.register("lifesteal_potion_2",
            () -> new Potion(new MobEffectInstance(ModMobEffects.LIFESTEAL.getDelegate(), 1800, 1)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}