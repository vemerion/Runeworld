package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.TopazCreatureEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

// Made with Blockbench 4.3.1

public class TopazCreatureModel extends EntityModel<TopazCreatureEntity> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart leftBackLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart rightBackLeg;

	public TopazCreatureModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.body = root.getChild("body");
		this.head = body.getChild("head");
		this.jaw = head.getChild("jaw");
		this.leftBackLeg = body.getChild("leftBackLeg");
		this.leftFrontLeg = body.getChild("leftFrontLeg");
		this.rightFrontLeg = body.getChild("rightFrontLeg");
		this.rightBackLeg = body.getChild("rightBackLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(56, 0)
						.addBox(1.0F, -12.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(2.0F, -9.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(0.0F, -13.0F, 2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(0.0F, -11.0F, 4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(2.0F, -10.0F, 3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(-2.0F, -9.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(-4.0F, -12.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
						.addBox(-3.0F, -11.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(0.0F, -14.0F, -3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(0.0F, -11.0F, -2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-4.0F, -11.0F, -3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-3.0F, -13.0F, -3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-4.0F, -10.0F, 3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-2.0F, -13.0F, -1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(2.0F, -11.0F, 1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(2.0F, -9.0F, 2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(3.0F, -13.0F, 2.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-1.0F, -10.0F, 5.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 0)
						.addBox(-1.0F, -10.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(40, 0)
						.addBox(-3.0F, -12.0F, 2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 1.0F));

		PartDefinition head = body.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 9)
						.addBox(-2.5F, -2.0F, -5.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(2.5F, -1.0F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).mirror()
						.addBox(-3.5F, -1.0F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, -10.0F, -3.0F));

		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 3.0F,
				6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		body.addOrReplaceChild("leftBackLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F,
				6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, 4.5F));

		body.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F,
				6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, -3.5F));

		body.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F,
				6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, -3.5F));

		body.addOrReplaceChild("rightBackLeg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F,
				6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, 4.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(TopazCreatureEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		head.xRot = Helper.toRad(headPitch);
		head.yRot = Helper.toRad(netHeadYaw);
		rightBackLeg.xRot = Mth.cos(limbSwing * 0.7f) * limbSwingAmount;
		leftBackLeg.xRot = Mth.cos(limbSwing * 0.7f + Mth.PI) * limbSwingAmount;
		rightFrontLeg.xRot = Mth.cos(limbSwing * 0.7f + Mth.PI) * limbSwingAmount;
		leftFrontLeg.xRot = Mth.cos(limbSwing * 0.7f) * limbSwingAmount;
		rightBackLeg.zRot = 0;
		leftBackLeg.zRot = 0;
		rightFrontLeg.zRot = 0;
		leftFrontLeg.zRot = 0;
		body.y = 24;

		if (entity.isInSittingPose()) {
			body.y = 29;
			rightBackLeg.xRot = 0;
			leftBackLeg.xRot = 0;
			rightFrontLeg.xRot = 0;
			leftFrontLeg.xRot = 0;
			rightBackLeg.zRot = Helper.toRad(90);
			leftBackLeg.zRot = Helper.toRad(-90);
			rightFrontLeg.zRot = Helper.toRad(90);
			leftFrontLeg.zRot = Helper.toRad(-90);
		}
	}

	@Override
	public void prepareMobModel(TopazCreatureEntity pEntity, float pLimbSwing, float pLimbSwingAmount,
			float pPartialTick) {
		jaw.xRot = pEntity.getJawRot(pPartialTick);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}