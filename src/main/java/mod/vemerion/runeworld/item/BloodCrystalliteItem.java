package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodCrystalliteItem extends Item {

	public BloodCrystalliteItem() {
		super(new Item.Properties().group(ItemGroup.SEARCH));
	}

	// Convert crystallite to diamond
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getItem();
		if (state.getBlock() == Blocks.CAULDRON) {
			if (!world.isRemote && state.get(CauldronBlock.LEVEL) == 3) {
				CauldronBlock cauldron = (CauldronBlock) state.getBlock();
				cauldron.setWaterLevel(world, pos, state, 0);
				if (!player.isCreative())
					stack.shrink(1);
				ItemStack diamond = new ItemStack(Items.DIAMOND);
				if (!player.addItemStackToInventory(diamond)) {
					player.dropItem(diamond, false);
				}
			}

			return ActionResultType.func_233537_a_(world.isRemote);
		}

		return super.onItemUse(context);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isRemote || !(entityIn instanceof PlayerEntity) || ((PlayerEntity) entityIn).isCreative())
			return;
		for (BloodBatEntity bat : worldIn.getEntitiesWithinAABB(BloodBatEntity.class, entityIn.getBoundingBox().grow(5),
				bat -> bat.isHanging())) {
			bat.stopHanging();
			bat.setAttackTarget((PlayerEntity) entityIn);
		}
	}

}
