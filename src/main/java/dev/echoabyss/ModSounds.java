package dev.echoabyss;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

/** Own sounds. The files are in assets/echo_abyss/sounds and are listed in sounds.json. */
public final class ModSounds {
    public static final SoundEvent ECHO_FORK = register("echo_fork");
    public static final SoundEvent ECHO_BELL = register("echo_bell");
    public static final SoundEvent ECHO_WAVE = register("echo_wave");
    public static final SoundEvent ECHO_PORTAL = register("echo_portal");
    public static final SoundEvent ECHO_WARDEN_AMBIENT = register("echo_warden_ambient");
    public static final SoundEvent ECHO_WARDEN_HURT = register("echo_warden_hurt");
    public static final SoundEvent ECHO_WARDEN_ROAR = register("echo_warden_roar");
    public static final SoundEvent ECHO_WARDEN_DEATH = register("echo_warden_death");
    public static final SoundEvent BELL_WARDEN_AMBIENT = register("bell_warden_ambient");
    public static final SoundEvent BELL_WARDEN_HURT = register("bell_warden_hurt");
    public static final SoundEvent BELL_WARDEN_ROAR = register("bell_warden_roar");
    public static final SoundEvent BELL_WARDEN_DEATH = register("bell_warden_death");

    private ModSounds() {
    }

    private static SoundEvent register(String name) {
        Identifier id = EchoAbyss.id(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    /** Calling this loads the class, which registers every sound. */
    public static void initialize() {
    }
}
