package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Created using Tabula 8.0.0
 */
public class BloodMonkeyModel extends EntityModel<BloodMonkeyEntity> implements ArmedModel {
	public ModelPart body;
	public ModelPart leftLeg1;
	public ModelPart rightLeg1;
	public ModelPart leftArm1;
	public ModelPart head;
	public ModelPart rightArm1;
	public ModelPart tail1;
	public ModelPart leftArm2;
	public ModelPart leftEar;
	public ModelPart rightEar;
	public ModelPart nose;
	public ModelPart rightArm2;
	public ModelPart tail2;
	public ModelPart tail3;
	public ModelPart tail4;
	public ModelPart leftLeg2;
	public ModelPart rightLeg2;

	public BloodMonkeyModel(ModelPart parts) {
		this.body = parts.getChild("body");
		this.leftLeg1 = parts.getChild("leftLeg1");
		this.rightLeg1 = parts.getChild("rightLeg1");
		this.leftArm1 = body.getChild("leftArm1");
		this.head = body.getChild("head");
		this.rightArm1 = body.getChild("rightArm1");
		this.tail1 = body.getChild("tail1");
		this.leftArm2 = leftArm1.getChild("leftArm2");
		this.leftEar = head.getChild("leftEar");
		this.rightEar = head.getChild("rightEar");
		this.nose = head.getChild("nose");
		this.rightArm2 = rightArm1.getChild("rightArm2");
		this.tail2 = tail1.getChild("tail2");
		this.tail3 = tail2.getChild("tail3");
		this.tail4 = tail3.getChild("tail4");
		this.leftLeg2 = leftLeg1.getChild("leftLeg2");
		this.rightLeg2 = rightLeg1.getChild("rightLeg2");
	}
	
