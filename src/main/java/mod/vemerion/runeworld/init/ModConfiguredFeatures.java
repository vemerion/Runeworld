package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FireRootBlock;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ModConfiguredFeatures {
	
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOOD_POOL;
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOOD_PILLAR_SINGLE;
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOOD_PILLAR_CLUSTER;
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOOD_ROCK_PATCH;
	public static Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> BLOOD_CRYSTAL;
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOOD_BAT_TREE;
	public static Holder<ConfiguredFeature<BlockStateConfiguration, ?>> BLOOD_RUNE_PORTAL_FEATURE;
	public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_ROOT_PATCH;
	public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_PATCH;
	

	public static void onRegisterConfiguredFeature() {
		
		BLOOD_POOL = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_pool").toString(), ModFeatures.BLOOD_POOL);
		BLOOD_PILLAR_SINGLE = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_pillar_single").toString(), ModFeatures.BLOOD_PILLAR_SINGLE);
		BLOOD_PILLAR_CLUSTER = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_pillar_cluster").toString(), ModFeatures.BLOOD_PILLAR_CLUSTER);
		BLOOD_ROCK_PATCH = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_rock_patch").toString(), ModFeatures.BLOOD_ROCK_PATCH);
		BLOOD_CRYSTAL = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_crystal").toString(), Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.BLOOD_CRYSTAL.get())));
		BLOOD_BAT_TREE = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_bat_tree").toString(), ModFeatures.BLOOD_BAT_TREE);
		BLOOD_RUNE_PORTAL_FEATURE = FeatureUtils.register(new ResourceLocation(Main.MODID, "blood_rune_portal_feature").toString(), ModFeatures.RUNE_PORTAL_FEATURE, new BlockStateConfiguration(ModBlocks.BLOOD_RUNE_PORTAL.get().defaultBlockState()));
		
		FIRE_ROOT_PATCH = FeatureUtils.register(new ResourceLocation(Main.MODID, "fire_root_patch").toString(), Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.FIRE_ROOT.get().defaultBlockState(), 20).add(ModBlocks.FIRE_ROOT.get().defaultBlockState().setValue(FireRootBlock.AGE, 3), 10).add(ModBlocks.FIRE_ROOT.get().defaultBlockState().setValue(FireRootBlock.AGE, 6), 5))))));
		FIRE_PATCH = FeatureUtils.register(new ResourceLocation(Main.MODID, "fire_patch").toString(), Feature.RANDOM_PATCH, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FIRE.defaultBlockState())))));
	}
}
