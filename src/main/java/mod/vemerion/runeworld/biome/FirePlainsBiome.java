package mod.vemerion.runeworld.biome;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModPlacedFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FirePlainsBiome extends ModBiome {

	@Override
	public Biome create() {
		MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();

		generation.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.FIRE_ROOT_PATCH);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.FIRE_PATCH);

		int waterColor = Helper.color(200, 100, 0, 100);
		int skyColor = Helper.color(255, 155, 85, 255);
		int fogColor = Helper.color(255, 155, 85, 255);
		return new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.PLAINS)
				.temperature(2).downfall(0)
				.specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterColor)
						.fogColor(fogColor).skyColor(skyColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.build())
				.mobSpawnSettings(mobs.build()).generationSettings(generation.build()).build();
	}

}
