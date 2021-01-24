package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.fluid.BloodFluid;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;

public class BloodBlock extends FlowingFluidBlock {
	public BloodBlock() {
		super(BloodFluid.Source::new,
				Properties.create(Material.LAVA).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
	}
}