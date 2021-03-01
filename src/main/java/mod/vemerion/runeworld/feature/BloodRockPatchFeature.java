package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BloodRockPatchFeature extends Feature<NoFeatureConfig> {
	private static final BlockState ROCK = ModBlocks.BLOOD_ROCK.getDefaultState();

	public BloodRockPatchFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		pos = pos.down();
		List<BlockPos> positions = new ArrayList<>();
		positions.add(pos);
		for (int i = 0; i < rand.nextInt(200) + 200; i++) {
			if (positions.size() <= i)
				break;
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					if (Math.abs(x + z) == 1 && rand.nextDouble() < 0.5) {
						BlockPos p = positions.get(i).add(x, 0, z);
						p = findGround(reader, p).down();
						positions.add(p);

						if (rand.nextDouble() < 0.1)
							positions.add(p.up());
					}
				}
			}
		}

		if (positions.size() < 10) // Too small patch
			return false;

		for (BlockPos p : positions) {
			reader.setBlockState(p, ROCK, 2);
		}

		return true;
	}

	private BlockPos findGround(ISeedReader reader, BlockPos p) {
		for (int i = 0; i < 5; i++) {
			if (reader.getBlockState(p.down(i)).isSolid())
				return p.down(i - 1);
			if (reader.getBlockState(p.up(i)).isSolid())
				return p.up(i + 1);
		}
		return p;
	}

}
