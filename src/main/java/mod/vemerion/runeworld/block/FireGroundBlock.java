package mod.vemerion.runeworld.block;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class FireGroundBlock extends Block {

	public FireGroundBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		return plantable.getPlantType(world, pos) == PlantType.get("fire");
	}

	@Override
	public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos) {
		for (BlockPos p : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 1, 1))) {
			if (world.getBlockState(p).getBlock() instanceof AbstractFireBlock) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return true;
	}
}
