package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.TopazCreatureEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

// Made with Blockbench 4.3.1

public class TopazCreatureModel extends EntityModel<TopazCreatureEntity> {
	private final ModelPart body;

	public TopazCreatureModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(32, 0)
						.addBox(3.0F, -6.0F, 3.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(3.0F, -6.0F, -4.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(-5.0F, -6.0F, -4.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(-5.0F, -6.0F, 3.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 0)
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

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(),
				PartPose.offset(0.0F, -1.0F, 0.0F));

		jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 3.0F,
				6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(TopazCreatureEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}