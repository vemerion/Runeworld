package mod.vemerion.runeworld.biome.dimensionrenderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.ICloudRenderHandler;

public class FireRenderer {

	public static class CloudRenderer implements ICloudRenderHandler {

		public static final float SIZE = 4;
		public static final float REGION_SIZE = 128;

		private boolean fastClouds = false;

		@Override
		public void render(int ticks, float partialTicks, MatrixStack matrix, ClientWorld world, Minecraft mc,
				double viewEntityX, double viewEntityY, double viewEntityZ) {
			fastClouds = mc.gameSettings.getCloudOption() == CloudOption.FAST;
			BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
			matrix = new MatrixStack();
			float time = ticks + partialTicks;
			matrix.push();
			matrix.translate(-viewEntityX, -viewEntityY + 130, -viewEntityZ);

			RenderSystem.disableTexture();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.disableDepthTest();
			RenderSystem.disableAlphaTest();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableFog();
			RenderSystem.depthMask(true);

			int regionX = (int) viewEntityX >> 7;
			int regionZ = (int) viewEntityZ >> 7;
			int bound = fastClouds ? 1 : 2;
			for (int i = -bound; i <= bound; i++) {
				for (int j = -bound; j <= bound; j++) {
					drawRegion(matrix, bufferbuilder, time, regionX + i, regionZ + j);
				}
			}

			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			RenderSystem.disableFog();
			RenderSystem.enableTexture();
			matrix.pop();
		}

		private void drawRegion(MatrixStack matrix, BufferBuilder bufferbuilder, float time, int regionX, int regionZ) {
			Random rand = new Random(regionX * 31 + regionZ);
			float x = regionX * REGION_SIZE;
			float z = regionZ * REGION_SIZE;

			for (int i = 0; i < 30; i++) {
				matrix.push();
				float speed = MathHelper.nextFloat(rand, 200, 800);
				float progress = (rand.nextFloat() + time / speed) % 1;
				float scale = 1 - progress;
				float y = progress * 60 - 30;
				RenderSystem.color4f(0.2f, 0.2f, 0.2f, progress);
				drawCloud(matrix, bufferbuilder, x + rand.nextFloat() * REGION_SIZE, z + rand.nextFloat() * REGION_SIZE,
						y, scale);
				matrix.pop();
			}
		}

		private void drawCloud(MatrixStack matrix, BufferBuilder bufferbuilder, float x, float z, float y,
				float scale) {
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			matrix.push();
			matrix.translate(x, y, z);
			matrix.scale(scale, scale, scale);

			Matrix4f matrix4f = matrix.getLast().getMatrix();

			// Bottom
			bufferbuilder.pos(matrix4f, -SIZE, 0, -SIZE).tex(0.0F, 0.0F).endVertex();
			bufferbuilder.pos(matrix4f, SIZE, 0, -SIZE).tex(1.0F, 0.0F).endVertex();
			bufferbuilder.pos(matrix4f, SIZE, 0, SIZE).tex(1.0F, 1.0F).endVertex();
			bufferbuilder.pos(matrix4f, -SIZE, 0, SIZE).tex(0.0F, 1.0F).endVertex();

			if (!fastClouds) {
				// Top
				bufferbuilder.pos(matrix4f, -SIZE, SIZE * 2, -SIZE).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE * 2, -SIZE).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE * 2, SIZE).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, SIZE * 2, SIZE).tex(0.0F, 1.0F).endVertex();

				// Sides
				bufferbuilder.pos(matrix4f, -SIZE, -SIZE + SIZE, SIZE).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, -SIZE + SIZE, SIZE).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE + SIZE, SIZE).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, SIZE + SIZE, SIZE).tex(0.0F, 1.0F).endVertex();

				bufferbuilder.pos(matrix4f, -SIZE, -SIZE + SIZE, -SIZE).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, -SIZE + SIZE, -SIZE).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE + SIZE, -SIZE).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, SIZE + SIZE, -SIZE).tex(0.0F, 1.0F).endVertex();

				bufferbuilder.pos(matrix4f, SIZE, -SIZE + SIZE, -SIZE).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, -SIZE + SIZE, SIZE).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE + SIZE, SIZE).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f, SIZE, SIZE + SIZE, -SIZE).tex(0.0F, 1.0F).endVertex();

				bufferbuilder.pos(matrix4f, -SIZE, -SIZE + SIZE, -SIZE).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, -SIZE + SIZE, SIZE).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, SIZE + SIZE, SIZE).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f, -SIZE, SIZE + SIZE, -SIZE).tex(0.0F, 1.0F).endVertex();
			}

			bufferbuilder.finishDrawing();
			WorldVertexBufferUploader.draw(bufferbuilder);
			matrix.pop();
		}
	}
}
