package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class FleshEatingPlantBlock extends FacingBlock {

	public static final DirectionProperty ATTACHMENT = DirectionProperty.create("attachment", Direction.values());
	public static final BooleanProperty BASE = BooleanProperty.create("base");

	public FleshEatingPlantBlock(VoxelShape[] shapes) {
		super(BlockBehaviour.Properties.of(Material.BAMBOO, MaterialColor.PLANT).strength(1, 1).sound(SoundType.BAMBOO)
				.noOcclusion().dynamicShape(), shapes);
		registerDefaultState(defaultBlockState().setValue(BASE, false));
	}

	protected boolean isNextToPlant(BlockState state, LevelReader level, BlockPos pos, DirectionProperty property) {
		var opposite = state.getValue(property).getOpposite();
		var nearby = level.getBlockState(pos.relative(opposite));
		return nearby.getBlock() == ModBlocks.FLESH_EATING_PLANT_FLOWER.get()
				|| (nearby.getBlock() == ModBlocks.FLESH_EATING_PLANT_STEM.get()
						&& nearby.getValue(property == ATTACHMENT ? FACING : ATTACHMENT) == opposite);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return super.canSurvive(state, level, pos) || isNextToPlant(state, level, pos, FACING);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BASE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var state = super.getStateForPlacement(context);
		if (state != null && !isNextToPlant(state, context.getLevel(), context.getClickedPos(), FACING)) {
			state = state.setValue(BASE, true);
		}

		return state;
	}
}
