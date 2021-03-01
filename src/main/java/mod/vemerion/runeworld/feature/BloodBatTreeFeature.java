package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BloodBatTreeFeature extends Feature<NoFeatureConfig> {

	private static final BlockState LOG = Blocks.OAK_LOG.getDefaultState();

	public BloodBatTreeFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).isSolid())
			return false;

		double slantChance = 0;
		double branchChance = 0;
		int height = rand.nextInt(3) + 6;
		for (int i = 0; i < height; i++) {
			reader.setBlockState(pos, LOG, 2);
			pos = pos.up();

			if (rand.nextDouble() < slantChance) {
				slantChance = 0;
				pos = pos.offset(Direction.byHorizontalIndex(rand.nextInt(4)));
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

	private void spawnBats(ISeedReader reader, Random rand, List<BlockPos> batLocations) {
		for (int i = 0; i < 4 + rand.nextInt(5); i++) {
			if (batLocations.isEmpty())
				break;
			BlockPos pos = batLocations.remove(rand.nextInt(batLocations.size()));
			if (BloodBatEntity.isValidLedgePos(reader, pos, null)) {
				BloodBatEntity bat = ModEntities.BLOOD_BAT.create(reader.getWorld());
				Vector3d position = Vector3d.copyCenteredHorizontally(pos).add(0, -1.75, 0);
				bat.setPositionAndRotation(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
				bat.startHanging(position);
				reader.addEntity(bat);
			}
		}
	}

	private List<BlockPos> createCrown(ISeedReader reader, Random rand, BlockPos pos) {
		List<BlockPos> ps = new ArrayList<>();
		BlockPos start = new BlockPos(pos);
		for (int i = 0; i < 4; i++) {
			Direction d = Direction.byHorizontalIndex(i);
			BlockState branch = LOG.with(RotatedPillarBlock.AXIS, d.getAxis());
			pos = start.offset(d);
			double slantChance = 0;
			for (int j = 0; j < 5 + rand.nextInt(5); j++) {
				reader.setBlockState(pos, branch, 2);
				ps.add(pos);
				if (rand.nextDouble() < 0.3) {
					createBranch(reader, rand, pos, rand.nextBoolean() ? d.rotateY() : d.rotateYCCW(),
							2 + rand.nextInt(3));
				}

				pos = pos.offset(d);
				if (rand.nextDouble() < slantChance) {
					slantChance = 0.2;
					pos = pos.add(0, 1, 0);
				} else {
					slantChance += 0.3;
				}
			}
		}
		return ps;
	}

	private void createBranch(ISeedReader reader, Random rand, BlockPos pos) {
		createBranch(reader, rand, pos, Direction.byHorizontalIndex(rand.nextInt(4)), 2 + rand.nextInt(3));
	}

	private void createBranch(ISeedReader reader, Random rand, BlockPos pos, Direction direction, int length) {
		BlockState branch = LOG.with(RotatedPillarBlock.AXIS, direction.getAxis());
		boolean slant = false;

		pos = pos.offset(direction).offset(rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW());
		for (int i = 0; i < length; i++) {
			reader.setBlockState(pos, branch, 2);
			if (!slant && rand.nextDouble() < 0.3) {
				slant = true;
				pos = pos.add(0, rand.nextBoolean() ? 1 : -1, 0);
			}
			pos = pos.offset(direction);
		}
	}
}
