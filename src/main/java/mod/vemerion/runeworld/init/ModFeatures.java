package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.feature.BloodPoolFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModFeatures {

	public static Feature<NoFeatureConfig> BLOOD_POOL;
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event) {
		BLOOD_POOL = new BloodPoolFeature();
		
		event.getRegistry().register(Init.setup(BLOOD_POOL, "blood_pool"));
		
		ModConfiguredFeatures.onRegisterConfiguredFeature();
	}	
}
