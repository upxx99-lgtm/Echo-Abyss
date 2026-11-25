package dev.echoabyss;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

/** Draws a mini-boss with its own model. The texture depends on the mood: calm, angry or hurt. */
public class MiniWardenRenderer extends MobRenderer<MiniWarden, MiniWardenRenderState, MiniWardenModel> {
    private final Identifier calm;
    private final Identifier angry;
    private final Identifier hurt;

    public MiniWardenRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, String name) {
        super(context, new MiniWardenModel(context.bakeLayer(layer)), 0.9F);
        this.calm = EchoAbyss.id("textures/entity/" + name + "_calm.png");
        this.angry = EchoAbyss.id("textures/entity/" + name + "_angry.png");
        this.hurt = EchoAbyss.id("textures/entity/" + name + "_hurt.png");
    }

    @Override
    public MiniWardenRenderState createRenderState() {
        return new MiniWardenRenderState();
    }

    @Override
    public void extractRenderState(MiniWarden entity, MiniWardenRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.attack = entity.getAttackAnim(tickProgress);
        if (entity.isAggressive()) {
            state.mood = 1;
        } else if (entity.getHealth() < entity.getMaxHealth() * 0.4F) {
            state.mood = 2;
        } else {
            state.mood = 0;
        }
    }

    @Override
    public Identifier getTextureLocation(MiniWardenRenderState state) {
        return state.mood == 1 ? this.angry : (state.mood == 2 ? this.hurt : this.calm);
    }
}
