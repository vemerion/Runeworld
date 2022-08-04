package mod.vemerion.runeworld.renderer;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.vemerion.runeworld.entity.FireElementalProjectileEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.util.Mth;

public class FireElementalProjectileRenderer extends ThrownItemRenderer<FireElementalProjectileEntity> {

	private static final float SCALE = 4;

	public FireElementalProjectileRenderer(EntityRendererProvider.Context context) {
		super(context, 1, false);
	}

	@Override
	public void render(FireElementalProjectileEntity entityIn, float entityYaw, float partialTicks,
			PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		Random rand = new Random(0);
		matrixStackIn.pushPose();
		shake(matrixStackIn, rand, entityIn.tickCount + partialTicks);
		matrixStackIn.translate(0, -0.25, 0);
		matrixStackIn.scale(SCALE, SCALE, SCALE);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.popPose();
	}

	private void shake(PoseStack matrix, Random rand, float ageInTicks) {
		matrix.translate(randPos(rand, ageInTicks), randPos(rand, ageInTicks), randPos(rand, ageInTicks));
	}

	private double randPos(Random rand, float ageInTicks) {
		return Mth.cos(ageInTicks + Helper.toRad(rand.nextDouble() * 360)) * 0.01;
	}
}
