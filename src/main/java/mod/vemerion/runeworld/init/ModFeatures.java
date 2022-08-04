package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.feature.BloodBatTreeFeature;
import mod.vemerion.runeworld.feature.BloodPillarClusterFeature;
import mod.vemerion.runeworld.feature.BloodPillarSingleFeature;
import mod.vemerion.runeworld.feature.BloodPoolFeature;
import mod.vemerion.runeworld.feature.BloodRockPatchFeature;
import mod.vemerion.runeworld.feature.RunePortalFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModFeatures {

	private static boolean init = true;

	public static Feature<NoneFeatureConfiguration> BLOOD_POOL;
	public static Feature<NoneFeatureConfiguration> BLOOD_PILLAR_SINGLE;
	public static Feature<NoneFeatureConfiguration> BLOOD_PILLAR_CLUSTER;
	public static Feature<NoneFeatureConfiguration> BLOOD_ROCK_PATCH;
	public static Feature<NoneFeatureConfiguration> BLOOD_BAT_TREE;
	public static Feature<BlockStateConfiguration> RUNE_PORTAL_FEATURE;

	public static void init() {
		if (init) {
			init = false;

			BLOOD_POOL = new BloodPoolFeature();
			BLOOD_PILLAR_SINGLE = new BloodPillarSingleFeature();
			BLOOD_PILLAR_CLUSTER = new BloodPillarClusterFeature();
			BLOOD_ROCK_PATCH = new BloodRockPatchFeature();
			BLOOD_BAT_TREE = new BloodBatTreeFeature();
			RUNE_PORTAL_FEATURE = new RunePortalFeature();
			
			ModConfiguredFeatures.onRegisterConfiguredFeature();
		}
	}

	@SubscribeEvent
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event) {
		init();
		
		event.getRegistry().register(Init.setup(BLOOD_POOL, "blood_pool"));
		event.getRegistry().register(Init.setup(BLOOD_PILLAR_SINGLE, "blood_pillar_single"));
		event.getRegistry().register(Init.setup(BLOOD_PILLAR_CLUSTER, "blood_pillar_cluster"));
		event.getRegistry().register(Init.setup(BLOOD_ROCK_PATCH, "blood_rock_patch"));
		event.getRegistry().register(Init.setup(BLOOD_BAT_TREE, "blood_bat_tree"));
		event.getRegistry().register(Init.setup(RUNE_PORTAL_FEATURE, "rune_portal_feature"));

	}
}
