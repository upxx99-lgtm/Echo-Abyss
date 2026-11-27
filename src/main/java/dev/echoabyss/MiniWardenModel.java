package dev.echoabyss;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** One animated model for both mini-bosses. The two shapes are built in createEchoLayer and createBellLayer. */
public class MiniWardenModel extends EntityModel<MiniWardenRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public MiniWardenModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createEchoLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
		PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 28).addBox(-5F, -10F, -5F, 10F, 10F, 10F), PartPose.offsetAndRotation(0F, -12F, 0F, 0F, 0F, 0F));
		PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7F, 0F, -4F, 14F, 20F, 8F), PartPose.offsetAndRotation(0F, -12F, 0F, 0F, 0F, 0F));
		PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 0).addBox(-3F, -1F, -3F, 6F, 22F, 6F), PartPose.offsetAndRotation(10F, -11F, 0F, 0F, 0F, 0F));
		PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(68, 0).addBox(-3F, -1F, -3F, 6F, 22F, 6F), PartPose.offsetAndRotation(-10F, -11F, 0F, 0F, 0F, 0F));
		PartDefinition p_left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(92, 0).addBox(-3F, 0F, -3F, 6F, 16F, 6F), PartPose.offsetAndRotation(4F, 8F, 0F, 0F, 0F, 0F));
		PartDefinition p_right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-3F, 0F, -3F, 6F, 16F, 6F), PartPose.offsetAndRotation(-4F, 8F, 0F, 0F, 0F, 0F));
		PartDefinition p_horn_left = p_head.addOrReplaceChild("horn_left", CubeListBuilder.create().texOffs(64, 28).addBox(-2F, -9F, -2F, 4F, 9F, 4F), PartPose.offsetAndRotation(3F, -10F, 0F, 0F, 0F, -0.35F));
		PartDefinition p_horn_right = p_head.addOrReplaceChild("horn_right", CubeListBuilder.create().texOffs(80, 28).addBox(-2F, -9F, -2F, 4F, 9F, 4F), PartPose.offsetAndRotation(-3F, -10F, 0F, 0F, 0F, 0.35F));
		PartDefinition p_chest_crystal = p_body.addOrReplaceChild("chest_crystal", CubeListBuilder.create().texOffs(108, 28).addBox(-3F, 3F, -6F, 6F, 9F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_spine_crystal = p_body.addOrReplaceChild("spine_crystal", CubeListBuilder.create().texOffs(96, 28).addBox(-2F, 2F, 4F, 4F, 10F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_shoulder_left = p_left_arm.addOrReplaceChild("shoulder_left", CubeListBuilder.create().texOffs(0, 50).addBox(-1F, -5F, -1F, 2F, 5F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_shoulder_right = p_right_arm.addOrReplaceChild("shoulder_right", CubeListBuilder.create().texOffs(8, 50).addBox(-1F, -5F, -1F, 2F, 5F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    public static LayerDefinition createBellLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
		PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -12F, 0F, 0F, 0F, 0F));
		PartDefinition p_bell_top = p_head.addOrReplaceChild("bell_top", CubeListBuilder.create().texOffs(92, 50).addBox(-3F, -14F, -3F, 6F, 4F, 6F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_bell_mid = p_head.addOrReplaceChild("bell_mid", CubeListBuilder.create().texOffs(60, 50).addBox(-4F, -10F, -4F, 8F, 5F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_bell_flare = p_head.addOrReplaceChild("bell_flare", CubeListBuilder.create().texOffs(24, 26).addBox(-6F, -5F, -6F, 12F, 5F, 12F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_clapper = p_head.addOrReplaceChild("clapper", CubeListBuilder.create().texOffs(0, 65).addBox(-2F, 0F, -2F, 4F, 5F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8F, 0F, -4F, 16F, 18F, 8F), PartPose.offsetAndRotation(0F, -12F, 0F, 0F, 0F, 0F));
		PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 0).addBox(-3F, -1F, -3F, 6F, 20F, 6F), PartPose.offsetAndRotation(11F, -10F, 0F, 0F, 0F, 0F));
		PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(72, 0).addBox(-3F, -1F, -3F, 6F, 20F, 6F), PartPose.offsetAndRotation(-11F, -10F, 0F, 0F, 0F, 0F));
		PartDefinition p_fist_left = p_left_arm.addOrReplaceChild("fist_left", CubeListBuilder.create().texOffs(72, 26).addBox(-4F, 18F, -4F, 8F, 7F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_fist_right = p_right_arm.addOrReplaceChild("fist_right", CubeListBuilder.create().texOffs(0, 50).addBox(-4F, 18F, -4F, 8F, 7F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
		PartDefinition p_left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(96, 0).addBox(-3F, 0F, -3F, 6F, 18F, 6F), PartPose.offsetAndRotation(5F, 6F, 0F, 0F, 0F, 0F));
		PartDefinition p_right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-3F, 0F, -3F, 6F, 18F, 6F), PartPose.offsetAndRotation(-5F, 6F, 0F, 0F, 0F, 0F));
		PartDefinition p_back_bell = p_body.addOrReplaceChild("back_bell", CubeListBuilder.create().texOffs(32, 50).addBox(-5F, 4F, 4F, 10F, 10F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    public static LayerDefinition createGolemLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 28).addBox(-4F, -9F, -4F, 8F, 9F, 8F), PartPose.offsetAndRotation(0F, -4F, 0F, 0F, 0F, 0F));
        PartDefinition p_body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(44, 0).addBox(-8F, 0F, -5F, 16F, 14F, 10F), PartPose.offsetAndRotation(0F, -4F, 0F, 0F, 0F, 0F));
        PartDefinition p_left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-3F, -1F, -3F, 5F, 22F, 6F), PartPose.offsetAndRotation(11F, -3F, 0F, 0F, 0F, 0F));
        PartDefinition p_right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(22, 0).addBox(-2F, -1F, -3F, 5F, 22F, 6F), PartPose.offsetAndRotation(-11F, -3F, 0F, 0F, 0F, 0F));
        PartDefinition p_left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(96, 0).addBox(-3F, 0F, -3F, 6F, 14F, 6F), PartPose.offsetAndRotation(4F, 10F, 0F, 0F, 0F, 0F));
        PartDefinition p_right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-3F, 0F, -3F, 6F, 14F, 6F), PartPose.offsetAndRotation(-4F, 10F, 0F, 0F, 0F, 0F));
        PartDefinition p_nose = p_head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(98, 28).addBox(-1F, -3F, -6F, 2F, 4F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        PartDefinition p_back_crystal = p_body.addOrReplaceChild("back_crystal", CubeListBuilder.create().texOffs(56, 28).addBox(-3F, 1F, 5F, 6F, 10F, 3F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        PartDefinition p_shoulder_crystal_l = p_left_arm.addOrReplaceChild("shoulder_crystal_l", CubeListBuilder.create().texOffs(74, 28).addBox(-1F, -5F, -1F, 3F, 5F, 3F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        PartDefinition p_shoulder_crystal_r = p_right_arm.addOrReplaceChild("shoulder_crystal_r", CubeListBuilder.create().texOffs(86, 28).addBox(-1F, -5F, -1F, 3F, 5F, 3F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(MiniWardenRenderState state) {
        super.setupAnim(state);

        float walkPos = state.walkAnimationPos;
        float walkSpeed = state.walkAnimationSpeed;
        float age = state.ageInTicks;
        boolean angry = state.mood == 1;
        boolean hurt = state.mood == 2;

        // head follows the look direction; tilts when hurt
        this.head.xRot = state.xRot * Mth.DEG_TO_RAD;
        this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
        this.head.zRot = hurt ? 0.15F : 0.0F;

        // walking
        this.leftLeg.xRot = Mth.cos(walkPos * 0.6F) * 1.1F * walkSpeed;
        this.rightLeg.xRot = Mth.cos(walkPos * 0.6F + Mth.PI) * 1.1F * walkSpeed;

        // slow breathing, leaning forward when angry, hunched when hurt
        float breathe = Mth.sin(age * 0.07F);
        this.body.xRot = 0.03F * breathe + (angry ? 0.12F : 0.0F) + (hurt ? 0.1F : 0.0F);

        // arms: swing while walking, big swing when attacking
        float armSwing = Mth.cos(walkPos * 0.6F) * 0.7F * walkSpeed;
        float attack = Mth.sin(state.attack * Mth.PI);
        this.leftArm.xRot = armSwing - attack * 1.4F - (angry ? 0.5F : 0.0F);
        this.rightArm.xRot = -armSwing - attack * 1.8F - (angry ? 0.5F : 0.0F);
        this.leftArm.zRot = angry ? 0.2F + 0.06F * Mth.sin(age * 0.3F) : 0.05F + 0.03F * breathe;
        this.rightArm.zRot = angry ? -0.2F - 0.06F * Mth.sin(age * 0.3F) : -0.05F - 0.03F * breathe;
    }
}
