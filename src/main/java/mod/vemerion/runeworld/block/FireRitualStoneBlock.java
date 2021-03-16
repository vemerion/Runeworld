package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FireRitualStoneBlock extends Block {

	public static final BooleanProperty BLOODIED = BooleanProperty.create("bloodied");

	public FireRitualStoneBlock() {
		super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool()
				.hardnessAndResistance(1.5F, 6.0F).tickRandomly());
		this.setDefaultState(this.stateContainer.getBaseState().with(BLOODIED, false));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		ItemStack stack = player.getHeldItem(handIn);
		if (stack.getItem() == ModItems.BLOOD_CRYSTALLITE && !state.get(BLOODIED)) {
			if (!player.isCreative())
				stack.shrink(1);

			worldIn.setBlockState(pos, state.with(BLOODIED, true), 2);
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}

		return ActionResultType.PASS;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		FireRitual.createRitual(worldIn, pos).ifPresent(ritual -> ritual.performRitual(worldIn, pos));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BLOODIED);
	}
}
