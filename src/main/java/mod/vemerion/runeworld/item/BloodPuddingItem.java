package mod.vemerion.runeworld.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodPuddingItem extends Item {

	public BloodPuddingItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH)
				.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().build()));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (!worldIn.isClientSide)
			entityLiving.curePotionEffects(stack);
		
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}

}
