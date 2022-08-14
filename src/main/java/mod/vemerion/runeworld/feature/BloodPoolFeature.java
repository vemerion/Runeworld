package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.vemerion.runeworld.block.BloodLeechBlock;
import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BloodPoolFeature extends Feature<NoneFeatureConfiguration> {
	private static final BlockState BLOOD = ModBlocks.BLOOD.get().defaultBlockState();
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final BlockState FLOWER = ModBlocks.BLOOD_FLOWER.get().defaultBlockState();
	private static final BlockState ROCK = ModBlocks.BLOOD_ROCK.get().defaultBlockState();
	private static final BlockState LEECH = ModBlocks.BLOOD_LEECH.get().defaultBlockState().setValue(BloodLeechBlock.WATERLOGGED,
			true);

	public BloodPoolFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		pos = reader.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos);

		pos = pos.below();
		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> bloods = new HashSet<>();
		positions.add(pos);
		int count = 0;
		while (!positions.isEmpty() && count++ < 100) {
			pos = positions.remove(0);
			if (isSurrounded(reader, pos)) {
				bloods.add(pos);

				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (Math.abs(i + j) != 1)
							continue;

						BlockPos p = pos.offset(i, 0, j);
						if (rand.nextDouble() < 0.6 && !bloods.contains(p)) {
							positions.add(p);
						}
					}
				}
			}
		}

		if (bloods.size() < 10) // Too small pool
			return false;

		for (BlockPos blood : bloods) { // Actually create blood pool
			BlockPos up = blood.above();
			if (reader.getBlockState(up).canOcclude())
				continue;

			reader.setBlock(blood, BLOOD, 2);
			if (rand.nextDouble() < 0.1)
				reader.setBlock(up, FLOWER, 2);
			else
				reader.setBlock(up, AIR, 2);
		}

		for (BlockPos blood : bloods) {
			if (shouldBeDeep(reader, rand, blood))
				reader.setBlock(blood.below(), BLOOD, 2); // Make the pool deeper at certain parts
			surroundWithBloodRock(reader, rand, blood);
		}

		if (rand.nextDouble() < 0.3)
			generateBloodPillars(bloods, reader, rand);

		generateLeeches(bloods, reader, rand);

		return true;
	}

	private void generateLeeches(Set<BlockPos> bloods, WorldGenLevel reader, Random rand) {
		int count = rand.nextInt(3);
		if (count == 0)
			return;

		for (BlockPos pos : bloods) {
			if (count == 0)
				return;

			if (rand.nextDouble() < 0.01 && reader.getBlockState(pos).getBlock() == ModBlocks.BLOOD.get()) {
				for (Direction d : Direction.values()) {
					BlockState leech = LEECH.setValue(BloodLeechBlock.FACING, d);
					if (leech.canSurvive(reader, pos)) {
						reader.setBlock(pos, leech, 2);
						count--;
						break;
					}
				}
			}
		}
	}

	private void generateBloodPillars(Set<BlockPos> bloods, WorldGenLevel reader, Random rand) {
		for (BlockPos pos : bloods) {
			while (reader.getBlockState(pos.below()) == BLOOD)
				pos = pos.below();
			if (rand.nextDouble() < 0.1)
				BloodPillarBlock.generatePillar(reader, rand, pos);
		}
	}

	private void surroundWithBloodRock(WorldGenLevel reader, Random rand, BlockPos blood) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				BlockPos pos = blood.offset(i, 0, j);
				if (reader.getBlockState(blood.offset(i, 0, j)).canOcclude() && rand.nextDouble() < 0.5)
					reader.setBlock(pos, ROCK, 2);
			}
		}
	}

	private boolean shouldBeDeep(WorldGenLevel reader, Random rand, BlockPos blood) {
		if (reader.getBlockState(blood) != BLOOD)
			return false;

		int limit = rand.nextBoolean() ? 1 : 2;
		for (int i = -limit; i <= limit; i++) {
			for (int j = -limit; j <= limit; j++) {
				if (reader.getBlockState(blood.offset(i, 0, j)) != BLOOD)
					return false;
			}
		}
		return true;
	}

	private boolean isSurrounded(WorldGenLevel reader, BlockPos pos) {
		return validBorder(reader.getBlockState(pos.offset(1, 0, 0)))
				&& validBorder(reader.getBlockState(pos.offset(-1, 0, 0)))
				&& validBorder(reader.getBlockState(pos.offset(0, 0, 1)))
				&& validBorder(reader.getBlockState(pos.offset(0, 0, -1)));
	}

	private boolean validBorder(BlockState state) {
		return state.canOcclude() || state == BLOOD;
	}

}
