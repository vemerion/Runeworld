package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.feature.TopazFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModConfiguredFeatures {
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Main.MODID);
	
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_POOL = CONFIGURED_FEATURES.register("blood_pool", () -> new ConfiguredFeature<>(ModFeatures.BLOOD_POOL.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_PILLAR_SINGLE = CONFIGURED_FEATURES.register("blood_pillar_single", () -> new ConfiguredFeature<>(ModFeatures.BLOOD_PILLAR_SINGLE.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_PILLAR_CLUSTER = CONFIGURED_FEATURES.register("blood_pillar_cluster", () -> new ConfiguredFeature<>(ModFeatures.BLOOD_PILLAR_CLUSTER.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_ROCK_PATCH = CONFIGURED_FEATURES.register("blood_rock_patch", () -> new ConfiguredFeature<>(ModFeatures.BLOOD_ROCK_PATCH.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_CRYSTAL = CONFIGURED_FEATURES.register("blood_crystal", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.BLOOD_CRYSTAL.get()))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_BAT_TREE = CONFIGURED_FEATURES.register("blood_bat_tree", () -> new ConfiguredFeature<>(ModFeatures.BLOOD_BAT_TREE.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLOOD_RUNE_PORTAL_FEATURE = CONFIGURED_FEATURES.register("blood_rune_portal_feature", () -> new ConfiguredFeature<>(ModFeatures.RUNE_PORTAL_FEATURE.get(), new BlockStateConfiguration(ModBlocks.BLOOD_RUNE_PORTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<?, ?>> FIRE_ROOT_PATCH = CONFIGURED_FEATURES.register("fire_root_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.FIRE_ROOT.get().defaultBlockState(), 20).add(ModBlocks.FIRE_ROOT.get().defaultBlockState().setValue(FireRootBlock.AGE, 3), 10).add(ModBlocks.FIRE_ROOT.get().defaultBlockState().setValue(FireRootBlock.AGE, 6), 5)))))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> FIRE_PATCH = CONFIGURED_FEATURES.register("fire_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FIRE.defaultBlockState()))))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> FLESH_EATING_PLANT = CONFIGURED_FEATURES.register("flesh_eating_plant", () -> new ConfiguredFeature<>(ModFeatures.FLESH_EATING_PLANT.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> TOPAZ = CONFIGURED_FEATURES.register("topaz", () -> new ConfiguredFeature<>(ModFeatures.TOPAZ.get(), FeatureConfiguration.NONE));
}
