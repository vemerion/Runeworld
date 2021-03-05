package mod.vemerion.runeworld.feature;

import java.util.Random;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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
			if (BloodPillarBlock.generatePillar(reader, rand, p)) {
				generated = true;
				if (rand.nextDouble() < 0.1)
					spawnBloodMonkey(reader, rand, p);
			}
		}
		return generated;
	}

	private void spawnBloodMonkey(ISeedReader reader, Random rand, BlockPos p) {
		while (BloodPillarBlock.isPillar(reader, p))
			p = p.up();
		BloodMonkeyEntity monkey = ModEntities.BLOOD_MONKEY.create(reader.getWorld());
		Vector3d position = Vector3d.copyCenteredHorizontally(p);
		monkey.setPositionAndRotation(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
		reader.addEntity(monkey);
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
