package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class CharredDirtBlock extends FireGroundBlock {

	public CharredDirtBlock() {
		super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BLACK).strength(0.5f)
				.sound(SoundType.GRAVEL).randomTicks());
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		if (worldIn.getBlockState(pos.above()).getBlock() instanceof BaseFireBlock)
			worldIn.setBlockAndUpdate(pos, ModBlocks.BURNT_DIRT.get().defaultBlockState());
	}
}
