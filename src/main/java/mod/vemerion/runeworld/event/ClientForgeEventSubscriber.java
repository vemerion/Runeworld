package mod.vemerion.runeworld.event;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.dimensionrenderer.BloodRenderer;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.init.ModDimensions;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.item.DislocatorItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.FogColors event) {
		if (event.getInfo().getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD)) {
			event.setBlue(0.15f);
			event.setGreen(0.15f);
			event.setRed(0.7f);
		}
	}

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.FogDensity event) {
		if (event.getInfo().getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD)) {
			event.setDensity(0.35f);
			event.setCanceled(true);
		}
	}

	// Render rune portal texture over the screen when player in portal, similar to
	// vanilla nether portal
	@SubscribeEvent
	public static void renderPortalOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.ALL)
			return;

		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		World world = mc.world;
		BlockState state = world.getBlockState(player.getPosition());
		if (!(state.getBlock() instanceof RunePortalBlock))
			return;

		RunePortalBlock portal = (RunePortalBlock) state.getBlock();
		List<BakedQuad> quads = mc.getBlockRendererDispatcher().getModelForState(state).getQuads(state, null,
				world.getRandom(), EmptyModelData.INSTANCE);
		if (quads.isEmpty())
			return;

		TextureAtlasSprite sprite = quads.get(0).getSprite();
		RenderSystem.disableAlphaTest();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();
		RenderSystem.color4f(portal.red / 255f, portal.green / 255f, portal.blue / 255f, 1);
		sprite.getAtlasTexture().bindTexture();

		float minU = sprite.getMinU();
		float minV = sprite.getMinV();
		float maxU = sprite.getMaxU();
		float maxV = sprite.getMaxV();
		int height = event.getWindow().getScaledHeight();
		int width = event.getWindow().getScaledWidth();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0, height, -90).tex(minU, maxV).endVertex();
		bufferbuilder.pos(width, height, -90).tex(maxU, maxV).endVertex();
		bufferbuilder.pos(width, 0, -90).tex(maxU, minV).endVertex();
		bufferbuilder.pos(0, 0, -90).tex(minU, minV).endVertex();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1, 1, 1, 1);
	}

	@SubscribeEvent
	public static void loadDimensionRenderInfo(WorldEvent.Load event) {
		if (event.getWorld() instanceof ClientWorld) {
			ClientWorld world = (ClientWorld) event.getWorld();
			if (world.getDimensionKey().equals(ModDimensions.BLOOD)) {
				world.func_239132_a_().setSkyRenderHandler(new BloodRenderer.SkyRenderer());
			}
		}
	}

	@SubscribeEvent
	public static void unloadDimensionRenderInfo(WorldEvent.Unload event) {
		if (event.getWorld() instanceof ClientWorld) {
			ClientWorld world = (ClientWorld) event.getWorld();

			if (world.getDimensionKey().equals(ModDimensions.BLOOD)) {
				world.func_239132_a_().setSkyRenderHandler(null);
			}
		}
	}

	@SubscribeEvent
	public static void dislocatorAnimation(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		if (item instanceof DislocatorItem && player.getActiveItemStack().equals(stack)) {
			event.setCanceled(true);
			MatrixStack matrix = event.getMatrixStack();
			float partialTicks = event.getPartialTicks();
			float count = player.getItemInUseMaxCount();
			float time = count + partialTicks;
			float progress = count / item.getUseDuration(stack);
			HandSide side = event.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
					: player.getPrimaryHand().opposite();
			TransformType transform = side == HandSide.RIGHT ? TransformType.FIRST_PERSON_RIGHT_HAND
					: TransformType.FIRST_PERSON_LEFT_HAND;
			matrix.push();
			matrix.translate(side == HandSide.RIGHT ? 0.7 : -0.7, -0.4, -0.7);
			matrix.rotate(new Quaternion(0, time * 360 * progress * 2 / 20, 0, true));
			Minecraft.getInstance().getItemRenderer().renderItem(player, stack, transform, false,
					event.getMatrixStack(), event.getBuffers(), player.world, event.getLight(),
					OverlayTexture.NO_OVERLAY);
			matrix.pop();
		}
	}
}
