package mod.vemerion.runeworld.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.runeworld.tileentity.BloodLeechTileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class BloodLeechModel extends Model {
	public ModelRenderer body;
	public ModelRenderer eye1;
	public ModelRenderer eye2;

	public BloodLeechModel() {
		super(RenderType::getEntityCutout);
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.body.addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 7.0F, 0.0F, 0.0F, 0.0F);
		this.eye1 = new ModelRenderer(this, 0, 0);
		this.eye1.setRotationPoint(-0.5F, -1.0F, -2.0F);
		this.eye1.addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(eye1, 0.3490658503988659F, 0.7428121536172364F, 0.0F);
		this.eye2 = new ModelRenderer(this, 0, 0);
		this.eye2.setRotationPoint(0.4F, -1.0F, -2.0F);
		this.eye2.addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(eye2, 0.35185837453889574F, -0.6981317007977318F, 0.0F);
		this.body.addChild(this.eye1);
		this.body.addChild(this.eye2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	public void animate(BloodLeechTileEntity leech, float partialTicks, float ticksExisted) {
		eye1.rotateAngleX = (float) (Math.toRadians(20) + MathHelper.cos(ticksExisted / 20) * Math.toRadians(10));
		eye2.rotateAngleX = (float) (Math.toRadians(20) + MathHelper.cos(ticksExisted / 20) * Math.toRadians(10));
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
