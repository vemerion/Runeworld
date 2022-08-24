package mod.vemerion.runeworld.feature;

import mod.vemerion.runeworld.block.FleshEatingPlantBlock;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FleshEatingPlantFeature extends Feature<NoneFeatureConfiguration> {

	public FleshEatingPlantFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var level = context.level();
		var pos = context.origin();
		var rand = context.random();

		var block = ModBlocks.FLESH_EATING_PLANT_FLOWER.get();

		if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.DOWN))
			return false;

		level.setBlock(pos, block.defaultBlockState().setValue(FleshEatingPlantBlock.FACING, Direction.UP)
				.setValue(FleshEatingPlantBlock.BASE, true), 2);

		for (int i = 0; i < rand.nextInt(25, 100); i++) {
			pos = block.expand(level.getBlockState(pos), level, pos);
		}

		return true;
	}
}
