package mod.vemerion.runeworld.blockentity;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BloodLeechBlockEntity extends BlockEntity {

	private static final float SPEED = 0.001f;

	private int tickCount;
	private Random rand = new Random();
	private float offset;
	private int direction;

	public BloodLeechBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(ModBlockEntities.BLOOD_LEECH, pWorldPosition, pBlockState);
		offset = (float) Math.toRadians(rand.nextDouble() * 360);
		direction = rand.nextBoolean() ? 1 : -1;
	}

	public void tick() {
		if (!level.isClientSide)
			return;

		tickCount++;
	}

	public float getRot(float partialTicks) {
		return direction * (float) -((tickCount + partialTicks) * Math.toRadians(360) * SPEED) + offset;
	}

	public int getTickCount() {
		return tickCount;
	}

	public int getDirection() {
		return direction;
	}
}
