package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModConfiguredStructures {
	
	public static class Keys {
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> BLOOD_BAT_LAIR = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_bat_lair"));
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> FIRE_RITUAL = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "fire_ritual"));
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> BLOOD_GORILLA_THRONE = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_gorilla_throne"));
	}
	
	   public static final TagKey<Biome> HAS_BLOOD_BAT_LAIR = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_blood_bat_lair"));
	   public static final TagKey<Biome> HAS_FIRE_RITUAL = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_fire_ritual"));
	   public static final TagKey<Biome> HAS_BLOOD_GORILLA_THRONE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_blood_gorilla_throne"));


	public static Holder<ConfiguredStructureFeature<?, ?>> BLOOD_BAT_LAIR;
	public static Holder<ConfiguredStructureFeature<?, ?>> FIRE_RITUAL;
	public static Holder<ConfiguredStructureFeature<?, ?>> BLOOD_GORILLA_THRONE;

	public static void register() {
		Registry<ConfiguredStructureFeature<?, ?>> reg = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;

		BLOOD_BAT_LAIR = BuiltinRegistries.register(reg, Keys.BLOOD_BAT_LAIR, ModStructures.BLOOD_BAT_LAIR.configured(NoneFeatureConfiguration.INSTANCE, HAS_BLOOD_BAT_LAIR));
		FIRE_RITUAL = BuiltinRegistries.register(reg, Keys.FIRE_RITUAL, ModStructures.FIRE_RITUAL.configured(NoneFeatureConfiguration.INSTANCE, HAS_FIRE_RITUAL));
		BLOOD_GORILLA_THRONE = BuiltinRegistries.register(reg, Keys.BLOOD_GORILLA_THRONE, ModStructures.BLOOD_GORILLA_THRONE.configured(NoneFeatureConfiguration.INSTANCE, HAS_BLOOD_GORILLA_THRONE));
	}
}
