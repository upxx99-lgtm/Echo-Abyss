package dev.echoabyss;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

/** Draws the golem with the shared animated model. The texture changes with the mood. */
public class EchoGolemRenderer extends MobRenderer<EchoGolem, MiniWardenRenderState, MiniWardenModel> {
    private final Identifier calm = EchoAbyss.id("textures/entity/echo_golem_calm.png");
    private final Identifier angry = EchoAbyss.id("textures/entity/echo_golem_angry.png");
    private final Identifier hurt = EchoAbyss.id("textures/entity/echo_golem_hurt.png");

    public EchoGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniWardenModel(context.bakeLayer(ModModelLayers.ECHO_GOLEM)), 0.8F);
    }

    @Override
    public MiniWardenRenderState createRenderState() {
        return new MiniWardenRenderState();
    }

    @Override
    public void extractRenderState(EchoGolem entity, MiniWardenRenderState state, float tickProgress) {
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
