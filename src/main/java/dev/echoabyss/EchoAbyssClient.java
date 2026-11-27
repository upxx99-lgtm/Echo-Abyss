package dev.echoabyss;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;

public class EchoAbyssClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelLayers.register();

        // the upgrade template shows the same kind of tooltip as the vanilla netherite template
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.getItem() == ModItems.ECHO_UPGRADE_TEMPLATE) {
                lines.add(Component.translatable("tooltip.echo_abyss.template.upgrade").withStyle(ChatFormatting.GRAY));
                lines.add(Component.translatable("tooltip.echo_abyss.template.applies_to").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" ").append(Component.translatable("tooltip.echo_abyss.template.applies_to_value")).withStyle(ChatFormatting.BLUE));
                lines.add(Component.translatable("tooltip.echo_abyss.template.ingredients").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" ").append(Component.translatable("tooltip.echo_abyss.template.ingredients_value")).withStyle(ChatFormatting.BLUE));
            }
        });

        EntityRendererProvider<MiniWarden> echo = context -> new MiniWardenRenderer(context, ModModelLayers.ECHO_WARDEN, "echo_warden");
        EntityRendererProvider<MiniWarden> bell = context -> new MiniWardenRenderer(context, ModModelLayers.BELL_WARDEN, "bell_warden");
        EntityRenderers.register(ModEntities.ECHO_WARDEN, echo);
        EntityRenderers.register(ModEntities.BELL_WARDEN, bell);
        EntityRenderers.register(ModEntities.ECHO_BLAZE, EchoBlazeRenderer::new);
        EntityRenderers.register(ModEntities.ECHO_GOLEM, EchoGolemRenderer::new);

        // the particles behave like end rod sparks, but use our own sprites
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_SPARK, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_RING, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_WISP, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ECHO_NOTE, EndRodParticle.Provider::new);
    }
}
