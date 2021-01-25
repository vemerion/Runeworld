package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.fluid.BloodFluid;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodBlock extends FlowingFluidBlock {
	public BloodBlock() {
		super(BloodFluid.Source::new,
				Properties.create(Material.LAVA).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
	}
}