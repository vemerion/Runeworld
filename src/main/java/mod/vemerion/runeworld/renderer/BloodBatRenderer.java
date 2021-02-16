package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.model.BloodBatModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class BloodBatRenderer extends MobRenderer<BloodBatEntity, BloodBatModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/blood_bat.png");

	public BloodBatRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BloodBatModel(), 0.5f);
	}

	@Override
	public void render(BloodBatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		if (!entityIn.isHanging()) {
			matrixStackIn.translate(0, entityIn.getAnimationHeight(partialTicks) * 0.1, 0);
		} else {
			Vector3d offset = Vector3d.fromPitchYaw(0, entityIn.rotationYaw).scale(-0.4);
			matrixStackIn.translate(offset.x, 0.08, offset.z);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(BloodBatEntity entity) {
		return TEXTURE;
	}

}
