package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.blockentity.ChaliceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidUtil;

public class ChaliceBlock extends Block implements EntityBlock {

	private static VoxelShape SHAPE = Block.box(4, 0, 4, 12, 13, 12);

	public ChaliceBlock() {
		super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.COPPER).strength(1));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		var stack = pPlayer.getItemInHand(pHand);
		var fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack.isPresent() && pLevel.getBlockEntity(pPos) instanceof ChaliceBlockEntity chalice) {
			chalice.putFluid(fluidStack.get().getFluid());
			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}

		return InteractionResult.PASS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new ChaliceBlockEntity(pPos, pState);
	}

}
