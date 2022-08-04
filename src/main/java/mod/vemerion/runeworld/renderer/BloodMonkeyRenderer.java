package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodMonkeyModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BloodMonkeyRenderer extends MobRenderer<BloodMonkeyEntity, BloodMonkeyModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/blood_monkey.png");

	public BloodMonkeyRenderer(EntityRendererProvider.Context context) {
		super(context, new BloodMonkeyModel(context.bakeLayer(ModLayerLocations.BLOOD_MONKEY)), 0.5f);
	}

	@Override
	public void render(BloodMonkeyEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(BloodMonkeyEntity entity) {
		return TEXTURE;
	}

}
