package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class BloodBucketItem extends BucketItem {

	public BloodBucketItem() {
		super(ModFluids.BLOOD,
				new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CreativeModeTab.TAB_SEARCH));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (!worldIn.isClientSide)
			entityLiving.curePotionEffects(stack);

		if (entityLiving instanceof Player player && !player.getAbilities().instabuild) {
			stack.shrink(1);
		}

		return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		InteractionResultHolder<ItemStack> bucket = super.use(worldIn, playerIn, handIn);
		if (bucket.getResult() == InteractionResult.PASS) {
			return ItemUtils.startUsingInstantly(worldIn, playerIn, handIn);
		} else {
			return bucket;
		}
	}

	@Override
	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack,
			CompoundTag nbt) {
		return new FluidBucketWrapper(stack);
	}

}
