package mod.vemerion.runeworld.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class HandMirrorItem extends Item {

	private static final int DURATION = 20 * 60 * 60;

	public HandMirrorItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).durability(64));
	}

	@Override
	public int getUseDuration(ItemStack pStack) {
		return DURATION;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		var stack = pPlayer.getItemInHand(pHand);
		pPlayer.startUsingItem(pHand);
		return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
	}

	@Override
	public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
		var box = pLivingEntity.getBoundingBox().inflate(2);
		var projectiles = pLevel.getEntitiesOfClass(Projectile.class, box, p -> p.getOwner() != pLivingEntity);
		for (var projectile : projectiles) {
			projectile.setOwner(pLivingEntity);
			projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
		}

		if (!projectiles.isEmpty() && pLivingEntity instanceof Player player && !player.isCreative()) {
			pStack.hurtAndBreak(projectiles.size(), player, p -> {
				p.broadcastBreakEvent(player.getUsedItemHand());
			});
		}
	}

}
