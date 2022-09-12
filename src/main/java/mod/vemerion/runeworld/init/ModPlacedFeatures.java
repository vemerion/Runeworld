package mod.vemerion.runeworld.init;

import java.util.List;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures {
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Main.MODID);
	
	public static final RegistryObject<PlacedFeature> BLOOD_POOL = PLACED_FEATURES.register("blood_pool", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_POOL.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(200), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_PILLAR_SINGLE = PLACED_FEATURES.register("blood_pillar_single", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_PILLAR_SINGLE.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_PILLAR_CLUSTER = PLACED_FEATURES.register("blood_pillar_cluster", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_PILLAR_CLUSTER.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_ROCK_PATCH = PLACED_FEATURES.register("blood_rock_patch", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_ROCK_PATCH.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_CRYSTAL = PLACED_FEATURES.register("blood_crystal", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_CRYSTAL.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_BAT_TREE = PLACED_FEATURES.register("blood_bat_tree", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_BAT_TREE.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> BLOOD_RUNE_PORTAL_FEATURE = PLACED_FEATURES.register("blood_rune_portal_feature", () -> new PlacedFeature(ModConfiguredFeatures.BLOOD_RUNE_PORTAL_FEATURE.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(400), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> FLESH_EATING_PLANT = PLACED_FEATURES.register("flesh_eating_plant", () -> new PlacedFeature(ModConfiguredFeatures.FLESH_EATING_PLANT.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	
	public static final RegistryObject<PlacedFeature> FIRE_ROOT_PATCH = PLACED_FEATURES.register("fire_root_patch", () -> new PlacedFeature(ModConfiguredFeatures.FIRE_ROOT_PATCH.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(150), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> FIRE_PATCH = PLACED_FEATURES.register("fire_patch", () -> new PlacedFeature(ModConfiguredFeatures.FIRE_PATCH.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> TOPAZ = PLACED_FEATURES.register("topaz", () -> new PlacedFeature(ModConfiguredFeatures.TOPAZ.getHolder().get(), List.of(CountPlacement.of(90), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> CAIRN = PLACED_FEATURES.register("cairn", () -> new PlacedFeature(ModConfiguredFeatures.CAIRN.getHolder().get(), List.of(CountPlacement.of(90), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlock(ModBlocks.CHARRED_STONE.get(), Vec3i.ZERO), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome())));
}
