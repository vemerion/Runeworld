package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import mod.vemerion.runeworld.model.MosquitoModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MosquitoRenderer extends MobRenderer<MosquitoEntity, MosquitoModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/mosquito.png");

	public MosquitoRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new MosquitoModel(), 0.5f);
	}

	@Override
	public void render(MosquitoEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0, -1, 0);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(MosquitoEntity entity) {
		return TEXTURE;
	}

}
