package mod.vemerion.runeworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TopazBlock extends FacingBlock {
	
	private static VoxelShape[] SHAPES = { Block.box(3, 5, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 11, 13),
			Block.box(3, 3, 5, 13, 13, 16), Block.box(3, 3, 0, 13, 13, 11), Block.box(5, 3, 3, 16, 13, 13),
			Block.box(0, 3, 3, 11, 13, 13) };

	public TopazBlock(Properties builder) {
		super(builder, SHAPES);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return true;
	}

}
