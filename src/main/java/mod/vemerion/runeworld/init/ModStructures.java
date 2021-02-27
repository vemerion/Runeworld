package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModStructures {

	private static boolean init = true;

	public static Structure<NoFeatureConfig> BLOOD_BAT_LAIR;

	public static void init() {
		if (init) {
			init = false;

			BLOOD_BAT_LAIR = setupStructure(new BloodBatLairStructure(NoFeatureConfig.field_236558_a_));

			ModConfiguredStructures.register();
		}
	}

	private static <C extends IFeatureConfig, T extends Structure<C>> T setupStructure(T structure) {
		Structure.NAME_STRUCTURE_BIMAP.put(structure.getStructureName(), structure);
		return structure;
	}

	@SubscribeEvent
	public static void onRegisterStructure(RegistryEvent.Register<Structure<?>> event) {
		init();

		event.getRegistry()
				.register(Init.setup(BLOOD_BAT_LAIR, new ResourceLocation(BLOOD_BAT_LAIR.getStructureName())));

		ModStructurePieces.register();
	}
}
