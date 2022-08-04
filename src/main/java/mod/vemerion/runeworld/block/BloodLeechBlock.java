package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import mod.vemerion.runeworld.init.ModBlockEntities;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BloodLeechBlock extends FacingBlock implements IBloodLoggable, EntityBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static VoxelShape[] SHAPES = { Block.box(0, 12, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 4, 16),
			Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 4), Block.box(12, 0, 0, 16, 16, 16),
			Block.box(0, 0, 0, 4, 16, 16) };

	public BloodLeechBlock() {
		super(Block.Properties.of(Material.GRASS).sound(SoundType.SLIME_BLOCK).instabreak().noOcclusion()
				.noCollission(), SHAPES);
		this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
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
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;

		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == ModFluids.BLOOD.get()));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (worldIn.isClientSide)
			return;

		if (!entityIn.isOnGround() && entityIn.fallDistance > 1 && entityIn instanceof Player) {
			worldIn.destroyBlock(pos, true, entityIn);
		} else if (RANDOM.nextDouble() < 0.01 && entityIn instanceof LivingEntity) {
			((LivingEntity) entityIn).addEffect(new MobEffectInstance(ModEffects.BLOOD_DRAINED, 20 * 20));
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BloodLeechBlockEntity(pPos, pState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
			BlockEntityType<T> pBlockEntityType) {
		return pBlockEntityType == ModBlockEntities.BLOOD_LEECH
				? (level, pos, state, tileEntity) -> ((BloodLeechBlockEntity) tileEntity).tick()
				: null;
	}
}
