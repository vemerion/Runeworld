package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BloodPillarBlock extends Block implements IWaterLoggable {
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final VoxelShape LARGE = Block.makeCuboidShape(2, 0, 2, 14, 16, 14);
	public static final VoxelShape MEDIUM = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	public static final VoxelShape SMALL = Block.makeCuboidShape(6, 0, 6, 10, 16, 10);

	private VoxelShape shape;

	public BloodPillarBlock(VoxelShape shape) {
		super(Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(1.5F, 6.0F));
		this.shape = shape;
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, ModFluids.BLOOD,
					ModFluids.BLOOD.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? ModFluids.BLOOD.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		return getDefaultState().with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == ModFluids.BLOOD));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return !state.get(BlockStateProperties.WATERLOGGED) && fluidIn == ModFluids.BLOOD;
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == ModFluids.BLOOD) {
			if (!worldIn.isRemote()) {
				worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 3);
				worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(),
						fluidStateIn.getFluid().getTickRate(worldIn));
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
		if (state.get(BlockStateProperties.WATERLOGGED)) {
			worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
			return ModFluids.BLOOD;
		} else {
			return Fluids.EMPTY;
		}
	}

	public static boolean generatePillar(IWorld world, Random rand, BlockPos pos) {
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
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
			}
		} else if (height == 3) {
			if (rand.nextBoolean()) {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);

			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			}
		} else if (height == 4) {
			if (rand.nextBoolean()) {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_LARGE, 2);
				setPillar(world, pos.up(2), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(3), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			} else {
				setPillar(world, pos, ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(), ModBlocks.BLOOD_PILLAR_MEDIUM, 2);
				setPillar(world, pos.up(2), ModBlocks.BLOOD_PILLAR_SMALL, 2);
				setPillar(world, pos.up(3), ModBlocks.BLOOD_PILLAR_SMALL, 2);
			}
		}

		return true;
	}

	private static void setPillar(IWorld world, BlockPos pos, BloodPillarBlock pillar, int i) {
		FluidState fluidState = world.getFluidState(pos);
		world.setBlockState(pos, pillar.getDefaultState(), 2);
		BlockState state = world.getBlockState(pos);
		pillar.receiveFluid(world, pos, state, fluidState);

	}

	private static boolean isValidPos(IWorld world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD)
				|| state.getBlock().isAir(state, world, pos);
	}
}
