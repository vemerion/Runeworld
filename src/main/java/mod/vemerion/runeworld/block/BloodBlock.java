package mod.vemerion.runeworld.block;

import mod.vemerion.runeworld.fluid.BloodFluid;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodBlock extends FlowingFluidBlock {
	public BloodBlock() {
		super(BloodFluid.Source::new,
				Properties.create(Material.LAVA).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entityIn;
			ItemStack stack = itemEntity.getItem();
			Item item = stack.getItem();
			double x = itemEntity.getPosX();
			double y = itemEntity.getPosY() + itemEntity.getHeight() * 0.5;
			double z = itemEntity.getPosZ();
			if (Runesword.isRune(item) && item != Runesword.BLOOD_RUNE) {
				if (!worldIn.isRemote) {
					if (worldIn.getRandom().nextInt(100) == 0) {
						itemEntity.getItem().shrink(1);
						ItemEntity bloodRuneEntity = new ItemEntity(worldIn, itemEntity.getPosX(), itemEntity.getPosY(),
								itemEntity.getPosZ(), new ItemStack(Runesword.BLOOD_RUNE));
						worldIn.addEntity(bloodRuneEntity);
					}
				} else {
					worldIn.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), x, y, z, speed(), speed(), speed());
				}
			}
		}
	}
	
	private double speed() {
		return (RANDOM.nextDouble() - 0.5) * 0.8;
	}
}