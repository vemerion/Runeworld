package mod.vemerion.runeworld.event;

import java.util.List;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Quaternion;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.item.DislocatorItem;
import mod.vemerion.runeworld.item.MonkeyPawItem;
import mod.vemerion.runeworld.item.SlingshotItem;
import mod.vemerion.runeworld.renderer.MirrorBlockEntityRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	private static boolean inBlood(Camera camera, Level level) {
		var state = camera.getBlockAtCamera().getFluidState();
		return state.getType().isSame(ModFluids.BLOOD.get())
				&& camera.getPosition().y < camera.getBlockPosition().getY()
						+ state.getHeight(level, camera.getBlockPosition());
	}

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.FogColors event) {
		if (inBlood(event.getCamera(), event.getRenderer().getMinecraft().level)) {
			event.setBlue(0.15f);
			event.setGreen(0.15f);
			event.setRed(0.7f);
		}
	}

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.RenderFogEvent event) {
		if (inBlood(event.getCamera(), event.getRenderer().getMinecraft().level)) {
			float fogStart = -8;
			float fogEnd = 10f;
			RenderSystem.setShaderFogStart(fogStart);
			RenderSystem.setShaderFogEnd(fogEnd);
			RenderSystem.setShaderFogShape(FogShape.SPHERE);
			event.setNearPlaneDistance(fogStart);
			event.setFarPlaneDistance(fogEnd);
		}
	}

	// Render rune portal texture over the screen when player in portal, similar to
	// vanilla nether portal
	@SubscribeEvent
	public static void renderPortalOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.ALL)
			return;

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		Level world = mc.level;
		BlockState state = world.getBlockState(player.blockPosition());
		if (!(state.getBlock() instanceof RunePortalBlock))
			return;

		RunePortalBlock portal = (RunePortalBlock) state.getBlock();
		List<BakedQuad> quads = mc.getBlockRenderer().getBlockModel(state).getQuads(state, null, world.getRandom(),
				EmptyModelData.INSTANCE);
		if (quads.isEmpty())
			return;

		TextureAtlasSprite sprite = quads.get(0).getSprite();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(portal.red / 255f, portal.green / 255f, portal.blue / 255f, 0.8f);
		sprite.atlas().bind();

		float minU = sprite.getU0();
		float minV = sprite.getV0();
		float maxU = sprite.getU1();
		float maxV = sprite.getV1();
		int height = event.getWindow().getGuiScaledHeight();
		int width = event.getWindow().getGuiScaledWidth();
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(0, height, -90).uv(minU, maxV).endVertex();
		bufferbuilder.vertex(width, height, -90).uv(maxU, maxV).endVertex();
		bufferbuilder.vertex(width, 0, -90).uv(maxU, minV).endVertex();
		bufferbuilder.vertex(0, 0, -90).uv(minU, minV).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	@SubscribeEvent
	public static void itemAnimations(RenderHandEvent event) {
		var mc = Minecraft.getInstance();
		var player = mc.player;
		var stack = event.getItemStack();
		var item = stack.getItem();
		if (player.getUseItem().equals(stack)) {
			if (!(item instanceof DislocatorItem) && !(item instanceof MonkeyPawItem)
					&& !(item instanceof SlingshotItem))
				return;

			event.setCanceled(true);
			var poseStack = event.getPoseStack();
			float partialTicks = event.getPartialTicks();
			float count = player.getTicksUsingItem();
			float time = count + partialTicks;
			float progress = time / item.getUseDuration(stack);
			var side = event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm()
					: player.getMainArm().getOpposite();
			var transform = side == HumanoidArm.RIGHT ? TransformType.FIRST_PERSON_RIGHT_HAND
					: TransformType.FIRST_PERSON_LEFT_HAND;

			if (item instanceof DislocatorItem) {
				poseStack.pushPose();
				poseStack.translate(side == HumanoidArm.RIGHT ? 0.7 : -0.7, -0.4, -0.7);
				poseStack.mulPose(new Quaternion(0, time * 360 * progress * 2 / 20, 0, true));
				mc.getItemRenderer().renderStatic(player, stack, transform, false, event.getPoseStack(),
						event.getMultiBufferSource(), player.level, event.getPackedLight(), OverlayTexture.NO_OVERLAY,
						0);
				poseStack.popPose();
			} else if (item instanceof MonkeyPawItem) {
				poseStack.pushPose();
				poseStack.translate(side == HumanoidArm.RIGHT ? 0.55 : -0.55, -0.4, -0.7);
				float size = Mth.sin(time * 0.7f) * 0.4f + 1;
				poseStack.scale(size, size, size);
				mc.getItemRenderer().renderStatic(player, stack, transform, false, event.getPoseStack(),
						event.getMultiBufferSource(), player.level, event.getPackedLight(), OverlayTexture.NO_OVERLAY,
						0);
				poseStack.popPose();
			} else if (item instanceof SlingshotItem) {
				poseStack.pushPose();
				poseStack.translate(side == HumanoidArm.RIGHT ? 0.55 : -0.55, -0.4, -1.0 + progress * 0.5);
				poseStack.mulPose(new Quaternion(0, -70, -20, true));
				mc.getItemRenderer().renderStatic(player, stack, transform, false, event.getPoseStack(),
						event.getMultiBufferSource(), player.level, event.getPackedLight(), OverlayTexture.NO_OVERLAY,
						0);
				poseStack.popPose();
			}
		}
	}
	
	@SubscribeEvent
	public static void tickMirrors(ClientTickEvent event) {
		MirrorBlockEntityRenderer.tick();
	}
}
