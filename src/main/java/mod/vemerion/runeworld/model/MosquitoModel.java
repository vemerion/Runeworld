package mod.vemerion.runeworld.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.entity.MosquitoEntity;
import net.minecraft.client.model.EntityModel;
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
public class MosquitoModel extends EntityModel<MosquitoEntity> {
	public ModelPart bodyLower;
	public ModelPart bodyUpper;
	public ModelPart head;
	public ModelPart tail1;
	public ModelPart leg21;
	public ModelPart leg31;
	public ModelPart leg11;
	public ModelPart leg41;
	public ModelPart leg51;
	public ModelPart leg61;
	public ModelPart leftWing;
	public ModelPart rightWing;
	public ModelPart straw;
	public ModelPart leftTooth;
	public ModelPart rightTooth;
	public ModelPart tail2;
	public ModelPart tail3;
	public ModelPart tail4;
	public ModelPart tail5;
	public ModelPart tail6;
	public ModelPart leg22;
	public ModelPart leg23;
	public ModelPart leg32;
	public ModelPart leg33;
	public ModelPart leg12;
	public ModelPart leg13;
	public ModelPart leg42;
	public ModelPart leg43;
	public ModelPart leg52;
	public ModelPart leg53;
	public ModelPart leg62;
	public ModelPart leg63;

	public MosquitoModel(ModelPart parts) {
		super(RenderType::entityTranslucent);
		this.bodyLower = parts.getChild("bodyLower");
		this.bodyUpper = bodyLower.getChild("bodyUpper");
		this.head = bodyLower.getChild("head");
		this.tail1 = parts.getChild("tail1");
		this.leg21 = bodyLower.getChild("leg21");
		this.leg31 = bodyLower.getChild("leg31");
		this.leg11 = bodyLower.getChild("leg11");
		this.leg41 = bodyLower.getChild("leg41");
		this.leg51 = bodyLower.getChild("leg51");
		this.leg61 = bodyLower.getChild("leg61");
		this.leftWing = bodyUpper.getChild("leftWing");
		this.rightWing = bodyUpper.getChild("rightWing");
		this.straw = head.getChild("straw");
		this.leftTooth = head.getChild("leftTooth");
		this.rightTooth = head.getChild("rightTooth");
		this.tail2 = parts.getChild("tail2");
		this.tail3 = parts.getChild("tail3");
		this.tail4 = parts.getChild("tail4");
		this.tail5 = parts.getChild("tail5");
		this.tail6 = parts.getChild("tail6");
		this.leg22 = leg21.getChild("leg22");
		this.leg23 = leg22.getChild("leg23");
		this.leg32 = leg31.getChild("leg32");
		this.leg33 = leg32.getChild("leg33");
		this.leg12 = leg11.getChild("leg12");
		this.leg13 = leg12.getChild("leg13");
		this.leg42 = leg41.getChild("leg42");
		this.leg43 = leg42.getChild("leg43");
		this.leg52 = leg51.getChild("leg52");
		this.leg53 = leg52.getChild("leg53");
		this.leg62 = leg61.getChild("leg62");
		this.leg63 = leg62.getChild("leg63");
	}
	
