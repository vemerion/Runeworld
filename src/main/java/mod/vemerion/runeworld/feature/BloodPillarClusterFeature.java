package mod.vemerion.runeworld.feature;

import java.util.Random;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class BloodPillarClusterFeature extends Feature<NoneFeatureConfiguration> {

	public BloodPillarClusterFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		boolean generated = false;
		for (int i = 0; i < rand.nextInt(5) + 5; i++) {
			BlockPos p = pos.offset(rand.nextInt(10) - 5, 0, rand.nextInt(10) - 5);
			p = findGround(reader, p);
			if (!reader.getBlockState(p.below()).canOcclude() && !BloodPillarBlock.isPillar(reader, p.below()))
				continue;
			if (BloodPillarBlock.generatePillar(reader, rand, p)) {
				generated = true;
				if (rand.nextDouble() < 0.1)
					spawnBloodMonkey(reader, rand, p);
			}
		}
		return generated;
	}

	private void spawnBloodMonkey(WorldGenLevel reader, Random rand, BlockPos p) {
		while (BloodPillarBlock.isPillar(reader, p))
			p = p.above();
		BloodMonkeyEntity monkey = ModEntities.BLOOD_MONKEY.get().create(reader.getLevel());
		Vec3 position = Vec3.atBottomCenterOf(p);
		monkey.absMoveTo(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
		reader.addFreshEntity(monkey);
	}

	private BlockPos findGround(WorldGenLevel reader, BlockPos p) {
		for (int i = 0; i < 4; i++) {
			if (reader.getBlockState(p.below(i)).canOcclude())
				return p.below(i - 1);
			if (reader.getBlockState(p.above(i)).canOcclude())
				return p.above(i + 1);
		}
		return p;
	}
}
