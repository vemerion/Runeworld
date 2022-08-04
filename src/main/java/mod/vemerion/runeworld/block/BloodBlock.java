package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class BloodBlock extends LiquidBlock {
	public BloodBlock() {
		super(ModFluids.BLOOD,
				Properties.of(Material.LAVA).noCollission().strength(100.0F).noDrops());
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		return plantable.getPlantType(world, pos) == PlantType.get("blood");
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!(entityIn instanceof ItemEntity))
			return;
		ItemEntity itemEntity = (ItemEntity) entityIn;
		ItemStack stack = itemEntity.getItem();
		Item item = stack.getItem();

		if (!(Runesword.isRune(item) && item != Runesword.BLOOD_RUNE))
			return;

		double x = itemEntity.getX();
		double y = itemEntity.getY() + itemEntity.getBbHeight() * 0.5;
		double z = itemEntity.getZ();

		if (!worldIn.isClientSide) {
			if (worldIn.getRandom().nextInt(100) == 0) {
				List<BlockPos> nearbyBlood = getNearbyBlood(worldIn, pos);
				if (nearbyBlood.size() > 8) {
					for (BlockPos p : nearbyBlood)
						worldIn.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());

					itemEntity.getItem().shrink(1);
					ItemEntity bloodRuneEntity = new ItemEntity(worldIn, itemEntity.getX(), itemEntity.getY(),
							itemEntity.getZ(), new ItemStack(Runesword.BLOOD_RUNE));
					worldIn.addFreshEntity(bloodRuneEntity);
				}
			}
		} else {
			worldIn.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, speed(), speed(), speed());
		}
	}

	private List<BlockPos> getNearbyBlood(Level worldIn, BlockPos pos) {
		List<BlockPos> blood = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					BlockPos p = pos.offset(i, j, k);
					if (worldIn.getFluidState(p).getType() == ModFluids.BLOOD.get())
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