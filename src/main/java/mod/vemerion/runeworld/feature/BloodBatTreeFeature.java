package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class BloodBatTreeFeature extends Feature<NoneFeatureConfiguration> {

	private static final BlockState LOG = Blocks.OAK_LOG.defaultBlockState();

	public BloodBatTreeFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		if (!reader.getBlockState(pos.below()).canOcclude())
			return false;

		double slantChance = 0;
		double branchChance = 0;
		int height = rand.nextInt(3) + 6;
		for (int i = 0; i < height; i++) {
			reader.setBlock(pos, LOG, 2);
			pos = pos.above();

			if (rand.nextDouble() < slantChance) {
				slantChance = 0;
				pos = pos.relative(Direction.from2DDataValue(rand.nextInt(4)));
			} else {
				slantChance += 0.2;
			}

			if (rand.nextDouble() < branchChance && i < height - 2) {
				branchChance = 0;
				createBranch(reader, rand, pos);
			} else {
				branchChance += 0.13;
			}
		}

		List<BlockPos> batLocations = createCrown(reader, rand, pos);

		spawnBats(reader, rand, batLocations);

		return true;
	}

	private void spawnBats(WorldGenLevel reader, Random rand, List<BlockPos> batLocations) {
		for (int i = 0; i < 4 + rand.nextInt(5); i++) {
			if (batLocations.isEmpty())
				break;
			BlockPos pos = batLocations.remove(rand.nextInt(batLocations.size()));
			if (BloodBatEntity.isValidLedgePos(reader, pos, null)) {
				BloodBatEntity bat = ModEntities.BLOOD_BAT.get().create(reader.getLevel());
				Vec3 position = Vec3.atBottomCenterOf(pos).add(0, -1.75, 0);
				bat.absMoveTo(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
				bat.startHanging(position);
				reader.addFreshEntity(bat);
			}
		}
	}

	private List<BlockPos> createCrown(WorldGenLevel reader, Random rand, BlockPos pos) {
		List<BlockPos> ps = new ArrayList<>();
		BlockPos start = new BlockPos(pos);
		for (int i = 0; i < 4; i++) {
			Direction d = Direction.from2DDataValue(i);
			BlockState branch = LOG.setValue(RotatedPillarBlock.AXIS, d.getAxis());
			pos = start.relative(d);
			double slantChance = 0;
			for (int j = 0; j < 5 + rand.nextInt(5); j++) {
				reader.setBlock(pos, branch, 2);
				ps.add(pos);
				if (rand.nextDouble() < 0.3) {
					createBranch(reader, rand, pos, rand.nextBoolean() ? d.getClockWise() : d.getCounterClockWise(),
							2 + rand.nextInt(3));
				}

				pos = pos.relative(d);
				if (rand.nextDouble() < slantChance) {
					slantChance = 0.2;
					pos = pos.offset(0, 1, 0);
				} else {
					slantChance += 0.3;
				}
			}
		}
		return ps;
	}

	private void createBranch(WorldGenLevel reader, Random rand, BlockPos pos) {
		createBranch(reader, rand, pos, Direction.from2DDataValue(rand.nextInt(4)), 2 + rand.nextInt(3));
	}

	private void createBranch(WorldGenLevel reader, Random rand, BlockPos pos, Direction direction, int length) {
		BlockState branch = LOG.setValue(RotatedPillarBlock.AXIS, direction.getAxis());
		boolean slant = false;

		pos = pos.relative(direction).relative(rand.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise());
		for (int i = 0; i < length; i++) {
			reader.setBlock(pos, branch, 2);
			if (!slant && rand.nextDouble() < 0.3) {
				slant = true;
				pos = pos.offset(0, rand.nextBoolean() ? 1 : -1, 0);
			}
			pos = pos.relative(direction);
		}
	}
}
