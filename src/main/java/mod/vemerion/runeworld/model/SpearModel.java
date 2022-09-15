package mod.vemerion.runeworld.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.SpearEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

// Made with Blockbench 4.3.1

public class SpearModel extends EntityModel<SpearEntity> {
	private final ModelPart spear1;

	public SpearModel(ModelPart root) {
		this.spear1 = root.getChild("spear1");
	}

	public static void fillParts(PartDefinition partdefinition, PartPose pose) {

		PartDefinition spear1 = partdefinition.addOrReplaceChild("spear1",
				CubeListBuilder.create().texOffs(50, 85)
						.addBox(-1.5F, -1.5F, -11.0F, 3.0F, 3.0F, 36.0F, new CubeDeformation(0.0F)).texOffs(56, 111)
						.addBox(-5.0F, 0.0F, -21.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
				pose);

		spear1.addOrReplaceChild("spear3_r1",
				CubeListBuilder.create().texOffs(56, 111).addBox(-5.0F, 0.0F, -6.0F, 10.0F, 0.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -15.0F, 0.0F, 0.0F, 1.5708F));
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		fillParts(partdefinition, PartPose.offset(0, 7.5F, 0));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(SpearEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		spear1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}