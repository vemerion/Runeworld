package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FleshEatingPlantFlowerBlock extends FleshEatingPlantBlock {

	public static final BooleanProperty OPEN = BooleanProperty.create("open");

	private static VoxelShape[] SHAPES = { Block.box(3, 2, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 14, 13),
			Block.box(3, 3, 2, 13, 13, 16), Block.box(3, 3, 0, 13, 13, 14), Block.box(2, 3, 3, 16, 13, 13),
			Block.box(0, 3, 3, 14, 13, 13) };

	public FleshEatingPlantFlowerBlock() {
		super(SHAPES);
		registerDefaultState(defaultBlockState().setValue(OPEN, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(OPEN);
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(OPEN) == false;
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        pLevel.setBlock(pPos, pState.setValue(OPEN, true), 2);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level,
			BlockPos currentPos, BlockPos facingPos) {
		state = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
		if (state.getBlock() instanceof FleshEatingPlantFlowerBlock
				&& facingState.getBlock() instanceof FleshEatingPlantBlock) {
			if (isNextToPlant(facingState, level, facingPos, FACING)
					&& state.getValue(FACING).getOpposite() != facing) { // Turn head to stem if new head attached
				state = ModBlocks.FLESH_EATING_PLANT_STEM.get().defaultBlockState()
						.setValue(FACING, state.getValue(FACING)).setValue(ATTACHMENT, facing.getOpposite())
						.setValue(BASE, state.getValue(BASE));
			}
		}
		return state;
	}
}
