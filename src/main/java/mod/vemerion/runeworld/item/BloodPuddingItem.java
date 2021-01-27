package mod.vemerion.runeworld.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BloodPuddingItem extends Item {

	public BloodPuddingItem() {
		super(new Item.Properties().group(ItemGroup.SEARCH)
				.food(new Food.Builder().hunger(3).saturation(0.3F).meat().build()));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (!worldIn.isRemote)
			entityLiving.curePotionEffects(stack);
		
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

}