	public static LayerDefinition createLayer() {
	    MeshDefinition mesh = new MeshDefinition();
	    PartDefinition parts = mesh.getRoot();
	    PartDefinition bodyLower = parts.addOrReplaceChild("bodyLower", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0, 0, 0));
	    PartDefinition bodyUpper = bodyLower.addOrReplaceChild("bodyUpper", CubeListBuilder.create().texOffs(7, 0).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 3.0F, 7.0F), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, 0.5473352640780661F, 0.0F, 0.0F));
	    PartDefinition head = bodyLower.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -4.0F, 0.19547687289441354F, 0.0F, 0.0F));
	    parts.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(38, 0).addBox(-2.5F, -2.5F, -1.5F, 5.0F, 5.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, -0.19547687289441354F, 0.0F, 0.0F));
	    PartDefinition leg21 = bodyLower.addOrReplaceChild("leg21", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.35185837453889574F, -0.19547687289441354F, -2.3458971115214444F));
	    PartDefinition leg31 = bodyLower.addOrReplaceChild("leg31", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(2.0F, 1.0F, 1.0F, 0.8991936386169619F, 0.6255260065779288F, -1.9940386704035213F));
	    PartDefinition leg11 = bodyLower.addOrReplaceChild("leg11", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(2.0F, 1.0F, -1.0F, -0.3127630032889644F, -0.6646214111173737F, -2.3068016404029725F));
	    PartDefinition leg41 = bodyLower.addOrReplaceChild("leg41", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, 1.0F, -1.0F, 3.0634019776689576F, -0.3504621124688808F, -0.5473352640780661F));
	    PartDefinition leg51 = bodyLower.addOrReplaceChild("leg51", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 2.907020359511178F, 0.12077678556958815F, -0.8210028961170991F));
	    PartDefinition leg61 = bodyLower.addOrReplaceChild("leg61", CubeListBuilder.create().texOffs(59, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-2.0F, 1.0F, 1.0F, 2.3460715485728447F, 0.7819074915776542F, -1.13376586611655F));
	    bodyUpper.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(-16, 20).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 16.0F), PartPose.offsetAndRotation(2.0F, -1.0F, 4.0F, -0.3909537457888271F, 0.2F, 0.0F));
	    bodyUpper.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(-6, 20).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 16.0F), PartPose.offsetAndRotation(-2.0F, -1.0F, 4.0F, -0.3909537457888271F, -0.2F, 0.0F));
	    head.addOrReplaceChild("straw", CubeListBuilder.create().texOffs(34, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 10.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0, 0, 0));
	    head.addOrReplaceChild("leftTooth", CubeListBuilder.create().texOffs(34, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(1.2F, 1.0F, -1.0F, -0.5082398928281348F, 0.0F, 0.0F));
	    head.addOrReplaceChild("rightTooth", CubeListBuilder.create().texOffs(34, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-1.2F, 1.0F, -1.0F, -0.5082398928281348F, 0.0F, 0.0F));
	    parts.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(35, 8).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0, 0, 0));
	    parts.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 10).addBox(-3.5F, -3.5F, -1.5F, 7.0F, 7.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0, 0, 0));
	    parts.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0, 0, 0));
	    parts.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(51, 15).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, 0, 0, 0));
	    parts.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 1.5F, 0, 0, 0));
	    PartDefinition leg22 = leg21.addOrReplaceChild("leg22", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg22.addOrReplaceChild("leg23", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7428121536172364F));
	    PartDefinition leg32 = leg31.addOrReplaceChild("leg32", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg32.addOrReplaceChild("leg33", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7428121536172364F));
	    PartDefinition leg12 = leg11.addOrReplaceChild("leg12", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg12.addOrReplaceChild("leg13", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7330382858376184F));
	    PartDefinition leg42 = leg41.addOrReplaceChild("leg42", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg42.addOrReplaceChild("leg43", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7330382858376184F));
	    PartDefinition leg52 = leg51.addOrReplaceChild("leg52", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg52.addOrReplaceChild("leg53", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7428121536172364F));
	    PartDefinition leg62 = leg61.addOrReplaceChild("leg62", CubeListBuilder.create().texOffs(38, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 2.242398948393804F));
	    leg62.addOrReplaceChild("leg63", CubeListBuilder.create().texOffs(53, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, -0.7428121536172364F));
	    return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MosquitoEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// Tail
		tail1.xRot = (float) (Mth.cos((ageInTicks / 60) * (float) Math.PI * 2) * Math.toRadians(4)
				+ Math.toRadians(-20));

		// Teeth
		leftTooth.zRot = (float) (Mth.cos((ageInTicks / 35) * (float) Math.PI * 2) * Math.toRadians(10));
		rightTooth.zRot = (float) -(Mth.cos((ageInTicks / 35) * (float) Math.PI * 2)
				* Math.toRadians(10));

		// Wings
		leftWing.xRot = (float) (Mth.cos((ageInTicks / 5) * (float) Math.PI * 2) * Math.toRadians(15));
		rightWing.xRot = (float) (Mth.cos(((ageInTicks + 0.2f) / 5) * (float) Math.PI * 2)
				* Math.toRadians(15));

		// Legs
		animateLegs(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
		// Hit
		limbSwingAmount = (float) Math.max(0, limbSwingAmount - 0.4);
		bodyLower.zRot = Mth.cos(limbSwing * 0.6662f) * 2f * limbSwingAmount * 0.2f;
		tail1.zRot = Mth.cos(limbSwing * 0.6662f) * 2f * limbSwingAmount * 0.2f;
		
		// Look
		head.yRot = (float) Math.toRadians(netHeadYaw) * 0.25f;
		head.zRot = (float) Math.toRadians(netHeadYaw) * 0.25f;
	}

	private void animateLegs(MosquitoEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {

		List<ModelPart> rightLegsInner = ImmutableList.of(leg41, leg51, leg61);
		List<ModelPart> rightLegsMiddle = ImmutableList.of(leg42, leg52, leg62);
		List<ModelPart> rightLegsOuter = ImmutableList.of(leg43, leg53, leg63);
		List<ModelPart> leftLegsInner = ImmutableList.of(leg11, leg21, leg31);
		List<ModelPart> leftLegsMiddle = ImmutableList.of(leg12, leg22, leg32);
		List<ModelPart> leftLegsOuter = ImmutableList.of(leg13, leg23, leg33);

		for (int i = 0; i < rightLegsInner.size(); i++) {
			rightLegsInner.get(i).zRot = (float) Math.toRadians(-70 + (3 - i) * 10) + (float) (Mth
					.cos(((ageInTicks + 0.2f) / 30 + (float) Math.toRadians(10) * i) * (float) Math.PI * 2)
					* Math.toRadians(5));
			leftLegsInner.get(i).zRot = (float) Math.toRadians(-110 - (3 - i) * 10) - (float) (Mth
					.cos(((ageInTicks + 0.2f) / 30 + (float) Math.toRadians(10) * i) * (float) Math.PI * 2)
					* Math.toRadians(5));
		}

		leftLegsMiddle.forEach(l -> l.zRot = (float) Math.toRadians(120)
				+ (float) (Mth.cos(((ageInTicks + 0.2f) / 30) * (float) Math.PI * 2) * Math.toRadians(5)));
		leftLegsOuter.forEach(l -> l.zRot = (float) Math.toRadians(50)
				+ (float) (Mth.cos(((ageInTicks + 0.2f) / 30) * (float) Math.PI * 2) * Math.toRadians(5)));
		rightLegsMiddle.forEach(l -> l.zRot = (float) Math.toRadians(120)
				+ (float) (Mth.cos(((ageInTicks + 0.2f) / 30) * (float) Math.PI * 2) * Math.toRadians(5)));
		rightLegsOuter.forEach(l -> l.zRot = (float) Math.toRadians(50)
				+ (float) (Mth.cos(((ageInTicks + 0.2f) / 30) * (float) Math.PI * 2) * Math.toRadians(5)));
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red,
			float green, float blue, float alpha) {
		ImmutableList.of(bodyLower, tail1).forEach(r -> r.render(matrix, bufferIn, packedLightIn, packedOverlayIn));

	}

}
