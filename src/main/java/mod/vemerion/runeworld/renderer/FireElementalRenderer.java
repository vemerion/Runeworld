package mod.vemerion.runeworld.renderer;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.EmptyModelData;

public class FireElementalRenderer extends EntityRenderer<FireElementalEntity> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/block/fire_ritual_stone.png");
	private static final BlockState BODY = ModBlocks.FIRE_RITUAL_STONE.defaultBlockState();

	public FireElementalRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(FireElementalEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

		int ticksExisted = entityIn.tickCount;
		float ageInTicks = ticksExisted + partialTicks;
		Random rand = new Random(2);

		Minecraft mc = Minecraft.getInstance();
		RenderBuffers renderTypeBuffers = mc.renderBuffers();
		BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();

		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5, 1, -0.5);

		// Body
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Vec3i offset = Direction.from2DDataValue(j).getNormal();
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
				Direction direction = Direction.from2DDataValue(j);
				Vec3i offset = direction.getNormal();
				matrixStackIn.translate(offset.getX(), 0, offset.getZ());
				matrixStackIn.pushPose();
				headX = offset.getX() != 0 ? offset.getX() : headX;
				headZ = offset.getZ() != 0 ? offset.getZ() : headZ;
				double length = Mth.abs(Mth.cos(ageInTicks / 20)) * 0.2 + 0.1;
				matrixStackIn.translate(length * headX, length * headY, length * headZ);
				renderPart(renderTypeBuffers, entityIn, matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand,
						blockRenderer);
				matrixStackIn.popPose();
			}
			matrixStackIn.translate(0, 1, 0);
		}

		// Arms
		float armHeight = entityIn.getArmHeight(partialTicks);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				matrixStackIn.pushPose();
				Vec3 offset = Vec3.directionFromRotation(0, entityIn.getArmRotation(partialTicks))
						.yRot(Helper.toRad(i == 0 ? -90 : 90)).scale(4);
				Vec3 position = offset.add((rand.nextDouble() - 0.5) * 2.5,
						-j * 1.5 - rand.nextDouble() * 0.5 + armHeight, (rand.nextDouble() - 0.5) * 2.5);
				matrixStackIn.translate(position.x, position.y, position.z);
				renderPart(renderTypeBuffers, entityIn, matrixStackIn, bufferIn, packedLightIn, ageInTicks, rand,
						blockRenderer);
				matrixStackIn.popPose();

			}
		}

		matrixStackIn.popPose();
	}

	private void renderPart(RenderBuffers renderTypeBuffers, FireElementalEntity entity, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn, float ageInTicks, Random rand,
			BlockRenderDispatcher blockRenderer) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(-0.5, -0.5, -0.5);
		shake(matrixStackIn, rand, ageInTicks);

		// Render block damage
		PoseStack.Pose matrixEntry = matrixStackIn.last();
		int damage = Math.round(Mth.lerp(entity.getHealthPercent(), 9, -1));
		if (damage != -1) {
			VertexConsumer builder = new SheetedDecalTextureGenerator(
					renderTypeBuffers.crumblingBufferSource()
							.getBuffer(ModelBakery.DESTROY_TYPES.get(damage)),
					matrixEntry.pose(), matrixEntry.normal());
			blockRenderer.renderBreakingTexture(BODY, entity.blockPosition(), entity.level, matrixStackIn, builder,
					EmptyModelData.INSTANCE);
		}

		// Render block
		blockRenderer.renderSingleBlock(BODY, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY,
				EmptyModelData.INSTANCE);
		matrixStackIn.popPose();
	}

	private void shake(PoseStack matrix, Random rand, float ageInTicks) {
		matrix.translate(randPos(rand, ageInTicks), randPos(rand, ageInTicks), randPos(rand, ageInTicks));
	}

	private double randPos(Random rand, float ageInTicks) {
		return Mth.cos(ageInTicks + Helper.toRad(rand.nextDouble() * 360)) * 0.01;
	}

	@Override
	public ResourceLocation getTextureLocation(FireElementalEntity entity) {
		return TEXTURE;
	}

}
