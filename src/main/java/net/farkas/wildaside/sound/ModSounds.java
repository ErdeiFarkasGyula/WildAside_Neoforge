package net.farkas.wildaside.sound;

import net.farkas.wildaside.WildAside;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, WildAside.MOD_ID);

    public static final Supplier<SoundEvent> VIBRION_HIVE_MUSIC = registerSoundEvent("music.vibrion_hive");

    public static final Supplier<SoundEvent> MUCELLITH_AMBIENT_1 = registerSoundEvent("mucellith.ambient_1");
    public static final Supplier<SoundEvent> MUCELLITH_AMBIENT_2 = registerSoundEvent("mucellith.ambient_2");
    public static final Supplier<SoundEvent> MUCELLITH_AMBIENT_3 = registerSoundEvent("mucellith.ambient_3");
    public static final Supplier<SoundEvent> MUCELLITH_AMBIENT_4 = registerSoundEvent("mucellith.ambient_4");

    public static final Supplier<SoundEvent> MUCELLITH_HURT_1 = registerSoundEvent("mucellith.hurt_1");
    public static final Supplier<SoundEvent> MUCELLITH_HURT_2 = registerSoundEvent("mucellith.hurt_2");
    public static final Supplier<SoundEvent> MUCELLITH_HURT_3 = registerSoundEvent("mucellith.hurt_3");

    public static final Supplier<SoundEvent> MUCELLITH_DEATH = registerSoundEvent("mucellith.death");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}