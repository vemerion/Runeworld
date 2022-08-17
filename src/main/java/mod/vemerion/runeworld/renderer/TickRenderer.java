package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.TickEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.TickModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TickRenderer extends MobRenderer<TickEntity, TickModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/tick.png");

	public TickRenderer(EntityRendererProvider.Context context) {
		super(context, new TickModel(context.bakeLayer(ModLayerLocations.TICK)), 0.3f);
	}

	@Override
	public void render(TickEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(TickEntity entity) {
		return TEXTURE;
	}
}
