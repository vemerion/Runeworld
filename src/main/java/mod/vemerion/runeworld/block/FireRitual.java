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

	private static final Block RITUAL = ModBlocks.FIRE_RITUAL_STONE.get();
	private static final int RITUAL_SIZE = 5 * 4;

	private Set<BlockPos> positions;

	private FireRitual(Set<BlockPos> positions) {
		this.positions = positions;
	}

	public void performRitual(Level level, BlockPos pos) {
		if (!positions.contains(pos))
			return;

		// Blood Leech
		for (int i = 0; i < 4; i++) {
			var d = Direction.from2DDataValue(i);
			var p = pos.relative(d);
			var block = level.getBlockState(p).getBlock();
			if (block == ModBlocks.BLOOD_LEECH.get()) {
				level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
				Vec3 itemPos = Vec3.atCenterOf(p);
				ItemEntity grilledLeech = new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z,
						ModItems.GRILLED_BLOOD_LEECH.get().getDefaultInstance());
				level.addFreshEntity(grilledLeech);
				level.playSound(null, p, ModSounds.SIZZLE.get(), SoundSource.BLOCKS, 1,
						Helper.soundPitch(level.random));
			} else if (block == ModBlocks.TOPAZ.get()) {
				level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
				var itemPos = Vec3.atCenterOf(p);
				var shard = new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z,
						ModItems.TOPAZ_SHARD.get().getDefaultInstance());
				level.addFreshEntity(shard);
			}
		}

		summonFireElemental(level, pos);
	}

	private void summonFireElemental(Level level, BlockPos pos) {
		if (!level.isStateAtPosition(pos, s -> s.getValue(FireRitualStoneBlock.BLOODIED)))
			return;

		int bloodied = 0;
		for (var p : positions)
			if (level.isStateAtPosition(p, s -> s.getValue(FireRitualStoneBlock.BLOODIED)))
				bloodied++;

		if (bloodied > 3) {
			for (var p : positions)
				level.destroyBlock(p, false);
			level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5, BlockInteraction.DESTROY);

			var elemental = new FireElementalEntity(ModEntities.FIRE_ELEMENTAL.get(), level);
			elemental.setPos(pos.getX(), pos.getY(), pos.getZ());
			level.addFreshEntity(elemental);
		}
	}

	public static Optional<FireRitual> createRitual(Level level, BlockPos pos) {
		if (!isRitualBlock(level, pos))
			return Optional.empty();
		Set<BlockPos> positions = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		worklist.add(pos);

		while (!worklist.isEmpty()) {
			var p = worklist.remove(0);
			if (positions.size() > RITUAL_SIZE || countNearbyRitual(level, p) != 2)
				return Optional.empty();
			positions.add(p);

			for (var d : Direction.values()) {
				var nearby = p.relative(d);
				if (isRitualBlock(level, nearby) && !positions.contains(nearby))
					worklist.add(nearby);

			}
		}

		if (positions.size() != RITUAL_SIZE)
			return Optional.empty();

		return Optional.of(new FireRitual(positions));
	}

	private static int countNearbyRitual(Level level, BlockPos pos) {
		int count = 0;

		for (int i = 0; i < 4; i++) {
			var d = Direction.from2DDataValue(i);
			if (isRitualBlock(level, pos.relative(d)))
				count++;
		}

		return count;
	}

	private static boolean isRitualBlock(Level level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() == RITUAL;
	}

}
