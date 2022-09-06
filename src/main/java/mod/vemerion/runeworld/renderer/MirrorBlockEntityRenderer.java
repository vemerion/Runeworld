package mod.vemerion.runeworld.renderer;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.MirrorBlock;
import mod.vemerion.runeworld.blockentity.MirrorBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class MirrorBlockEntityRenderer implements BlockEntityRenderer<MirrorBlockEntity> {

	private static final float DEPTH_OFFSET = 1.001f / 16;
	private static final int TIMEOUT_CLEAR = 1000 * 60;
	private static final int RESOLUTION = 64;

	private static final Map<BlockPos, RenderInfo> renderInfo = new HashMap<>();

	private static final RenderBuffers RENDER_BUFFERS = new RenderBuffers();

	public MirrorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	private static ResourceLocation location(BlockPos pos) {
		return new ResourceLocation(Main.MODID, "mirror" + pos.getX() + "_" + pos.getY() + "_" + pos.getZ());
	}

	public static void tick() {
		var time = Util.getMillis();

		renderInfo.entrySet().removeIf(entry -> {
			var pos = entry.getKey();
			var info = entry.getValue();
			if (time - info.timestamp > TIMEOUT_CLEAR) {
				Minecraft.getInstance().getTextureManager().release(location(pos));
				info.texture.close();
				return true;
			}
			return false;
		});
	}

	private static DynamicTexture getTexture(BlockPos pos) {
		var info = renderInfo.get(pos);

		if (info == null) {
			info = new RenderInfo();
			info.texture = new DynamicTexture(new NativeImage(RESOLUTION, RESOLUTION, true));
			Minecraft.getInstance().getTextureManager().register(location(pos), info.texture);
			renderInfo.put(pos, info);
		}

		info.timestamp = Util.getMillis();
		return info.texture;
	}

	@Override
	public int getViewDistance() {
		return 12;
	}

	@Override
	public void render(MirrorBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		if (!blockEntity.hasLevel())
			return;

		var direction = blockEntity.getBlockState().getValue(MirrorBlock.FACING);

		var texture = getTexture(blockEntity.getBlockPos());
		if (blockEntity.shouldRedraw()) {
			var mc = Minecraft.getInstance();
			var player = mc.player;

			var target = new TextureTarget(RESOLUTION, RESOLUTION, true, Util.getPlatform() == Util.OS.OSX);

			target.bindWrite(true);

			var buffer = RENDER_BUFFERS.bufferSource();
			var renderer = mc.getEntityRenderDispatcher().getRenderer(player);

			var offset = Vec3.atCenterOf(blockEntity.getBlockPos())
					.subtract(direction.getStepX() * 0.5, 0, direction.getStepZ() * 0.5).subtract(player.position());

			// Render mirror player to buffer
			var playerPoseStack = new PoseStack();
			playerPoseStack.pushPose();
			playerPoseStack.mulPose(new Quaternion(0, -direction.toYRot(), 0, true));
			playerPoseStack.translate(-offset.x, offset.y, offset.z);
			playerPoseStack.scale(1, -1, -1);
			renderer.render(player, player.getYRot(), partialTicks, playerPoseStack, buffer, combinedLightIn);
			playerPoseStack.popPose();

			buffer.endBatch();

			// Upload buffer to image
			RenderSystem.bindTexture(target.getColorTextureId());

			var pixels = texture.getPixels();
			pixels.downloadTexture(0, false);
			pixels.flipY();
			texture.upload();

			target.destroyBuffers();
			mc.getMainRenderTarget().bindWrite(true);
		}

		// Render dynamic image to screen
		var vertexConsumer = bufferIn.getBuffer(RenderType.entityCutout(location(blockEntity.getBlockPos())));

		poseStack.pushPose();
		poseStack.translate(0.5, 0, 0.5);
		poseStack.mulPose(new Quaternion(0, -direction.toYRot(), 0, true));
		poseStack.translate(-0.5, 0, -0.5);

		var matrix = poseStack.last().pose();
		var normal = poseStack.last().normal();

		vertexConsumer.vertex(matrix, 0, 0, DEPTH_OFFSET).color(0xffffffff).uv(0, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 0, 1).endVertex();
		vertexConsumer.vertex(matrix, 1, 0, DEPTH_OFFSET).color(0xffffffff).uv(1, 0)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 0, 1).endVertex();
		vertexConsumer.vertex(matrix, 1, 1, DEPTH_OFFSET).color(0xffffffff).uv(1, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 0, 1).endVertex();
		vertexConsumer.vertex(matrix, 0, 1, DEPTH_OFFSET).color(0xffffffff).uv(0, 1)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 0, 1).endVertex();

		poseStack.popPose();
	}

	private static class RenderInfo {
		private DynamicTexture texture;
		private long timestamp;
	}
}
