package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireRootItem extends BlockItem {

	public FireRootItem() {
		super(ModBlocks.FIRE_ROOT.get(), new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).fireResistant()
				.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.7f).build()));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		entityLiving.setSecondsOnFire(4);
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}
