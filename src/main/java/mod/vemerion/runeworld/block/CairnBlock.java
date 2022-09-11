package mod.vemerion.runeworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CairnBlock extends Block {

	public static final int MIN_LEVEL = 0;
	public static final int MAX_LEVEL = 7;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", MIN_LEVEL, MAX_LEVEL);

	private static VoxelShape[] SHAPES = { Block.box(4, 0, 4, 12, 3, 12), Block.box(4, 0, 4, 12, 6, 12),
			Block.box(4, 0, 4, 12, 8, 12), Block.box(4, 0, 4, 12, 10, 12), Block.box(4, 0, 4, 12, 12, 12),
			Block.box(4, 0, 4, 12, 13, 12), Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 15, 12) };

	public CairnBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NONE).sound(SoundType.STONE)
				.requiresCorrectToolForDrops().strength(1));
		this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(LEVEL)];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		var stack = pPlayer.getItemInHand(pHand);
		if (stack.is(asItem()) && pState.getValue(LEVEL) < MAX_LEVEL) {

			pLevel.setBlock(pPos, pState.setValue(LEVEL, pState.getValue(LEVEL) + 1), 2);

			if (!pPlayer.isCreative())
				stack.shrink(1);

			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}

		return InteractionResult.PASS;
	}

}
