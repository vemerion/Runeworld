package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.effect.BloodDrainedEffect;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEffects {

	public static final MobEffect BLOOD_DRAINED = null;

	@SubscribeEvent
	public static void onRegisterEffect(RegistryEvent.Register<MobEffect> event) {
		event.getRegistry().register(Init
				.setup(new BloodDrainedEffect(MobEffectCategory.HARMFUL, Helper.color(150, 20, 20, 255)), "blood_drained"));
	}
}
