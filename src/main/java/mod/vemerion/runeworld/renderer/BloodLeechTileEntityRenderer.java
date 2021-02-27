package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FacingBlock;
import mod.vemerion.runeworld.model.BloodLeechModel;
import mod.vemerion.runeworld.tileentity.BloodLeechTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class BloodLeechTileEntityRenderer extends TileEntityRenderer<BloodLeechTileEntity> {
	private static final BloodLeechModel MODEL = new BloodLeechModel();
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/blood_leech.png");

	private static final float RADIUS = 0.32f;

	public BloodLeechTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(BloodLeechTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction facing = Direction.UP;
		if (tileEntityIn.hasWorld()) {
			BlockState blockstate = tileEntityIn.getBlockState();
			facing = blockstate.get(FacingBlock.FACING);
		}

		float ticksExisted = tileEntityIn.getTicksExisted() + partialTicks;
		float rotation = tileEntityIn.getRot(partialTicks);
		float scale = MathHelper.cos(ticksExisted / 10) * 0.1f;
		matrixStackIn.push();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		matrixStackIn.rotate(facing.getRotation());
		matrixStackIn.rotate(new Quaternion(0, rotation, 0, false));
		matrixStackIn.translate(RADIUS * -tileEntityIn.getDirection(), -0.5, 0);
		matrixStackIn.scale(1 - scale * 0.5f, 1 - scale * 0.5f, 1 + scale);
		matrixStackIn.scale(-1, -1, 1);
		matrixStackIn.translate(0, -1.5, 0);

		IVertexBuilder renderBuffer = bufferIn.getBuffer(MODEL.getRenderType(TEXTURE));
		MODEL.animate(tileEntityIn, partialTicks, ticksExisted);
		MODEL.render(matrixStackIn, renderBuffer, combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);

		matrixStackIn.pop();
	}
}
