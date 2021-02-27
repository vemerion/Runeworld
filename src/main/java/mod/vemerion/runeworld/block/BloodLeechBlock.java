package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.tileentity.BloodLeechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BloodLeechBlock extends FacingBlock {

	private static VoxelShape[] SHAPES = { Block.makeCuboidShape(0, 12, 0, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 16, 4, 16), Block.makeCuboidShape(0, 0, 12, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 16, 16, 4), Block.makeCuboidShape(12, 0, 0, 16, 16, 16),
			Block.makeCuboidShape(0, 0, 0, 4, 16, 16) };

	public BloodLeechBlock() {
		super(Block.Properties.create(Material.ORGANIC).sound(SoundType.SLIME).zeroHardnessAndResistance().notSolid()
				.doesNotBlockMovement(), SHAPES);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BloodLeechTileEntity();
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (worldIn.isRemote)
			return;
		
		if (!entityIn.isOnGround() && entityIn.fallDistance > 1) {
			worldIn.destroyBlock(pos, true, entityIn);
		} else if (RANDOM.nextDouble() < 0.01 && entityIn instanceof LivingEntity) {
			((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.BLOOD_DRAINED, 20 * 20));
		}
	}
}
