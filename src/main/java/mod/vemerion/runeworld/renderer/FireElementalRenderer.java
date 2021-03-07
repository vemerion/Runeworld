package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;

public class FireElementalRenderer extends EntityRenderer<FireElementalEntity> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/block/fire_ritual_stone.png");
	private static final BlockState BODY = ModBlocks.FIRE_RITUAL_STONE.getDefaultState();

	public FireElementalRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(FireElementalEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

		Minecraft mc = Minecraft.getInstance();
		mc.getBlockRendererDispatcher().renderBlock(BODY, matrixStackIn, bufferIn, packedLightIn,
				OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
	}

	@Override
	public ResourceLocation getEntityTexture(FireElementalEntity entity) {
		return TEXTURE;
	}

}
