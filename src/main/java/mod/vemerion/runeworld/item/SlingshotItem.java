package mod.vemerion.runeworld.item;

import java.util.function.Predicate;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModEnchantments;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class SlingshotItem extends ProjectileWeaponItem {

	public static final TagKey<Item> SUPPORTED_PROJECTILES = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),
			new ResourceLocation(Main.MODID, "slingshot_projectiles"));

	private static final int MAX_DURATION = 16;
	private static final int RANGE = 15;
	private static final int DURABILITY = 128;

	public SlingshotItem() {
		super((new Item.Properties()).durability(DURABILITY).tab(CreativeModeTab.TAB_SEARCH));
	}

	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		var stack = pPlayer.getItemInHand(pHand);

		if (!pPlayer.isCreative() && pPlayer.getProjectile(stack).isEmpty()) {
			return InteractionResultHolder.fail(stack);
		} else {
			pPlayer.startUsingItem(pHand);
			return InteractionResultHolder.consume(stack);
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
		releaseUsing(pStack, pLevel, pLivingEntity, 0);
		return super.finishUsingItem(pStack, pLevel, pLivingEntity);
	}

	@Override
	public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
		if (pLevel.isClientSide)
			return;

		if (pLivingEntity instanceof Player player) {
			var pebbles = player.getProjectile(pStack);
			var creative = player.isCreative();

			if (pebbles.isEmpty() && !creative)
				return;

			if (pebbles.isEmpty() || pebbles.is(Items.ARROW))
				pebbles = ModItems.BLOOD_PEBBLE.get().getDefaultInstance();

			float duration = getUseDuration(pStack);
			float progress = (duration - pTimeCharged) / duration;
			float power = progress + 0.3f;

			var projectile = ModEntities.SLINGSHOT_PROJECTILE.get().create(pLevel);

			var damage = Math.max(1, (int) (progress
					* (2 + EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ELASTIC.get(), pStack))));
			projectile.setDamage(damage);
			projectile.setBreakChance(
					0.5f - EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.HARDNESS.get(), pStack) * 0.1f);
			projectile.setReturnChance(
					EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RETENTION.get(), pStack) * 0.1f);

			projectile.setOwner(player);
			projectile.setPos(player.getX(), player.getEyeY() - 0.1f, player.getZ());
			projectile.setItem(pebbles);
			projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, power, 1);
			pLevel.addFreshEntity(projectile);

			if (!creative) {
				pebbles.shrink(1);
				pStack.hurtAndBreak(1, player, p -> {
					p.broadcastBreakEvent(player.getUsedItemHand());
				});
			}
		}

	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return s -> s.is(SUPPORTED_PROJECTILES);
	}

	@Override
	public int getDefaultProjectileRange() {
		return RANGE;
	}

	public int getUseDuration(ItemStack pStack) {
		return MAX_DURATION - EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.QUICK_DRAW.get(), pStack) * 2;
	}

	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.BOW;
	}

}
