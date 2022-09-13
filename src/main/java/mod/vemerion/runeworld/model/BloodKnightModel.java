package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.BloodKnightEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
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

public class BloodKnightModel extends EntityModel<BloodKnightEntity> {
	private final ModelPart body;
	private final ModelPart leftArm1;
	private final ModelPart leftArm2;
	private final ModelPart leftLeg1;
	private final ModelPart leftLeg2;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart rightLeg1;
	private final ModelPart rightLeg2;
	private final ModelPart rightArm1;
	private final ModelPart rightArm2;
	private final ModelPart club1;
	private final ModelPart spear1;
	private final ModelPart shield;

	public BloodKnightModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leftArm1 = body.getChild("leftArm1");
		this.leftArm2 = leftArm1.getChild("leftArm2");
		this.leftLeg1 = body.getChild("leftLeg1");
		this.leftLeg2 = leftLeg1.getChild("leftLeg2");
		this.neck = body.getChild("neck");
		this.head = neck.getChild("head");
		this.rightLeg1 = body.getChild("rightLeg1");
		this.rightLeg2 = rightLeg1.getChild("rightLeg2");
		this.rightArm1 = body.getChild("rightArm1");
		this.rightArm2 = rightArm1.getChild("rightArm2");
		this.club1 = rightArm2.getChild("club1");
		this.spear1 = rightArm2.getChild("spear1");
		this.shield = leftArm2.getChild("shield");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		body.addOrReplaceChild("body_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -12.0F, -6.0F, 16.0F, 24.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -35.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(0, 56)
				.addBox(-4.0F, -3.0F, -5.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offset(12.0F, -47.0F, 0.0F));

		PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2",
				CubeListBuilder.create().texOffs(40, 36).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 14.0F, -1.0F, -0.3054F, 0.0F, 0.0F));

		leftArm2.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(86, 45).addBox(-0.5F, -5.0F, -17.5F, 1.0F,
				20.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(4.25F, 4.0F, 7.5F));

		PartDefinition leftLeg1 = body.addOrReplaceChild("leftLeg1",
				CubeListBuilder.create().texOffs(56, 0).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 14.0F, 7.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, -22.0F, 0.5F, -0.1309F, 0.0F, 0.0F));

		leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(61, 21).addBox(-3.5F, 0.0F, -3.5F, 7.0F,
				12.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 82).addBox(-2.5F,
				-50.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		neck.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 36).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, 0.2618F, -0.7854F, -0.1745F));

		PartDefinition rightLeg1 = body.addOrReplaceChild("rightLeg1",
				CubeListBuilder.create().texOffs(56, 0).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 14.0F, 7.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, -22.0F, 0.5F, -0.1309F, 0.0F, 0.0F));

		rightLeg1
				.addOrReplaceChild("rightLeg2",
						CubeListBuilder.create().texOffs(61, 21).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 12.0F, 7.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1",
				CubeListBuilder.create().texOffs(0, 56).mirror()
						.addBox(-4.0F, -3.0F, -5.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-12.0F, -47.0F, 0.0F));

		PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2",
				CubeListBuilder.create().texOffs(40, 36).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 14.0F, -1.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition club1 = rightArm2.addOrReplaceChild("club1", CubeListBuilder.create().texOffs(0, 89)
				.addBox(-1.5F, -1.5F, -31.0F, 3.0F, 3.0F, 36.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 11.5F, 1.0F));

		club1.addOrReplaceChild("club2_r1",
				CubeListBuilder.create().texOffs(32, 57).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 10.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -35.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition spear1 = rightArm2.addOrReplaceChild("spear1",
				CubeListBuilder.create().texOffs(50, 85)
						.addBox(-1.5F, -1.5F, -31.0F, 3.0F, 3.0F, 36.0F, new CubeDeformation(0.0F)).texOffs(56, 111)
						.addBox(-5.0F, 0.0F, -41.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 11.5F, 1.0F));

		spear1.addOrReplaceChild("spear3_r1",
				CubeListBuilder.create().texOffs(56, 111).addBox(-5.0F, 0.0F, -6.0F, 10.0F, 0.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -35.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(BloodKnightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		var hasClub = entity.getType() == ModEntities.BLOOD_KNIGHT_CLUB.get();

		club1.visible = hasClub;
		shield.visible = !hasClub;
		spear1.visible = !hasClub;
		
		reset();

		neck.yRot = Helper.toRad(netHeadYaw);

		if (hasClub) {
			rightArm1.xRot = Helper.toRad(-20) + Mth.cos(limbSwing * 0.3f) * 0.3f * limbSwingAmount;
			rightArm2.xRot = Helper.toRad(-10);
			rightArm2.yRot = Helper.toRad(-60);
			leftArm1.xRot = Helper.toRad(-32) + Mth.cos(limbSwing * 0.3f) * 0.3f * limbSwingAmount;
			
			if (attackTime > 0) {
				rightArm1.xRot += Mth.sin(attackTime * Mth.TWO_PI) * Helper.toRad(25);
				leftArm1.xRot += Mth.sin(attackTime * Mth.TWO_PI) * Helper.toRad(25);
			}
		} else {
			rightArm1.xRot = Mth.cos(limbSwing * 0.7f + Mth.PI) * 0.9f * limbSwingAmount;
			leftArm1.xRot = Mth.cos(limbSwing * 0.7f) * 0.9f * limbSwingAmount;
			
			if (attackTime > 0) {
				leftArm1.yRot += Mth.sin(attackTime * Mth.PI) * Helper.toRad(70);
				leftArm1.xRot += -Mth.sin(attackTime * Mth.TWO_PI) * Helper.toRad(40);
			}
		}

		leftLeg1.xRot = Mth.cos(limbSwing * 0.7f + Mth.PI) * 0.9f * limbSwingAmount;
		leftLeg2.xRot = (Mth.cos(limbSwing * 0.7f) + 1) * 0.9f * limbSwingAmount;
		rightLeg1.xRot = Mth.cos(limbSwing * 0.7f) * 0.9f * limbSwingAmount;
		rightLeg2.xRot = (Mth.cos(limbSwing * 0.7f + Mth.PI) + 1) * 0.9f * limbSwingAmount;
	}

	private void reset() {
		rightArm1.xRot = 0;
		rightArm2.xRot = -0.31f;
		rightArm2.yRot = 0;
		leftArm1.xRot = 0;
		leftArm1.yRot = 0;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}