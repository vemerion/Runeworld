package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.capability.RuneTeleport;
import mod.vemerion.runeworld.init.ModDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BloodDislocatorItem extends Item {

	public BloodDislocatorItem() {
		super(new Item.Properties().group(ItemGroup.SEARCH));
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return ActionResult.resultConsume(stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative())
			stack.shrink(1);

		if (!worldIn.isRemote)
			RuneTeleport.teleport(entityLiving, worldIn, ModDimensions.BLOOD);

		return stack;
	}
}
