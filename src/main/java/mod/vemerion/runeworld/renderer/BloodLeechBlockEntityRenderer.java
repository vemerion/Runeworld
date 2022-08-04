package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FacingBlock;
import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodLeechModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class BloodLeechBlockEntityRenderer implements BlockEntityRenderer<BloodLeechBlockEntity> {
	private final BloodLeechModel MODEL;
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/blood_leech.png");

	private static final float RADIUS = 0.32f;

	public BloodLeechBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		MODEL = new BloodLeechModel(context.bakeLayer(ModLayerLocations.BLOOD_LEECH));
	}

	@Override
	public void render(BloodLeechBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction facing = Direction.UP;
		if (tileEntityIn.hasLevel()) {
			BlockState blockstate = tileEntityIn.getBlockState();
			facing = blockstate.getValue(FacingBlock.FACING);
		}

		float ticksExisted = tileEntityIn.getTickCount() + partialTicks;
		float rotation = tileEntityIn.getRot(partialTicks);
		float scale = Mth.cos(ticksExisted / 10) * 0.1f;
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		matrixStackIn.mulPose(facing.getRotation());
		matrixStackIn.mulPose(new Quaternion(0, rotation, 0, false));
		matrixStackIn.translate(RADIUS * -tileEntityIn.getDirection(), -0.5, 0);
		matrixStackIn.scale(1 - scale * 0.5f, 1 - scale * 0.5f, 1 + scale);
		matrixStackIn.scale(-1, -1, 1);
		matrixStackIn.translate(0, -1.5, 0);

		VertexConsumer renderBuffer = bufferIn.getBuffer(MODEL.renderType(TEXTURE));
		MODEL.animate(tileEntityIn, partialTicks, ticksExisted);
		MODEL.renderToBuffer(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);

		matrixStackIn.popPose();
	}
}
