package mod.vemerion.runeworld.enchantment;

import mod.vemerion.runeworld.item.SlingshotItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ModEnchantmentCategories {
	public static final EnchantmentCategory SLINGSHOT = EnchantmentCategory.create("slingshot", item -> {
		return item instanceof SlingshotItem;
	});
}
