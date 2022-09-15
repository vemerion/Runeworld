package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodKnightEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodKnightModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BloodKnightRenderer extends MobRenderer<BloodKnightEntity, BloodKnightModel> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/blood_knight.png");
	private static final ResourceLocation OVERLAY = new ResourceLocation(Main.MODID,
			"textures/entity/blood_knight_overlay.png");

	public BloodKnightRenderer(EntityRendererProvider.Context context) {
		super(context, new BloodKnightModel(context.bakeLayer(ModLayerLocations.BLOOD_KNIGHT)), 0.8f);
		this.addLayer(new ClothRenderer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(BloodKnightEntity entity) {
		return TEXTURE;
	}

	private class ClothRenderer extends RenderLayer<BloodKnightEntity, BloodKnightModel> {

		public ClothRenderer(RenderLayerParent<BloodKnightEntity, BloodKnightModel> pRenderer) {
			super(pRenderer);
		}

		@Override
		public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight,
				BloodKnightEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks,
				float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
			var model = getParentModel();
			var overlay = getOverlayCoords(pLivingEntity,
					BloodKnightRenderer.this.getWhiteOverlayProgress(pLivingEntity, pPartialTicks));
			model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(OVERLAY)), pPackedLight, overlay,
					pLivingEntity.getRed() / 255f, pLivingEntity.getGreen() / 255f, pLivingEntity.getBlue() / 255f,
					0.65f);
		}
	}
}
