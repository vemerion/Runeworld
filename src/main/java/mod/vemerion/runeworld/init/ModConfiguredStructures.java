package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModConfiguredStructures {
	
	public static final DeferredRegister<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURES = DeferredRegister.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, Main.MODID);

	public static final RegistryObject<ConfiguredStructureFeature<?, ?>> BLOOD_BAT_LAIR = CONFIGURED_STRUCTURES.register("blood_bat_lair", () -> ModStructures.BLOOD_BAT_LAIR.get().configured(NoneFeatureConfiguration.INSTANCE, hasStructure(ModStructures.BLOOD_BAT_LAIR)));
	public static final RegistryObject<ConfiguredStructureFeature<?, ?>> FIRE_RITUAL = CONFIGURED_STRUCTURES.register("fire_ritual", () -> ModStructures.FIRE_RITUAL.get().configured(NoneFeatureConfiguration.INSTANCE, hasStructure(ModStructures.FIRE_RITUAL)));
	public static final RegistryObject<ConfiguredStructureFeature<?, ?>> BLOOD_GORILLA_THRONE = CONFIGURED_STRUCTURES.register("blood_gorilla_throne", () -> ModStructures.BLOOD_GORILLA_THRONE.get().configured(NoneFeatureConfiguration.INSTANCE, hasStructure(ModStructures.BLOOD_GORILLA_THRONE)));
	public static final RegistryObject<ConfiguredStructureFeature<?, ?>> BLOOD_MONKEY_TUNNELS = CONFIGURED_STRUCTURES.register("blood_monkey_tunnels", () -> ModStructures.BLOOD_MONKEY_TUNNELS.get().configured(new JigsawConfiguration(ModTemplatePools.BLOOD_MONKEY_TUNNELS_START.getHolder().get(), 6), hasStructure(ModStructures.BLOOD_MONKEY_TUNNELS), true));
	
	public static final TagKey<ConfiguredStructureFeature<?, ?>> BLOOD_MONKEY_TUNNELS_TAG = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Main.MODID, "blood_monkey_tunnels"));
	
	public static TagKey<Biome> hasStructure(RegistryObject<?> structure) {
		return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Main.MODID, "has_" + structure.getId().getPath()));
	}
}
