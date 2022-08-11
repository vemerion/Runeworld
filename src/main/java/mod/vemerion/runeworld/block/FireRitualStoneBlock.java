package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class FireRitualStoneBlock extends Block {

	public static final BooleanProperty BLOODIED = BooleanProperty.create("bloodied");

	public FireRitualStoneBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops()
				.strength(1.5F, 6.0F).randomTicks());
		this.registerDefaultState(this.stateDefinition.any().setValue(BLOODIED, false));
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(handIn);
		if (stack.getItem() == ModItems.BLOOD_CRYSTALLITE.get() && !state.getValue(BLOODIED)) {
			if (!player.isCreative())
				stack.shrink(1);

			worldIn.setBlock(pos, state.setValue(BLOODIED, true), 2);
			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		}

		return InteractionResult.PASS;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		FireRitual.createRitual(worldIn, pos).ifPresent(ritual -> ritual.performRitual(worldIn, pos));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BLOODIED);
	}
}
