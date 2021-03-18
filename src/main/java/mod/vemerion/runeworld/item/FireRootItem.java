package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FireRootItem extends BlockItem {

	public FireRootItem() {
		super(ModBlocks.FIRE_ROOT, new Item.Properties().group(ItemGroup.SEARCH).isImmuneToFire()
				.food(new Food.Builder().hunger(4).saturation(0.7f).build()));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.setFire(4);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}
