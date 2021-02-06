package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

public class BloodBatToothItem extends Item {

	public BloodBatToothItem() {
		super(new Item.Properties().group(ItemGroup.SEARCH).maxDamage(32));
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addPotionEffect(new EffectInstance(ModEffects.BLOOD_DRAINED, 20 * 60));
		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

}
