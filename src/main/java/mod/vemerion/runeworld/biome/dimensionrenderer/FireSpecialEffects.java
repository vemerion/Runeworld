package mod.vemerion.runeworld.biome.dimensionrenderer;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects.OverworldEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ICloudRenderHandler;

public class FireSpecialEffects extends OverworldEffects {

	public FireSpecialEffects() {
		super();
		this.setCloudRenderHandler(new CloudRenderer());
	}

	public static class CloudRenderer implements ICloudRenderHandler {

		public static final float SIZE = 4;
		public static final float REGION_SIZE = 128;

		private boolean fastClouds = false;

		@Override
		public void render(int ticks, float partialTicks, PoseStack matrix, ClientLevel world, Minecraft mc,
				double viewEntityX, double viewEntityY, double viewEntityZ) {
			fastClouds = mc.options.getCloudsType() == CloudStatus.FAST;
			BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
			matrix = new PoseStack();
			float time = ticks + partialTicks;
			matrix.pushPose();
			matrix.translate(-viewEntityX, -viewEntityY + 130, -viewEntityZ);

			RenderSystem.disableTexture();
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(true);

			int regionX = (int) viewEntityX >> 7;
			int regionZ = (int) viewEntityZ >> 7;
			int bound = 2;
			bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
			for (int i = -bound; i <= bound; i++) {
				for (int j = -bound; j <= bound; j++) {
					drawRegion(matrix, bufferbuilder, time, regionX + i, regionZ + j, viewEntityX, viewEntityZ);
				}
			}
			bufferbuilder.setQuadSortOrigin(0.5f, 0.5f, 0.5f);
			bufferbuilder.end();
			BufferUploader.end(bufferbuilder);

			RenderSystem.setShaderColor(1, 1, 1, 1);
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			RenderSystem.enableTexture();
			matrix.popPose();
		}

		private void drawRegion(PoseStack matrix, BufferBuilder bufferbuilder, float time, int regionX, int regionZ,
				double viewEntityX, double viewEntityZ) {
			Random rand = new Random(regionX * 31 + regionZ);

			for (int i = 0; i < 30; i++) {
				float x = (regionX + rand.nextFloat()) * REGION_SIZE;
				float z = (regionZ + rand.nextFloat()) * REGION_SIZE;

				float speed = Mth.nextFloat(rand, 200, 800);
				float progress = (rand.nextFloat() + time / speed) % 1;
				float y = progress * 60 - 30;
				float scale = progress - 1;
				// Also base alpha on distance to player to avoid smoke clouds 'popping in'
				float distance = Mth.square((float) viewEntityX - x) + Mth.square((float) viewEntityZ - z);
				float alpha = progress * Math.max(1 - distance / 70000, 0);
				RenderSystem.setShaderColor(1, 1, 1, 1);

				matrix.pushPose();
				drawCloud(matrix, bufferbuilder, x, z, y, scale, alpha);
				matrix.popPose();
			}
		}

		private void drawCloud(PoseStack matrix, BufferBuilder bufferbuilder, float x, float z, float y, float scale,
				float alpha) {
			matrix.pushPose();
			matrix.translate(x, y, z);
			matrix.scale(scale, scale, scale);

			Matrix4f matrix4f = matrix.last().pose();

			// Bottom
			bufferbuilder.vertex(matrix4f, -SIZE, 0, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
			bufferbuilder.vertex(matrix4f, SIZE, 0, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
			bufferbuilder.vertex(matrix4f, SIZE, 0, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
			bufferbuilder.vertex(matrix4f, -SIZE, 0, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();

			if (!fastClouds) {
				// Top
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE * 2, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE * 2, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE * 2, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE * 2, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();

				// Sides
				bufferbuilder.vertex(matrix4f, -SIZE, -SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, -SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();

				bufferbuilder.vertex(matrix4f, -SIZE, -SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, -SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();

				bufferbuilder.vertex(matrix4f, SIZE, -SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, -SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, SIZE, SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();

				bufferbuilder.vertex(matrix4f, -SIZE, -SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, -SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE + SIZE, SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
				bufferbuilder.vertex(matrix4f, -SIZE, SIZE + SIZE, -SIZE).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
			}

			matrix.popPose();
		}
	}
}
