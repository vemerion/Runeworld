package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.feature.BloodPillarClusterFeature;
import mod.vemerion.runeworld.feature.BloodPillarSingleFeature;
import mod.vemerion.runeworld.feature.BloodPoolFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModFeatures {

	private static boolean init = true;

	public static Feature<NoFeatureConfig> BLOOD_POOL;
	public static Feature<NoFeatureConfig> BLOOD_PILLAR_SINGLE;
	public static Feature<NoFeatureConfig> BLOOD_PILLAR_CLUSTER;

	public static void init() {
		if (init) {
			init = false;

			BLOOD_POOL = new BloodPoolFeature();
			BLOOD_PILLAR_SINGLE = new BloodPillarSingleFeature();
			BLOOD_PILLAR_CLUSTER = new BloodPillarClusterFeature();
			
			ModConfiguredFeatures.onRegisterConfiguredFeature();
		}
	}

	@SubscribeEvent
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event) {
		init();
		
		event.getRegistry().register(Init.setup(BLOOD_POOL, "blood_pool"));
		event.getRegistry().register(Init.setup(BLOOD_PILLAR_SINGLE, "blood_pillar_single"));
		event.getRegistry().register(Init.setup(BLOOD_PILLAR_CLUSTER, "blood_pillar_cluster"));

	}
}
