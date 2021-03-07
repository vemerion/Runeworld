package mod.vemerion.runeworld.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.runeworld.entity.FireElementalProjectileEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.math.MathHelper;

public class FireElementalProjectileRenderer extends SpriteRenderer<FireElementalProjectileEntity> {

	private static final float SCALE = 4;

	public FireElementalProjectileRenderer(EntityRendererManager renderManagerIn, ItemRenderer itemRendererIn) {
		super(renderManagerIn, itemRendererIn);
	}

	@Override
	public void render(FireElementalProjectileEntity entityIn, float entityYaw, float partialTicks,
			MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		Random rand = new Random(0);
		matrixStackIn.push();
		shake(matrixStackIn, rand, entityIn.ticksExisted + partialTicks);
		matrixStackIn.translate(0, -0.25, 0);
		matrixStackIn.scale(SCALE, SCALE, SCALE);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}

	private void shake(MatrixStack matrix, Random rand, float ageInTicks) {
		matrix.translate(randPos(rand, ageInTicks), randPos(rand, ageInTicks), randPos(rand, ageInTicks));
	}

	private double randPos(Random rand, float ageInTicks) {
		return MathHelper.cos(ageInTicks + Helper.toRad(rand.nextDouble() * 360)) * 0.01;
	}
}
