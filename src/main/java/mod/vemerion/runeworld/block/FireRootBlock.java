package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class FireRootBlock extends CropsBlock {

	public FireRootBlock() {
		super(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.GRAY).doesNotBlockMovement().tickRandomly()
				.zeroHardnessAndResistance().sound(SoundType.CROP));
	}

	@Override
	protected IItemProvider getSeedsItem() {
		return ModItems.FIRE_ROOT;
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.get("fire");
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.makeCuboidShape(0, 0, 0, 16, state.get(getAgeProperty()) + 2, 16);
	}
}
