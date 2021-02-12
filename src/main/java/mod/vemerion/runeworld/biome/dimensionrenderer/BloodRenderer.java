package mod.vemerion.runeworld.biome.dimensionrenderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.vemerion.runeworld.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.IWeatherRenderHandler;

public class BloodRenderer {

	public static class SkyRenderer implements ISkyRenderHandler {

		private static final ResourceLocation SUN = new ResourceLocation(Main.MODID,
				"textures/environment/blood/sun.png");

		@Override
		public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
			BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
			matrixStack.push();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableBlend();
			RenderSystem.color4f(1, 1, 1, 0.8f);
			matrixStack.translate(0, -25, 0);

			matrixStack.rotate(Vector3f.YP.rotationDegrees(75));
			matrixStack.rotate(Vector3f.XP.rotationDegrees(-60));
			matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
			Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
			mc.textureManager.bindTexture(SUN);
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			float sizeX = 20;
			float sizeY = sizeX * 4;
			bufferbuilder.pos(matrix4f1, -sizeX, 100.0F, -sizeY).tex(0.0F, 0.0F).endVertex();
			bufferbuilder.pos(matrix4f1, sizeX, 100.0F, -sizeY).tex(1.0F, 0.0F).endVertex();
			bufferbuilder.pos(matrix4f1, sizeX, 100.0F, sizeY).tex(1.0F, 1.0F).endVertex();
			bufferbuilder.pos(matrix4f1, -sizeX, 100.0F, sizeY).tex(0.0F, 1.0F).endVertex();
			bufferbuilder.finishDrawing();
			WorldVertexBufferUploader.draw(bufferbuilder);

			renderParticles(ticks, partialTicks, bufferbuilder, matrix4f1);

			matrixStack.pop();
		}

		private static final int PARTICLE_PERIOD = 200;

		private void renderParticles(int ticks, float partialTicks, BufferBuilder bufferbuilder, Matrix4f matrix4f1) {
			RenderSystem.disableTexture();
			float size = 0.2f;
			Random random = new Random(0);
			for (int i = 0; i < 100; i++) {
				float progress = 1 - (ticks + partialTicks + random.nextInt(PARTICLE_PERIOD)) % PARTICLE_PERIOD
						/ (float) PARTICLE_PERIOD;
				float radius = 30 * progress;
				float direction = (float) Math.toRadians(360) * random.nextFloat();
				float x = MathHelper.cos(direction) * radius;
				float y = MathHelper.sin(direction) * radius - 65;
				float z = random.nextFloat() * 10 * progress;
				RenderSystem.color4f(0, 0, 0, (1 - progress) * 0.8f);
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				bufferbuilder.pos(matrix4f1, -size + x, 100.0F - z, -size + y).tex(0.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f1, size + x, 100.0F - z, -size + y).tex(1.0F, 0.0F).endVertex();
				bufferbuilder.pos(matrix4f1, size + x, 100.0F - z, size + y).tex(1.0F, 1.0F).endVertex();
				bufferbuilder.pos(matrix4f1, -size + x, 100.0F - z, size + y).tex(0.0F, 1.0F).endVertex();
				bufferbuilder.finishDrawing();
				WorldVertexBufferUploader.draw(bufferbuilder);
			}
			RenderSystem.color4f(1, 1, 1, 1);
			RenderSystem.enableTexture();
		}

	}

	public static class WeatherRenderer implements IWeatherRenderHandler {

		@Override
		public void render(int ticks, float partialTicks, ClientWorld world, Minecraft mc, LightTexture lightmapIn,
				double xIn, double yIn, double zIn) {
//			if (!world.isRaining())
//				return;
//
//			MatrixStack matrix = new MatrixStack();
//			RenderSystem.disableAlphaTest();
//
//			RenderSystem.enableBlend();
//			RenderSystem.defaultBlendFunc();
//			RenderSystem.depthMask(false);
//			mc.getTextureManager().bindTexture(new ResourceLocation("textures/environment/rain.png"));
//			Tessellator tessellator = Tessellator.getInstance();
//
//			matrix.push();
//			matrix.rotate(mc.getRenderManager().getCameraOrientation());
//			matrix.scale(-1, -1, 1);
//			matrix.translate(-0.15, -0.09, 0.1);
//			matrix.scale(0.01f, 0.01f, 0.01f);
//
//			for (int i = 0; i < 2; i++) {
//				matrix.push();
//				matrix.scale(0.5f, 0.5f, 1);
//				float height = (ticks + partialTicks) % 30;
//				Matrix4f matrix4f = matrix.getLast().getMatrix();
//				BufferBuilder bufferbuilder = tessellator.getBuffer();
//				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//				bufferbuilder.pos(matrix4f, 0, height - i * 30, 0).tex(0.0F, 0.0F).color(255, 0, 0, 200).endVertex();
//				bufferbuilder.pos(matrix4f, 0, 40 + height - i * 30, 0).tex(0.0F, 1.0F).color(255, 0, 0, 200).endVertex();
//				bufferbuilder.pos(matrix4f, 10, 40 + height - i * 30, 0).tex(1.0F, 1.0F).color(255, 0, 0, 200).endVertex();
//				bufferbuilder.pos(matrix4f, 10, height - i * 30, 0).tex(1.0F, 0.0F).color(255, 0, 0, 200).endVertex();
//
//				tessellator.draw();
//				matrix.pop();
//			}
//			
//			matrix.pop();
//
//			RenderSystem.depthMask(true);
//			RenderSystem.enableTexture();
//			RenderSystem.disableBlend();
//			RenderSystem.enableAlphaTest();
		}
	}
}
