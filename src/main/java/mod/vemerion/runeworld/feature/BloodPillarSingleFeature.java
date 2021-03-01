package mod.vemerion.runeworld.feature;

import java.util.Random;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BloodPillarSingleFeature extends Feature<NoFeatureConfig> {

	public BloodPillarSingleFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).isSolid())
			return false;
		return BloodPillarBlock.generatePillar(reader, rand, pos);
	}
}
