package mod.vemerion.runeworld.effect;

import java.util.List;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.item.MonkeyPawItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MonkeyCurseEffect extends MobEffect {

	public MonkeyCurseEffect(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level.isClientSide)
			return;
		
		entity.hurt(MonkeyPawItem.DAMAGE_SOURCE, amplifier + 1);
		if (entity instanceof Player player)
			player.causeFoodExhaustion((amplifier + 1) * 2);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return duration % (20 * 20) == 0;
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return ImmutableList.of();
	}
}
