package dev.echoabyss;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class ModModelLayers {
    public static final ModelLayerLocation ECHO_WARDEN = new ModelLayerLocation(EchoAbyss.id("echo_warden"), "main");
    public static final ModelLayerLocation BELL_WARDEN = new ModelLayerLocation(EchoAbyss.id("bell_warden"), "main");
    public static final ModelLayerLocation ECHO_BLAZE = new ModelLayerLocation(EchoAbyss.id("echo_blaze"), "main");
    public static final ModelLayerLocation ECHO_GOLEM = new ModelLayerLocation(EchoAbyss.id("echo_golem"), "main");

    private ModModelLayers() {
    }

    public static void register() {
        ModelLayerRegistry.registerModelLayer(ECHO_WARDEN, MiniWardenModel::createEchoLayer);
        ModelLayerRegistry.registerModelLayer(BELL_WARDEN, MiniWardenModel::createBellLayer);
        ModelLayerRegistry.registerModelLayer(ECHO_BLAZE, EchoBlazeModel::createLayer);
        ModelLayerRegistry.registerModelLayer(ECHO_GOLEM, MiniWardenModel::createGolemLayer);
    }
}
