package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;

public class BloodBatToothItem extends Item {

	public BloodBatToothItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).durability(32));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addEffect(new MobEffectInstance(ModEffects.BLOOD_DRAINED.get(), 20 * 60));
		stack.hurtAndBreak(1, attacker, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return true;
	}

}
