package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodGorillaEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodGorillaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BloodGorillaRenderer extends MobRenderer<BloodGorillaEntity, BloodGorillaModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/blood_gorilla.png");

	public BloodGorillaRenderer(EntityRendererProvider.Context context) {
		super(context, new BloodGorillaModel(context.bakeLayer(ModLayerLocations.BLOOD_GORILLA)), 1);
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

}
