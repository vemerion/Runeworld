package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class BloodLeechModel extends Model {
	public ModelPart body;
	public ModelPart eye1;
	public ModelPart eye2;

	public BloodLeechModel(ModelPart parts) {
		super(RenderType::entityCutout);
		this.body = parts.getChild("body");
		this.eye1 = body.getChild("eye1");
		this.eye2 = body.getChild("eye2");
	}
	
	public static LayerDefinition createLayer() {
	    MeshDefinition mesh = new MeshDefinition();
	    PartDefinition parts = mesh.getRoot();
	    PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0, 0, 0));
	    body.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-0.5F, -1.0F, -2.0F, 0.3490658503988659F, 0.7428121536172364F, 0.0F));
	    body.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.4F, -1.0F, -2.0F, 0.35185837453889574F, -0.6981317007977318F, 0.0F));
	    return LayerDefinition.create(mesh, 64, 32);
	}


	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	public void animate(BloodLeechBlockEntity leech, float partialTicks, float ticksExisted) {
		eye1.xRot = (float) (Math.toRadians(20) + Mth.cos(ticksExisted / 20) * Math.toRadians(10));
		eye2.xRot = (float) (Math.toRadians(20) + Mth.cos(ticksExisted / 20) * Math.toRadians(10));
	}
}
