package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodBatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BloodBatRenderer extends MobRenderer<BloodBatEntity, BloodBatModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/blood_bat.png");

	public BloodBatRenderer(EntityRendererProvider.Context context) {
		super(context, new BloodBatModel(context.bakeLayer(ModLayerLocations.BLOOD_BAT)), 0.5f);
	}

	@Override
	public void render(BloodBatEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		if (!entityIn.isHanging()) {
			matrixStackIn.translate(0, entityIn.getAnimationHeight(partialTicks) * 0.1, 0);
		} else {
			Vec3 offset = Vec3.directionFromRotation(0, entityIn.getYRot()).scale(-0.4);
			matrixStackIn.translate(offset.x, 0.08, offset.z);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(BloodBatEntity entity) {
		return TEXTURE;
	}

}
