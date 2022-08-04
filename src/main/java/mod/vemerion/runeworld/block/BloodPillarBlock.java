package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BloodPillarBlock extends Block implements IBloodLoggable {
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final VoxelShape LARGE = Block.box(2, 0, 2, 14, 16, 14);
	public static final VoxelShape MEDIUM = Block.box(4, 0, 4, 12, 16, 12);
	public static final VoxelShape SMALL = Block.box(6, 0, 6, 10, 16, 10);

	private VoxelShape shape;

	public BloodPillarBlock(VoxelShape shape) {
		super(Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(1.5F,
				6.0F));
		this.shape = shape;
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, ModFluids.BLOOD.get(), ModFluids.BLOOD.get().getTickDelay(level));
		}

		return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? ModFluids.BLOOD.get().getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == ModFluids.BLOOD.get()));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return shape;
	}

	public static boolean isPillar(Block block) {
		return block == ModBlocks.BLOOD_PILLAR_LARGE || block == ModBlocks.BLOOD_PILLAR_MEDIUM
				|| block == ModBlocks.BLOOD_PILLAR_SMALL;
	}

	public static boolean isPillar(LevelReader world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return isPillar(block);
	}

	public static boolean generatePillar(LevelAccessor world, Random rand, BlockPos pos) {
		int height = 0;
		for (int i = 0; i < 4; i++) {
			if (!isValidPos(world, pos))
				break;
			height++;
		}

		if (height < 2)
			return false;

		height = rand.nextInt(height + 1 - 2) + 2;

		if (height == 2) {
			if (rand.nextBoolean()) {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
			}
		} else if (height == 3) {
			if (rand.nextBoolean()) {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);

			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			}
		} else if (height == 4) {
			if (rand.nextBoolean()) {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.above(2), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(3), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.above(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);
				setPillar(world, pos.above(3), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			}
		}

		return true;
	}

	private static void setPillar(LevelAccessor world, BlockPos pos, BloodPillarBlock pillar, int i) {
		FluidState fluidState = world.getFluidState(pos);
		BlockState state = pillar.defaultBlockState();
		world.setBlock(pos, state, 2);
		pillar.placeLiquid(world, pos, state, fluidState);

	}

	private static boolean isValidPos(LevelAccessor world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.getFluidState().getType().isSame(ModFluids.BLOOD.get()) || state.isAir();
	}
}
