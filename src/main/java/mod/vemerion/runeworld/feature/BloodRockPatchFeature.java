package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BloodRockPatchFeature extends Feature<NoneFeatureConfiguration> {
	private static final BlockState ROCK = ModBlocks.BLOOD_ROCK.defaultBlockState();

	public BloodRockPatchFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		pos = pos.below();
		List<BlockPos> positions = new ArrayList<>();
		positions.add(pos);
		for (int i = 0; i < rand.nextInt(200) + 200; i++) {
			if (positions.size() <= i)
				break;
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					if (Math.abs(x + z) == 1 && rand.nextDouble() < 0.5) {
						BlockPos p = positions.get(i).offset(x, 0, z);
						p = findGround(reader, p).below();
						positions.add(p);

						if (rand.nextDouble() < 0.1)
							positions.add(p.above());
					}
				}
			}
		}

		if (positions.size() < 10) // Too small patch
			return false;

		for (BlockPos p : positions) {
			reader.setBlock(p, ROCK, 2);
		}

		return true;
	}

	private BlockPos findGround(WorldGenLevel reader, BlockPos p) {
		for (int i = 0; i < 5; i++) {
			if (reader.getBlockState(p.below(i)).canOcclude())
				return p.below(i - 1);
			if (reader.getBlockState(p.above(i)).canOcclude())
				return p.above(i + 1);
		}
		return p;
	}

}
