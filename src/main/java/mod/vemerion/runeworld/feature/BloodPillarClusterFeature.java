package mod.vemerion.runeworld.feature;

import java.util.Random;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
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
		pos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
		for (int i = 0; i < rand.nextInt(5) + 5; i++) {
			BlockPos p = pos.add(rand.nextInt(10) - 5, 0, rand.nextInt(10) - 5);
			p = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, p);
			if (!reader.getBlockState(p.down()).isSolid())
				continue;
			if (BloodPillarBlock.generatePillar(reader, rand, p))
				generated = true;
		}
		return generated;
	}
}
