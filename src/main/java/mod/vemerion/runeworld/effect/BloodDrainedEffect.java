package mod.vemerion.runeworld.effect;

import java.util.List;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class BloodDrainedEffect extends MobEffect {

	private static final MobEffect[] EFFECTS = { MobEffects.BLINDNESS, MobEffects.CONFUSION, MobEffects.POISON, MobEffects.MOVEMENT_SLOWDOWN };

	public BloodDrainedEffect(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
		if (!entityLivingBaseIn.level.isClientSide)
			entityLivingBaseIn.addEffect(new MobEffectInstance(
					EFFECTS[entityLivingBaseIn.getRandom().nextInt(EFFECTS.length)], 20 * 15, amplifier));
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return duration % (20 * 20) == 0;
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return ImmutableList.of(new ItemStack(ModItems.BLOOD_PUDDING), new ItemStack(ModItems.BLOOD_BUCKET));
	}
}
