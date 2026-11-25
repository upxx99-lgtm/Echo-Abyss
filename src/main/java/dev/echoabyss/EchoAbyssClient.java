package dev.echoabyss;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class EchoAbyssClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelLayers.register();

        EntityRendererProvider<MiniWarden> echo = context -> new MiniWardenRenderer(context, ModModelLayers.ECHO_WARDEN, "echo_warden");
        EntityRendererProvider<MiniWarden> bell = context -> new MiniWardenRenderer(context, ModModelLayers.BELL_WARDEN, "bell_warden");
        EntityRenderers.register(ModEntities.ECHO_WARDEN, echo);
        EntityRenderers.register(ModEntities.BELL_WARDEN, bell);

        // the particles behave like end rod sparks, but use our own sprites
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_SPARK, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_RING, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_WISP, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_NOTE, EndRodParticle.Provider::new);
    }
}
