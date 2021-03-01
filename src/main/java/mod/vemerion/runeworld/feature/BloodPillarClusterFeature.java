package mod.vemerion.runeworld.feature;

import java.util.Random;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BloodPillarClusterFeature extends Feature<NoFeatureConfig> {

	public BloodPillarClusterFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		boolean generated = false;
		for (int i = 0; i < rand.nextInt(5) + 5; i++) {
			BlockPos p = pos.add(rand.nextInt(10) - 5, 0, rand.nextInt(10) - 5);
			p = findGround(reader, p);
			if (!reader.getBlockState(p.down()).isSolid() && !BloodPillarBlock.isPillar(reader, p.down()))
				continue;
			if (BloodPillarBlock.generatePillar(reader, rand, p))
				generated = true;
		}
		return generated;
	}

	private BlockPos findGround(ISeedReader reader, BlockPos p) {
		for (int i = 0; i < 4; i++) {
			if (reader.getBlockState(p.down(i)).isSolid())
				return p.down(i - 1);
			if (reader.getBlockState(p.up(i)).isSolid())
				return p.up(i + 1);
		}
		return p;
	}
}
