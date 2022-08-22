package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FleshEatingPlantStemBlock extends FleshEatingPlantBlock {

	/*
	 * On top of a facing, the stem also has an attachment direction, which
	 * determines which outgoing direction it has to the next block. Points inwards,
	 * similar to facing, so to get attached block use getOpposite. Attachment
	 * property is defined is superclass.
	 */

	private static VoxelShape[][] SHAPES;

	public FleshEatingPlantStemBlock() {
		super(new VoxelShape[] {});
		registerDefaultState(defaultBlockState().setValue(ATTACHMENT, Direction.NORTH));
		SHAPES = makeShapes();
	}

	private VoxelShape[][] makeShapes() {
		var shapes = new VoxelShape[Direction.values().length][Direction.values().length];
		for (var facing : Direction.values()) {
			for (var attachment : Direction.values()) {
				var facingShape = Block.box(shapePoint(facing.getStepX(), true), shapePoint(facing.getStepY(), true),
						shapePoint(facing.getStepZ(), true), shapePoint(facing.getStepX(), false),
						shapePoint(facing.getStepY(), false), shapePoint(facing.getStepZ(), false));
				var attachmentShape = Block.box(shapePoint(attachment.getStepX(), true),
						shapePoint(attachment.getStepY(), true), shapePoint(attachment.getStepZ(), true),
						shapePoint(attachment.getStepX(), false), shapePoint(attachment.getStepY(), false),
						shapePoint(attachment.getStepZ(), false));
				shapes[facing.get3DDataValue()][attachment.get3DDataValue()] = Shapes.or(facingShape, attachmentShape);
			}
		}
		return shapes;
	}

	private int shapePoint(int step, boolean start) {
		if (step == 0) {
			return start ? 6 : 10;
		} else if (step > 0) {
			return start ? 0 : 8;
		} else {
			return start ? 8 : 16;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(FACING).get3DDataValue()][state.getValue(ATTACHMENT).get3DDataValue()];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ATTACHMENT);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level,
			BlockPos currentPos, BlockPos facingPos) {
		state = super.updateShape(state, facing, facingState, level, currentPos, facingPos);

		// Turn stem to head if head was removed
		if (state.getBlock() instanceof FleshEatingPlantStemBlock
				&& !isNextToPlant(state, level, currentPos, ATTACHMENT)) {
			state = ModBlocks.FLESH_EATING_PLANT_FLOWER.get().defaultBlockState()
					.setValue(FACING, state.getValue(FACING)).setValue(BASE, state.getValue(BASE));
		}

		return state;
	}
}
