package mod.vemerion.runeworld.init;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacedFeatures {
	
	   public static final Holder<PlacedFeature> BLOOD_POOL = PlacementUtils.register("blood_pool", ModConfiguredFeatures.BLOOD_POOL, RarityFilter.onAverageOnceEvery(200), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_PILLAR_SINGLE = PlacementUtils.register("blood_pillar_single", ModConfiguredFeatures.BLOOD_PILLAR_SINGLE, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_PILLAR_CLUSTER = PlacementUtils.register("blood_pillar_cluster", ModConfiguredFeatures.BLOOD_PILLAR_CLUSTER, RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_ROCK_PATCH = PlacementUtils.register("blood_rock_patch", ModConfiguredFeatures.BLOOD_ROCK_PATCH, RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_CRYSTAL = PlacementUtils.register("blood_crystal", ModConfiguredFeatures.BLOOD_CRYSTAL, RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_BAT_TREE = PlacementUtils.register("blood_bat_tree", ModConfiguredFeatures.BLOOD_BAT_TREE, RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> BLOOD_RUNE_PORTAL_FEATURE = PlacementUtils.register("blood_rune_portal_feature", ModConfiguredFeatures.BLOOD_RUNE_PORTAL_FEATURE, RarityFilter.onAverageOnceEvery(400), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> FLESH_EATING_PLANT = PlacementUtils.register("flesh_eating_plant", ModConfiguredFeatures.FLESH_EATING_PLANT, RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   
	   public static final Holder<PlacedFeature> FIRE_ROOT_PATCH = PlacementUtils.register("fire_root_patch", ModConfiguredFeatures.FIRE_ROOT_PATCH, RarityFilter.onAverageOnceEvery(150), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	   public static final Holder<PlacedFeature> FIRE_PATCH = PlacementUtils.register("fire_patch", ModConfiguredFeatures.FIRE_PATCH, RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
}
