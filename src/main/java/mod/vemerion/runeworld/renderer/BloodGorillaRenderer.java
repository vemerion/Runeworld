package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodGorillaEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodCrownModel;
import mod.vemerion.runeworld.model.BloodGorillaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BloodGorillaRenderer extends MobRenderer<BloodGorillaEntity, BloodGorillaModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/blood_gorilla.png");

	public BloodGorillaRenderer(EntityRendererProvider.Context context) {
		super(context, new BloodGorillaModel(context.bakeLayer(ModLayerLocations.BLOOD_GORILLA)), 1);
		this.addLayer(new CrownLayer(this, context));
	}

	@Override
	public void render(BloodGorillaEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(BloodGorillaEntity entity) {
		return TEXTURE;
	}

	public static class CrownLayer extends RenderLayer<BloodGorillaEntity, BloodGorillaModel> {
		private final BloodCrownModel MODEL;

		public CrownLayer(RenderLayerParent<BloodGorillaEntity, BloodGorillaModel> pRenderer,
				EntityRendererProvider.Context context) {
			super(pRenderer);
			MODEL = new BloodCrownModel(context.bakeLayer(ModLayerLocations.BLOOD_CROWN));
		}

		@Override
		public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight,
				BloodGorillaEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks,
				float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
			pPoseStack.pushPose();
			getParentModel().body.translateAndRotate(pPoseStack);
			getParentModel().head.translateAndRotate(pPoseStack);
			pPoseStack.translate(0.1, -1.4, -0.55);
			MODEL.renderToBuffer(pPoseStack, pBuffer.getBuffer(MODEL.renderType(BloodCrownModel.TEXTURE)),
					pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
	}
}
