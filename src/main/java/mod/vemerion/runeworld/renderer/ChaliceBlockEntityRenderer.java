package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.blockentity.ChaliceBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluids;

public class ChaliceBlockEntityRenderer implements BlockEntityRenderer<ChaliceBlockEntity> {

	private static final float START = 5 / 16f;
	private static final float END = START + 6 / 16f;
	private static final float HEIGHT = 0.75f;

	public ChaliceBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(ChaliceBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
			MultiBufferSource bufferIn, int light, int overlay) {

		if (!blockEntity.hasLevel())
			return;

		var fluid = blockEntity.getFluid();
		var level = blockEntity.getLevel();
		var pos = blockEntity.getBlockPos();

		if (fluid == Fluids.EMPTY)
			return;

		var texture = fluid.getAttributes().getStillTexture(level, pos);
		var color = fluid.getAttributes().getColor(level, pos);
		var material = new Material(TextureAtlas.LOCATION_BLOCKS, texture);
		var sprite = material.sprite();
		var consumer = material.buffer(bufferIn, RenderType::entityTranslucent);

		var pose = poseStack.last().pose();
		var normal = poseStack.last().normal();

		consumer.defaultColor(FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color),
				FastColor.ARGB32.alpha(color));
		consumer.vertex(pose, START, HEIGHT, START).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(light)
				.normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(pose, START, HEIGHT, END).uv(sprite.getU(0), sprite.getV(16)).overlayCoords(overlay).uv2(light)
				.normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(pose, END, HEIGHT, END).uv(sprite.getU(16), sprite.getV(16)).overlayCoords(overlay).uv2(light)
				.normal(normal, 0, 1, 0).endVertex();
		consumer.vertex(pose, END, HEIGHT, START).uv(sprite.getU(16), sprite.getV(0)).overlayCoords(overlay).uv2(light)
				.normal(normal, 0, 1, 0).endVertex();
		consumer.unsetDefaultColor();
	}
}
