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

    public static final Supplier<SoundEvent> MUCELLITH_AMBIENT = registerSoundEvent("entity.mucellith.ambient");
    public static final Supplier<SoundEvent> MUCELLITH_HURT = registerSoundEvent("entity.mucellith.hurt");
    public static final Supplier<SoundEvent> MUCELLITH_DEATH = registerSoundEvent("entity.mucellith.death");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(WildAside.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}