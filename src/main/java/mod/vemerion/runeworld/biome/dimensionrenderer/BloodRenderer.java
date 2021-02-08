package mod.vemerion.runeworld.biome.dimensionrenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.IWeatherRenderHandler;

public class BloodRenderer {

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
