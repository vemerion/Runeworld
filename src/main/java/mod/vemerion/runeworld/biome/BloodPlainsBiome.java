package mod.vemerion.runeworld.biome;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModPlacedFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BloodPlainsBiome extends ModBiome {

	@Override
	public Biome create() {
		MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();

		generation.addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.BLOOD_POOL.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
				ModPlacedFeatures.BLOOD_PILLAR_SINGLE.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
				ModPlacedFeatures.BLOOD_PILLAR_CLUSTER.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.BLOOD_CRYSTAL.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
				ModPlacedFeatures.BLOOD_ROCK_PATCH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.BLOOD_BAT_TREE.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
				ModPlacedFeatures.BLOOD_RUNE_PORTAL_FEATURE.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
				ModPlacedFeatures.FLESH_EATING_PLANT.getHolder().get());

		mobs.addSpawn(MobCategory.MONSTER, new SpawnerData(ModEntities.MOSQUITO.get(), 10, 1, 2));

		int waterColor = Helper.color(200, 70, 70, 100);
		int skyColor = Helper.color(255, 180, 180, 255);
		int fogColor = Helper.color(255, 192, 216, 255);
		return new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.PLAINS)
				.temperature(0.8F).downfall(0.4F)
				.specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterColor)
						.fogColor(fogColor).skyColor(skyColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.build())
				.mobSpawnSettings(mobs.build()).generationSettings(generation.build()).build();
	}

}
