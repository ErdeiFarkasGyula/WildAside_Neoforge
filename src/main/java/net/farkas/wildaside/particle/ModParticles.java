package net.farkas.wildaside.particle;

import net.farkas.wildaside.WildAside;
import net.farkas.wildaside.util.HickoryColour;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, WildAside.MOD_ID);

    public static final Supplier<SimpleParticleType> VIBRION_PARTICLE =
            PARTICLES.register("vibrion_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> VIBRION_DRIP_PARTICLE =
            PARTICLES.register("vibrion_drip_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> ENTORIUM_PARTICLE =
            PARTICLES.register("entorium_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> SUBSTILIUM_PARTICLE =
            PARTICLES.register("substilium_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> STILL_SUBSTILIUM_PARTICLE =
            PARTICLES.register("still_substilium_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> LIFESTEAL_PARTICLE =
            PARTICLES.register("lifesteal_particle", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> HICKORY_LEAF_PARTICLE =
            PARTICLES.register("hickory_leaf_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> RED_GLOWING_HICKORY_LEAF_PARTICLE =
            PARTICLES.register("red_glowing_hickory_leaf_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> BROWN_GLOWING_HICKORY_LEAF_PARTICLE =
            PARTICLES.register("brown_glowing_hickory_leaf_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> YELLOW_GLOWING_HICKORY_LEAF_PARTICLE =
            PARTICLES.register("yellow_glowing_hickory_leaf_particle", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> GREEN_GLOWING_HICKORY_LEAF_PARTICLE =
            PARTICLES.register("green_glowing_hickory_leaf_particle", () -> new SimpleParticleType(true));

    public static final EnumMap<HickoryColour, Supplier<SimpleParticleType>> HICKORY_PARTICLES = new EnumMap<>(HickoryColour.class);
    static {
        HICKORY_PARTICLES.put(HickoryColour.HICKORY, HICKORY_LEAF_PARTICLE);
        HICKORY_PARTICLES.put(HickoryColour.RED_GLOWING, RED_GLOWING_HICKORY_LEAF_PARTICLE);
        HICKORY_PARTICLES.put(HickoryColour.BROWN_GLOWING, BROWN_GLOWING_HICKORY_LEAF_PARTICLE);
        HICKORY_PARTICLES.put(HickoryColour.YELLOW_GLOWING, YELLOW_GLOWING_HICKORY_LEAF_PARTICLE);
        HICKORY_PARTICLES.put(HickoryColour.GREEN_GLOWING, GREEN_GLOWING_HICKORY_LEAF_PARTICLE);
    }

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}