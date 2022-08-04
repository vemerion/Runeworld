package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class BloodBatModel extends EntityModel<BloodBatEntity> {
	public ModelPart body;
	public ModelPart head;
	public ModelPart leftWing1;
	public ModelPart rightWing1;
	public ModelPart leftLeg1;
	public ModelPart rightLeg1;
	public ModelPart jawUpper;
	public ModelPart leftEar;
	public ModelPart rightEar;
	public ModelPart jawLower;
	public ModelPart leftWing2;
	public ModelPart rightWing2;
	public ModelPart leftLeg2;
	public ModelPart leftFoot;
	public ModelPart rightLeg2;
	public ModelPart rightFoot;

	private final float HEAD_ROT;
	private final float BODY_ROT;

	public BloodBatModel(ModelPart parts) {
		this.body = parts.getChild("body");
		this.head = body.getChild("head");
		this.leftWing1 = body.getChild("leftWing1");
		this.rightWing1 = body.getChild("rightWing1");
		this.leftLeg1 = body.getChild("leftLeg1");
		this.rightLeg1 = body.getChild("rightLeg1");
		this.jawUpper = head.getChild("jawUpper");
		this.leftEar = head.getChild("leftEar");
		this.rightEar = head.getChild("rightEar");
		this.jawLower = jawUpper.getChild("jawLower");
		this.leftWing2 = leftWing1.getChild("leftWing2");
		this.rightWing2 = rightWing1.getChild("rightWing2");
		this.leftLeg2 = leftLeg1.getChild("leftLeg2");
		this.leftFoot = leftLeg2.getChild("leftFoot");
		this.rightLeg2 = rightLeg1.getChild("rightLeg2");
		this.rightFoot = rightLeg2.getChild("rightFoot");

		this.HEAD_ROT = this.head.xRot;
		this.BODY_ROT = this.body.xRot;
	}
	
	public static LayerDefinition createLayer() {
	    MeshDefinition mesh = new MeshDefinition();
	    PartDefinition parts = mesh.getRoot();
	    PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 14.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.11728612207217244F, 0.0F, 0.0F));
	    PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(26, 0).addBox(-3.0F, -4.0F, -8.0F, 6.0F, 4.0F, 8.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.35185837453889574F, 0.0F, 0.0F));
	    PartDefinition leftWing1 = body.addOrReplaceChild("leftWing1", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F), PartPose.offsetAndRotation(4.0F, 2.0F, -1.0F, 0.0F, -0.1563815016444822F, 0.0F));
	    PartDefinition rightWing1 = body.addOrReplaceChild("rightWing1", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-10.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F), PartPose.offsetAndRotation(-4.0F, 2.0F, -1.0F, 0.0F, 0.23457224414434488F, 0.0F));
	    PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(54, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(2.0F, 5.0F, 0.1F, 0.3909537457888271F, 0.0F, 0.0F));
	    PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(54, 5).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(-2.0F, 5.0F, 0.1F, 0.3909537457888271F, 0.0F, 0.0F));
	    PartDefinition jawUpper = head.addOrReplaceChild("jawUpper", CubeListBuilder.create().texOffs(46, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, 0, 0, 0));
	    head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(21, 0).addBox(-0.0F, -3.0F, 0.0F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(1.0F, -4.0F, -2.0F, 0.4300491170387584F, 0.0F, 0.27366763203903305F));
	    head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(21, 0).mirror().addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-1.0F, -4.0F, -2.0F, 0.4300491170387584F, 0.0F, -0.27366763203903305F));
	    jawUpper.addOrReplaceChild("jawLower", CubeListBuilder.create().texOffs(26, 12).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0, 0, 0));
	    leftWing1.addOrReplaceChild("leftWing2", CubeListBuilder.create().texOffs(0, 37).addBox(0.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F), PartPose.offsetAndRotation(10.0F, 0.0F, 0.0F, 0.0F, -0.3080506063190964F, 0.0F));
	    rightWing1.addOrReplaceChild("rightWing2", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-10.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, 0.0F, 0.4300491170387584F, 0.0F));
	    PartDefinition leftLeg2 = leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(52, 11).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.27366763203903305F, 0.0F, 0.0F));
	    leftLeg2.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(37, 12).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, 0.1563815016444822F, 0.0F, 0.0F));
	    PartDefinition rightLeg2 = rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(52, 11).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.27366763203903305F, 0.0F, 0.0F));
	    rightLeg2.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(37, 12).mirror().addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, 0.1563815016444822F, 0.0F, 0.0F));
	    return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void prepareMobModel(BloodBatEntity entityIn, float limbSwing, float limbSwingAmount,
			float partialTick) {
		// Wings
		if (!entityIn.isHanging()) {
			float animationHeight = entityIn.getAnimationHeight(partialTick);
			leftWing1.yRot = animationHeight * (float) Math.toRadians(20);
			leftWing2.yRot = animationHeight * (float) Math.toRadians(20);
			rightWing1.yRot = -animationHeight * (float) Math.toRadians(20);
			rightWing2.yRot = -animationHeight * (float) Math.toRadians(20);
		} else {
			leftWing1.yRot = (float) Math.toRadians(70);
			leftWing2.yRot = (float) Math.toRadians(70);
			rightWing1.yRot = -(float) Math.toRadians(70);
			rightWing2.yRot = -(float) Math.toRadians(70);
		}
	}

	@Override
	public void setupAnim(BloodBatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {

		if (!entityIn.isHanging()) {
			// Legs
			leftLeg1.xRot = -Mth.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			leftLeg2.xRot = -Mth.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			rightLeg1.xRot = -Mth.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			rightLeg2.xRot = -Mth.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);

			// Look
			head.yRot = (float) Math.toRadians(netHeadYaw) * 0.35f;
			head.zRot = (float) Math.toRadians(netHeadYaw) * 0.35f;

			// Attack
			head.xRot = HEAD_ROT
					+ Mth.sin(attackTime * (float) Math.PI * 2) * (float) Math.toRadians(20);

			// Body
			body.xRot = BODY_ROT + limbSwingAmount * (float) Math.toRadians(100);
			body.yRot = 0;
		} else {
			leftLeg1.xRot = 0;
			leftLeg2.xRot = 0;
			rightLeg1.xRot = 0;
			rightLeg2.xRot = 0;
			head.xRot = 0.2f;
			head.yRot = 0;
			head.zRot = 0;
			body.xRot = (float) Math.toRadians(180);
			body.yRot = (float) Math.toRadians(180);
		}
	}
}
