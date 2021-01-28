package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BloodPoolFeature extends Feature<NoFeatureConfig> {
	private static final BlockState BLOOD = ModBlocks.BLOOD.getDefaultState();
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	private static final BlockState FLOWER = ModBlocks.BLOOD_FLOWER.getDefaultState();

	public BloodPoolFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);

		pos = pos.down();
		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> bloods = new HashSet<>();
		positions.add(pos);
		int count = 0;
		while (!positions.isEmpty() && count++ < 100) {
			pos = positions.remove(0);
			if (isSurrounded(reader, pos)) {
				bloods.add(pos);

				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (Math.abs(i + j) != 1)
							continue;

						BlockPos p = pos.add(i, 0, j);
						if (rand.nextDouble() < 0.6 && !bloods.contains(p)) {
							positions.add(p);
						}
					}
				}
			}
		}

		if (bloods.size() < 10) // Too small pool
			return false;

		for (BlockPos blood : bloods) { // Actually create blood pool
			BlockPos up = blood.up();
			if (reader.getBlockState(up).isSolid())
				continue;
			
			reader.setBlockState(blood, BLOOD, 2);
			if (rand.nextDouble() < 0.1)
				reader.setBlockState(up, FLOWER, 2);
			else
				reader.setBlockState(up, AIR, 2);
		}
		
		for (BlockPos blood : bloods) { // Make the pool deeper at certain parts
			if (shouldBeDeep(reader, rand, blood))
				reader.setBlockState(blood.down(), BLOOD, 2);
		}

		return true;
	}

	private boolean shouldBeDeep(ISeedReader reader, Random rand, BlockPos blood) {
		int limit = rand.nextBoolean() ? 1 : 2;
		for (int i = -limit; i <= limit; i++) {
			for (int j = -limit; j <= limit; j++) {
				if (reader.getBlockState(blood.add(i, 0, j)) != BLOOD)
					return false;
			}
		}
		return true;
	}

	private boolean isSurrounded(ISeedReader reader, BlockPos pos) {
		return validBorder(reader.getBlockState(pos.add(1, 0, 0)))
				&& validBorder(reader.getBlockState(pos.add(-1, 0, 0)))
				&& validBorder(reader.getBlockState(pos.add(0, 0, 1)))
				&& validBorder(reader.getBlockState(pos.add(0, 0, -1)));
	}

	private boolean validBorder(BlockState state) {
		return state.isSolid() || state == BLOOD;
	}

}
