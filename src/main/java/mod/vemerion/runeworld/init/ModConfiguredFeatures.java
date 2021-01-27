package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModConfiguredFeatures {

	public static ConfiguredFeature<?, ?> BLOOD_POOL;

	public static void onRegisterConfiguredFeature() {
		BLOOD_POOL = ModFeatures.BLOOD_POOL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4)));

		Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_pool"), BLOOD_POOL);

	}
}
