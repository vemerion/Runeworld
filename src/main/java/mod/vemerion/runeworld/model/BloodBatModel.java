package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BloodBatModel extends EntityModel<BloodBatEntity> {
	public ModelRenderer body;
	public ModelRenderer head;
	public ModelRenderer leftWing1;
	public ModelRenderer rightWing1;
	public ModelRenderer leftLeg1;
	public ModelRenderer rightLeg1;
	public ModelRenderer jawUpper;
	public ModelRenderer leftEar;
	public ModelRenderer rightEar;
	public ModelRenderer jawLower;
	public ModelRenderer leftWing2;
	public ModelRenderer rightWing2;
	public ModelRenderer leftLeg2;
	public ModelRenderer leftFoot;
	public ModelRenderer rightLeg2;
	public ModelRenderer rightFoot;

	private final float HEAD_ROT;
	private final float BODY_ROT;

	public BloodBatModel() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.head = new ModelRenderer(this, 26, 0);
		this.head.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.head.addBox(-3.0F, -4.0F, -8.0F, 6.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(head, 0.35185837453889574F, 0.0F, 0.0F);
		this.rightEar = new ModelRenderer(this, 21, 0);
		this.rightEar.mirror = true;
		this.rightEar.setRotationPoint(-1.0F, -4.0F, -2.0F);
		this.rightEar.addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightEar, 0.4300491170387584F, 0.0F, -0.27366763203903305F);
		this.jawUpper = new ModelRenderer(this, 46, 0);
		this.jawUpper.setRotationPoint(0.0F, -1.0F, -8.0F);
		this.jawUpper.addBox(-2.0F, -2.0F, -4.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.jawLower = new ModelRenderer(this, 26, 12);
		this.jawLower.setRotationPoint(0.0F, -0.5F, 0.0F);
		this.jawLower.addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.rightFoot = new ModelRenderer(this, 37, 12);
		this.rightFoot.mirror = true;
		this.rightFoot.setRotationPoint(0.0F, 3.0F, 1.0F);
		this.rightFoot.addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightFoot, 0.1563815016444822F, 0.0F, 0.0F);
		this.rightLeg1 = new ModelRenderer(this, 54, 5);
		this.rightLeg1.mirror = true;
		this.rightLeg1.setRotationPoint(-2.0F, 5.0F, 0.1F);
		this.rightLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightLeg1, 0.3909537457888271F, 0.0F, 0.0F);
		this.rightWing1 = new ModelRenderer(this, 0, 19);
		this.rightWing1.mirror = true;
		this.rightWing1.setRotationPoint(-4.0F, 2.0F, -1.0F);
		this.rightWing1.addBox(-10.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightWing1, 0.0F, 0.23457224414434488F, 0.0F);
		this.rightWing2 = new ModelRenderer(this, 0, 37);
		this.rightWing2.mirror = true;
		this.rightWing2.setRotationPoint(-10.0F, 0.0F, 0.0F);
		this.rightWing2.addBox(-10.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightWing2, 0.0F, 0.4300491170387584F, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.body.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 14.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(body, 0.11728612207217244F, 0.0F, 0.0F);
		this.rightLeg2 = new ModelRenderer(this, 52, 11);
		this.rightLeg2.mirror = true;
		this.rightLeg2.setRotationPoint(0.0F, 3.0F, 0.0F);
		this.rightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightLeg2, 0.27366763203903305F, 0.0F, 0.0F);
		this.leftWing1 = new ModelRenderer(this, 0, 19);
		this.leftWing1.setRotationPoint(4.0F, 2.0F, -1.0F);
		this.leftWing1.addBox(0.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftWing1, 0.0F, -0.1563815016444822F, 0.0F);
		this.leftWing2 = new ModelRenderer(this, 0, 37);
		this.leftWing2.setRotationPoint(10.0F, 0.0F, 0.0F);
		this.leftWing2.addBox(0.0F, -9.0F, 0.0F, 10.0F, 18.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftWing2, 0.0F, -0.3080506063190964F, 0.0F);
		this.leftLeg1 = new ModelRenderer(this, 54, 5);
		this.leftLeg1.setRotationPoint(2.0F, 5.0F, 0.1F);
		this.leftLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg1, 0.3909537457888271F, 0.0F, 0.0F);
		this.leftEar = new ModelRenderer(this, 21, 0);
		this.leftEar.setRotationPoint(1.0F, -4.0F, -2.0F);
		this.leftEar.addBox(-0.0F, -3.0F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftEar, 0.4300491170387584F, 0.0F, 0.27366763203903305F);
		this.leftLeg2 = new ModelRenderer(this, 52, 11);
		this.leftLeg2.setRotationPoint(0.0F, 3.0F, 0.0F);
		this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg2, 0.27366763203903305F, 0.0F, 0.0F);
		this.leftFoot = new ModelRenderer(this, 37, 12);
		this.leftFoot.setRotationPoint(0.0F, 3.0F, 1.0F);
		this.leftFoot.addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftFoot, 0.1563815016444822F, 0.0F, 0.0F);
		this.body.addChild(this.head);
		this.head.addChild(this.rightEar);
		this.head.addChild(this.jawUpper);
		this.jawUpper.addChild(this.jawLower);
		this.rightLeg2.addChild(this.rightFoot);
		this.body.addChild(this.rightLeg1);
		this.body.addChild(this.rightWing1);
		this.rightWing1.addChild(this.rightWing2);
		this.rightLeg1.addChild(this.rightLeg2);
		this.body.addChild(this.leftWing1);
		this.leftWing1.addChild(this.leftWing2);
		this.body.addChild(this.leftLeg1);
		this.head.addChild(this.leftEar);
		this.leftLeg1.addChild(this.leftLeg2);
		this.leftLeg2.addChild(this.leftFoot);

		this.HEAD_ROT = this.head.rotateAngleX;
		this.BODY_ROT = this.body.rotateAngleX;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setLivingAnimations(BloodBatEntity entityIn, float limbSwing, float limbSwingAmount,
			float partialTick) {
		// Wings
		if (!entityIn.isHanging()) {
			float animationHeight = entityIn.getAnimationHeight(partialTick);
			leftWing1.rotateAngleY = animationHeight * (float) Math.toRadians(20);
			leftWing2.rotateAngleY = animationHeight * (float) Math.toRadians(20);
			rightWing1.rotateAngleY = -animationHeight * (float) Math.toRadians(20);
			rightWing2.rotateAngleY = -animationHeight * (float) Math.toRadians(20);
		} else {
			leftWing1.rotateAngleY = (float) Math.toRadians(70);
			leftWing2.rotateAngleY = (float) Math.toRadians(70);
			rightWing1.rotateAngleY = -(float) Math.toRadians(70);
			rightWing2.rotateAngleY = -(float) Math.toRadians(70);
		}
	}

	@Override
	public void setRotationAngles(BloodBatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {

		if (!entityIn.isHanging()) {
			// Legs
			leftLeg1.rotateAngleX = -MathHelper.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			leftLeg2.rotateAngleX = -MathHelper.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			rightLeg1.rotateAngleX = -MathHelper.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);
			rightLeg2.rotateAngleX = -MathHelper.cos(ageInTicks / 10) * (float) Math.toRadians(5)
					+ (float) Math.toRadians(10);

			// Look
			head.rotateAngleY = (float) Math.toRadians(netHeadYaw) * 0.35f;
			head.rotateAngleZ = (float) Math.toRadians(netHeadYaw) * 0.35f;

			// Attack
			head.rotateAngleX = HEAD_ROT
					+ MathHelper.sin(swingProgress * (float) Math.PI * 2) * (float) Math.toRadians(20);

			// Body
			body.rotateAngleX = BODY_ROT + limbSwingAmount * (float) Math.toRadians(100);
			body.rotateAngleY = 0;
		} else {
			leftLeg1.rotateAngleX = 0;
			leftLeg2.rotateAngleX = 0;
			rightLeg1.rotateAngleX = 0;
			rightLeg2.rotateAngleX = 0;
			head.rotateAngleX = 0.2f;
			head.rotateAngleY = 0;
			head.rotateAngleZ = 0;
			body.rotateAngleX = (float) Math.toRadians(180);
			body.rotateAngleY = (float) Math.toRadians(180);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
