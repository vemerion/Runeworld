package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CharredDirtBlock extends Block {

	public CharredDirtBlock() {
		super(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BLACK).hardnessAndResistance(0.5f)
				.sound(SoundType.GROUND).tickRandomly());
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (worldIn.getBlockState(pos.up()).getBlock() instanceof AbstractFireBlock)
			worldIn.setBlockState(pos, ModBlocks.BURNT_DIRT.getDefaultState());
	}
}
