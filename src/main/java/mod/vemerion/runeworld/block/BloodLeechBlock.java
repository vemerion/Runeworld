package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.tileentity.BloodLeechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BloodLeechBlock extends FacingBlock implements IBloodLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static VoxelShape[] SHAPES = { Block.makeCuboidShape(0, 12, 0, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 16, 4, 16), Block.makeCuboidShape(0, 0, 12, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 16, 16, 4), Block.makeCuboidShape(12, 0, 0, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 4, 16, 16) };

	public BloodLeechBlock() {
		super(Block.Properties.create(Material.ORGANIC).sound(SoundType.SLIME).zeroHardnessAndResistance().notSolid()
				.doesNotBlockMovement(), SHAPES);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, false));
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
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;

		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		return state.with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == ModFluids.BLOOD));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BloodLeechTileEntity();
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (worldIn.isRemote)
			return;

		if (!entityIn.isOnGround() && entityIn.fallDistance > 1 && entityIn instanceof PlayerEntity) {
			worldIn.destroyBlock(pos, true, entityIn);
		} else if (RANDOM.nextDouble() < 0.01 && entityIn instanceof LivingEntity) {
			((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.BLOOD_DRAINED, 20 * 20));
		}
	}
}
