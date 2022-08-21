package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.entity.TickEntity;
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

public class TickModel extends EntityModel<TickEntity> {
	private final ModelPart head;
	private final ModelPart leftLeg1;
	private final ModelPart leftLeg2;
	private final ModelPart leftLeg3;
	private final ModelPart rightLeg1;
	private final ModelPart rightLeg2;
	private final ModelPart rightLeg3;


	public TickModel(ModelPart root) {
		this.head = root.getChild("head");
		this.leftLeg1 = head.getChild("leftLeg1_r1");
		this.leftLeg2 = head.getChild("leftLeg2_r1");
		this.leftLeg3 = head.getChild("leftLeg3_r1");
		this.rightLeg1 = head.getChild("rightLeg1_r1");
		this.rightLeg2 = head.getChild("rightLeg2_r1");
		this.rightLeg3 = head.getChild("rightLeg3_r1");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 14)
						.addBox(-2.0F, -6.0F, -6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 14)
						.addBox(-2.0F, -2.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(0.0F, -4.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(-1.5F, -5.25F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(-1.0F, -6.5F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(-2.5F, -4.25F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(1.5F, -4.75F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
						.addBox(1.5F, -3.75F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		head.addOrReplaceChild("rightLeg3_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.5F, -3.0F, 1.5F, 0.2618F, 0.5236F, 0.5236F));

		head.addOrReplaceChild("rightLeg2_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, 0.25F, -0.5F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.5F, -3.0F, 0.5F, 0.0F, 0.0F, 0.4363F));

		head.addOrReplaceChild("rightLeg1_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.5F, -3.0F, -0.5F, -0.2618F, -0.5236F, 0.5236F));

		head.addOrReplaceChild("leftLeg3_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.5F, -3.0F, 1.5F, 0.2618F, -0.5236F, -0.5236F));

		head.addOrReplaceChild("leftLeg2_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.5F, -3.0F, 0.5F, 0.0F, 0.0F, -0.4363F));

		head.addOrReplaceChild("leftLeg1_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.5F, -3.0F, -0.5F, -0.2618F, 0.5236F, -0.5236F));

		PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("body_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.0F, -2.5F, 0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(TickEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	      leftLeg1.xRot = Mth.cos(limbSwing * 1.2f) * 1.4F * limbSwingAmount;
	      rightLeg2.xRot = Mth.cos(limbSwing * 1.2f) * 1.4F * limbSwingAmount;
	      leftLeg3.xRot = Mth.cos(limbSwing * 1.2f) * 1.4F * limbSwingAmount;
	      rightLeg1.xRot = Mth.cos(limbSwing * 1.2f + Mth.PI) * 1.4F * limbSwingAmount;
	      leftLeg2.xRot = Mth.cos(limbSwing * 1.2f + Mth.PI) * 1.4F * limbSwingAmount;
	      rightLeg3.xRot = Mth.cos(limbSwing * 1.2f + Mth.PI) * 1.4F * limbSwingAmount;
	      
	      if (!(entity.getVehicle() instanceof BloodMonkeyEntity)) {
	    	  head.xRot = -Mth.sin(attackTime * Helper.toRad(360)) * Helper.toRad(30);
	      }
	}

	@Override
	public void prepareMobModel(TickEntity entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		if (entity.getVehicle() instanceof BloodMonkeyEntity) {
			head.xRot = Helper.toRad(entity.getViewXRot(pPartialTick));
		} else {
			head.xRot = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}