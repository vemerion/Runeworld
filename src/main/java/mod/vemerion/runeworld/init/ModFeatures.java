package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.feature.BloodBatTreeFeature;
import mod.vemerion.runeworld.feature.BloodPillarClusterFeature;
import mod.vemerion.runeworld.feature.BloodPillarSingleFeature;
import mod.vemerion.runeworld.feature.BloodPoolFeature;
import mod.vemerion.runeworld.feature.BloodRockPatchFeature;
import mod.vemerion.runeworld.feature.FleshEatingPlantFeature;
import mod.vemerion.runeworld.feature.RunePortalFeature;
import mod.vemerion.runeworld.feature.TopazFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
	
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Main.MODID);
	
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLOOD_POOL = FEATURES.register("blood_pool", BloodPoolFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLOOD_PILLAR_SINGLE = FEATURES.register("blood_pillar_single", BloodPillarSingleFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLOOD_PILLAR_CLUSTER = FEATURES.register("blood_pillar_cluster", BloodPillarClusterFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLOOD_ROCK_PATCH = FEATURES.register("blood_rock_patch", BloodRockPatchFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLOOD_BAT_TREE = FEATURES.register("blood_bat_tree", BloodBatTreeFeature::new);
	public static final RegistryObject<Feature<BlockStateConfiguration>> RUNE_PORTAL_FEATURE = FEATURES.register("rune_portal_feature", RunePortalFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLESH_EATING_PLANT = FEATURES.register("flesh_eating_plant", FleshEatingPlantFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> TOPAZ = FEATURES.register("topaz", TopazFeature::new);
}
