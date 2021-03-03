package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.model.BloodMonkeyModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BloodMonkeyRenderer extends MobRenderer<BloodMonkeyEntity, BloodMonkeyModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/blood_monkey.png");

	public BloodMonkeyRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BloodMonkeyModel(), 0.5f);
	}

	@Override
	public void render(BloodMonkeyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(BloodMonkeyEntity entity) {
		return TEXTURE;
	}

}
