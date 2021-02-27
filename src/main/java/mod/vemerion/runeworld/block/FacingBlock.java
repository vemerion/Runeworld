package mod.vemerion.runeworld.block;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class FacingBlock extends DirectionalBlock {

	private VoxelShape[] shapes;

	public FacingBlock(Properties builder, VoxelShape[] shapes) {
		super(builder);
		Preconditions.checkElementIndex(5, shapes.length);
		this.shapes = shapes;
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
	}
	
	// D-U-N-S-W-E
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapes[state.get(FACING).getIndex()];
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.get(FACING);
		pos = pos.offset(direction.getOpposite());
		state = worldIn.getBlockState(pos);
		return state.isSolidSide(worldIn, pos, direction);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = getDefaultState();
		IWorldReader world = context.getWorld();
		BlockPos pos = context.getPos();

		for (Direction direction : context.getNearestLookingDirections())
			if ((state = state.with(FACING, direction.getOpposite())).isValidPosition(world, pos))
				return state;

		return null;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

}
