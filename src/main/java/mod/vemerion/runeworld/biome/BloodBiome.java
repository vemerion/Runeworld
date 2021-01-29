package mod.vemerion.runeworld.biome;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModConfiguredFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class BloodBiome extends ModBiome {

	@Override
	public Biome create() {
		MobSpawnInfo.Builder mobs = new MobSpawnInfo.Builder();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
				.withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);

		generation.withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_POOL);

		int waterColor = Helper.color(200, 70, 70, 100);
		int skyColor = Helper.color(255, 180, 180, 255);
		int fogColor = Helper.color(255, 192, 216, 255);
		return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.125F)
				.scale(0.05F).temperature(0.8F).downfall(0.4F)
				.setEffects((new BiomeAmbience.Builder()).setWaterColor(waterColor).setWaterFogColor(waterColor)
						.setFogColor(fogColor).withSkyColor(skyColor).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
						.build())
				.withMobSpawnSettings(mobs.copy()).withGenerationSettings(generation.build()).build();
	}

}
