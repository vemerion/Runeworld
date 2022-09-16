package mod.vemerion.runeworld.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
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

	// Is v1 in front of v2 if v2 is looking in direction dir?
	public static boolean isInFrontOf(Vec3 v1, Vec3 v2, Vec3 dir, double tolerance) {
		return v1.subtract(v2).normalize().dot(dir) > tolerance;
	}

	public static List<Direction> shuffledDirections() {
		var directions = new ArrayList<Direction>();
		for (var direction : Direction.values()) {
			directions.add(direction);
		}
		Collections.shuffle(directions);
		return directions;
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

	public static void fillBelowStructure(WorldGenLevel level, StructureFeatureManager manager,
			ChunkGenerator generator, Random rand, BoundingBox box, ChunkPos chunkPos, PiecesContainer pieces,
			BlockState first, BlockState second) {
		var pos = new BlockPos.MutableBlockPos();
		BoundingBox boundingbox = pieces.calculateBoundingBox();
		int yStart = boundingbox.minY() - 1;

		for (int x = box.minX(); x <= box.maxX(); x++) {
			for (int z = box.minZ(); z <= box.maxZ(); z++) {
				pos.set(x, yStart + 1, z);
				if (!level.isEmptyBlock(pos) && boundingbox.isInside(pos) && pieces.isInsidePiece(pos)) {
					for (int y = yStart; y > level.getMinBuildHeight(); y--) {
						pos.setY(y);
						if (!level.isEmptyBlock(pos))
							break;

						level.setBlock(pos, y == yStart ? first : second, 2);
					}
				}
			}
		}
	}
}
