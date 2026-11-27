package dev.echoabyss;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** Same shape as a blaze: a head with twelve rods that circle around it. */
public class EchoBlazeModel extends EntityModel<FloaterRenderState> {
    private final ModelPart head;
    private final ModelPart[] sticks = new ModelPart[12];

    public EchoBlazeModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        for (int i = 0; i < 12; i++) {
            this.sticks[i] = root.getChild("stick" + i);
        }
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        for (int i = 0; i < 12; i++) {
            root.addOrReplaceChild("stick" + i, CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(FloaterRenderState state) {
        super.setupAnim(state);
        float age = state.ageInTicks;
        float quarter = Mth.PI / 2.0F;

        float rot = age * Mth.PI * -0.1F;
        for (int i = 0; i < 4; i++) {
            this.sticks[i].y = -2.0F + Mth.cos((i * 2 + age) * 0.25F);
            this.sticks[i].x = Mth.cos(rot) * 9.0F;
            this.sticks[i].z = Mth.sin(rot) * 9.0F;
            rot += quarter;
        }
        rot = 0.7853982F + age * Mth.PI * 0.03F;
        for (int i = 4; i < 8; i++) {
            this.sticks[i].y = 2.0F + Mth.cos((i * 2 + age) * 0.25F);
            this.sticks[i].x = Mth.cos(rot) * 7.0F;
            this.sticks[i].z = Mth.sin(rot) * 7.0F;
            rot += quarter;
        }
        rot = 0.47123888F + age * Mth.PI * -0.05F;
        for (int i = 8; i < 12; i++) {
            this.sticks[i].y = 11.0F + Mth.cos((i * 1.5F + age) * 0.5F);
            this.sticks[i].x = Mth.cos(rot) * 5.0F;
            this.sticks[i].z = Mth.sin(rot) * 5.0F;
            rot += quarter;
        }
        this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
        this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
    }
}
