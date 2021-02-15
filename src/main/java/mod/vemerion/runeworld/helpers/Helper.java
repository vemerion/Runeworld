package mod.vemerion.runeworld.helpers;

import java.util.Random;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class Helper {

	public static int color(int r, int g, int b, int a) {
		a = (a << 24) & 0xFF000000;
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b &= 0x000000FF;

		return a | r | g | b;
	}
	
	public static BlockPos[] offsets(Direction dir) {
		return new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, -1, 0), BlockPos.ZERO.offset(dir, 1),
				BlockPos.ZERO.offset(dir, -1) };
	}
	
	public static float soundPitch(Random rand) {
		return 0.8f + rand.nextFloat() * 0.4f;
	}
}
