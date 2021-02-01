package mod.vemerion.runeworld.init;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModConfiguredFeatures {

	public static ConfiguredFeature<?, ?> BLOOD_POOL;
	public static ConfiguredFeature<?, ?> BLOOD_PILLAR_SINGLE;
	public static ConfiguredFeature<?, ?> BLOOD_PILLAR_CLUSTER;
	public static ConfiguredFeature<?, ?> BLOOD_ROCK_PATCH;
	public static ConfiguredFeature<?, ?> BLOOD_CRYSTAL;

	public static void onRegisterConfiguredFeature() {

		BLOOD_POOL = ModFeatures.BLOOD_POOL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4)));

		BLOOD_PILLAR_SINGLE = ModFeatures.BLOOD_PILLAR_SINGLE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.CHANCE.configure(new ChanceConfig(8)));

		BLOOD_PILLAR_CLUSTER = ModFeatures.BLOOD_PILLAR_CLUSTER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.CHANCE.configure(new ChanceConfig(15)));

		BLOOD_ROCK_PATCH = ModFeatures.BLOOD_ROCK_PATCH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.CHANCE.configure(new ChanceConfig(15)));

		BLOOD_CRYSTAL = Feature.SIMPLE_BLOCK
				.withConfiguration(new BlockWithContextConfig(ModBlocks.BLOOD_CRYSTAL.getDefaultState(),
						ImmutableList.of(ModBlocks.BLOOD_MOSS.getDefaultState()),
						ImmutableList.of(Blocks.AIR.getDefaultState()), ImmutableList.of(Blocks.AIR.getDefaultState())))
				.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT.func_242731_b(2));

		Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_pool"), BLOOD_POOL);
		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_pillar_single"), BLOOD_PILLAR_SINGLE);
		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_pillar_cluster"), BLOOD_PILLAR_CLUSTER);
		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_rock_patch"), BLOOD_ROCK_PATCH);
		Registry.register(registry, new ResourceLocation(Main.MODID, "blood_crystal"), BLOOD_CRYSTAL);
	}
}
