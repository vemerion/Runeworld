package mod.vemerion.runeworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BloodPillarBlock extends Block {
	public static final VoxelShape LARGE = Block.makeCuboidShape(2, 0, 2, 14, 16, 14);
	public static final VoxelShape MEDIUM = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	public static final VoxelShape SMALL = Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
	
	private VoxelShape shape;

	public BloodPillarBlock(VoxelShape shape) {
		super(Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(1.5F, 6.0F));
		this.shape = shape;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}
}
