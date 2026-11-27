package dev.echoabyss;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class EchoBlazeRenderer extends MobRenderer<EchoBlaze, FloaterRenderState, EchoBlazeModel> {
    private static final Identifier TEXTURE = EchoAbyss.id("textures/entity/echo_blaze.png");

    public EchoBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new EchoBlazeModel(context.bakeLayer(ModModelLayers.ECHO_BLAZE)), 0.5F);
    }

    @Override
    public FloaterRenderState createRenderState() {
        return new FloaterRenderState();
    }

    @Override
    public Identifier getTextureLocation(FloaterRenderState state) {
        return TEXTURE;
    }
}
