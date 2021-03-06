package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireRitual {

	private static final Block RITUAL = ModBlocks.FIRE_RITUAL_STONE;
	private static final int RITUAL_SIZE = 5 * 4;

	private Set<BlockPos> positions;

	private FireRitual(Set<BlockPos> positions) {
		this.positions = positions;
	}

	public void performRitual(World world, BlockPos pos) {
		if (!positions.contains(pos))
			return;

		for (int i = 0; i < 4; i++) {
			Direction d = Direction.byHorizontalIndex(i);
			BlockPos p = pos.offset(d);
			if (world.getBlockState(p).getBlock() == ModBlocks.BLOOD_LEECH) {
				world.setBlockState(p, Blocks.AIR.getDefaultState());
				Vector3d itemPos = Vector3d.copyCentered(p);
				ItemEntity grilledLeech = new ItemEntity(world, itemPos.x, itemPos.y, itemPos.z,
						ModItems.GRILLED_BLOOD_LEECH.getDefaultInstance());
				world.addEntity(grilledLeech);
				world.playSound(null, p, ModSounds.SIZZLE, SoundCategory.BLOCKS, 1, Helper.soundPitch(world.rand));
			}
		}
	}

	public static Optional<FireRitual> createRitual(World world, BlockPos pos) {
		if (!isRitualBlock(world, pos))
			return Optional.empty();
		Set<BlockPos> positions = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		worklist.add(pos);

		while (!worklist.isEmpty()) {
			BlockPos p = worklist.remove(0);
			if (positions.size() > RITUAL_SIZE || countNearbyRitual(world, p) != 2)
				return Optional.empty();
			positions.add(p);

			for (Direction d : Direction.values()) {
				BlockPos nearby = p.offset(d);
				if (isRitualBlock(world, nearby) && !positions.contains(nearby))
					worklist.add(nearby);

			}
		}

		if (positions.size() != RITUAL_SIZE)
			return Optional.empty();

		return Optional.of(new FireRitual(positions));
	}

	private static int countNearbyRitual(World world, BlockPos pos) {
		int count = 0;

		for (int i = 0; i < 4; i++) {
			Direction d = Direction.byHorizontalIndex(i);
			if (isRitualBlock(world, pos.offset(d)))
				count++;
		}

		return count;
	}

	private static boolean isRitualBlock(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == RITUAL;
	}

}
