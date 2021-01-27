package mod.vemerion.runeworld.effect;

import java.util.List;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

public class BloodDrainedEffect extends Effect {

	private static final Effect[] EFFECTS = { Effects.BLINDNESS, Effects.NAUSEA, Effects.POISON, Effects.SLOWNESS };

	public BloodDrainedEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		if (!entityLivingBaseIn.world.isRemote)
			entityLivingBaseIn.addPotionEffect(new EffectInstance(
					EFFECTS[entityLivingBaseIn.getRNG().nextInt(EFFECTS.length)], 20 * 15, amplifier));
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % (20 * 45) == 0;
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return ImmutableList.of(new ItemStack(ModItems.BLOOD_PUDDING), new ItemStack(ModItems.BLOOD_BUCKET));
	}
}
