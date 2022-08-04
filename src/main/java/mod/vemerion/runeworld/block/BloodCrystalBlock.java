package mod.vemerion.runeworld.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BloodCrystalBlock extends FacingBlock {

	private static VoxelShape[] SHAPES = { Block.box(2, 2, 2, 14, 16, 14),
			Block.box(2, 0, 2, 14, 14, 14), Block.box(2, 2, 2, 14, 14, 16),
			Block.box(2, 2, 0, 14, 14, 14), Block.box(2, 2, 2, 16, 14, 14),
			Block.box(0, 2, 2, 14, 14, 14) };

	public BloodCrystalBlock() {
		super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak()
				.sound(SoundType.GLASS).lightLevel(s -> 4), SHAPES);
	}

}
