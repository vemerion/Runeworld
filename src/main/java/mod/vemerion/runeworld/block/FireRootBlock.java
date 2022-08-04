package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.PlantType;

public class FireRootBlock extends CropBlock {

	public FireRootBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_GRAY).noCollission().randomTicks()
				.instabreak().sound(SoundType.CROP));
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.FIRE_ROOT;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.get("fire");
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return Block.box(0, 0, 0, 16, state.getValue(getAgeProperty()) + 2, 16);
	}
}
