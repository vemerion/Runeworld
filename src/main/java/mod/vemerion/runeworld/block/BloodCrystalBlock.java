package mod.vemerion.runeworld.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;

public class BloodCrystalBlock extends FacingBlock {

	private static VoxelShape[] SHAPES = { Block.makeCuboidShape(2, 2, 2, 14, 16, 14),
			Block.makeCuboidShape(2, 0, 2, 14, 14, 14), Block.makeCuboidShape(2, 2, 2, 14, 14, 16),
			Block.makeCuboidShape(2, 2, 0, 14, 14, 14), Block.makeCuboidShape(2, 2, 2, 16, 14, 14),
			Block.makeCuboidShape(0, 2, 2, 14, 14, 14) };

	public BloodCrystalBlock() {
		super(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance()
				.sound(SoundType.GLASS).setLightLevel(s -> 4), SHAPES);
	}

}
