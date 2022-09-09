package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TopazBlock extends FacingBlock {

	public static final Vec3i COLOR = new Vec3i(211, 119, 0);

	private static VoxelShape[] SHAPES = { Block.box(3, 5, 3, 13, 16, 13), Block.box(3, 0, 3, 13, 11, 13),
			Block.box(3, 3, 5, 13, 13, 16), Block.box(3, 3, 0, 13, 13, 11), Block.box(5, 3, 3, 16, 13, 13),
			Block.box(0, 3, 3, 11, 13, 13) };

	public TopazBlock(Properties builder) {
		super(builder, SHAPES);
	}

	public static int getColor(BlockPos pPos, int pTintIndex) {
		if (pPos == null)
			return FastColor.ARGB32.color(255, COLOR.getX(), COLOR.getY(), COLOR.getZ());

		var rand = new Random(Mth.getSeed(pPos));
		return FastColor.ARGB32.color(255, COLOR.getX() + rand.nextInt(-30, 30), COLOR.getY() + rand.nextInt(-20, 20),
				COLOR.getZ());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		var stack = pPlayer.getItemInHand(pHand);
		if (stack.is(ModItems.FIRE_HEART.get())) {
			pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 2);

			if (!pPlayer.isCreative())
				stack.shrink(1);

			var topazCreature = ModEntities.TOPAZ_CREATURE.get().create(pLevel);
			topazCreature.setPos(Vec3.atBottomCenterOf(pPos));
			topazCreature.tame(pPlayer);
			pLevel.addFreshEntity(topazCreature);

			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}

		return InteractionResult.PASS;
	}
}
