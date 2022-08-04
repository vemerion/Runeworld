package mod.vemerion.runeworld.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class RunePortalFeature extends Feature<BlockStateConfiguration> {

	public RunePortalFeature() {
		super(BlockStateConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
		var reader = context.level();
		var pos = context.origin();
		var rand = context.random();
		
		if (!reader.getBlockState(pos.below()).canOcclude() || !reader.isEmptyBlock(pos))
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
			if (reader.isEmptyBlock(pos)) {
				portal.add(pos);

				
				for (int i = 0; i < 4; i++) {
					BlockPos p = pos.offset(offsets[rand.nextInt(offsets.length)]);
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
				reader.setBlock(p, Blocks.OBSIDIAN.defaultBlockState(), 2);
		}
		
		RunePortalBlock.createPortal(reader, createPortalPos, context.config().state.getBlock(), axis);

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
			if (nonBorder.contains(p.above())) {
				while (nonBorder.contains(p.below()))
					p = p.below();
				return p.below();
			}
		}
		
		return null;
	}

	private boolean isBorder(Set<BlockPos> portal, BlockPos pos, BlockPos[] offsets) {
		for (BlockPos offset : offsets)
			if (!portal.contains(pos.offset(offset)))
				return true;
		return false;
	}
}
