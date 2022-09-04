package mod.vemerion.runeworld.feature;

import mod.vemerion.runeworld.block.TopazBlock;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TopazFeature extends Feature<NoneFeatureConfiguration> {

	public TopazFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var level = context.level();
		var pos = context.origin();

		for (var dir : Helper.shuffledDirections()) {
			var p = pos.mutable();
			for (int i = 0; i < 10; i++) {
				p.move(dir);
				var state = level.getBlockState(p);
				if (state.is(ModBlocks.CHARRED_STONE.get())
						&& state.isFaceSturdy(level, p, dir.getOpposite())) {
					level.setBlock(p.move(dir.getOpposite()),
							ModBlocks.TOPAZ.get().defaultBlockState().setValue(TopazBlock.FACING, dir.getOpposite()),
							3);
					return true;
				}
			}
		}

		return false;
	}
}
