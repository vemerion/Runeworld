package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.effect.BloodDrainedEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEffects {
	
	public static final Effect BLOOD_DRAINED = null;

	@SubscribeEvent
	public static void onRegisterEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(Init.setup(new BloodDrainedEffect(EffectType.HARMFUL, 0), "blood_drained"));
	}
}
