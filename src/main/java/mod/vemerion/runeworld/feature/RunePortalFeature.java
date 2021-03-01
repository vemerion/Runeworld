package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class RunePortalFeature extends Feature<BlockStateFeatureConfig> {

	public RunePortalFeature() {
		super(BlockStateFeatureConfig.field_236455_a_);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			BlockStateFeatureConfig config) {
		if (!reader.getBlockState(pos.down()).isSolid() || !reader.isAirBlock(pos))
			return false;

		int size = 15 + rand.nextInt(10);
		Direction.Axis axis = rand.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = Helper.offsets(dir);

		List<BlockPos> positions = new ArrayList<>();
		Set<BlockPos> portal = new HashSet<>();
		positions.add(pos);

		while (!positions.isEmpty() && portal.size() < size) {
			pos = positions.remove(0);
			if (reader.isAirBlock(pos)) {
				portal.add(pos);

				
				for (int i = 0; i < 4; i++) {
					BlockPos p = pos.add(offsets[rand.nextInt(offsets.length)]);
					if (!portal.contains(p))
						positions.add(p);
				}
			}
		}

		if (portal.size() < size * 0.8)
			return false;
		
		BlockPos createPortalPos = getValidPortal(portal, offsets);
		if (createPortalPos == null)
			return false;

		for (BlockPos p : portal) {
			if (isBorder(portal, p, offsets))
				reader.setBlockState(p, Blocks.OBSIDIAN.getDefaultState(), 2);
		}
		
		RunePortalBlock.createPortal(reader, createPortalPos, config.state.getBlock(), axis);

		return false;
	}

	// Checks that the portal set actually forms a valid portal (is two blocks high at least in one place,
	// and returns a border BlockPos which can be used in RunePortalBlock.createPortal() to light the portal
	private BlockPos getValidPortal(Set<BlockPos> portal, BlockPos[] offsets) {
		Set<BlockPos> nonBorder = new HashSet<>();
		for (BlockPos p : portal)
			if (!isBorder(portal, p, offsets))
				nonBorder.add(p);
		
		for (BlockPos p : nonBorder) {
			if (nonBorder.contains(p.up())) {
				while (nonBorder.contains(p.down()))
					p = p.down();
				return p.down();
			}
		}
		
		return null;
	}

	private boolean isBorder(Set<BlockPos> portal, BlockPos pos, BlockPos[] offsets) {
		for (BlockPos offset : offsets)
			if (!portal.contains(pos.add(offset)))
				return true;
		return false;
	}
}
