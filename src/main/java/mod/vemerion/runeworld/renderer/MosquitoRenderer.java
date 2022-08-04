package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.MosquitoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MosquitoRenderer extends MobRenderer<MosquitoEntity, MosquitoModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/mosquito.png");

	public MosquitoRenderer(EntityRendererProvider.Context context) {
		super(context, new MosquitoModel(context.bakeLayer(ModLayerLocations.MOSQUITO)), 0.5f);
	}

	@Override
	public void render(MosquitoEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0, -1, 0);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(MosquitoEntity entity) {
		return TEXTURE;
	}

}
