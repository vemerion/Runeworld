package mod.vemerion.runeworld.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class FireRitualStoneBlock extends Block {

	public FireRitualStoneBlock() {
		super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool()
				.hardnessAndResistance(1.5F, 6.0F).tickRandomly());
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
	}
}
