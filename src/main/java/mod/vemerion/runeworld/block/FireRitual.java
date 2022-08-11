package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;

public class FireRitual {

	private static final Block RITUAL = ModBlocks.FIRE_RITUAL_STONE;
	private static final int RITUAL_SIZE = 5 * 4;

	private Set<BlockPos> positions;

	private FireRitual(Set<BlockPos> positions) {
		this.positions = positions;
	}

	public void performRitual(Level world, BlockPos pos) {
		if (!positions.contains(pos))
			return;

		// Blood Leech
		for (int i = 0; i < 4; i++) {
			Direction d = Direction.from2DDataValue(i);
			BlockPos p = pos.relative(d);
			if (world.getBlockState(p).getBlock() == ModBlocks.BLOOD_LEECH) {
				world.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
				Vec3 itemPos = Vec3.atCenterOf(p);
				ItemEntity grilledLeech = new ItemEntity(world, itemPos.x, itemPos.y, itemPos.z,
						ModItems.GRILLED_BLOOD_LEECH.getDefaultInstance());
				world.addFreshEntity(grilledLeech);
				world.playSound(null, p, ModSounds.SIZZLE.get(), SoundSource.BLOCKS, 1, Helper.soundPitch(world.random));
			}
		}

		summonFireElemental(world, pos);
	}

	private void summonFireElemental(Level world, BlockPos pos) {
		if (!world.isStateAtPosition(pos, s -> s.getValue(FireRitualStoneBlock.BLOODIED)))
			return;

		int bloodied = 0;
		for (BlockPos p : positions)
			if (world.isStateAtPosition(p, s -> s.getValue(FireRitualStoneBlock.BLOODIED)))
				bloodied++;

		if (bloodied > 3) {
			for (BlockPos p : positions)
				world.destroyBlock(p, false);
			world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5, BlockInteraction.DESTROY);

			FireElementalEntity elemental = new FireElementalEntity(ModEntities.FIRE_ELEMENTAL.get(), world);
			elemental.setPos(pos.getX(), pos.getY(), pos.getZ());
			world.addFreshEntity(elemental);
		}
	}

	public static Optional<FireRitual> createRitual(Level world, BlockPos pos) {
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
				BlockPos nearby = p.relative(d);
				if (isRitualBlock(world, nearby) && !positions.contains(nearby))
					worklist.add(nearby);

			}
		}

		if (positions.size() != RITUAL_SIZE)
			return Optional.empty();

		return Optional.of(new FireRitual(positions));
	}

	private static int countNearbyRitual(Level world, BlockPos pos) {
		int count = 0;

		for (int i = 0; i < 4; i++) {
			Direction d = Direction.from2DDataValue(i);
			if (isRitualBlock(world, pos.relative(d)))
				count++;
		}

		return count;
	}

	private static boolean isRitualBlock(Level world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == RITUAL;
	}

}
