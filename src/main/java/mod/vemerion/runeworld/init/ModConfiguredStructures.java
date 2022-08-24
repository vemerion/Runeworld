package mod.vemerion.runeworld.init;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModConfiguredStructures {
	
	public static class Keys {
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> BLOOD_BAT_LAIR = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_bat_lair"));
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> FIRE_RITUAL = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "fire_ritual"));
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> BLOOD_GORILLA_THRONE = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_gorilla_throne"));
		public static final ResourceKey<ConfiguredStructureFeature<?, ?>> BLOOD_MONKEY_TUNNELS = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_monkey_tunnels"));
	}
	
	   public static final TagKey<Biome> HAS_BLOOD_BAT_LAIR = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_blood_bat_lair"));
	   public static final TagKey<Biome> HAS_FIRE_RITUAL = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_fire_ritual"));
	   public static final TagKey<Biome> HAS_BLOOD_GORILLA_THRONE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_blood_gorilla_throne"));
	   public static final TagKey<Biome> HAS_BLOOD_MONKEY_TUNNELS = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_blood_monkey_tunnels"));
	   
	public static final Holder<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_START = Pools.register(new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/throne_room").toString()), 1)), StructureTemplatePool.Projection.RIGID));


	public static Holder<ConfiguredStructureFeature<?, ?>> BLOOD_BAT_LAIR;
	public static Holder<ConfiguredStructureFeature<?, ?>> FIRE_RITUAL;
	public static Holder<ConfiguredStructureFeature<?, ?>> BLOOD_GORILLA_THRONE;
	public static Holder<ConfiguredStructureFeature<?, ?>> BLOOD_MONKEY_TUNNELS;

	public static void register() {
		Registry<ConfiguredStructureFeature<?, ?>> reg = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;

		BLOOD_BAT_LAIR = BuiltinRegistries.register(reg, Keys.BLOOD_BAT_LAIR, ModStructures.BLOOD_BAT_LAIR.configured(NoneFeatureConfiguration.INSTANCE, HAS_BLOOD_BAT_LAIR));
		FIRE_RITUAL = BuiltinRegistries.register(reg, Keys.FIRE_RITUAL, ModStructures.FIRE_RITUAL.configured(NoneFeatureConfiguration.INSTANCE, HAS_FIRE_RITUAL));
		BLOOD_GORILLA_THRONE = BuiltinRegistries.register(reg, Keys.BLOOD_GORILLA_THRONE, ModStructures.BLOOD_GORILLA_THRONE.configured(NoneFeatureConfiguration.INSTANCE, HAS_BLOOD_GORILLA_THRONE));
		BLOOD_MONKEY_TUNNELS = BuiltinRegistries.register(reg, Keys.BLOOD_MONKEY_TUNNELS, ModStructures.BLOOD_MONKEY_TUNNELS.configured(new JigsawConfiguration(BLOOD_MONKEY_TUNNELS_START, 6), HAS_BLOOD_MONKEY_TUNNELS, true));
	}
}
