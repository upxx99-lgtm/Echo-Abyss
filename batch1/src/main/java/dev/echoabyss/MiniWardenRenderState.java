package dev.echoabyss;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class MiniWardenRenderState extends LivingEntityRenderState {
    /** 0 = calm, 1 = angry, 2 = hurt. */
    public int mood;

    /** Attack swing progress, 0 to 1. */
    public float attack;
}