	public static LayerDefinition createLayer() {
	    MeshDefinition mesh = new MeshDefinition();
	    PartDefinition parts = mesh.getRoot();
	    PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 14.2F, 4.0F, 0.27366763203903305F, 0.0F, 0.0F));
	    PartDefinition leftLeg1 = parts.addOrReplaceChild("leftLeg1", CubeListBuilder.create().texOffs(8, 16).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 6.0F, 2.0F), PartPose.offsetAndRotation(2.5F, 12.5F, 4.2F, -0.5235987755982988F, 0.7672467231977407F, -0.6646214111173737F));
	    PartDefinition rightLeg1 = parts.addOrReplaceChild("rightLeg1", CubeListBuilder.create().texOffs(8, 16).mirror().addBox(-1.5F, 0.0F, -1.0F, 3.0F, 6.0F, 2.0F), PartPose.offsetAndRotation(-2.5F, 12.5F, 4.2F, -0.5235987755982988F, -0.7672467231977407F, 0.6646214111173737F));
	    PartDefinition leftArm1 = body.addOrReplaceChild("leftArm1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(4.5F, -11.0F, 0.0F, -0.8726646259971648F, -0.35185837453889574F, -0.0781907508222411F));
	    PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 3).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, -0.19198621771937624F, 0.0F, 0.0F));
	    PartDefinition rightArm1 = body.addOrReplaceChild("rightArm1", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(-4.5F, -11.0F, 0.0F, -0.8754571501371946F, 0.35185837453889574F, -0.0781907508222411F));
	    PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(58, 7).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 0.5866051722479385F, 0.0F, 0.0F));
	    leftArm1.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.5473352640780661F, 0.17453292519943295F, 0.3127630032889644F));
	    head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(3.0F, -4.0F, -1.0F, 0.37699112508867794F, -0.3490658503988659F, 0.0F));
	    head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-3.0F, -4.0F, -1.0F, 0.3490658503988659F, 0.3490658503988659F, 0.0F));
	    head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 12).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -1.5F, -3.0F, 0, 0, 0));
	    rightArm1.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.5473352640780661F, -0.17453292519943295F, -0.3127630032889644F));
	    PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(52, 4).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 5.5F, 0.0F, 1.055749693826142F, 0.0F, 0.0F));
	    PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 3.5F, 0.0F, 1.1730357742864224F, 0.0F, 0.0F));
	    tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 1.1730357742864224F, 0.0F, 0.0F));
	    leftLeg1.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(18, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.10053096890961502F, -0.41050144805854955F, 0.47472956985826303F));
	    rightLeg1.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(18, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.10053096890961502F, 0.41050144805854955F, -0.47472956985826303F));
	    return LayerDefinition.create(mesh, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(body, leftLeg1, rightLeg1).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void prepareMobModel(BloodMonkeyEntity entityIn, float limbSwing, float limbSwingAmount,
			float partialTick) {
		body.xRot = entityIn.getBodyRot(partialTick);
	}

	@Override
	public void setupAnim(BloodMonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		reset();
		
		// Tail
		tail1.xRot = 0.59f + Mth.cos(ageInTicks / 15) * Helper.toRad(15);
		tail2.xRot = 1.06f + Mth.cos(ageInTicks / 15) * Helper.toRad(15);
		tail3.xRot = 1.17f + Mth.cos(ageInTicks / 15) * Helper.toRad(15);
		tail4.xRot = 1.17f + Mth.cos(ageInTicks / 15) * Helper.toRad(15);

		// Look
		head.yRot = Helper.toRad(netHeadYaw) * 0.25f;
		head.zRot = Helper.toRad(netHeadYaw) * 0.25f;

		if (entityIn.canUseRangedAttack()) {
			rightArm1.xRot = Helper.toRad(-130) + Mth.cos(ageInTicks / 30) * Helper.toRad(10)
					+ Mth.sin(attackTime * (float) Math.PI * 2f) * Helper.toRad(50);
			leftArm1.xRot = Helper.toRad(-20) + Mth.cos(ageInTicks / 30) * Helper.toRad(10);
			leftLeg1.zRot = Helper.toRad(-25);
			rightLeg1.yRot = Helper.toRad(-10);
			rightLeg1.zRot = Helper.toRad(70) + Mth.cos(ageInTicks / 7) * Helper.toRad(15);
			rightLeg2.zRot = Helper.toRad(-75) + Mth.cos(ageInTicks / 7) * Helper.toRad(10);
		} else {
			head.xRot = Helper.toRad(-40);
			leftLeg1.xRot = -0.52f
					+ Mth.cos(limbSwing * 0.7f + (float) Math.PI) * 2f * limbSwingAmount * 0.35f;
			rightLeg1.xRot = -0.52f
					+ Mth.cos(limbSwing * 0.7f) * 2f * limbSwingAmount * 0.35f;

			if (!entityIn.swinging) {
				rightArm1.xRot = -0.88f
						+ Mth.cos(limbSwing * 0.7f + (float) Math.PI) * 2f * limbSwingAmount * 0.35f;
				leftArm1.xRot = -0.87f
						+ Mth.cos(limbSwing * 0.7f) * 2f * limbSwingAmount * 0.35f;
			} else {
				rightArm1.xRot = -0.88f
						- Mth.sin(attackTime * (float) Math.PI * 2f) * Helper.toRad(50) - Helper.toRad(50);
				leftArm1.xRot = -0.87f
						- Mth.sin(attackTime * (float) Math.PI * 2f) * Helper.toRad(50) - Helper.toRad(50);
			}
		}
	}

	private void reset() {
		rightArm1.setRotation(-0.88f, 0.35f, -0.08f);
		leftArm1.setRotation(-0.87f, -0.35f, -0.08f);
		leftLeg1.setRotation(-0.52f, 0.77f, -0.66f);
		rightLeg1.setRotation(-0.52f, -0.77f, 0.66f);
		rightLeg2.setRotation(0.10f, 0.41f, -0.47f);
		head.setRotation(-0.19f, 0, 0);
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		getArmParts(pSide).forEach(p -> p.translateAndRotate(pPoseStack));
		pPoseStack.translate(0.05, -0.05, 0);
		pPoseStack.scale(0.75f, 0.75f, 0.75f);
	}

	private Iterable<ModelPart> getArmParts(HumanoidArm pSide) {
		return pSide == HumanoidArm.LEFT ? ImmutableList.of(body, leftArm1, leftArm2)
				: ImmutableList.of(body, rightArm1, rightArm2);
	}
}
