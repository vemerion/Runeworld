package mod.vemerion.runeworld.biome;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModConfiguredFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BloodPlainsBiome extends ModBiome {

	@Override
	public Biome create() {
		MobSpawnInfo.Builder mobs = new MobSpawnInfo.Builder();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
				.withSurfaceBuilder(() -> SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
						ModBlocks.BLOOD_MOSS.getDefaultState(),
						ModBlocks.BLOOD_ROCK.getDefaultState(),
						ModBlocks.BLOOD_ROCK.getDefaultState())));

		generation.withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_POOL);
		generation.withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_PILLAR_SINGLE);
		generation.withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_PILLAR_CLUSTER);

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
