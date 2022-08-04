package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.PlantType;

public class BloodFlowerBlock extends BushBlock {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);

	public BloodFlowerBlock() {
		super(Block.Properties.of(Material.PLANT).noCollission().instabreak()
				.sound(SoundType.GRASS));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return worldIn.getFluidState(pos).getType() == ModFluids.BLOOD.get()
				&& worldIn.getFluidState(pos.above()).getType() == Fluids.EMPTY;
	}
	
	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.get("blood");
	}
}
