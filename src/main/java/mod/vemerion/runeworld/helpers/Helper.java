package mod.vemerion.runeworld.helpers;

import java.util.Random;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Helper {

	public static int color(int r, int g, int b, int a) {
		a = (a << 24) & 0xFF000000;
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b &= 0x000000FF;

		return a | r | g | b;
	}

	public static BlockPos[] offsets(Direction dir) {
		return new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, -1, 0), BlockPos.ZERO.relative(dir, 1),
				BlockPos.ZERO.relative(dir, -1) };
	}

	public static float soundPitch(Random rand) {
		return 0.8f + rand.nextFloat() * 0.4f;
	}

	public static Vec3 randomInEntity(Entity entity, Random rand) {
		float height = entity.getBbHeight();
		float width = entity.getBbWidth();
		return entity.position().add((rand.nextDouble() - 0.5) * width, rand.nextDouble() * height,
				(rand.nextDouble() - 0.5) * width);
	}
	
	public static float toRad(double deg) {
		return (float) Math.toRadians(deg);
	}
}
