package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created using Tabula 8.0.0
 */
public class BloodMonkeyModel extends EntityModel<BloodMonkeyEntity> {
	public ModelRenderer body;
	public ModelRenderer leftArm1;
	public ModelRenderer head;
	public ModelRenderer leftLeg1;
	public ModelRenderer leftArm2;
	public ModelRenderer leftEar;
	public ModelRenderer rightEar;
	public ModelRenderer nose;
	public ModelRenderer leftLeg2;

	public BloodMonkeyModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.leftLeg1 = new ModelRenderer(this, 0, 0);
		this.leftLeg1.setRotationPoint(2.5F, 11.0F, 0.5F);
		this.leftLeg1.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg1, -0.2932153010192419F, 0.712094321497881F, -0.16755161484935835F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(body, 0.35185837453889574F, 0.0F, 0.0F);
		this.leftArm2 = new ModelRenderer(this, 0, 0);
		this.leftArm2.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.leftArm2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftArm2, -0.5473352640780661F, 0.27366763203903305F, 0.3127630032889644F);
		this.rightEar = new ModelRenderer(this, 32, 0);
		this.rightEar.setRotationPoint(-3.0F, -4.0F, -1.0F);
		this.rightEar.addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightEar, 0.3490658503988659F, 0.3490658503988659F, 0.0F);
		this.nose = new ModelRenderer(this, 32, 0);
		this.nose.setRotationPoint(0.0F, -1.5F, -3.0F);
		this.nose.addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.leftLeg2 = new ModelRenderer(this, 0, 0);
		this.leftLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.leftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftLeg2, -0.25132740562928074F, -0.2932153010192419F, 0.08377580742467917F);
		this.leftEar = new ModelRenderer(this, 32, 0);
		this.leftEar.setRotationPoint(3.0F, -4.0F, -1.0F);
		this.leftEar.addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftEar, 0.37699112508867794F, -0.3490658503988659F, 0.0F);
		this.leftArm1 = new ModelRenderer(this, 0, 0);
		this.leftArm1.setRotationPoint(5.0F, 1.0F, 0.0F);
		this.leftArm1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftArm1, -0.8600982340775168F, 0.1563815016444822F, -0.0781907508222411F);
		this.head = new ModelRenderer(this, 32, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(head, -0.19547687289441354F, 0.0F, 0.0F);
		this.body.addChild(this.leftLeg1);
		this.leftArm1.addChild(this.leftArm2);
		this.head.addChild(this.rightEar);
		this.head.addChild(this.nose);
		this.leftLeg1.addChild(this.leftLeg2);
		this.head.addChild(this.leftEar);
		this.body.addChild(this.leftArm1);
		this.body.addChild(this.head);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(BloodMonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
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
