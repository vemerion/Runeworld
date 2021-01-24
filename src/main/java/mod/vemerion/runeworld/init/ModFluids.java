package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.fluid.BloodFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModFluids {
	
	public static final Fluid BLOOD_FLOWING = null;
	public static final Fluid BLOOD = null;

	@SubscribeEvent
	public static void onRegisterFluid(RegistryEvent.Register<Fluid> event) {
		event.getRegistry().register(Init.setup(new BloodFluid.Flowing(), "blood_flowing"));
		event.getRegistry().register(Init.setup(new BloodFluid.Source(), "blood"));

	}
}
