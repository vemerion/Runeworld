package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public interface IBloodLoggable extends SimpleWaterloggedBlock {

	@Override
	default boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return !state.getValue(BlockStateProperties.WATERLOGGED) && fluidIn == ModFluids.BLOOD.get();
	}

	@Override
	default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == ModFluids.BLOOD.get()) {
			if (!level.isClientSide()) {
				level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 3);
				level.scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(level));
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	default ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
		if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
			pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
			if (!pState.canSurvive(pLevel, pPos)) {
				pLevel.destroyBlock(pPos, true);
			}

			return new ItemStack(ModItems.BLOOD_BUCKET);
		} else {
			return ItemStack.EMPTY;
		}
	}
}
