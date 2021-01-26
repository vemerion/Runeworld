package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.fluid.BloodFluid;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.IBucketPickupHandler;
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
		if (!(entityIn instanceof ItemEntity))
			return;
		ItemEntity itemEntity = (ItemEntity) entityIn;
		ItemStack stack = itemEntity.getItem();
		Item item = stack.getItem();
		
		if (!(Runesword.isRune(item) && item != Runesword.BLOOD_RUNE))
			return;
		
		double x = itemEntity.getPosX();
		double y = itemEntity.getPosY() + itemEntity.getHeight() * 0.5;
		double z = itemEntity.getPosZ();
		
		if (!worldIn.isRemote) {
			if (worldIn.getRandom().nextInt(100) == 0) {
				List<BlockPos> nearbyBlood = getNearbyBlood(worldIn, pos);
				if (nearbyBlood.size() > 8) {
					for (BlockPos p : nearbyBlood)
						worldIn.setBlockState(p, Blocks.AIR.getDefaultState());

					itemEntity.getItem().shrink(1);
					ItemEntity bloodRuneEntity = new ItemEntity(worldIn, itemEntity.getPosX(), itemEntity.getPosY(),
							itemEntity.getPosZ(), new ItemStack(Runesword.BLOOD_RUNE));
					worldIn.addEntity(bloodRuneEntity);
				}
			}
		} else {
			worldIn.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), x, y, z, speed(), speed(), speed());
		}
	}

	private List<BlockPos> getNearbyBlood(World worldIn, BlockPos pos) {
		List<BlockPos> blood = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					BlockPos p = pos.add(i, j, k);
					if (worldIn.getFluidState(p).getFluid() == ModFluids.BLOOD)
						blood.add(p);
				}
			}
		}
		return blood;
	}

	private double speed() {
		return (RANDOM.nextDouble() - 0.5) * 0.8;
	}
}