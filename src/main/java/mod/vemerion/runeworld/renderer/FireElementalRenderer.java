package mod.vemerion.runeworld.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
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
		RenderTypeBuffers renderTypeBuffers = mc.getRenderTypeBuffers();
		BlockRendererDispatcher blockRenderer = mc.getBlockRendererDispatcher();

		matrixStackIn.push();
		matrixStackIn.translate(0.5, 1, -0.5);

		// Body
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Vector3i offset = Direction.byHorizontalIndex(j).getDirectionVec();
				matrixStackIn.translate(offset.getX(), 0, offset.getZ());
				renderPart(renderTypeBuffers, entityIn, matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand,
						blockRenderer);
			}
			matrixStackIn.translate(0, 1, 0);
		}

		matrixStackIn.translate(0, 0.5, 0);

		// Head
		int headX = 1;
		int headY = 0;
		int headZ = -1;
		for (int i = 0; i < 2; i++) {
			headY = i == 0 ? -1 : 1;
			for (int j = 0; j < 4; j++) {
				Direction direction = Direction.byHorizontalIndex(j);
				Vector3i offset = direction.getDirectionVec();
				matrixStackIn.translate(offset.getX(), 0, offset.getZ());
				matrixStackIn.push();
				headX = offset.getX() != 0 ? offset.getX() : headX;
				headZ = offset.getZ() != 0 ? offset.getZ() : headZ;
				double length = MathHelper.abs(MathHelper.cos(ageInTicks / 20)) * 0.2 + 0.1;
				matrixStackIn.translate(length * headX, length * headY, length * headZ);
				renderPart(renderTypeBuffers, entityIn, matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand,
						blockRenderer);
				matrixStackIn.pop();
			}
			matrixStackIn.translate(0, 1, 0);
		}

		// Arms
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				matrixStackIn.push();
				Vector3d offset = Vector3d.fromPitchYaw(0, entityYaw).rotateYaw(Helper.toRad(i == 0 ? -90 : 90))
						.scale(3);
				Vector3d position = offset.add((rand.nextDouble() - 0.5) * 2.5, -j * 1.5 - rand.nextDouble() * 0.5,
						(rand.nextDouble() - 0.5) * 2.5);
				matrixStackIn.translate(position.x, position.y, position.z);
				renderPart(renderTypeBuffers, entityIn, matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand,
						blockRenderer);
				matrixStackIn.pop();

			}
		}

		matrixStackIn.pop();
	}

	private void renderPart(RenderTypeBuffers renderTypeBuffers, FireElementalEntity entity, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn, float ageInTicks, Random rand,
			BlockRendererDispatcher blockRenderer) {
		matrixStackIn.push();
		matrixStackIn.translate(-0.5, -0.5, -0.5);
		shake(matrixStackIn, rand, ageInTicks);
		
		// Render block damage
		MatrixStack.Entry matrixEntry = matrixStackIn.getLast();
		int damage = Math.round(MathHelper.lerp(entity.getHealthPercent(), 9, -1));
		if (damage != -1) {
			IVertexBuilder builder = new MatrixApplyingVertexBuilder(
					renderTypeBuffers.getCrumblingBufferSource()
							.getBuffer(ModelBakery.DESTROY_RENDER_TYPES.get(damage)),
					matrixEntry.getMatrix(), matrixEntry.getNormal());
			blockRenderer.renderBlockDamage(BODY, entity.getPosition(), entity.world, matrixStackIn, builder,
					EmptyModelData.INSTANCE);
		}
		
		// Render block
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
