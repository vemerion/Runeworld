package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import mod.vemerion.runeworld.structure.BloodGorillaThroneStructure;
import mod.vemerion.runeworld.structure.FireRitualStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModStructures {

	private static boolean init = true;

	public static StructureFeature<NoneFeatureConfiguration> BLOOD_BAT_LAIR;
	public static StructureFeature<NoneFeatureConfiguration> FIRE_RITUAL;
	public static StructureFeature<NoneFeatureConfiguration> BLOOD_GORILLA_THRONE;

	public static void init() {
		if (init) {
			init = false;

			BLOOD_BAT_LAIR = new BloodBatLairStructure(NoneFeatureConfiguration.CODEC);
			FIRE_RITUAL = new FireRitualStructure(NoneFeatureConfiguration.CODEC);
			BLOOD_GORILLA_THRONE = new BloodGorillaThroneStructure(NoneFeatureConfiguration.CODEC);

			ModConfiguredStructures.register();
			
			ModStructureSets.register();
		}
	}

	@SubscribeEvent
	public static void onRegisterStructure(RegistryEvent.Register<StructureFeature<?>> event) {
		init();

		IForgeRegistry<StructureFeature<?>> reg = event.getRegistry();

		reg.register(Init.setup(BLOOD_BAT_LAIR, new ResourceLocation(Main.MODID, "blood_bat_lair")));
		reg.register(Init.setup(FIRE_RITUAL, new ResourceLocation(Main.MODID, "fire_ritual")));
		reg.register(Init.setup(BLOOD_GORILLA_THRONE, new ResourceLocation(Main.MODID, "blood_gorilla_throne")));

		ModStructurePieces.register();
	}
}
