package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.TopazBlock;
import mod.vemerion.runeworld.entity.TopazCreatureEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.TopazCreatureModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class TopazCreatureRenderer extends MobRenderer<TopazCreatureEntity, TopazCreatureModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/entity/topaz_creature.png");
	private static final ResourceLocation TEXTURE_CRYSTAL = new ResourceLocation(Main.MODID,
			"textures/entity/topaz_creature_crystal.png");

	public TopazCreatureRenderer(EntityRendererProvider.Context context) {
		super(context, new TopazCreatureModel(context.bakeLayer(ModLayerLocations.TOPAZ_CREATURE)), 0.4f);
		this.addLayer(new CrystalLayer(this));
	}

	@Override
	public void render(TopazCreatureEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(TopazCreatureEntity entity) {
		return TEXTURE;
	}

	public class CrystalLayer extends RenderLayer<TopazCreatureEntity, TopazCreatureModel> {

		public CrystalLayer(RenderLayerParent<TopazCreatureEntity, TopazCreatureModel> pRenderer) {
			super(pRenderer);
		}

		@Override
		public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight,
				TopazCreatureEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks,
				float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
			var model = getParentModel();
			var color = TopazBlock.COLOR;
			var overlay = getOverlayCoords(pLivingEntity,
					TopazCreatureRenderer.this.getWhiteOverlayProgress(pLivingEntity, pPartialTicks));
			model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(TEXTURE_CRYSTAL)), pPackedLight,
					overlay, color.getX() / 255f, color.getY() / 255f, color.getZ() / 255f, 0.65f);
		}
	}
}
