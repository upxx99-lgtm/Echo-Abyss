package dev.echoabyss;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

/** Own particles. Sprites are in assets/echo_abyss/textures/particle. */
public final class ModParticles {
    public static final SimpleParticleType ECHO_SPARK = FabricParticleTypes.simple();
    public static final SimpleParticleType ECHO_RING = FabricParticleTypes.simple();
    public static final SimpleParticleType ECHO_WISP = FabricParticleTypes.simple();
    public static final SimpleParticleType ECHO_NOTE = FabricParticleTypes.simple();

    private ModParticles() {
    }

    public static void initialize() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, EchoAbyss.id("echo_spark"), ECHO_SPARK);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, EchoAbyss.id("echo_ring"), ECHO_RING);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, EchoAbyss.id("echo_wisp"), ECHO_WISP);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, EchoAbyss.id("echo_note"), ECHO_NOTE);
    }
}
