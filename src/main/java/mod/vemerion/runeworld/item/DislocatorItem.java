package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.capability.RuneTeleport;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class DislocatorItem extends Item {

	private ResourceKey<Level> dimension;

	public DislocatorItem(Item.Properties properties, ResourceKey<Level> dimension) {
		super(properties);
		this.dimension = dimension;
	}
	
	public ResourceKey<Level> getDimension() {
		return dimension;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (entityLiving instanceof Player && !((Player) entityLiving).isCreative())
			if (stack.isDamageableItem())
				stack.hurtAndBreak(1, entityLiving, e -> e.broadcastBreakEvent(entityLiving.getUsedItemHand()));
			else
				stack.shrink(1);

		if (!worldIn.isClientSide)
			RuneTeleport.teleport(entityLiving, worldIn, dimension);

		return stack;
	}
}
