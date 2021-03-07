package mod.vemerion.runeworld.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

		int ticksExisted = entityIn.ticksExisted;
		float ageInTicks = ticksExisted + partialTicks;
		Random rand = new Random(2);

		Minecraft mc = Minecraft.getInstance();
		BlockRendererDispatcher blockRenderer = mc.getBlockRendererDispatcher();

		matrixStackIn.push();
		matrixStackIn.translate(0.5, 1, -0.5);

		// body
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				renderPart(matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand, blockRenderer);
				BlockPos direction = BlockPos.ZERO.offset(Direction.byHorizontalIndex(j));
				matrixStackIn.translate(direction.getX(), 0, direction.getZ());
			}
			matrixStackIn.translate(0, 1, 0);
		}

		matrixStackIn.pop();
	}

	private void renderPart(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, float ageInTicks,
			Random rand, BlockRendererDispatcher blockRenderer) {
		matrixStackIn.push();
		matrixStackIn.translate(-0.5, -0.5, -0.5);
		shake(matrixStackIn, rand, ageInTicks);
		blockRenderer.renderBlock(BODY, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY,
				EmptyModelData.INSTANCE);
		matrixStackIn.pop();
	}

	private void shake(MatrixStack matrix, Random rand, float ageInTicks) {
		matrix.translate(randPos(rand, ageInTicks), randPos(rand, ageInTicks), randPos(rand, ageInTicks));
	}

	private double randPos(Random rand, float ageInTicks) {
		return MathHelper.cos(ageInTicks + Helper.toRad(rand.nextDouble() * 360)) * 0.01;
	}

	@Override
	public ResourceLocation getEntityTexture(FireElementalEntity entity) {
		return TEXTURE;
	}

}
