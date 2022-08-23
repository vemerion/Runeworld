package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FleshEatingPlantFlowerBlock extends FleshEatingPlantBlock {

	public static final DamageSource DAMAGE_SOURCE = new DamageSource(Main.MODID + ".flesh_eating_plant");
	public static final int DAMAGE = 10;

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
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
		if (pLevel.isClientSide || !pState.getValue(OPEN) || !(pEntity instanceof LivingEntity))
			return;

		pEntity.hurt(DAMAGE_SOURCE, DAMAGE);
		pLevel.setBlock(pPos, pState.setValue(OPEN, false), 2);

		if (!pEntity.isAlive())
			expand(pState, pLevel, pPos, pEntity);
	}

	private void expand(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
		var directions = new ArrayList<Direction>();
		for (var direction : Direction.values()) {
			directions.add(direction);
		}
		Collections.shuffle(directions);

		for (var direction : directions) {
			var adjacent = pPos.relative(direction);
			if (pLevel.getBlockState(adjacent).isAir()) {
				pLevel.setBlock(adjacent,
						ModBlocks.FLESH_EATING_PLANT_FLOWER.get().defaultBlockState().setValue(FACING, direction), 2);
				return;
			}
		}
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
