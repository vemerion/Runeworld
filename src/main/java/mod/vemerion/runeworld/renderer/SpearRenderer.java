package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mod.vemerion.runeworld.entity.SpearEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.SpearModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SpearRenderer extends EntityRenderer<SpearEntity> {
	private final SpearModel MODEL;

	public SpearRenderer(EntityRendererProvider.Context context) {
		super(context);
		MODEL = new SpearModel(context.bakeLayer(ModLayerLocations.SPEAR));
	}

	@Override
	public void render(SpearEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack,
			MultiBufferSource bufferIn, int packedLightIn) {
		poseStack.pushPose();
		poseStack.translate(0, 0.5, 0);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(entityIn.getViewYRot(partialTicks) + 180));
		poseStack.mulPose(Vector3f.XP.rotationDegrees(entityIn.getViewXRot(partialTicks) + 0));
		poseStack.translate(0, -0.5, 0);
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
		MODEL.renderToBuffer(poseStack, bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entityIn))),
				packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(SpearEntity pEntity) {
		return BloodKnightRenderer.TEXTURE;
	}

}
