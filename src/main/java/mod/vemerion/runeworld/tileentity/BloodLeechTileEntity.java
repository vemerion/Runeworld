package mod.vemerion.runeworld.tileentity;

import java.util.Random;

import mod.vemerion.runeworld.init.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BloodLeechTileEntity extends TileEntity implements ITickableTileEntity {

	private static final float SPEED = 0.001f;

	private int ticksExisted;
	private Random rand = new Random();
	private float offset;
	private int direction;

	public BloodLeechTileEntity() {
		super(ModTileEntities.BLOOD_LEECH);
		offset = (float) Math.toRadians(rand.nextDouble() * 360);
		direction = rand.nextBoolean() ? 1 : -1;
	}

	@Override
	public void tick() {
		if (!world.isRemote)
			return;

		ticksExisted++;
	}

	public float getRot(float partialTicks) {
		return direction * (float) -((ticksExisted + partialTicks) * Math.toRadians(360) * SPEED) + offset;
	}

	public int getTicksExisted() {
		return ticksExisted;
	}

	public int getDirection() {
		return direction;
	}
}
