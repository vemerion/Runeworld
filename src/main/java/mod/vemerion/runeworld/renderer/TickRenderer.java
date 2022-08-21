package mod.vemerion.runeworld.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.entity.TickEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.TickModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class TickRenderer extends MobRenderer<TickEntity, TickModel> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/tick.png");

	public TickRenderer(EntityRendererProvider.Context context) {
		super(context, new TickModel(context.bakeLayer(ModLayerLocations.TICK)), 0.3f);
	}

	private boolean isAdjacentSturdy(TickEntity entity, Direction direction) {
		var p = entity.blockPosition().relative(direction);
		return entity.level.getBlockState(p).isFaceSturdy(entity.level, p, direction.getOpposite());
	}

	private Direction getHorizontalSturdy(TickEntity entity) {
		for (var d : Direction.Plane.HORIZONTAL) {
			if (isAdjacentSturdy(entity, d))
				return d;
		}
		return null;
	}

	@Override
	public void render(TickEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		if (!entity.isAlive() && entity.isExploding())
			return;
		
		matrixStackIn.pushPose();

		matrixStackIn.translate(0, 0.5, 0);
		if (!(entity.getVehicle() instanceof BloodMonkeyEntity)) {
			if (isAdjacentSturdy(entity, Direction.UP)) {
				matrixStackIn.mulPose(new Quaternion(180, 0, 0, true));
			} else {
				var rotDir = getHorizontalSturdy(entity);
				if (rotDir != null) {
					var rot = new Vector3f(Vec3.atLowerCornerOf(rotDir.getNormal()).yRot(Helper.toRad(90)))
							.rotationDegrees(-90);
					matrixStackIn.mulPose(rot);
				}
			}
		}
		matrixStackIn.translate(0, -0.5, 0);
		var size = entity.getSize(partialTicks);
		matrixStackIn.scale(size, size, size);
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(TickEntity entity) {
		return TEXTURE;
	}
}
