package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures {

	public static StructureFeature<?, ?> BLOOD_BAT_LAIR;
	public static StructureFeature<?, ?> FIRE_RITUAL;

	public static void register() {
		Registry<StructureFeature<?, ?>> reg = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

		BLOOD_BAT_LAIR = ModStructures.BLOOD_BAT_LAIR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
		FIRE_RITUAL = ModStructures.FIRE_RITUAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

		Registry.register(reg, new ResourceLocation(Main.MODID, "blood_bat_lair"), BLOOD_BAT_LAIR);
		Registry.register(reg, new ResourceLocation(Main.MODID, "fire_ritual"), FIRE_RITUAL);
	}
}
