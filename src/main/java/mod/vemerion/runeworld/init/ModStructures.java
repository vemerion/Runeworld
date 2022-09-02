package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import mod.vemerion.runeworld.structure.BloodMonkeyTunnelsStructure;
import mod.vemerion.runeworld.structure.FireRitualStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {
	
	public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Main.MODID);

	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> BLOOD_BAT_LAIR = STRUCTURES.register("blood_bat_lair", () -> new BloodBatLairStructure(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> FIRE_RITUAL = STRUCTURES.register("fire_ritual", () -> new FireRitualStructure(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<StructureFeature<JigsawConfiguration>> BLOOD_MONKEY_TUNNELS = STRUCTURES.register("blood_monkey_tunnels", () -> new BloodMonkeyTunnelsStructure(JigsawConfiguration.CODEC));
}
