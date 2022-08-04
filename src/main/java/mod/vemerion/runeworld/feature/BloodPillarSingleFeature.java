package mod.vemerion.runeworld.feature;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BloodPillarSingleFeature extends Feature<NoneFeatureConfiguration> {

	public BloodPillarSingleFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		if (!reader.getBlockState(pos.below()).canOcclude())
			return false;
		return BloodPillarBlock.generatePillar(reader, rand, pos);
	}
}
