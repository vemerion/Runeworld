package mod.vemerion.runeworld.biome;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class FirePlainsBiome extends ModBiome {

	@Override
	public Biome create() {
		MobSpawnInfo.Builder mobs = new MobSpawnInfo.Builder();
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()
				.withSurfaceBuilder(() -> SurfaceBuilder.DEFAULT
						.func_242929_a(new SurfaceBuilderConfig(ModBlocks.BURNT_DIRT.getDefaultState(),
								ModBlocks.CHARRED_DIRT.getDefaultState(), ModBlocks.CHARRED_DIRT.getDefaultState())));

		generation.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_LAVA);

		int waterColor = Helper.color(200, 100, 0, 100);
		int skyColor = Helper.color(255, 155, 85, 255);
		int fogColor = Helper.color(255, 155, 85, 255);
		return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.PLAINS).depth(0.125F)
				.scale(0.05F).temperature(2).downfall(0)
				.setEffects((new BiomeAmbience.Builder()).setWaterColor(waterColor).setWaterFogColor(waterColor)
						.setFogColor(fogColor).withSkyColor(skyColor).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
						.build())
				.withMobSpawnSettings(mobs.copy()).withGenerationSettings(generation.build()).build();
	}

}
