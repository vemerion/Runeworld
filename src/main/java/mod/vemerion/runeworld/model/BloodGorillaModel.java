package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.BloodGorillaEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

// Made with Blockbench 4.3.1

public class BloodGorillaModel extends EntityModel<BloodGorillaEntity> {
	public final ModelPart body;
	public final ModelPart leftArm1;
	public final ModelPart leftArm2;
	public final ModelPart head;
	public final ModelPart headTop_r1;
	public final ModelPart nose_r1;
	public final ModelPart leftLeg1;
	public final ModelPart leftLeg2;
	public final ModelPart rightArm1;
	public final ModelPart rightArm2;
	public final ModelPart rightLeg1;
	public final ModelPart rightLeg2;

	public BloodGorillaModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leftArm1 = body.getChild("leftArm1");
		this.leftArm2 = leftArm1.getChild("leftArm2");
		this.head = body.getChild("head");
		this.headTop_r1 = head.getChild("headTop_r1");
		this.nose_r1 = head.getChild("nose_r1");
		this.leftLeg1 = body.getChild("leftLeg1");
		this.leftLeg2 = leftLeg1.getChild("leftLeg2");
		this.rightArm1 = body.getChild("rightArm1");
		this.rightArm2 = rightArm1.getChild("rightArm2");
		this.rightLeg1 = body.getChild("rightLeg1");
		this.rightLeg2 = rightLeg1.getChild("rightLeg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -11.0F, -7.5F, 18.0F, 14.0F, 26.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 6.0F, -3.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1",
				CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, -6.0F, -6.0F, 8.0F, 17.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(11.0F, -6.0F, -5.5F, -0.0436F, 0.0F, 0.0F));

		leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(48, 40).addBox(-6.0F, -2.0F, -7.0F,
				10.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(-1.0F, -8.0F, -7.5F));

		head.addOrReplaceChild("headTop_r1",
				CubeListBuilder.create().texOffs(0, 64).addBox(-6.0F, -8.0F, -4.0F, 10.0F, 2.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 0.3801F, -5.2917F, 0.2618F, 0.0F, 0.0F));

		head.addOrReplaceChild("nose_r1",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -3.0024F, -12.2098F, 8.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 40)
						.addBox(-6.0F, -7.0024F, -10.2098F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1",
				CubeListBuilder.create().texOffs(92, 16).addBox(-6.0F, -5.0F, -6.0F, 9.0F, 16.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(8.0F, -7.0F, 18.5F, 0.0873F, 0.0F, 0.0F));

		leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(92, 41).addBox(-5.99F, -1.0F, -5.99F,
				9.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1",
				CubeListBuilder.create().texOffs(62, 0).mirror()
						.addBox(-5.0F, -6.0F, -6.0F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-11.0F, -6.0F, -5.5F, -0.0436F, 0.0F, 0.0F));

		rightArm1.addOrReplaceChild("rightArm2",
				CubeListBuilder.create().texOffs(48, 40).mirror()
						.addBox(-6.0F, -2.0F, -7.0F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1",
				CubeListBuilder.create().texOffs(92, 16).mirror()
						.addBox(-6.0F, -5.0F, -6.0F, 9.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-8.0F, -7.0F, 18.5F, 0.0873F, 0.0F, 0.0F));

		rightLeg1.addOrReplaceChild("rightLeg2",
				CubeListBuilder.create().texOffs(92, 41).mirror()
						.addBox(-5.99F, -1.0F, -6.01F, 9.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(BloodGorillaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// Reset
		this.head.z = -7.5f;
		this.body.xRot = -0.13f;
		this.body.y = 6;
		this.leftArm1.yRot = 0;
		this.leftArm2.xRot = 0.17f;
		this.leftArm2.zRot = 0;
		this.rightArm1.yRot = 0;
		this.rightArm2.zRot = 0;
		this.rightArm2.xRot = 0.17f;
		this.leftLeg2.xRot = 0.04f;
		this.rightLeg2.xRot = 0.04f;

		// Head
		this.head.xRot = Helper.toRad(headPitch);
		this.head.yRot = Helper.toRad(netHeadYaw);
		this.head.zRot = 0;

		// Movement
		float legRotFactor = 0.7f;
		this.rightLeg1.xRot = Mth.cos(limbSwing * 0.7f) * legRotFactor * limbSwingAmount + 0.09f;
		this.leftLeg1.xRot = Mth.cos(limbSwing * 0.7f + (float) Math.PI) * legRotFactor * limbSwingAmount + 0.09f;
		this.rightArm1.xRot = Mth.cos(limbSwing * 0.7f + (float) Math.PI) * legRotFactor * limbSwingAmount;
		this.leftArm1.xRot = Mth.cos(limbSwing * 0.7f) * legRotFactor * limbSwingAmount;

		// Raised hand
		if (!entity.getPassengers().isEmpty()) {
			this.leftArm1.xRot = Helper.toRad(-130);
			this.leftArm2.xRot = Helper.toRad(-40);
		}

		// Sitting
		if (entity.isSitting()) {
			this.head.xRot = Helper.toRad(70);
			this.head.yRot = Helper.toRad(0);
			this.head.zRot = Helper.toRad(netHeadYaw);
			this.head.z = -10f;

			this.body.xRot = Helper.toRad(-100);
			this.leftArm1.xRot = Helper.toRad(30);
			this.leftArm1.yRot = Helper.toRad(30);
			this.leftArm2.zRot = Helper.toRad(20);
			this.rightArm1.xRot = Helper.toRad(30);
			this.rightArm1.yRot = Helper.toRad(-30);
			this.rightArm2.zRot = Helper.toRad(-20);

		} else if (entity.isChestBeating()) { // Chest beating
			this.body.xRot = Helper.toRad(-70);
			this.head.xRot = Helper.toRad(50);
			this.body.y = -11;
			this.leftLeg1.xRot = Helper.toRad(50);
			this.leftLeg2.xRot = Helper.toRad(10);
			this.rightLeg1.xRot = Helper.toRad(50);
			this.rightLeg2.xRot = Helper.toRad(10);
			this.leftArm1.yRot = Helper.toRad(-70);
			this.leftArm1.xRot = Mth.cos(ageInTicks * 0.5f) * Helper.toRad(-30) + Helper.toRad(-30);
			this.leftArm2.xRot = Math.min(0, Mth.cos(ageInTicks * 0.5f)) * Helper.toRad(-70);
			this.rightArm1.yRot = Helper.toRad(70);
			this.rightArm1.xRot = Mth.cos(ageInTicks * 0.5f + 0.8f) * Helper.toRad(30) + Helper.toRad(-30);
			this.rightArm2.xRot = Math.max(0, Mth.cos(ageInTicks * 0.5f + 0.8f)) * Helper.toRad(70);
		}

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}