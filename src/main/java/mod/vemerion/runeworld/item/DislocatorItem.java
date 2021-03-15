package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.capability.RuneTeleport;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class DislocatorItem extends Item {

	private RegistryKey<World> dimension;

	public DislocatorItem(Item.Properties properties, RegistryKey<World> dimension) {
		super(properties);
		this.dimension = dimension;
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
			if (stack.isDamageable())
				stack.damageItem(1, entityLiving, e -> e.sendBreakAnimation(entityLiving.getActiveHand()));
			else
				stack.shrink(1);

		if (!worldIn.isRemote)
			RuneTeleport.teleport(entityLiving, worldIn, dimension);

		return stack;
	}
}
