package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BloodMonkeyModel extends EntityModel<BloodMonkeyEntity> {
	public ModModelRenderer body;
	public ModModelRenderer leftLeg1;
	public ModModelRenderer rightLeg1;
	public ModModelRenderer leftArm1;
	public ModModelRenderer head;
	public ModModelRenderer rightArm1;
	public ModModelRenderer tail1;
	public ModModelRenderer leftArm2;
	public ModModelRenderer leftEar;
	public ModModelRenderer rightEar;
	public ModModelRenderer nose;
	public ModModelRenderer rightArm2;
	public ModModelRenderer tail2;
	public ModModelRenderer tail3;
	public ModModelRenderer tail4;
	public ModModelRenderer leftLeg2;
	public ModModelRenderer rightLeg2;

	public BloodMonkeyModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.tail2 = new ModModelRenderer(this, 52, 4);
		this.tail2.setRotationPoint(0.0F, 5.5F, 0.0F);
		this.tail2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tail2, 1.055749693826142F, 0.0F, 0.0F);
		this.leftArm2 = new ModModelRenderer(this, 32, 0);
		this.leftArm2.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.leftArm2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftArm2, -0.5473352640780661F, 0.17453292519943295F, 0.3127630032889644F);
		this.nose = new ModModelRenderer(this, 24, 12);
		this.nose.setRotationPoint(0.0F, -1.5F, -3.0F);
		this.nose.addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.tail3 = new ModModelRenderer(this, 0, 0);
		this.tail3.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.tail3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tail3, 1.1730357742864224F, 0.0F, 0.0F);
		this.tail4 = new ModModelRenderer(this, 20, 0);
		this.tail4.setRotationPoint(0.0F, 2.5F, 0.0F);
		this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tail4, 1.1730357742864224F, 0.0F, 0.0F);
		this.leftLeg2 = new ModModelRenderer(this, 18, 16);
		this.leftLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg2, 0.10053096890961502F, -0.41050144805854955F, 0.47472956985826303F);
		this.head = new ModModelRenderer(this, 34, 3);
		this.head.setRotationPoint(0.0F, -12.0F, 0.0F);
		this.head.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(head, -0.19198621771937624F, 0.0F, 0.0F);
		this.rightLeg2 = new ModModelRenderer(this, 18, 16);
		this.rightLeg2.mirror = true;
		this.rightLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.rightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightLeg2, 0.10053096890961502F, 0.41050144805854955F, -0.47472956985826303F);
		this.body = new ModModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 14.2F, 4.0F);
		this.body.addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(body, 0.27366763203903305F, 0.0F, 0.0F);
		this.rightArm1 = new ModModelRenderer(this, 24, 0);
		this.rightArm1.mirror = true;
		this.rightArm1.setRotationPoint(-4.5F, -11.0F, 0.0F);
		this.rightArm1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightArm1, -0.8754571501371946F, 0.35185837453889574F, -0.0781907508222411F);
		this.rightLeg1 = new ModModelRenderer(this, 8, 16);
		this.rightLeg1.mirror = true;
		this.rightLeg1.setRotationPoint(-2.5F, 12.5F, 4.2F);
		this.rightLeg1.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightLeg1, -0.5235987755982988F, -0.7672467231977407F, 0.6646214111173737F);
		this.tail1 = new ModModelRenderer(this, 58, 7);
		this.tail1.setRotationPoint(0.0F, -3.0F, 1.0F);
		this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tail1, 0.5866051722479385F, 0.0F, 0.0F);
		this.leftLeg1 = new ModModelRenderer(this, 8, 16);
		this.leftLeg1.setRotationPoint(2.5F, 12.5F, 4.2F);
		this.leftLeg1.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg1, -0.5235987755982988F, 0.7672467231977407F, -0.6646214111173737F);
		this.leftEar = new ModModelRenderer(this, 52, 0);
		this.leftEar.setRotationPoint(3.0F, -4.0F, -1.0F);
		this.leftEar.addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftEar, 0.37699112508867794F, -0.3490658503988659F, 0.0F);
		this.rightEar = new ModModelRenderer(this, 52, 0);
		this.rightEar.mirror = true;
		this.rightEar.setRotationPoint(-3.0F, -4.0F, -1.0F);
		this.rightEar.addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightEar, 0.3490658503988659F, 0.3490658503988659F, 0.0F);
		this.leftArm1 = new ModModelRenderer(this, 24, 0);
		this.leftArm1.setRotationPoint(4.5F, -11.0F, 0.0F);
		this.leftArm1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftArm1, -0.8726646259971648F, -0.35185837453889574F, -0.0781907508222411F);
		this.rightArm2 = new ModModelRenderer(this, 32, 0);
		this.rightArm2.mirror = true;
		this.rightArm2.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.rightArm2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightArm2, -0.5473352640780661F, -0.17453292519943295F, -0.3127630032889644F);
		this.tail1.addChild(this.tail2);
		this.leftArm1.addChild(this.leftArm2);
		this.head.addChild(this.nose);
		this.tail2.addChild(this.tail3);
		this.tail3.addChild(this.tail4);
		this.leftLeg1.addChild(this.leftLeg2);
		this.body.addChild(this.head);
		this.rightLeg1.addChild(this.rightLeg2);
		this.body.addChild(this.rightArm1);
		this.body.addChild(this.tail1);
		this.head.addChild(this.leftEar);
		this.head.addChild(this.rightEar);
		this.body.addChild(this.leftArm1);
		this.rightArm1.addChild(this.rightArm2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(body, leftLeg1, rightLeg1).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setLivingAnimations(BloodMonkeyEntity entityIn, float limbSwing, float limbSwingAmount,
			float partialTick) {
		body.rotateAngleX = entityIn.getBodyRot(partialTick);
	}

	@Override
	public void setRotationAngles(BloodMonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		reset();
		// Tail
		ImmutableList.of(tail1, tail2, tail3, tail4).forEach(tail -> {
			tail.rotateAngleX = tail.startRotX + MathHelper.cos(ageInTicks / 15) * Helper.toRad(15);
		});

		// Look
		head.rotateAngleY = Helper.toRad(netHeadYaw) * 0.25f;
		head.rotateAngleZ = Helper.toRad(netHeadYaw) * 0.25f;

		if (entityIn.isStandingOnPillar()) {
			rightArm1.rotateAngleX = Helper.toRad(-130) + MathHelper.cos(ageInTicks / 30) * Helper.toRad(10)
					+ MathHelper.sin(swingProgress * (float) Math.PI * 2f) * Helper.toRad(50);
			leftArm1.rotateAngleX = Helper.toRad(-20) + MathHelper.cos(ageInTicks / 30) * Helper.toRad(10);
			leftLeg1.rotateAngleZ = Helper.toRad(-25);
			rightLeg1.rotateAngleY = Helper.toRad(-10);
			rightLeg1.rotateAngleZ = Helper.toRad(70) + MathHelper.cos(ageInTicks / 7) * Helper.toRad(15);
			rightLeg2.rotateAngleZ = Helper.toRad(-75) + MathHelper.cos(ageInTicks / 7) * Helper.toRad(10);
		} else {
			head.rotateAngleX = Helper.toRad(-40);
			leftLeg1.rotateAngleX = leftLeg1.startRotX
					+ MathHelper.cos(limbSwing * 0.7f + (float) Math.PI) * 2f * limbSwingAmount * 0.35f;
			rightLeg1.rotateAngleX = rightLeg1.startRotX
					+ MathHelper.cos(limbSwing * 0.7f) * 2f * limbSwingAmount * 0.35f;

			if (!entityIn.isSwingInProgress) {
				rightArm1.rotateAngleX = rightArm1.startRotX
						+ MathHelper.cos(limbSwing * 0.7f + (float) Math.PI) * 2f * limbSwingAmount * 0.35f;
				leftArm1.rotateAngleX = leftArm1.startRotX
						+ MathHelper.cos(limbSwing * 0.7f) * 2f * limbSwingAmount * 0.35f;
			} else {
				rightArm1.rotateAngleX = rightArm1.startRotX
						- MathHelper.sin(swingProgress * (float) Math.PI * 2f) * Helper.toRad(50) - Helper.toRad(50);
				leftArm1.rotateAngleX = leftArm1.startRotX
						- MathHelper.sin(swingProgress * (float) Math.PI * 2f) * Helper.toRad(50) - Helper.toRad(50);
			}
		}
	}

	private void reset() {
		rightArm1.resetRot();
		leftArm1.resetRot();
		leftLeg1.resetRot();
		rightLeg1.resetRot();
		rightLeg2.resetRot();
		head.resetRot();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.initRot(x, y, z);
	}
}
